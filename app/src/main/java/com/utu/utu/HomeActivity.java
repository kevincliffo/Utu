package com.utu.utu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;
//import com.utu.utu.DrawerHeader;
//import com.mindorks.test.gallery.ImageTypeBig;
//import com.mindorks.test.gallery.ImageTypeSmall;
//import com.mindorks.test.gallery.ImageTypeSmallPlaceHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static org.apache.commons.io.FileUtils.getFile;

public class HomeActivity extends AppCompatActivity implements DrawerMenuItem.DrawerCallBack {

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;
    private DrawerMenuItem dmix;
    private UserDetailsObject udox;
    private AppUtilities aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aux = new AppUtilities();
        udox = (UserDetailsObject) getIntent().getSerializableExtra("UDO");

        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);

        new AsyncGettingBitmapFromUrl().execute(udox.getProfileImageURL());

        setupDrawer();

    }

    private void setupDrawer(){
        DrawerMenuItem dmiAddress = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_ADDRESS);
        dmiAddress.setDrawerCallBack(this);
        DrawerMenuItem dmiServices = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SERVICES);
        dmiServices.setDrawerCallBack(this);
        DrawerMenuItem dmiProviders = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROVIDERS);
        dmiProviders.setDrawerCallBack(this);
        DrawerMenuItem dmiProfile = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE);
        dmiProfile.setDrawerCallBack(this);
        DrawerMenuItem dmiRecords = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_RECORDS);
        dmiRecords.setDrawerCallBack(this);
        DrawerMenuItem dmiNotifications = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS);
        dmiNotifications.setDrawerCallBack(this);
        DrawerMenuItem dmiInfo = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_INFO);
        dmiInfo.setDrawerCallBack(this);
        DrawerMenuItem dmiSettings = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS);
        dmiSettings.setDrawerCallBack(this);
        DrawerMenuItem dmiLogOut = new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT);
        dmiLogOut.setDrawerCallBack(this);

        mDrawerView
                .addView(new DrawerHeader(udox.getFullName(), udox.getEmail()))
                .addView(dmiAddress)
                .addView(dmiServices)
                .addView(dmiProviders)
                .addView(dmiProfile)
                .addView(dmiRecords)
                .addView(dmiNotifications)
                .addView(dmiInfo)
                .addView(dmiSettings)
                .addView(dmiLogOut);

        ActionBarDrawerToggle  drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onLogoutMenuSelected()
    {
        finish();
    }

    public void onAddressMenuSelected(){}
    public void onServicesMenuSelected()
    {
        Intent sla = new Intent(this, ServicesListActivity.class);
        startActivity(sla);
    }

    public void onProvidersMenuSelected()
    {
        try {
            Intent spa = new Intent(this, ServiceProvidersActivity.class);
            spa.putExtra("UDO", (Serializable) udox);
            startActivity(spa);
        }
        catch (Exception ex)
        {
            String szMessage = ex.getLocalizedMessage();
            Toast.makeText(getApplicationContext(), szMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void onProfileMenuSelected()
    {
        try {
            Intent iupa = new Intent(this, UserProfileActivity.class);
            iupa.putExtra("UDO", (Serializable) udox);
            startActivity(iupa);
            finish();
        }
        catch (Exception ex)
        {
            String szMessage = ex.getLocalizedMessage();
            Toast.makeText(getApplicationContext(), szMessage, Toast.LENGTH_SHORT).show();
        }
    }
    public void onNotificationsMenuSelected(){}
    public void onSettingsMenuSelected(){}
    public void onInfoMenuSelected()
    {
        String szURL = "http://www.utu.co.ke";
        Uri uriUrl = Uri.parse(szURL);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//        startActivity(launchBrowser);

        Intent iAboutUs = new Intent(this, AboutusActivity.class);
        startActivity(iAboutUs);
    }

    public void onRecordsMenuSelected(){}

    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            String szUrl = params[0];
            boolean bImageFound = false;
            Bitmap bmp;
            while(true) {
                try
                {
                    bmp = aux.getProfileBitmapFromLocalDirectoryIf(udox.getProfileImageName());
                    if(bmp != null)
                    {
                        bImageFound = true;
                        return bmp;
                    }

                    bImageFound = false;
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    bImageFound = false;
                }

                if(bImageFound == true)
                {
                    break;
                }
                try {
                    URL url = new URL(szUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if(bitmap != null) {
                ImageView imageView = (ImageView) findViewById(R.id.profileImageView);

                Bitmap bmResized = getResizedBitmap(bitmap, 500, 500);
                imageView.setImageBitmap(bmResized);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

}
