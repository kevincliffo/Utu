package com.utu.utu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.commons.io.FileUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    CustomButton btnCreateAccount;
    CustomButton btnSelectImage;
    SelectableRoundedImageView iv;
    Bitmap bmpxSelectedImage;
    String szxBitmapFilePath = "";
    String szxBitmapFileName = "";
    CustomTextView tvxLogin;
    InputStream isxBitmapFile;
    RadioGroup rgUsers;
    CustomRadioButton rbClient;
    CustomRadioButton rbServiceProviders;
    SearchableSpinner ssServices;
    SearchableSpinner ssLocations;
    private Calendar calCalendar;
    private int nYear, nMonth, nDay;
    private EditText txtDateOfBirth;
    private QueryExecutor qex;
    private UploadActivity uax;
    private FileUtils fux;
    private static final int SELECT_PHOTO = 100;
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
    private String KEY_ACTIVATED = "Activated";

    private String szxSelectedLocation = "";
    private String szxSelectedService = "";
    private UserDetailsObject udox = null;
    JSONParser jsonParser;
    private boolean bxProfileImageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        udox = new UserDetailsObject();
        uax = new UploadActivity();
        qex = new QueryExecutor(RegisterUserActivity.this);
        fux = new FileUtils();
        jsonParser = new JSONParser();
        bxProfileImageSelected = false;

        rgUsers = (RadioGroup)findViewById(R.id.rbUserTypeGroup);

        btnCreateAccount = (CustomButton)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);

        btnSelectImage = (CustomButton) findViewById(R.id.btnRegselectimage);
        btnSelectImage.setOnClickListener(this);

        iv = (SelectableRoundedImageView)findViewById(R.id.ivRegImageView);
        txtDateOfBirth = (CustomEditText) findViewById(R.id.txtDateOfBirth);
        txtDateOfBirth.setOnClickListener(this);

        tvxLogin = (CustomTextView)findViewById(R.id.tvLogin);
        tvxLogin.setOnClickListener(this);

        rbClient = (CustomRadioButton)findViewById(R.id.rbClient);
        rbClient.setOnClickListener(this);

        rbServiceProviders = (CustomRadioButton)findViewById(R.id.rbServiceProvider);
        rbServiceProviders.setOnClickListener(this);

        ssServices = (SearchableSpinner)findViewById(R.id.spServices);
        ssServices.setVisibility(View.GONE);
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
        ssServices.setSelection(0);

        ssLocations = (SearchableSpinner)findViewById(R.id.spLocations);
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
        ssLocations.setSelection(0);

        calCalendar = Calendar.getInstance();
        nYear = calCalendar.get(Calendar.YEAR);
        nMonth = calCalendar.get(Calendar.MONTH);
        nDay = calCalendar.get(Calendar.DAY_OF_MONTH);

        XX_ShowDate(nYear,
                nMonth,
                nDay);
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

    @Override
    public void onClick(View v) {

        Intent intent;
        Boolean bErrorFound = false;

        switch (v.getId())
        {
            case R.id.tvLogin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.btnCreateAccount:
                XX_ValidateRegistrationValuesAndAddToDataBase();

                break;
            case R.id.txtDateOfBirth:
                SelectDateOfBirth(v);
                break;
            case R.id.btnRegselectimage:
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
            case R.id.rbClient:
                ssServices.setVisibility(View.INVISIBLE);
                break;
            case  R.id.rbServiceProvider:
                ssServices.setVisibility(View.VISIBLE);
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                szxBitmapFileName = getRealPathFromURI(uri);

                isxBitmapFile  = getContentResolver().openInputStream(uri);

                Bitmap bmpResized = getResizedBitmap(bitmap, 1000, 1000);
                bmpxSelectedImage = bmpResized;
                iv.setImageBitmap(bmpResized);
                bxProfileImageSelected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);

        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return thePath;
    }



    private void XX_ValidateRegistrationValuesAndAddToDataBase()
    {
        String szFullName = ((EditText)findViewById(R.id.txtFullName)).getText().toString();
        String szEmail = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        String szPassword = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        String szReEnterPassword = ((EditText)findViewById(R.id.txtReEnterPassword)).getText().toString();
        String szIDNumber = ((EditText)findViewById(R.id.txtIdNumber)).getText().toString();
        String szMobileNo = ((EditText)findViewById(R.id.txtMobile)).getText().toString();
        String szAddress = ((EditText)findViewById(R.id.txtAddress)).getText().toString();
        String szLocation = szxSelectedLocation;
        String szServiceProviding = szxSelectedService;
        String szDateOfBirth = ((EditText)findViewById(R.id.txtDateOfBirth)).getText().toString();
        String szEncryptedPassword = "";
        int nSelectedUserType = rgUsers.getCheckedRadioButtonId();

        while (true)
        {
            if(bxProfileImageSelected == false)
            {
                Toast.makeText(getApplicationContext(),
                               "Please select Profile Image",
                               Toast.LENGTH_LONG).show();
                break;
            }
            if(szFullName.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Name Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szEmail.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Email Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            boolean bEmailIsValid = emailIsValid(szEmail);
            if(bEmailIsValid)
            {
                Toast.makeText(getApplicationContext(),
                        "Email Is invalid",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szPassword.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Password Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szReEnterPassword.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Confirm Password Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(!szPassword.equals(szReEnterPassword))
            {
                Toast.makeText(getApplicationContext(),
                        "Passwords do not Match!",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szIDNumber.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "ID Number Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(NumericValueIsValid(szIDNumber) == false)
            {
                Toast.makeText(getApplicationContext(),
                        "ID Number contains Invalid Character(s)",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szMobileNo.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Mobile Number Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(NumericValueIsValid(szMobileNo) == false)
            {
                Toast.makeText(getApplicationContext(),
                        "Mobile Number contains Invalid Character(s)",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szAddress.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Address Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(szLocation.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Select a Location",
                        Toast.LENGTH_LONG).show();
                break;
            }

            if(nSelectedUserType == R.id.rbServiceProvider)
            {
                if (szServiceProviding.trim().length() <= 0)
                {
                    Toast.makeText(getApplicationContext(),
                            "Select a Service",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if(szDateOfBirth.trim().length() <= 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Date of Birth Field is Empty",
                        Toast.LENGTH_LONG).show();
                break;
            }
            UUID uuid = UUID.randomUUID();
            String szToken = uuid.toString();
            int nUserType = 0;

            switch (nSelectedUserType)
            {
                case R.id.rbClient:
                    nUserType = 0;
                    break;
                case R.id.rbServiceProvider:
                    nUserType = 1;
                    break;
                default:
                    nUserType = 0;
                    break;
            }

            EncryptionClass ec = new EncryptionClass();
            try {
                szEncryptedPassword = ec.encrypt(szPassword);
            }
            catch (Exception ex)
            {
                szEncryptedPassword = szPassword;
            }
            udox.setFullName(szFullName);
            udox.setImage(bmpxSelectedImage);
            udox.setEmail(szEmail);
            udox.setPassword(szEncryptedPassword);
            udox.setIDNumber(szIDNumber);
            udox.setAddress(szAddress);
            udox.setLocation(szLocation);
            udox.setServiceProviding(szServiceProviding);
            udox.setMobileNumber(szMobileNo);
            udox.setProfileImageName(szxBitmapFileName);
            udox.setDateOfBirth(szDateOfBirth);
            udox.setUserType(nUserType);
            udox.setToken(szToken);
            udox.setUserActivated(0);

            String szImage = getStringImage(bmpxSelectedImage);
            new AsyncRegisterUser().execute(szFullName,
                                            szImage,
                                            szEmail,
                                            szEncryptedPassword,
                                            szIDNumber,
                                            szAddress,
                                            szLocation,
                                            szServiceProviding,
                                            szMobileNo,
                                            szxBitmapFileName,
                                            szDateOfBirth,
                                            String.valueOf(nUserType),
                                            szToken,
                                            "0");
            break;
        }
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
            Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return "";
        }
    }

    private Boolean emailIsValid(String szvEmail)
    {
        String EMAIL_REGIX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_REGIX);
        Matcher matcher = pattern.matcher(szvEmail);
        return ((!szvEmail.isEmpty()) && (szvEmail!=null) && (matcher.matches()));
    }

    private void XX_CopyProfileImageToApplicationFolderIf() {

        String szDestination = Environment.getExternalStorageDirectory() + File.separator + "UTU" + File.separator + szxBitmapFileName;

        File flDestination = new File(szDestination);
        OutputStream out = null;

        try {
            out = new FileOutputStream(flDestination);
            byte[] buf = new byte[1024];
            int len;
            while((len=isxBitmapFile.read(buf))>0){
                out.write(buf,0,len);
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
        finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if ( out != null ) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                isxBitmapFile.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    private boolean NumericValueIsValid(String szvIDNumber)
    {
        for(int i = 0; i<=szvIDNumber.length() - 1;i++)
        {
            if(!Character.isDigit(szvIDNumber.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private void SelectDateOfBirth(View v)
    {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener,
                    nYear,
                    nMonth,
                    nDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int nvYear, int nvMonth, int nvDay) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    XX_ShowDate(nvYear, nvMonth+1, nvDay);
                }
            };

    private void XX_ShowDate(int nvYear,
                             int nvMonth,
                             int nvDay) {

        String szDay = String.valueOf(nvDay);
        String szMonth = String.valueOf(nvMonth);
        String szYear = String.valueOf(nvYear);

        if(szDay.length() == 1)
        {
            szDay = "0" + szDay;
        }

        if(szMonth.length() == 1)
        {
            szMonth = "0" + szMonth;
        }

        txtDateOfBirth.setText(new StringBuilder().append(szDay)
                .append("-")
                .append(szMonth)
                .append("-")
                .append(szYear));
    }

    private class AsyncRegisterUser extends AsyncTask<String, String, JSONObject>
    {
        ProgressDialog pdLoading = new ProgressDialog(RegisterUserActivity.this);
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

            params.add(new BasicNameValuePair(KEY_FULL_NAME, args[0]));
            params.add(new BasicNameValuePair(KEY_IMAGE, args[1]));
            params.add(new BasicNameValuePair(KEY_EMAIL, args[2]));
            params.add(new BasicNameValuePair(KEY_PASSWORD, args[3]));
            params.add(new BasicNameValuePair(KEY_ID_NUMBER, args[4]));
            params.add(new BasicNameValuePair(KEY_ADDRESS, args[5]));
            params.add(new BasicNameValuePair(KEY_LOCATION, args[6]));
            params.add(new BasicNameValuePair(KEY_SERVICE_PROVIDING, args[7]));
            params.add(new BasicNameValuePair(KEY_MOBILE_NUMBER, args[8]));
            params.add(new BasicNameValuePair(KEY_PROFILE_IMAGE_NAME, args[9]));
            params.add(new BasicNameValuePair(KEY_PROFILE_IMAGE_URL, ""));
            params.add(new BasicNameValuePair(KEY_DATE_OF_BIRTH, args[10]));
            params.add(new BasicNameValuePair(KEY_USER_TYPE, args[11]));
            params.add(new BasicNameValuePair(KEY_TOKEN, args[12]));
            params.add(new BasicNameValuePair(KEY_ACTIVATED, args[13]));
            
            JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST", params);

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
                                XX_CopyProfileImageToApplicationFolderIf();

                                szEmailBody = "<HTML>"
                                        + "  <P>"
                                        + "      Thanks for Registering with UTU, Please click on the provided link to Activate your Accout.</BR>"
                                        + "      <a href='http://www.mantikiplan.solutions/utu/activate.php?Token=" + udox.getToken() + "'>http://www.mantikiplan.solutions/activate</a>"
                                        + "  </p>"
                                        + "</HTML>";

                                new EMailTask(RegisterUserActivity.this).execute("utuapplication@gmail.com",
                                        "1utuapplication@", udox.getEmail(), "Registration", szEmailBody);

                                Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                                Intent iLogin = new Intent(RegisterUserActivity.this, LoginActivity.class);
                                startActivity(iLogin);
                                break;

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
