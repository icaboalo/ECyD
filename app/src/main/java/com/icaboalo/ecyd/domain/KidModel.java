package com.icaboalo.ecyd.domain;

/**
 * Created by icaboalo on 11/27/2015.
 */
public class KidModel {

    public KidModel(String kidName) {
        mKidName = kidName;
    }

    public KidModel(String kidName, String kidImage) {
        mKidName = kidName;
        mKidImage = kidImage;
    }

    String mKidName;

    String mKidImage;

    public String getKidName() {
        return mKidName;
    }

    public String getKidImage() {
        return mKidImage;
    }
}
