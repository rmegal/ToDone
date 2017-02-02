package com.raymegal.todone;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by ray on 1/31/2017.
 */

public class ToDoneApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());

        // add for verbose Logging
        // setMinimumLoggingLevel(Level.V);
    }
}
