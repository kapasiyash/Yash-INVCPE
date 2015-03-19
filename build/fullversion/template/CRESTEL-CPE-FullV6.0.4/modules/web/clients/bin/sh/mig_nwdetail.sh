#!/bin/sh

if [ -z $1 ]
then
        echo "Give input csv file as argument (without csv suffix) "
        exit;
fi

test -f $1.csv
if [ $? -ne 0 ]
then
        echo "The given input file $1.csv does not exist"
        echo "make sure that the file exists, and readable"
        echo "also, ensure that you have not given .csv in the put for the filename";
        exit;
fi

IP_FNAME=mig_ipaddress
BW_FNAME=mig_bandwidth
EXT=sql


if [  -f ${IP_FNAME}_${1}.${EXT} ]
then
        echo "Taking backup of ${IP_FNAME}_${1}.${EXT} TO ${IP_FNAME}_${1}_backup.${EXT}"
        mv -f ${IP_FNAME}_${1}.${EXT} ${IP_FNAME}_${1}_backup.${EXT}.
fi

if [  -f ${BW_FNAME}_${1}.${EXT} ]
then
        echo "Taking backup of ${BW_FNAME}_${1}.${EXT} TO ${BW_FNAME}_${1}_backup.${EXT}"
        mv -f ${BW_FNAME}_${1}.${EXT} ${BW_FNAME}_${1}_backup.${EXT}.
fi


if [ -f ${IP_FNAME}.${EXT} ]
then
        echo "Removing existing file: ${IP_FNAME}.${EXT}"
        rm -f ${IP_FNAME}.${EXT}
fi

if [ -f ${BW_FNAME}.${EXT} ]
then
        echo "Removing existing file: ${BW_FNAME}.${EXT}"
        rm -f ${BW_FNAME}.${EXT}
fi

echo ""
echo "Generating files..."
echo ""


cat $1.csv | awk -F "," ' BEGIN {
  USER_COL = 0;
  IP_COL = 0;
  BW_COL=0 ;
  row = 0;
  IP_FILE = "mig_ipaddress.sql";
  BW_FILE = "mig_bandwidth.sql"
  bwformat = "INSERT INTO TBLMRADIUSPOLICYPARAMDETAIL ( RADIUSPOLICYID, RADIUSPOLICYPARAMDETAILID,DICTIONARYPARAMETERNAME, VALUE, VENDORPARAMETERID, DATATYPENAME, PARAMETERUSAGE, CUSTOMERID,DISPLAYVALUE, VENDORPARAMETEROVERRIDDEN, ACTIVE, IANAPVTENTNUMBER, DICTIONARYNUMBER, OPERATORID,RADIUSRFCDICTIONARYPARAMETERID ) SELECT  %cC0000000%c, seqradiuspolicyparamdetail.nextval, %c24Online-AVPair%c, %c%s%c, %c0%c, %cstring%c, %cR%c, B.customerid, %c%s%c, %cY%c, %cY%c, 21067, 4, 1, 26 FROM tblmaccount A, tblmcustomer B WHERE A.accountid = B.accountid AND A.username = %c%s%c; \n";
  ipformat = "INSERT INTO TBLMRADIUSPOLICYPARAMDETAIL ( RADIUSPOLICYID, RADIUSPOLICYPARAMDETAILID,DICTIONARYPARAMETERNAME, VALUE, VENDORPARAMETERID, DATATYPENAME, PARAMETERUSAGE, CUSTOMERID,DISPLAYVALUE, VENDORPARAMETEROVERRIDDEN, ACTIVE, IANAPVTENTNUMBER, DICTIONARYNUMBER, OPERATORID,RADIUSRFCDICTIONARYPARAMETERID )  SELECT  %cC0000000%c, seqradiuspolicyparamdetail.nextval, %cFramed-IP-Address%c, %c%s%c, %c0%c, %cipaddr%c, %cC%c, B.customerid, %c%s%c, %cN%c, %cY%c, 0, 1, 1, 8 FROM tblmaccount A, tblmcustomer B WHERE A.accountid = B.accountid AND A.username = %c%s%c; \n";
} {
  row ++;
  if( row == 1) {
    for (i = 1; i<=NF ; i++ )  {
      if($i == "Customer IP Address") {
        IP_COL = i ;
      } else if($i == "Username") {
        USER_COL = i ;
      } else if($i == "Bandwidth Policy") {
        BW_COL = i ;
      }
    }
    if (USER_COL == 0) {
      printf "Username column not found in the header, \n  make sure that header has [username] as the column title\n";
    }
    if (IP_COL == 0) {
      printf "IP Address column not found in the header, \n  make sure that header has [Customer IP Address] as the column title\n";
    }
    if (BW_COL == 0) {
      printf "Bandwidth column not found in the header, \n  make sure that header has [Bandwidth Policy] as the column titlei\n";
    }
    if (USER_COL == 0 || IP_COL == 0 || BW_COL == 0) {
      printf "Columns not found.. Stopping the processing\n";
      exit 1;
    }
    printf "Username Column: %d\nCustomer IP Address Column: %d\nBandwidth Policy Column: %d\n",USER_COL, IP_COL, BW_COL;
    printf "\nProcessing records..."
  } else if (BW_COL ==0 || IP_COL ==0) {
    print "Bandwidth or IP Column not found";
  } else if($USER_COL == "" || $USER_COL == "-") {
    printf "Username not found in row: %03d\n",row;
  } else {
    username = $USER_COL;
    if ($IP_COL != "" && $IP_COL != "-") {
      split($IP_COL, iparray, ";");
      ipiter = 1;
      while(iparray[ipiter] != "") {
        if(iparray[ipiter] != "-") {
          printf ipformat,39,39,39,39,39,iparray[ipiter],39,39,39,39,39,39,39,39,iparray[ipiter],39,39,39,39,39,39,tolower(username),39 > IP_FILE;
       } else {
          printf "invalid ip address(%s) in row: %000d\n", iparray[ipiter], row;
        }
        ipiter ++;
      }
    }
    if ($BW_COL != "" && $BW_COL != "-") {
      split($BW_COL, bwarray, ";");
      bwiter = 1;
      while(bwarray[bwiter] != "") {
        if(bwarray[bwiter] != "-") {
          printf bwformat, 39,39,39,39,39,  bwarray[bwiter], 39,39,39,39,39,39,39,39,  bwarray[bwiter], 39,39,39,39,39,39, tolower(username),39 > BW_FILE;
        } else {
          printf "invalid bandwidth(%s) in row: %000d\n", bwarray[bwiter], row;
        }
        bwiter ++;
      }
    }
  }
} END {
  printf "\ncommit;\nexit;\n" >> IP_FILE
  printf "\ncommit;\nexit;\n" >> BW_FILE
} '
echo "files generated successfully"
echo "renaming files.."
mv -f ${IP_FNAME}.${EXT} ${IP_FNAME}_${1}.${EXT}
mv -f ${BW_FNAME}.${EXT} ${BW_FNAME}_${1}.${EXT}
echo ""
echo "Files available are:"
echo "   IP Address: ${IP_FNAME}_${1}.${EXT}"
echo "   Bandwidth : ${BW_FNAME}_${1}.${EXT}"
echo ""
echo "Counts summary..."
wc -l ${1}.csv
wc -l ${IP_FNAME}_${1}.${EXT}
wc -l ${BW_FNAME}_${1}.${EXT}
echo ""
echo "Execute following statements in the oracle user to migrate the ip and bandwidth information"
echo "   sqlplus {dbuser}/{dbpassword}@{dbservice} @${IP_FNAME}_${1}.${EXT}"
echo "   sqlplus {dbuser}/{dbpassword}@{dbservice} @${BW_FNAME}_${1}.${EXT}"
echo ""


