package com.example.desafiobtg.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Genre(@PrimaryKey
                  @ColumnInfo(name = ID) @SerializedName(ID) @Expose var id: String,
                  @ColumnInfo(name = NAME) @SerializedName(NAME) @Expose var name: String?) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
    }
}