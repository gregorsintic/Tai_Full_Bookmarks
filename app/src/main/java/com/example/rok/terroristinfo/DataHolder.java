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
        /*File file           = new File(context.getFilesDir(), FILENAME);
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
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
        ////////////////////////////
        tring("icon"),
                        jsonObject.getString("location"),
                        jsonObject.getString("briefSummary"),
                        jsonObject.getString("eventInfo"),
                        jsonObject.getString("weekDay"),
                        jsonObject.getString("month"),
                        jsonObject.getInt("day"),
                        jsonObject.getString("year"),
                        jsonObject.getInt("notify"),
                        jsonObject.getString("eventType"),
                        jsonObject.getInt("mainEvent")*/
        String stringData = "[{\"id\":1,\"lat\":30.186,\"lng\":66.9989,\"icon\":\"ak_red\",\"location\":\"Quetta, Balochistan, Pakistan\",\"briefSummary\":\"Suicide attack, Automatic Rifle and explosives used. 80+ killed, 120+ injured. Islamic state claimed responsibility.\",\"eventInfo\":\"On 8 August 2016, terrorists attacked the Government Hospital of Quetta in Pakistan with a suicide bombing and shooting. They killed around 80 people (mainly lawyers). Responsibility for the attack has been initially claimed by Islamic state (ISIS). However, Jamaat-ul-Ahrar also claimed credit for thr attack.\",\"time\":\"10:00\",\"weekDay\":\"Monday\",\"month\":\"August\",\"day\":8,\"year\":2016,\"notify\":0,\"eventType\":\"rifleAssault\",\"mainEvent\":1},{\"id\":5,\"lat\":49.3828,\"lng\":1.10672,\"icon\":\"hostage_black\",\"location\":\"Saint-Étienne-du-Rouvray, Norm\",\"briefSummary\":\"Two Islamist terrorists took six people captive and later slit throat of one of them (85 year old priest). The terrorist were later shot dead by police.\",\"eventInfo\":\"At about 9.45, two men wielding knives, handgun and fake explosive belts entered church as Mass was being held. Priest, three nuns and two parishioners were taken hostage. The attackers forced the priest to kneel at the altar and then slit his throat while screaming 'Allahu akbar'. Parishioner was later knifed and left critically wounded (he survived). Other Hostages were unhurt. Police tried to negotiate with perpetrators - without success. Later they tried to run out of the church with hostages as human shield, but the police successfully eliminated them.\",\"time\":\"09:45\",\"weekDay\":\"Tuesday\",\"month\":\"July\",\"day\":26,\"year\":2016,\"notify\":0,\"eventType\":\"hostageSituation\",\"mainEvent\":1}] ";

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
