###上海证券报付费用户PDF下载器    
我一直比较喜欢[上海证券报](http://paper.cnstock.com/), 也订阅了它的电子版。但很无奈的是，它的网络版本在WEB上的阅读体验很差，而我又是一个比较注重阅读体验的人 :) 。    
怎么办呢？后来发现了它的PDF版本和纸质版完全采用相同的风格，而这，一直以来是我最喜欢纸质版本的原因。如果能一次自动下载好一期的所有PDF文件，那么阅读起来真是一件很爽的事情。简单看了一下网站的结构，这次用 Java 写了一个小程序来处理自动下载。

##使用
程序会把所有依赖打包成一个可执行的 Jar 文件，使用时，只需要执行：  
  
a. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar             # 默认下载当天的所有PDF     

b. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar yyyy-MM-dd  # 下载指定某一天的所有PDF     

c. java -jar ./cnstock_downloader-1.0-jar-with-dependencies.jar yyyy-MM-dd cookie_file_path  # 下载指定某一天的所有PDF,并指定 cookie 文件路径     

###注意：
a 和 b 需要预先 copy 你当前阅读电子版页面的 cookie 内容，存储到程序所在目录的 site_cookie 文件中。     
记住这个不是盗取内容的程序，只是针对订阅了《上海证券报》- 电子版 的用户有效。    

##运行时要求：   
Java 1.8   


