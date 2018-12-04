# java-sftp-example
A simple java example to get file listing from SFTP

### Requirements
* jdk 1.7+
* maven

### To run locally
* Clone respository
* Edit Sftp.java to provide sftp details
* `mvn install`
* `mvn compile`
* `mvn exec:java -Dexec.mainClass="com.sftp.Sftp"`

### Todo:

* Add download, remove capabilities
* Take host, username, password from env variables or properties file
