package com.practicesession.d2dpushnotificationapp

open class UserId {
    var userId = ""
    fun <T : UserId?> withId(id: String): T {
        userId = id
        return this as T
    }
}