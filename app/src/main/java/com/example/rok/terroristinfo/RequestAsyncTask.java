package com.example.rok.terroristinfo;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

class RequestAsyncTask extends AsyncTask<String, String, String> {

    private ProgressDialog dialog;
    private Activity       activity;
    private Context        context;
    // show toast in onPostExecute if IOException was caught (can't use Toast in doInBackground())
    boolean caughtException = false;


    public RequestAsyncTask(Activity activity, Context context) {
        this.activity = activity;
        this.context  = context;
    }

    @Override
    protected String doInBackground(String... uri) {

        String responseString = null;

        //timeout parameters
        final HttpParams httpParams = new BasicHttpParams();

        // timeout for how long to wait to establish a TCP connection
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);

        //timeout for how long to wait for a subsequent byte of data
        HttpConnectionParams.setSoTimeout(httpParams, 2000);

        HttpClient httpClient = new DefaultHttpClient(httpParams);

        HttpResponse response;

        try {
            response = httpClient.execute(new HttpGet(uri[0]));
            StatusLine statusLine;
            statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO ?
        }
        catch (IOException e) {
            caughtException = true;
        }
        return responseString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // show dialog only when refreshButton was clicked
        if (activity instanceof AppCompatActivity) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Please wait, loading data ...");
            dialog.show();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // users internet obviously working, probably problem with server
        if (caughtException) {
            Toast.makeText(activity, "If your connection is working properly, please, try again later (there is probably a problem on our side).", Toast.LENGTH_LONG).show();
            DataHolder.setDataFromInternalStorage(context);
        }
        if (activity instanceof AppCompatActivity && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (result != null) {
            // there is data, store it to internal storage and set it.
            DataHolder.storeData(result, context);
            DataHolder.setData(result);
            if (activity instanceof AppCompatActivity) {
                // again, only recreate when refresh button was clicked
                activity.recreate();
            }
        }
    }
}