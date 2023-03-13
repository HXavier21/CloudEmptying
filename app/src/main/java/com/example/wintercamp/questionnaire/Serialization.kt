package com.example.wintercamp.questionnaire

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}
fun Decode(jsonimport:String):List<Question>{
    val questions = json.decodeFromString<List<Question>>(jsonimport)
    return questions
}

fun Encode(questions:List<Question>):String{
    val jsonexport = json.encodeToString(questions)
    return jsonexport
}