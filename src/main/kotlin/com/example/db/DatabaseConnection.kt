package com.example.db

import org.ktorm.database.Database
object DatabaseConnection {
    val database = Database.connect(
        url = "jdbc:postgresql:notes?user=bommidisatish&password=1234",
        driver = "org.postgresql.Driver",
        user = "bommidisatish",
        password = "1234"
    )
}