package com.example.desafiobtg.db.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class LongTypeAdapter : TypeAdapter<Long>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Long?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value)
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Long? {
        val peek = `in`.peek()
        when (peek) {
            JsonToken.NULL -> {
                `in`.nextNull()
                return null
            }
            JsonToken.NUMBER -> {
                try {
                    val value = `in`.nextDouble()
                    return Math.round(value)
                } catch (e: NumberFormatException) {
                }

                return `in`.nextLong()
            }
            JsonToken.STRING -> {
                `in`.nextString()
                return 0L
            }
            else -> throw IllegalStateException("Expected BOOLEAN or NUMBER but was $peek")
        }
    }
}