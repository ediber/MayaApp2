package com.e.myapp2;

import android.app.Application;

public class MyApplication extends Application
{
    private String myPhoneNumber = "054111111";

    public String getMyPhoneNumber() {
        return myPhoneNumber;
    }
}
