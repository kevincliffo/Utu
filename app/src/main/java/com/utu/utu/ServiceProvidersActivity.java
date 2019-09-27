package com.utu.utu;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceProvidersActivity extends Activity{
    // All static variables
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_FULL_NAME = "title";
    static final String KEY_SERVICE_PROVIDING = "artist";
    static final String KEY_LOCATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    JSONParser jsonParser=new JSONParser();
    private UserDetailsObject udox;

    private static final String GET_ALL_PROVIDERS_URL = "http://www.mantikiplan.solutions/utu/getallproviders.php";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);

        udox = (UserDetailsObject) getIntent().getSerializableExtra("UDO");

        XX_GetAllServiceProvidersAndAddToList();
    }

    private void XX_GetAllServiceProvidersAndAddToList()
    {
        new AsyncGetAllServiceProviders().execute(udox.getEmail(), udox.getPassword());
    }

    private class AsyncGetAllServiceProviders extends AsyncTask<String, String, JSONArray>
    {
        ProgressDialog pdLoading = new ProgressDialog(ServiceProvidersActivity.this);
        HttpURLConnection conn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override

        protected JSONArray doInBackground(String... args) {
            String szEmail = args[0];
            String szPassword = args[1];;

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Email", szEmail));
            params.add(new BasicNameValuePair("Password", szPassword));

            JSONArray json = jsonParser.makeHttpRequestArray(GET_ALL_PROVIDERS_URL, "POST", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONArray result) {

            pdLoading.dismiss();
            JSONObject obj;
            ArrayList<HashMap<String, String>> collProviders = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = new HashMap<String, String>();
            int nCounter = 1;

            try
            {
                if (result != null)
                {
                    for (int nRecord = 0; nRecord < result.length(); nRecord++)
                    {
                        obj = (JSONObject) result.get(nRecord);

                        map = new HashMap<String, String>();
                        map.put(KEY_ID, String.valueOf(nCounter));
                        map.put(KEY_FULL_NAME, obj.getString("FullName"));
                        map.put(KEY_SERVICE_PROVIDING, obj.getString("ServiceProviding"));
                        map.put(KEY_LOCATION, obj.getString("Location"));
                        map.put(KEY_THUMB_URL, obj.getString("ProfileImageURL"));
                        collProviders.add(map);

                        nCounter = nCounter + 1;
                    }

                    list = (ListView)findViewById(R.id.list);
                    adapter = new LazyAdapter(ServiceProvidersActivity.this, collProviders);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            Toast.makeText(getApplicationContext(),
                                    position + "",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
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