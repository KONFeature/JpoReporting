package com.nivelais.supinfo.jporating.data.csv

import java.util.*

data class InterrogationCsvEntity(
    val start: Date,
    val end: Date,
    val ratings: List<Int>
)