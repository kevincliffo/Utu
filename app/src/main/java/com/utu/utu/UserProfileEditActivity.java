package com.utu.utu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class UserProfileEditActivity extends Activity implements View.OnClickListener {
    AppUtilities aux;
    private UserDetailsObject udox;
    SearchableSpinner ssServices;
    SearchableSpinner ssLocations;
    JSONParser jsonParser;
    CustomButton btnUpdate;

    private String KEY_EMAIL = "Email";
    private String KEY_SERVICE_PROVIDING = "ServiceProviding";
    private String KEY_LOCATION = "Location";
    private String KEY_USER_TYPE = "UserType";

    private String UPDATE_URL = "http://www.mantikiplan.solutions/utu/UpdateProfile.php";
    private String szxSelectedLocation = "";
    private String szxSelectedService = "";
    private String szxEmail = "";
    private String szxUserType = "";
    private String szxImageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        jsonParser = new JSONParser();
        udox = new UserDetailsObject();
        aux = new AppUtilities();

        szxUserType = getIntent().getExtras().getString("UserType");
        szxEmail = getIntent().getExtras().getString("Email");
        szxImageName = getIntent().getExtras().getString("ProfileImage");

        if(szxUserType.equals("1")) {
            ssServices = (SearchableSpinner) findViewById(R.id.spEditUserServices);
            ssServices.setVisibility(View.VISIBLE);

            XX_SetSpinnerFont(ssServices,
                    R.array.services);

            ssServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int pos, long id) {
                    szxSelectedService = parent.getItemAtPosition(pos).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }

        ssLocations = (SearchableSpinner)findViewById(R.id.spEditUserLocations);
        ssLocations.setVisibility(View.VISIBLE);
        XX_SetSpinnerFont(ssLocations,
                          R.array.locations);

        ssLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                szxSelectedLocation = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnUpdate = (CustomButton)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        String szURL = getIntent().getExtras().getString("ProfileImage");
        new AsyncGettingBitmapFromUrl().execute(szURL);

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
                    bmp = aux.getProfileBitmapFromLocalDirectoryIf(szxImageName);
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

            ImageView imageView = (ImageView) findViewById(R.id.ivEditProfileImageView);
            Bitmap bmResized = getResizedBitmap(bitmap, 1000, 1000);
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

    private void XX_SetSpinnerFont(SearchableSpinner spv,
                                   int nvArray)
    {
        Adapter adpServices = ArrayAdapter.createFromResource(this, nvArray, android.R.layout.simple_spinner_item);
        int nItemsCount = adpServices.getCount();
        List<String> items = new ArrayList<String>(nItemsCount);

        for(int i =0; i < nItemsCount; i++ )
        {
            items.add((String)adpServices.getItem(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, items) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Hanken-Book.ttf");
                ((TextView) v).setTypeface(externalFont);

                return v;
            }


            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Hanken-Book.ttf");
                ((TextView) v).setTypeface(externalFont);
                v.setBackgroundColor(Color.GREEN);

                return v;
            }
        };
        spv.setAdapter(adapter);
    }

    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnUpdate:
                new AsyncUserProfileUpdate().execute(szxEmail,
                                                     szxSelectedLocation,
                                                     szxSelectedService,
                                                     szxUserType);
                break;
        }
    }

    private class AsyncUserProfileUpdate extends AsyncTask<String, String, JSONObject>
    {
        ProgressDialog pdLoading = new ProgressDialog(UserProfileEditActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair(KEY_EMAIL, args[0]));
            params.add(new BasicNameValuePair(KEY_LOCATION, args[1]));
            params.add(new BasicNameValuePair(KEY_SERVICE_PROVIDING, args[2]));
            params.add(new BasicNameValuePair(KEY_USER_TYPE, args[3]));

            JSONObject json = jsonParser.makeHttpRequest(UPDATE_URL, "POST", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            pdLoading.dismiss();

            try
            {
                if (result != null)
                {
                    while (true) {
                        String szErrorFound = result.get("errorfound").toString();
                        String szEmailBody = "";

                        switch (szErrorFound)
                        {
                            case "0":
                                udox.setUserId(Integer.valueOf(result.getString("UserId")));
                                udox.setFullName(result.getString("FullName"));
                                udox.setEmail(result.getString("Email"));
                                udox.setPassword(result.getString("Password"));
                                udox.setIDNumber(result.getString("IDNumber"));
                                udox.setAddress(result.getString("Address"));
                                udox.setLocation(result.getString("Location"));
                                udox.setServiceProviding(result.getString("ServiceProviding"));
                                udox.setMobileNumber(result.getString("MobileNumber"));
                                udox.setDateOfBirth(result.getString("DateOfBirth"));
                                udox.setUserType(Integer.valueOf(result.getString("UserType")));
                                udox.setProfileImageName(result.getString("ProfileImageName"));
                                udox.setProfileImageURL(result.getString("ProfileImageURL"));
                                udox.setUserActivated(Integer.valueOf(result.getString("Activated")));

                                Intent iupa = new Intent(UserProfileEditActivity.this, UserProfileActivity.class);
                                iupa.putExtra("UDO", (Serializable) udox);
                                startActivity(iupa);
                                finish();

                            case "1":
                                Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                                break;
                        }

                        break;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
