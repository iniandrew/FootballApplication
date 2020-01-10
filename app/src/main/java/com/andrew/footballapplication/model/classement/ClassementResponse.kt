package com.andrew.footballapplication.model.classement

import com.google.gson.annotations.SerializedName

data class ClassementResponse (
    @SerializedName("table")
    val results: MutableList<ClassementItem>
)