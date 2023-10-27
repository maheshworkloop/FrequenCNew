package com.dev.frequenc.data.confirmuserotp

import com.dev.frequenc.data.confirmuserotp.User

data class Data(
    val isEmailRegistered: Boolean,
    val isEventType: Boolean,
    val message: String,
    val user: User
)