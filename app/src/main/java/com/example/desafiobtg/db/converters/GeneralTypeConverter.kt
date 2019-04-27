package com.example.desafiobtg.db.converters

import android.arch.persistence.room.TypeConverter
import com.example.desafiobtg.db.room.DataUtils
import com.google.gson.reflect.TypeToken
import java.io.Serializable

open class GeneralTypeConverter : Serializable {

    @TypeConverter
    fun restoreListString(listOfString: String?): List<String>? {
        return if (listOfString == null) null else DataUtils.createGsonBuilder().fromJson(listOfString, object : TypeToken<List<String>>() {
        }.type)
    }

    @TypeConverter
    fun saveListString(listOfString: List<String>?): String? {
        return if (listOfString == null) null else DataUtils.createGsonBuilder().toJson(listOfString)
    }

    @TypeConverter
    fun restoreListDouble(doubleValue: String?): List<Double>? {
        return DataUtils.createGsonBuilder().fromJson(doubleValue, object : TypeToken<List<Double>>() {
        }.type)
    }

    @TypeConverter
    fun saveListDouble(listOfDouble: List<Double>?): String? {
        return DataUtils.createGsonBuilder().toJson(listOfDouble)
    }

    @TypeConverter
    fun restoreMapDouble(mapValue: String?): Map<Double, Double>? {
        return DataUtils.createGsonBuilder().fromJson(mapValue, object : TypeToken<Map<Double, Double>>() {

        }.type)
    }

    @TypeConverter
    fun saveMapDouble(mapDouble: Map<Double, Double>?): String? {
        return DataUtils.createGsonBuilder().toJson(mapDouble)
    }

    @TypeConverter
    fun restoreListInt(intValue: String?): List<Int>? {
        return DataUtils.createGsonBuilder().fromJson(intValue, object : TypeToken<List<Int>>() {
        }.type)
    }

    @TypeConverter
    fun saveListInt(listOfInt: List<Int>?): String? {
        return DataUtils.createGsonBuilder().toJson(listOfInt)
    }

    @TypeConverter
    fun restoreBoolean(value: Int?): Boolean? {
        return value?.let {
            value > 0
        } ?: false
    }

    @TypeConverter
    fun saveBoolean(v: Boolean?): Int {
        return v?.let { if (it) 1 else 0 } ?: 0
    }
}