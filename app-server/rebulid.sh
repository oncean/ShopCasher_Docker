#!/bin/sh

echo "************delete old targert**********************"
rm -rf target

#maven打包
echo "************mvn install *********"
mvn install


apachehome=/home/apache-tomcat-9.0.5

echo "************delete old war and dir*********"
rm -rf ${apachehome}/webapps/ShopCasher.war
rm -rf ${apachehome}/webapps/ShopCasher


echo "************copy new war and dir*********"
cp target/ShopCasher.war ${apachehome}/webapps/


echo "************restart service*********"
${apachehome}/bin/shutdown.sh
${apachehome}/bin/startup.sh
