package com.lexxapps.mysettings.utils

fun dataToTitle(languageCode: String): String =
    when (languageCode) {
        "ru" -> "Русский"
        "uk" -> "Українська"
        else -> "English"
    }

fun titleToData(language: String): String =
    when (language) {
        "Русский" -> "ru"
        "Українська" -> "uk"
        else -> "en"
    }