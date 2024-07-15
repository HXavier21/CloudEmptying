package com.example.cloudemptying.network

import com.example.cloudemptying.questionnaire.Question
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

fun Decode(jsonimport:String):List<Question>{
    val questions = com.example.cloudemptying.questionnaire.json.decodeFromString<List<Question>>(jsonimport)
    return questions
}

fun Encode(questions:List<Question>):String{
    val jsonexport = com.example.cloudemptying.questionnaire.json.encodeToString(questions)
    return jsonexport
}