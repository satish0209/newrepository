package com.example

import com.example.db.DatabaseConnection.database
import com.example.entities.NotesEntity
import com.example.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select

fun main(){
    embeddedServer(Netty,port=8080,host="0.0.0.0"){
        configureRouting()





        install(ContentNegotiation){
            json()
        }



    }.start(true)
}