import com.Ostermiller.util.CSVParser;
import java.util.PropertyResourceBundle;
import java.util.HashMap;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * class to faclitate exception processing
 */
class MyException extends Exception
{
	public MyException(String strMessage)
	{
		super(strMessage);
	}
};

/**
 * class SLADataReader
 */
public class SLADataReader
{

/**
 * Main method
 */
	public static void main(String[] args) throws IOException
	{
		try
		{
//obtain csv parser
			CSVParser csvParser = getCSVParser(args);
//validate header
			validateHeader(csvParser.getLine());
//obtain data base connection
			Connection conn = createConnection(args[1]);
//create batch entry
			String[] strBatchDetail = createBatch(conn);
//create batch detail entries
			HashMap hEntryDetails=createBatchDetailEntries(conn, csvParser, strBatchDetail);
//update batch record count
			updateBatchCounts(conn, strBatchDetail[0], hEntryDetails);
			System.out.println("CSV Successfully Processed. Batch Number = "+strBatchDetail[1]);
		}
		catch(MyException myException)
		{
			System.out.println(myException.getMessage());
		}
	}

/**
 *  Method to get csv parser
 */
	private static CSVParser getCSVParser(String[] args) throws MyException
	{
		if(args.length > 0)
		{
			String strFileName = args[0];
			if(strFileName != null && strFileName.length() > 0)
			{
				try
				{
					CSVParser csvParser = new CSVParser(new FileReader(strFileName));
					if(csvParser != null)
						return csvParser;
					else
						throw (new MyException("Error in Processing File "+strFileName+"......"));
				}
				catch(IOException e)
				{
					throw (new MyException("Error in Processing File "+strFileName+"......"));
				}
			}
			else
				throw (new MyException("Improper arguments......"));
		}
		else
			throw (new MyException("No Arguments Provided......"));
	}

/**
 *  Method to validate header
 */
	private static void validateHeader(String[] strHeaders) throws MyException
	{
		boolean bFlag = true;
		if(strHeaders.length != 9)
			throw (new MyException("Improper header......"));
		ArrayList arrHeaders = new ArrayList();
		arrHeaders.add("sr.no.");
		arrHeaders.add("date");
		arrHeaders.add("customer reference");
		arrHeaders.add("sitename");
		arrHeaders.add("availability(in %)");
		arrHeaders.add("latency(in ms)");
		arrHeaders.add("jitter(in ms)");
		arrHeaders.add("throughput(in %)");
		arrHeaders.add("mttr(in hr.)");
		for(int iCounter = 0;iCounter < strHeaders.length;iCounter++ )
			if(!arrHeaders.contains(strHeaders[iCounter].toLowerCase()))
				throw (new MyException("Improper header......"));
	}

/**
 *  Method to create connection
 */
	private static Connection createConnection(String strFilePath) throws MyException
	{
		try
		{
			PropertyResourceBundle bundle = new PropertyResourceBundle(new FileInputStream(strFilePath));
	        String dataBaseUrl = bundle.getString("dataBaseUrl");
		    String userName = bundle.getString("userName");
			String passWord = bundle.getString("passWord");
			Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(dataBaseUrl, userName, passWord);
			return connection;
		}
		catch(FileNotFoundException e)
		{
			throw (new MyException("Database Connection Properties File Not Found......"));
		}
		catch(ClassNotFoundException e)
		{
			throw (new MyException("Unbale to Register Driver......"));
		}
		catch(IOException e)
		{
			throw (new MyException("Error While Reading Properties File......"));
		}
		catch(SQLException e)
		{
			throw (new MyException("Unable to Get DataBase Connection......"));
		}
	}

/**
 *  Method to create batch
 */
	private static String[] createBatch(Connection conn) throws MyException
	{
		String strMigrationId = null;
		String strMigrationNumber = null;
		try
		{
			Statement smt = conn.createStatement();
//obtaining pk from sequence block
			//ResultSet rowSet = smt.executeQuery("SELECT idx FROM SEQUENCE_BLOCK WHERE name = 'TBLTMIGRATIONBATCH.MIGRATIONBATCHNUMBER'");
			//if(rowSet != null && rowSet.next())
			//{
			//	int iMigrationNumber = rowSet.getInt("idx")+1;
			//	strMigrationId = "MBT000"+(String.valueOf(iMigrationNumber));
			//	strMigrationNumber = "MBAT0000"+(String.valueOf(iMigrationNumber));
			//	int iCount = smt.executeUpdate("UPDATE SEQUENCE_BLOCK SET idx = " + iMigrationNumber + " WHERE name = 'TBLTMIGRATIONBATCH.MIGRATIONBATCHNUMBER'");
			//	if(iCount <= 0)
			//		throw (new MyException("Error While Creating Migration Batch Record......"));
			//}
			//else
			//	throw (new MyException("Error While Creating Migration Batch Record......"));

			// Get Migration Batch Id
			ResultSet rowSet = smt.executeQuery("select 'EXT' || LPAD((SUBSTR(MAX(MIGRATIONBATCHID),4)+1),7,0) mbid from TBLTMIGRATIONBATCH where MIGRATIONBATCHID like 'EXT%'");
			if(rowSet != null && rowSet.next())
			{
				strMigrationId = rowSet.getString("mbid");
			}
			else
				throw (new MyException("Error While Creating Migration Batch Id......"));

			// Get Migration Batch Number
			rowSet = smt.executeQuery("select 'EXBT' || LPAD((SUBSTR(MAX(MIGRATIONBATCHNUMBER),5)+1),8,0) mbnumber from TBLTMIGRATIONBATCH where MIGRATIONBATCHNUMBER like 'EXBT%'");
			if(rowSet != null && rowSet.next())
			{
				strMigrationNumber = rowSet.getString("mbnumber");
			}
			else
				throw (new MyException("Error While Creating Migration Batch Number......"));
			
			
//creating new batch record
			int iCount = smt.executeUpdate("insert into tbltmigrationbatch(migrationbatchid, migrationbatchnumber, " +
			"uploaddate, totalentries, validentries, invalidentries, createactiondate, updateactiondate, "+
			"datasourceid, migrationbatchactionid, scheduledate, description, migrationbatchstatusid ) values "+
			"('"+strMigrationId+"','"+strMigrationNumber+"',sysdate,null,null,null,null,null,'DTS0002','MBA25',"+
			"sysdate,'SLA Records','MBS01')");
			if(iCount <= 0)
				throw (new MyException("Error While Creating Migration Batch Record......"));
		}
		catch(SQLException e)
		{
			throw (new MyException("Error While Creating Migration Batch Record......"));
		}
		return new String[]{strMigrationId,strMigrationNumber};
	}

/**
 *  Method to create batch
 */
	private static HashMap	createBatchDetailEntries(Connection conn,CSVParser csvParser,String[] strBatchDetail) throws MyException
	{
		try
		{
			PreparedStatement psmt = conn.prepareStatement("insert into tbltmigrationbatchdetail ( migrationbatchid,"+
			"serialnumber, migrationbatchnumber,username,createdate,"+
			"sidnumber,migrationstatusid, externalreferenceid, accountprofileparam1,"+
			"accountprofileparam2, accountprofileparam3, accountprofileparam4, accountprofileparam5,migrationerrormessage)"+ 
			"values ( '"+strBatchDetail[0]+"', ?, '"+strBatchDetail[1]+"', ?,  ?"+
			", ?, ? , ?, ?, ?, ?, ?, ?,?)"); 
			
			String[] strRecord;
			int iTotalCount = 0;
			int iValidCount = 0;
			for(strRecord = csvParser.getLine();strRecord != null;strRecord = csvParser.getLine())
			{
				iTotalCount++;
				iValidCount += processSingleRecord(psmt,conn,strRecord);
			}
			HashMap hEntryCount = new HashMap();
			hEntryCount.put("TOTAL_ENTRIES",new Integer(iTotalCount));
			hEntryCount.put("VALID_ENTRIES",new Integer(iValidCount));
			return hEntryCount;

		}
		catch(Exception e)
		{
			throw (new MyException("Error While Creating Migration Batch Detail Record......"));
		}
	}

/**
 * Method to process single record
 */
	private static int processSingleRecord(PreparedStatement psmt,Connection conn,String[] strRecord) throws MyException
	{
		int iValid=0;
		try
		{
//setting serial number
			psmt.setInt(1,(new Integer(strRecord[0])).intValue());
//setting reference id
			String strRefId = strRecord[2];
			psmt.setString(6,strRefId);
//getting username
			String strUserName = getUserName(conn,strRefId);
//getting validity message
			String strValidityMessage = validateRecord(strRecord);
//setting username
			if(strUserName!=null && strUserName.length() > 0)
				psmt.setString(2,strUserName);
			else
			{
				psmt.setString(2,strRefId);
				if(strValidityMessage != null && strValidityMessage.length() > 0)
					strValidityMessage += ", Invalid Reference Id";
				else
					strValidityMessage = "Invalid Reference Id";
			}
//setting validity status
			if(strUserName!=null && strUserName.length() > 0 && strValidityMessage.equals("Successfully Validated"))
			{
				iValid=1;
				psmt.setString(5,"MST0002");
			}
			else
				psmt.setString(5,"MST0001");
//setting error message
			psmt.setString(12,strValidityMessage);
//setting date
			psmt.setString(3,strRecord[1]);
//setting site id
			psmt.setString(4,strRecord[3]);
//setting sla parameters
			psmt.setString(7,strRecord[4]);
			psmt.setString(8,strRecord[5]);
			psmt.setString(9,strRecord[6]);
			psmt.setString(10,strRecord[7]);
			psmt.setString(11,strRecord[8]);
//executing query
			psmt.execute();
		}
		catch(Exception e)
		{
			throw (new MyException("Error While Processing Single Record Record......"));
		}
		return iValid;
	}

/**
 * Method to obtain user name
 */
	private static String getUserName(Connection conn,String strRefId)
	{
		try
		{
			Statement smt = conn.createStatement();
			ResultSet rowSet = smt.executeQuery("SELECT username FROM tblmaccount WHERE accountid IN (SELECT accountid FROM tblmcustomer WHERE externalreferenceid = '"+strRefId+"')");
			if(rowSet != null && rowSet.next())
				return rowSet.getString("username");
			else
				return null;
		}
		catch(SQLException e)
		{
			return null;
		}
	}

/**
 *  Method to validate record
 */
	private static String validateRecord(String[] strRecord)
	{
//validating serial number
		String strSNo = strRecord[0];
		if(!validNumber(strSNo))
			return "Invalid Serial Number";
//validating date
		String strDate = strRecord[1];
		if(!validDate(strDate))
			return "Invalid Date Value";
//validating parameters
		String strParameter = null;
		for(int i = 4;i < 9; i++)
		{
			strParameter = strRecord[i];
			if(strParameter.trim().length() > 0 && !validNumber(strParameter))
				return "Invalid Parameter Value";
		}
		return "Successfully Validated";
	}

/**
 * Validating number
 */
	private static boolean validNumber(String str)
	{
		try
		{
			new Float(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

/**
 * Validating date
 */
	private static boolean validDate(String str)
	{
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			Date recordingDate = dateFormat.parse(str);
			Date toDay = new Date();
			if(recordingDate.after(toDay))
				return false;
			if(recordingDate == null)
				return false;
		}
		catch(ParseException e)
		{
			return false;
		}
		return true;
	}
/**
 * Updating migration batch fields for entries
 */
	private static void updateBatchCounts(Connection conn, String strBatchId, HashMap hEntryDetails) throws MyException
	{
		try
		{
			int iTotalCount = ((Integer)hEntryDetails.get("TOTAL_ENTRIES")).intValue();
			int iValidCount = ((Integer)hEntryDetails.get("VALID_ENTRIES")).intValue();
			int iInValidCount = iTotalCount - iValidCount;

			Statement smt = conn.createStatement();
			String strQuery = "update tbltmigrationbatch set totalentries = "+ iTotalCount +", validentries = " +
			iValidCount+", invalidentries= "+iInValidCount+" where migrationbatchid = '"+strBatchId+"'";
			int iCount = smt.executeUpdate(strQuery);
			if(iCount <= 0)
				throw (new MyException("Error While Updating Migration Batch Record......"));
		}
		catch(SQLException e)
		{
			throw (new MyException("Error While Updating Migration Batch Record......"));
		}
	}
}