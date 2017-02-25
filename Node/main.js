var http = require("http");
var express = require("express");

http.createServer(function(request, response) {
  response.writeHead(200, {"Content-Type" : "text/plain"});
  response.end("Hello World\n");
}).listen(3002);
console.log("loook at 3002");
