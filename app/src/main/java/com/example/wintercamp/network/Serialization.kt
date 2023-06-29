package com.example.wintercamp.network

import com.example.wintercamp.questionnaire.Question
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

fun Decode(jsonimport:String):List<Question>{
    val questions = com.example.wintercamp.questionnaire.json.decodeFromString<List<Question>>(jsonimport)
    return questions
}

fun Encode(questions:List<Question>):String{
    val jsonexport = com.example.wintercamp.questionnaire.json.encodeToString(questions)
    return jsonexport
}