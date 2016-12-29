package com.example.rok.terroristinfo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class DataHolder {
    private static Data[] data = null;
    public static final String FILENAME = "fileTai";

    private static int clickCounter = 1;

    private DataHolder() {}

    public static Data[] getData() {
        return data;
    }

    public static void setData(String stringData) {
        if (stringData == null || stringData.equals("")) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(stringData);

            data = new Data[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data[i] = new Data(
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
    }

    public static void storeData(String stringData, Context context) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(stringData.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDataFromInternalStorage(Context context) {
        File file           = new File(context.getFilesDir(), FILENAME);
        String stringData   = "";
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            // we are reading byte by byte
            int content;

            try {
                while ((content = fis.read()) != -1) {
                    stringData += (char)content;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        setData(stringData);
    }

    //move this to helper class

    public static int getCounter() {
        return clickCounter;
    }

    public static void setCounter(int counter) {
        clickCounter = counter;
    }
}
