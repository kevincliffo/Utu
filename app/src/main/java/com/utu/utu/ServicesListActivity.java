package com.utu.utu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.utu.utu.ServiceProvidersActivity.KEY_FULL_NAME;
import static com.utu.utu.ServiceProvidersActivity.KEY_ID;
import static com.utu.utu.ServiceProvidersActivity.KEY_LOCATION;
import static com.utu.utu.ServiceProvidersActivity.KEY_SERVICE_PROVIDING;
import static com.utu.utu.ServiceProvidersActivity.KEY_THUMB_URL;

public class ServicesListActivity extends AppCompatActivity {
    ListView list;
    LazyAdapter adapter;

    private static final String IMAGE_CLEANING_URL = "http://www.mantikiplan.solutions/utu/app_images/cleaning.png";
    private static final String IMAGE_DELIVERY_URL = "http://www.mantikiplan.solutions/utu/app_images/delivery.jpg";
    private static final String IMAGE_PARCEL_DELIVERY_URL = "http://www.mantikiplan.solutions/utu/app_images/parceldelivery.jpg";
    private static final String IMAGE_SHOPPING_DELIVERY_URL = "http://www.mantikiplan.solutions/utu/app_images/shoppingdelivery.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        XX_AddServicesToList();
    }

    private void XX_AddServicesToList()
    {
        ArrayList<HashMap<String, String>> collProviders = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        int nCounter = 1;

        map = new HashMap<String, String>();
        map.put(KEY_ID, String.valueOf(1));
        map.put(KEY_FULL_NAME, "Delivery");
        map.put(KEY_SERVICE_PROVIDING, "Delivery");
        map.put(KEY_LOCATION, String.valueOf(1));
        map.put(KEY_THUMB_URL, IMAGE_CLEANING_URL);
        collProviders.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_ID, String.valueOf(2));
        map.put(KEY_FULL_NAME, "Cleaning");
        map.put(KEY_SERVICE_PROVIDING, "Cleaning");
        map.put(KEY_LOCATION, String.valueOf(2));
        map.put(KEY_THUMB_URL, IMAGE_DELIVERY_URL);
        collProviders.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_ID, String.valueOf(3));
        map.put(KEY_FULL_NAME, "Parcel Delivery");
        map.put(KEY_SERVICE_PROVIDING, "Parcel Delivery");
        map.put(KEY_LOCATION, String.valueOf(3));
        map.put(KEY_THUMB_URL, IMAGE_PARCEL_DELIVERY_URL);
        collProviders.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_ID, String.valueOf(3));
        map.put(KEY_FULL_NAME, "Shopping Delivery");
        map.put(KEY_SERVICE_PROVIDING, "Shopping Delivery");
        map.put(KEY_LOCATION, String.valueOf(4));
        map.put(KEY_THUMB_URL, IMAGE_SHOPPING_DELIVERY_URL);
        collProviders.add(map);

        list = (ListView)findViewById(R.id.serviceslist);
        adapter = new LazyAdapter(ServicesListActivity.this, collProviders);
        list.setAdapter(adapter);
    }
}
