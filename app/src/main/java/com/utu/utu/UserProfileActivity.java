package com.utu.utu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.joooonho.SelectableRoundedImageView;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import static android.content.ContentValues.TAG;

public class UserProfileActivity extends Activity implements View.OnClickListener {
    Button btnSelectImage;

    AppUtilities aux;
    SelectableRoundedImageView iv;
    Button btnEditProfile;
    CustomTextView lblFullName;
    CustomTextView lblEmail;
    CustomTextView lblIDNumber;
    CustomTextView lblMobileNumber;
    CustomTextView lblAddress;
    CustomTextView lblDateOfBirth;
    CustomTextView lblUserType;
    CustomTextView lblLocation;
    CustomTextView lblServiceProviding;
    CustomTextView lblServiceProvidingLabel;
    CustomTextView tvHomeLink;

    private static final int SELECT_PHOTO = 100;
    private int PICK_IMAGE_REQUEST = 1;
    private UserDetailsObject udox;
    private Bitmap bmpx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        aux = new AppUtilities();
        CustomTextView tvlblPersonalProfile = (CustomTextView)findViewById(R.id.lblPersonalProfile);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Hanken-Book.ttf");
        tvlblPersonalProfile.setTypeface(customFont);

        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(this);

        tvHomeLink = (CustomTextView) findViewById(R.id.lnkhome);
        tvHomeLink.setOnClickListener(this);

        udox = (UserDetailsObject) getIntent().getSerializableExtra("UDO");
        new AsyncGettingBitmapFromUrl().execute(udox.getProfileImageURL());

        lblFullName = (CustomTextView)findViewById(R.id.lblFullName);
        lblEmail = (CustomTextView)findViewById(R.id.lblEmailAddress);
        lblIDNumber = (CustomTextView)findViewById(R.id.lblIDNumber);
        lblMobileNumber = (CustomTextView)findViewById(R.id.lblMobileNumber);
        lblAddress = (CustomTextView)findViewById(R.id.lblAddress);
        lblDateOfBirth = (CustomTextView)findViewById(R.id.lblDateOfBirth);
        lblUserType = (CustomTextView)findViewById(R.id.lblUserType);
        lblLocation = (CustomTextView)findViewById(R.id.lblLocation);
        lblServiceProviding = (CustomTextView)findViewById(R.id.lblServiceProviding);
        lblServiceProvidingLabel = (CustomTextView)findViewById(R.id.lblServiceProvidingLabel);

        if(udox.getUserType() == 0)
        {
            lblServiceProvidingLabel.setVisibility(View.INVISIBLE);
            lblServiceProviding.setVisibility(View.INVISIBLE);
        }
        else if(udox.getUserType() == 1)
        {
            lblServiceProvidingLabel.setVisibility(View.VISIBLE);
            lblServiceProviding.setVisibility(View.VISIBLE);
        }

        XX_MoveUserDetailsObjectToControls(udox);

    }

    private void XX_MoveUserDetailsObjectToControls(UserDetailsObject udov)
    {
        String szUserType = "";
        boolean bDisplayServiceProvidingControls = false;

        lblFullName.setText(udov.getFullName());
        lblEmail.setText(udov.getEmail());
        lblIDNumber.setText(udov.getIDNumber());
        lblMobileNumber.setText(udov.getMobileNumber());
        lblAddress.setText(udov.getAddress());
        lblDateOfBirth.setText(udov.getDateOfBirth());
        lblLocation.setText(udov.getLocation());
        lblServiceProviding.setText(udov.getServiceProviding());

        if(udov.getUserType() == 0)
        {
            szUserType = "Client";
        }
        else if(udov.getUserType() == 1)
        {
            szUserType = "Service Provider";
        }

        lblUserType.setText(szUserType);
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnEditProfile:
                String szImageName = udox.getProfileImageName();
                int nUserType = udox.getUserType();
                String szEmail = udox.getEmail();

                Intent iEditProfile = new Intent(this, UserProfileEditActivity.class);
                iEditProfile.putExtra("Email", szEmail);
                iEditProfile.putExtra("UserType", String.valueOf(nUserType));
                iEditProfile.putExtra("ProfileImage", szImageName);

                startActivity(iEditProfile);

                break;
            case R.id.lnkhome:
                Intent ihome = new Intent(this, HomeActivity.class);
                ihome.putExtra("UDO", (Serializable) udox);
                startActivity(ihome);

                break;
        }
    }

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
        protected void onPostExecute(Bitmap bitmap) {

            ImageView imageView = (ImageView) findViewById(R.id.ivImageView);
            Bitmap bmResized = getResizedBitmap(bitmap, 1000, 1000);
            bmpx = bmResized;
            imageView.setImageBitmap(bmResized);
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
}
