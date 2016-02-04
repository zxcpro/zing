# zing
zing is a light weight RPC framework implemented by java.

Dependencies:
*  netty 4.0.32
*  spring 2.5.6
*  guava 17.0
*  hessian 3.1.5
*  slf4j 1.7.7
*  zookeeper 3.4.6
*  curator 2.7.1

Configuration:   
Before you start your app with zing, some configuration should be done first:   
1.config the ip address of the current server and the port you want zing to bind in the file "/data/app/env" with key "server.address.ip" and "server.address.port"   
e.g.   
server.address.ip=127.0.0.1   
server.address.port=4080   
2.config the zookeeper address with key "registry.zookeeper.address" in the file "zing.properties" which is in your classpath   
e.g
registry.zookeeper.address=127.0.0.1:2181


Run the demo:
1.complete the config above
2.run the main method of DemoServerTest, which is in the zing-demo module to start the zing server side
3.run the main method of DemoClientTest to start rpc invocation to the server