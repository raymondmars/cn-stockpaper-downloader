###上海证券报付费用户PDF下载器    
我一直比较喜欢[上海证券报](http://paper.cnstock.com/), 也订阅了它的电子版。但很无奈的是，它的网络版本在WEB上的阅读体验很差，而我又是一个比较注重阅读体验的人 :) 。    
怎么办呢？后来发现了它的PDF版本和纸质版完全采用相同的风格，而这，一直以来是我最喜欢纸质版本的原因。如果能一次自动下载好一期的所有PDF文件，那么阅读起来真是一件很爽的事情。   
简单看了一下网站结构，索性用Java写了一个小程序来处理自动下载。

##运行时要求：   
Java 1.8  

##打包:
mvn package   

##使用
程序会把所有依赖打包成一个可执行的 Jar 文件，使用时，只需要执行：  
  
a. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar             # 默认下载当天的所有PDF     

b. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar yyyy-MM-dd  # 下载指定某一天的所有PDF     

c. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar yyyy-MM-dd username password  # 下载指定某一天的所有PDF,并指定登陆用户名和密码     

###注意：
a 和 b 如果不指定用户名和密码，请将用户名和密码设置到环境变量里面，程序会自动读取。 用户名和密码的环境变量名称需要设置为：_CNSTOCK_USERNAME_ 和 _CNSTOCK_PASSWORD_     
记住这个只是针对订阅了《上海证券报》- 电子版 的用户有效。    

 


