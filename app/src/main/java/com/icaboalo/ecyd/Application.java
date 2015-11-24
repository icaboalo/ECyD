package com.icaboalo.ecyd;

import com.parse.Parse;

/**
 * Created by icaboalo on 11/24/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "vSBJYh299RRo1fQTpsEqpSKm4HjGUGhqhwYAWpX6", "9qiCM8L5uauH0fq40iJ2wXpBnZlNMuraJOjCH7X8");
    }
}
