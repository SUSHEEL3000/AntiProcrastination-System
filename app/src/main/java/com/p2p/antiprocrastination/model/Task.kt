package com.p2p.antiprocrastination.model

data class Task(
    val id: Long,
    var title: String,
    var isCompleted: Boolean = false,
    var xpReward: Int = 10,
    var xpAwarded: Boolean = false,
    val createdDate: Long = System.currentTimeMillis()
)