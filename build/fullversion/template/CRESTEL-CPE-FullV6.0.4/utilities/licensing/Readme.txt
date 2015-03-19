Licensing Module 5.1.2.08
############################################################

Steps to Install Version:

1) Copy the publickeygenerator.tar.gz in $BILLING_HOME
2) extract the archive using  tar xvzf publickeygenerator.tar.gz
3) Now go to the directory scripts using     cd  $BILL_HOME/scripts 
4) for creating the public key file for local machine run
      sh createPublicKey.sh local
5) For creating the public key file for different machine
   Specify the information in properties file & run
       sh createPublicKey.sh file_name
    
    