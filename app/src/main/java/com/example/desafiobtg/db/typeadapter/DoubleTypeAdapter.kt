package com.example.desafiobtg.db.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class DoubleTypeAdapter : TypeAdapter<Double>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Double?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value)
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Double? {
        val peek = `in`.peek()
        return when (peek) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.NUMBER -> `in`.nextDouble()
            JsonToken.STRING -> {
                `in`.nextString()
                java.lang.Double.valueOf(0.0)
            }
            else -> throw IllegalStateException("Expected BOOLEAN or NUMBER but was $peek")
        }
    }
}