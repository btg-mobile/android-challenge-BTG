package br.eloibrito.com.movie_db.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "TB_Movies")
data class Movies(

    @PrimaryKey @SerializedName("id") var id : Long? = null,
    @SerializedName("popularity") var popularity : Float? = null,
    @SerializedName("vote_count") var vote_count : Int? = null,
    @SerializedName("video") var video : Boolean? = null,
    @SerializedName("poster_path") var poster_path : String? = null,
    @SerializedName("adult") var adult : Boolean? = null,
    @SerializedName("backdrop_path") var backdrop_path : String? = null,
    @SerializedName("original_language") var original_language : String? = null,
    @SerializedName("original_title") var original_title : String? = null,
    @Ignore @SerializedName("genre_ids")  var genre_ids : IntArray? = null,
    @SerializedName("title") var title : String? = null,
    @SerializedName("vote_average") var vote_average : Float? = null,
    @SerializedName("overview") var overview : String? = null,
    @SerializedName("release_date") var release_date : String? = null,
    @Ignore var like : Boolean = false

) : Serializable