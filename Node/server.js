var express = require("express");
var app = express();
var fs = require("fs");
var sqlite = require("sqlite3").verbose();
var db = new sqlite.Database("./db/exampleDB.db");

db.serialize(function() {
    db.run("CREATE TABLE IF NOT EXISTS users (username TEXT, password TEXT)");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Harry Potter", "Expelliarmus");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Hermione Granger", "Expelliarmus");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Ron Weasley", "Expelliarmus");

});

app.get('/api/listUsers', function(request, response) {
  db.all("SELECT username FROM users", function(err, rows){
        response.setHeader('Content-Type', 'application/json');
        response.json(rows);
    });


})

app.post('/api/addUser', function(request, response) {
  db.run("INSERT INTO users (username, password) VALUES (?, ?)", request.body.username, request.body.password);
})

var server = app.listen(3003, function() {
  var host = server.address().address;
  var port = server.address().port;
  console.log("app listening at %s/%s", host, port);
})
