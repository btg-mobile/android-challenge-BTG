package com.example.desafiobtg.db.room

import com.example.desafiobtg.db.typeadapter.BooleanTypeAdapter
import com.example.desafiobtg.db.typeadapter.DoubleTypeAdapter
import com.example.desafiobtg.db.typeadapter.LongTypeAdapter
import com.google.gson.FieldNamingStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Field
import java.lang.reflect.Modifier

object DataUtils {
    fun createGsonBuilder(): Gson {
        val builder = GsonBuilder()
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        builder.excludeFieldsWithoutExposeAnnotation()
        builder.registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
        builder.registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanTypeAdapter())
        builder.registerTypeAdapter(Long::class.java, LongTypeAdapter())
        builder.registerTypeAdapter(Long::class.javaPrimitiveType, LongTypeAdapter())
        builder.registerTypeAdapter(Double::class.javaPrimitiveType, DoubleTypeAdapter())
        builder.registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
        builder.setLenient()
        return builder.create()
    }
}