package com.duolingo.domain.model

import java.io.Serializable

/** Data model of a language. */
enum class Language(
    val fullname: String,
    val abbreviation: String,
) : Serializable {
    ARABIC(
        "arabic",
        "ar",
    ),
    CHINESE(
        "chinese",
        "zs",
    ),
    DUTCH(
        "dutch",
        "dn",
    ),
    ENGLISH(
        "english",
        "en",
    ),
    FRENCH(
        "french",
        "fr",
    ),
    GERMAN(
        "german",
        "de",
    ),
    ITALIAN(
        "italian",
        "it",
    ),
    JAPANESE(
        "japanese",
        "ja",
    ),
    KOREAN(
        "korean",
        "ko",
    ),
    SPANISH(
        "spanish",
        "es",
    );

    /** Use abbreviation as language id. */
    val languageId: String = abbreviation

    companion object {

        /** Get [Language] from abbreviated name string, return null if no matches found. */
        fun fromLanguageId(languageId: String): Language? =
            values().firstOrNull { value -> value.abbreviation == languageId }
    }
}