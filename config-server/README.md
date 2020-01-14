[远程仓库](https://github.com/forezp/SpringcloudConfig/) 中有个文件config-client-dev.properties文件中有一个属性：

foo = foo version 3

启动程序：访问[地址](http://localhost:8771/foo/dev)

{"name":"foo","profiles":["dev"],"label":"master",
"version":"792ffc77c03f4b138d28e89b576900ac5e01a44b","state":null,"propertySources":[]}

证明配置服务中心可以从远程程序获取配置信息。