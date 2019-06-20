# 端口详情


  ##** monitor-admin:      192.168.1.106:5000  **
 
 
 
 
  
  ##**kafka-consumer-service:   192.168.1.50:8773**
   ##**terminal-monitor-server:  192.168.1.50:8766**
  ##**   web-service     192.168.1.50:8781 **
  ##**kafka-sender-service:     192.168.1.50:8768**


 ##**ws-push-service:  192.168.1.100:8771**
  ##**ws-push-http      192.168.1.100:8779**
 ##**voice-monitor-server-http  192.168.1.100:8780** 
 ##**voice-sender-service:     192.168.1.100:8767**
 ##**station-monitor-server:   192.168.1.100:8765**
 
  

##**config-server:             192.168.1.101:8769**
 ##**config-server-backups:    192.168.1.101:8770**
 ##**gateway-service:          192.168.1.101:8443** 
 ##**eureka-server:            192.168.1.101:8761**
 ##**eureka-server-backups:    192.168.1.101:8762**
 
 
 
 
  ************************************废弃开始**********************************************
   ##**station-partition-service:  192.168.1.106:8776**
   ##**staff-group-terminal-service:   192.168.1.106:8772**
   ##**gas-service:          192.168.1.50:8774**
   ##**chat-message-service:  192.168.1.50:8777**
   ##** attendance-service:   192.168.1.106:8775**
   ##** system-service            192.168.1.101:8778** 
      
  *************************************废弃结束*********************************************
 
 
 

 ##软件License授权过程：
 ###1、利用jdk的keytool工具生成公私密钥库
            1）、执行命令：keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "d:\privateKeys.keystore" -storepass "public_password1234" -keypass "private_password1234" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"
 			（解释：公钥库密码为：public_password1234，私钥库密码为：private_password1234 ，生成路径为 d:\）
 		2）、keytool -exportcert -alias "privateKey" -keystore "d:\privateKeys.keystore" -storepass "public_password1234" -file "d:\certfile.cer"
 		3）、keytool -import -alias "publicCert" -file "d:\certfile.cer" -keystore "d:\publicCerts.keystore" -storepass "public_password1234"
 
 		上述命令执行完成之后，会在当前路径下生成三个文件，分别是：privateKeys.keystore、publicCerts.keystore、certfile.cer。其中文件certfile.cer不再需要可以删除，文件privateKeys.keystore用于当前的项目给客户生成license文件，而文件publicCerts.keystore则随应用代码部署到客户服务器，用户解密license文件并校验其许可信息。
 ###2、点击手动生成，访问密钥生成项目输入想要输入的信息（必须与生成密钥是的信息保持一致）
 ###3、将publicCerts.keystore 和license.lic 部署到客户的网关服务中，启动网关服务。
 	

 
 

 


