package com.p2p.antiprocrastination.model

data class Achievement(
    val title: String,
    val description: String,
    val requiredXp: Int,
    var unlocked: Boolean = false
)