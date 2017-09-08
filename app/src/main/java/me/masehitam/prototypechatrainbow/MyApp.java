package me.masehitam.prototypechatrainbow;

import android.app.Application;
import android.graphics.Color;

import com.ale.rainbowsdk.RainbowSdk;

/**
 * Created by Wiwid Widyanto on 9/7/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RainbowSdk.instance().setNotificationBuilder(
                getApplicationContext(),
                MainActivity.class,
                0, // You can set it to 0 if you have no app icon
                getString(R.string.app_name),
                "Connect to the app",
                Color.RED);
        RainbowSdk.instance().initialize(); // Will change in the future
    }
}
