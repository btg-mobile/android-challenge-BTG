
package com.dacruzl2.btgdesafio.model.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.google.gson.annotations.SerializedName;

public class Genre implements Parcelable {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Genre(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public Genre() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
    }

    protected Genre(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass() && obj.getClass() != Movie.class) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            final Genre other = (Genre) obj;
            if (this.getId() != other.getId()) {
                return false;
            }
        } else {
            final Movie other = (Movie) obj;
            if (this.getId() != other.getGenreIds().size()) {
                return false;
            }
        }
        return true;
    }
}
