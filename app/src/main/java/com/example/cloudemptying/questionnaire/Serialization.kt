package com.example.cloudemptying.questionnaire

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

@Serializable
class UserOnline(
    val account_online: String = "",
    val nickname_online: String = "",
    var message_online: String = "",
    var type_online: Int = -1
)

fun DecodeUsers(jsonimport: String): List<UserOnline> {
    val users = json.decodeFromString<List<UserOnline>>(jsonimport)
    return users
}

fun EncodeUser(users: UserOnline): String {
    val jsonexport = json.encodeToString(users)
    return jsonexport
}