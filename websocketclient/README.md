
# BTC insight web socket
API https://github.com/bitpay/insight-api#web-socket-api

## socket.io Java client
https://github.com/socketio/socket.io-client-java
坑1：依赖org.json。需要exclude android-json
坑2：依赖okhttp 3.8.1，需要指定okio 1.13.0
坑3：URL是http://{host}:{port}/
