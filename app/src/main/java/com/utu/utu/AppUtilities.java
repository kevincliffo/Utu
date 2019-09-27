package com.utu.utu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * Created by kevin on 24/10/2017.
 */

public class AppUtilities {

    public AppUtilities()
    {}

    public Bitmap getProfileBitmapFromLocalDirectoryIf(String szvImageName)
    {
        String szProfileImagePath = Environment.getExternalStorageDirectory() + File.separator + "UTU" + File.separator + szvImageName;
        File fl = new File(szProfileImagePath);

        if(fl.exists()) {

            Bitmap bmp = BitmapFactory.decodeFile(fl.getAbsolutePath());
            return bmp;
        }
        return null;
    }

}
