package com.example.rok.terroristinfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestNotifyTask extends AsyncTask<String, String, String> {
    private Data[] data;
    private Context context;
    private NotificationManager notificationManager;

    public RequestNotifyTask(Context context) {
        this.context = context;
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
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {}
        catch (IOException e) {}
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null || result.equals("")) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(result);

            data = new Data[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data[i] = new Data(
                        jsonObject.getInt("id"),
                        (float)jsonObject.getInt("lat"), (float)jsonObject.getInt("lng"),
                        jsonObject.getString("icon"),
                        jsonObject.getString("location"),
                        jsonObject.getString("briefSummary"),
                        jsonObject.getString("eventInfo"),
                        jsonObject.getString("weekDay"),
                        jsonObject.getString("month"),
                        jsonObject.getInt("day"),
                        jsonObject.getString("year"),
                        jsonObject.getInt("notify"),
                        jsonObject.getString("eventType"),
                        jsonObject.getInt("mainEvent")
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setNotification();
    }

    private void setNotification() {
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i].getNotify() == 1) {
                notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                //Build the notification using Notification.Builder
                Notification.Builder builder = new Notification.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setContentTitle(data[i].getLocation())
                        .setContentText(data[i].getBriefSummary());

                //Show the notification
                notificationManager.notify(1, builder.build());
            }
        }
    }
}
