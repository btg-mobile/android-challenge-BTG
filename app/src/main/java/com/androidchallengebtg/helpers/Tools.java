package com.androidchallengebtg.helpers;

import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;

public class Tools {

    public static String getBaseImageUrl(String size){
        String baseImageUrl = ApplicationBTG.getContext().getString(R.string.tmdb_images_base_url);
        baseImageUrl = baseImageUrl.replace("{size}",size);
        return baseImageUrl;
    }

    public static String getBaseImageUrl(int size){
        return getBaseImageUrl("w"+String.valueOf(size));
    }
}
