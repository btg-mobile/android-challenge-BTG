
package com.dacruzl2.btgdesafio.model.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class RootGenres implements Parcelable {

    @SerializedName("genres")
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public RootGenres(List<Genre> mGenres) {
        this.mGenres = mGenres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mGenres);
    }

    protected RootGenres(Parcel in) {
        this.mGenres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Parcelable.Creator<RootGenres> CREATOR = new Parcelable.Creator<RootGenres>() {
        @Override
        public RootGenres createFromParcel(Parcel source) {
            return new RootGenres(source);
        }

        @Override
        public RootGenres[] newArray(int size) {
            return new RootGenres[size];
        }
    };
}
