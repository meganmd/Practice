var sqlite = require("sqlite3").verbose();
var db = new sqlite.Database("./db/exampleDB.db");

db.serialize(function() {
    db.run("CREATE TABLE IF NOT EXISTS users (username TEXT, password TEXT)");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Harry Potter", "Expelliarmus");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Hermione Granger", "Expelliarmus");
    //db.run("INSERT INTO users (username, password) VALUES (?, ?)", "Ron Weasley", "Expelliarmus");

});
