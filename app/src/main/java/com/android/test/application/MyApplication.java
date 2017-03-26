package com.android.test.application;

import android.app.Application;

import com.android.test.database.CategoryHelper;
import com.backendless.Backendless;
import com.facebook.stetho.Stetho;

/**
 * Created by Manish on 9/2/17.
 */

public class MyApplication extends Application {
//test simplex
//    simplextest02@gmail.com
//1234567890qwerty

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created. Implementations should be as quick as
     * possible (for example using lazy initialization of state) since the time spent in this
     * function directly impacts the performance of starting the first activity, service, or
     * receiver in a process. If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        CategoryHelper categoryHelper = new CategoryHelper(this);
        String appVersion = "v1";
        Backendless.initApp(this, "AA025DBA-FE4D-B6D4-FFDB-0D1966997600", "572E6EAE-8462-25BA-FFA9-3D00E77D8A00", appVersion);
    }
}
