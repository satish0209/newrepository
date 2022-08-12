package com.example.plugins

import com.example.db.DatabaseConnection
import com.example.entities.NotesEntity
import com.example.entities.NotesEntity.id
import com.example.model.MultipleNoteresponse
import com.example.model.Note
import com.example.model.NoteRequest
import com.example.model.NoteResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        val db = DatabaseConnection.database
        get("/notes") {
            val notes = db.from(NotesEntity).select()
                .map {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Note(id ?: -1, note ?: "")
                }
            call.respond(notes)

        }
//        private fun toNote(row:ResultRow) = Note(
//
//        )


        get("/notesByID") {
            val idList = call.request.queryParameters["id"]?.split(",")?: listOf()
//            println(idList.get(1))
//            val
//         val id = call.request.queryParameters["id"]?.toInt() ?: -1
//            val id2 = call.request.queryParameters["id2"]?.toInt() ?: -1


            // select * from tab1 where id = 1
            // select * from tab1 where id in (1,2,3,4)
            // inList
//            val notes =
            //val noteList: MutableList<Note>? = null
//            for(i in idList) {
//                var idNote: Int = i.toInt()
////                println(idNote)
//                val notes = db.from(NotesEntity)
//                    .select()
//                    .where { NotesEntity.id eq idNote }
//                    .map {
////                        val id = it[NotesEntity.id]
////                        val note = it[NotesEntity.note]
////                        Note(id ?: -1, note ?: "")
//                        Note(
//                            id = it[NotesEntity.id]!!,
//                            note = it[NotesEntity.note]!!
//                        )
//                    }
//            }
//            x

            val noteList = idList.map {
                db.from(NotesEntity)
                    .select()
                    .where { NotesEntity.id eq it.toInt() }
                    .map {
//                        val id = it[NotesEntity.id]
//                        val note = it[NotesEntity.note]
//                        Note(id ?: -1, note ?: "")
                        Note(
                            id = it[NotesEntity.id]!!,
                            note = it[NotesEntity.note]!!
                        )
                    }.first()
            }
            call.respond(noteList!!)

//            val note = db.from(NotesEntity)
//                .select()
//                .where { NotesEntity.id eq id }
//                .map {
//                    val id = it[NotesEntity.id]!!
//                    val note = it[NotesEntity.note]!!
//                    Note(id = id, note = note)
//                }.firstOrNull()
//
//            }
//            val note1 = db.from(NotesEntity)
//                .select()
//                .where { NotesEntity.id eq id2 }
//                .map {
//                    val id2 = it[NotesEntity.id]!!
//                    val note1 = it[NotesEntity.note]!!
//                    Note(id = id2, note = note1)
//                }.firstOrNull()

//            if (note == null && note1 == null) {
//                call.respond(
//                    HttpStatusCode.OK,
//                    MultipleNoteresponse(
//                        success = false,
//                        data = "Could not found note with  id = $id",
//                        data1 = "no $id2"
//                    )
//                )
//            } else {
//                call.respond(
//                    HttpStatusCode.OK,
//                    MultipleNoteresponse(
//                        success = true,
//                        data = note,
//                        data1 = note1
//                    )
//
//                )
           }





            post("/notes") {

                val request = call.receive<NoteRequest>()
                val result = db.insert(NotesEntity) {
                    set(it.note, request.note)

                }

                if (result == 1) {

                    call.respond(
                        HttpStatusCode.OK, NoteResponse(
                            success = true,
                            data = "Values has been successfully inserted"
                        )
                    )
                } else {

                    call.respond(
                        HttpStatusCode.BadRequest, NoteResponse(
                            success = false,
                            data = "Failed to insert values."
                        )
                    )
                }
            }



            put("/notes/{id}") {
                val id = call.parameters["id"]?.toInt() ?: -1
                val updatedNote = call.receive<NoteRequest>()

                val rowsEffected = db.update(NotesEntity) {
                    set(it.note, updatedNote.note)
                    where {
                        it.id eq id
                    }
                }

                if (rowsEffected == 1) {
                    call.respond(
                        HttpStatusCode.OK,
                        NoteResponse(
                            success = true,
                            data = "Note has been updated"
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        NoteResponse(
                            success = false,
                            data = "Note failed to update"
                        )
                    )
                }
            }


            this@routing.delete("/notes/{id}") {
                val id = call.parameters["id"]?.toInt() ?: -1


                val deleterow = db.delete(NotesEntity) {


                    it.id eq id
                }


                if (deleterow == 1) {
                    call.respond(
                        HttpStatusCode.OK,
                        NoteResponse(
                            success = true,
                            data = "Note $id has been deleted"
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        NoteResponse(
                            success = false,
                            data = "Note $id failed to delete"
                        )
                    )
                }
            }
        }
    }







