package com.example.db

import org.ktorm.database.Database
object DatabaseConnection {
    val database = Database.connect(
        url = "jdbc:postgresql:postgres?user=satish&password=Torch@123",
        driver = "org.postgresql.Driver",
        user = "satish",
        password = "Torch123"
    )
}