package com.utu.utu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtPassword;
    private CustomEditText txtEmail;
    private String szxEmail = "";
    private String szxPassword = "";
    private static final String LOGIN_URL = "http://www.mantikiplan.solutions/utu/login.php";
    JSONParser jsonParser=new JSONParser();
    private UserDetailsObject  udox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (CustomEditText) findViewById(R.id.input_email);
        txtPassword = (EditText) findViewById(R.id.input_password);
        CustomButton btnSignIn = (CustomButton) findViewById(R.id.btnLogin);
        btnSignIn.setOnClickListener(this);

        udox = new UserDetailsObject();
        CustomTextView btnSignUp = (CustomTextView) findViewById(R.id.link_signup);
        btnSignUp.setOnClickListener(this);

        XX_CreateApplicationFoldersIf();
    }

    private void XX_CreateApplicationFoldersIf()
    {
        File fdUtuFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "UTU");
        while(true)
        {
            if(fdUtuFolder.exists())
            {
                break;
            }

            fdUtuFolder.mkdirs();

            break;
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId())
        {
            case R.id.btnLogin:
                LoginToUtuInterface(v);
                break;

            case R.id.link_signup:
                intent = new Intent(this, RegisterUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void LoginToUtuInterface(View arg0) {

        EncryptionClass ec = new EncryptionClass();
        String szEncryptedPassword = "";
        szxEmail = txtEmail.getText().toString();
        szxPassword = txtPassword.getText().toString();

        try {
            szEncryptedPassword = ec.encrypt(szxPassword);
        }
        catch (Exception ex)
        {
            szEncryptedPassword = "";
        }

        while (true)
        {
            if(szxEmail.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                               "Email Field is Empty",
                               Toast.LENGTH_LONG).show();
                break;
            }

            if(szxPassword.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                               "Password Field is Empty",
                               Toast.LENGTH_LONG).show();
                break;
            }

            new AsyncLogin().execute(szxEmail,szEncryptedPassword);
            break;
        }

    }

    private class AsyncLogin extends AsyncTask<String, String, JSONObject>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
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
            String email = args[0];
            String password = args[1];;

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Email", email));
            params.add(new BasicNameValuePair("Password", password));

            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

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
                        String szPassword = result.getString("Password");
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

                        String szActivated = result.getString("Activated");

                        if ((szActivated).equals("0")) {
                            String szMessage = "Account has not been Activated. \nLogin to your email and click on the provided link to activate";
                            Toast.makeText(getApplicationContext(), szMessage, Toast.LENGTH_LONG).show();
                            break;
                        }

                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();

                        Intent ihome = new Intent(LoginActivity.this, HomeActivity.class);
                        ihome.putExtra("UDO", (Serializable) udox);
                        startActivity(ihome);

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
