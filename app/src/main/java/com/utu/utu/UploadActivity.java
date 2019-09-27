package com.utu.utu;


    import android.app.ProgressDialog;
    import android.content.Context;
    import android.graphics.Bitmap;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Base64;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;

    import java.io.ByteArrayOutputStream;
    import java.util.Hashtable;
    import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private EditText editTextName;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String REGISTRATION_URL = "http://www.mantikiplan.solutions/utu/Registration.php";

    private String KEY_FULL_NAME = "FullName";
    private String KEY_EMAIL = "Email";
    private String KEY_PASSWORD = "Password";
    private String KEY_ID_NUMBER = "IDNumber";
    private String KEY_MOBILE_NUMBER = "MobileNumber";
    private String KEY_ADDRESS = "Address";
    private String KEY_IMAGE = "Image";
    private String KEY_PROFILE_IMAGE_NAME = "ProfileImageName";
    private String KEY_PROFILE_IMAGE_URL = "ProfileImageURL";
    private String KEY_DATE_OF_BIRTH = "DateOfBirth";
    private String KEY_USER_TYPE = "UserType";
    private String KEY_TOKEN = "Token";
    private String KEY_SERVICE_PROVIDING = "ServiceProviding";
    private String KEY_LOCATION = "Location";

    private UserDetailsObject rox;
    private Context cx;
    boolean bxErrorFound = false;

    public UploadActivity()
    {

    }

    public String getStringImage(Bitmap bmp){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        catch (Exception ex)
        {
            Toast.makeText(cx, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            bxErrorFound = true;
            return "";
        }
    }

    public boolean uploadImage(Context cv, UserDetailsObject rov){
        //Showing the progress dialog
        rox = rov;
        cx = cv;

        final ProgressDialog loading = ProgressDialog.show(cx,"Registration...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(cx, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        bxErrorFound = true;
                        Toast.makeText(cx, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String szImage = getStringImage(rox.getImage());
                Bitmap bmp = rox.getImage();

                //Getting Image Name
                String name = "";//editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_FULL_NAME, rox.getFullName());
                params.put(KEY_EMAIL, rox.getEmail());
                params.put(KEY_IMAGE, szImage);
                params.put(KEY_EMAIL, rox.getEmail());
                params.put(KEY_PASSWORD, rox.getPassword());
                params.put(KEY_ID_NUMBER, rox.getIDNumber());
                params.put(KEY_MOBILE_NUMBER, rox.getMobileNumber());
                params.put(KEY_ADDRESS, rox.getAddress());
                params.put(KEY_LOCATION, rox.getLocation());
                params.put(KEY_SERVICE_PROVIDING, rox.getServiceProviding());
                params.put(KEY_PROFILE_IMAGE_NAME, rox.getProfileImageName());
                params.put(KEY_PROFILE_IMAGE_URL, rox.getProfileImageURL());
                params.put(KEY_DATE_OF_BIRTH, rox.getDateOfBirth());
                params.put(KEY_USER_TYPE, String.valueOf(rox.getUserType()));
                params.put(KEY_TOKEN, rox.getToken());
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(cx);

        //Adding request to the queue
        requestQueue.add(stringRequest);

        return bxErrorFound;
    }
}