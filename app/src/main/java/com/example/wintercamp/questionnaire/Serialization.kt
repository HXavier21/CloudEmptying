package com.example.wintercamp.questionnaire

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

@Serializable
class UserOnline(
    val account: String = "",
    val nickname: String = "",
    var message: String = "",
    var type: Int = -1
)

fun DecodeUsers(jsonimport: String): List<UserOnline> {
    val users = json.decodeFromString<List<UserOnline>>(jsonimport)
    return users
}

fun EncodeUser(users: UserOnline): String {
    val jsonexport = json.encodeToString(users)
    return jsonexport
}