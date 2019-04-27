package com.example.desafiobtg.db.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class BooleanTypeAdapter : TypeAdapter<Boolean>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Boolean?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value)
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Boolean? {
        val peek = `in`.peek()
        return when (peek) {
            JsonToken.BOOLEAN -> `in`.nextBoolean()
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.NUMBER -> `in`.nextInt() != 0
            JsonToken.STRING -> {
                val string = `in`.nextString()
                string.equals("1", ignoreCase = true) || string.equals("true", ignoreCase = true)
            }
            else -> throw IllegalStateException("Expected BOOLEAN or NUMBER but was $peek")
        }
    }
}