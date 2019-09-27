package com.utu.utu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class EMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity mainActivity;

    public EMailTask(Activity activity) {
        mainActivity = activity;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(mainActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
//            Log.i("SendMailTask", "About to instantiate GMail...");
//            publishProgress("Processing input....");
            EMailSender androidEmail = new EMailSender(args[0].toString(),
                    args[1].toString(), args[2].toString(), args[3].toString(),
                    args[4].toString());
//            publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
//            publishProgress("Sending email....");
            androidEmail.sendEmail();
//            publishProgress("Email Sent.");
//            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("Confirmation Mail..", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result)
    {
        statusDialog.dismiss();
    }

}
