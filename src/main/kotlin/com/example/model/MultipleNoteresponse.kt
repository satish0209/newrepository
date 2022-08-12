package com.example.model

import kotlinx.serialization.Serializable


@Serializable
data class MultipleNoteresponse<T>(
    val data: T,
    val data1:T,
    val success: Boolean
)

