package com.example.rok.terroristinfo;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;


public class Bookmarks extends Activity {

    private HashMap<Integer, LinearLayout> items = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks_view);
        View view = findViewById(R.id.linearLayoutBookmarks);

        try {
            listBookmarks(view);
        } catch (Exception e) {
            Toast.makeText(this, "Trouble...", Toast.LENGTH_LONG).show();
        }
    }

    private void listBookmarks(View view) {
        LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.linearLayoutBookmarks);

        BookmarksDB db = new BookmarksDB(this);
        Cursor cursor = db.getAllBookmarks();

        if (cursor != null) {
            int i = 0;
            if (cursor.moveToFirst()) {
                do {

                    LinearLayout mainLayout = new LinearLayout(this);
                    mainLayout.setPadding(5, 10, 5, 10);
                    mainLayout.setClickable(true);

                    mainLayout.setId(i);
                    items.put(i, mainLayout);
                    drawBorder(mainLayout);


                    //for title and text
                    LinearLayout verticalLayout = new LinearLayout(this);
                    verticalLayout.setOrientation(LinearLayout.VERTICAL);
                    verticalLayout.setPadding(12, 0, 0, 0);

                    String location = cursor.getString(cursor.getColumnIndex("location"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String summary = cursor.getString(cursor.getColumnIndex("summary"));
                    String icon = cursor.getString(cursor.getColumnIndex("icon"));


                    TextView tv_location = new TextView(this);
                    tv_location.setTextSize(19);
                    tv_location.setTextColor(Color.DKGRAY);
                    tv_location.setText(location);

                    //DATE
                    TextView tv_date = new TextView(this);
                    tv_date.setTextSize(15);
                    tv_date.setTextColor(Color.DKGRAY);
                    tv_date.setText(date);

                    //TEXT
                    ExpandableTextView etv_summary = new ExpandableTextView(this);
                    etv_summary.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                    etv_summary.setTextSize(13);
                    etv_summary.setText('\n' + summary);
                    etv_summary.makeExpandable(5);


                    //ICON
                    TextView tv_icon = new TextView(this);
                    tv_icon.setCompoundDrawablesWithIntrinsicBounds(getIconFromString(icon), 0, 0, 0);

                    mainLayout.addView(tv_icon);
                    verticalLayout.addView(tv_location);
                    verticalLayout.addView(tv_date);
                    verticalLayout.addView(etv_summary);

                    mainLayout.addView(verticalLayout);

                    rootLayout.addView(mainLayout);


                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Database problems...", Toast.LENGTH_LONG).show();
            return;
        }


    }
    private void drawBorder(LinearLayout linLayout) {
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xEDEDEDFF);
        border.setStroke(3, 0x6B6B6BFF); //black border with full opacity
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            linLayout.setBackgroundDrawable(border);
        } else {
            linLayout.setBackground(border);
        }
    }

    public int getIconFromString(String icon) {
        switch(icon) {
            case "ak_red":
                return R.drawable.ak_red;
            case "ak_green":
                return R.drawable.ak_green;
            case "ak_black":
                return R.drawable.ak_black;
            case "ak_yellow":
                return R.drawable.ak_yellow;

            case "gun_red":
                return R.drawable.gun_red;
            case "gun_green":
                return  R.drawable.gun_green;
            case "gun_black":
                return R.drawable.gun_black;
            case "gun_yellow":
                return R.drawable.gun_yellow;

            case "stab_red":
                return R.drawable.op_red;
            case "stab_green":
                return R.drawable.op_green;
            case "stab_black":
                return R.drawable.op_black;
            case "stab_yellow":
                return  R.drawable.op_yellow;

            case "explosion_red":
                return R.drawable.explode_red;
            case "explosion_green":
                return R.drawable.explode_green;
            case "explosion_black":
                return R.drawable.explode_black;
            case "explosion_yellow":
                return R.drawable.explode_yellow;

            case "hostage_red":
                return R.drawable.hostage_red;
            case "hostage_green":
                return R.drawable.hostage_green;
            case "hostage_black":
                return R.drawable.hostage_black;
            case "hostage_yellow":
                return R.drawable.hostage_yellow;

            case "car_red":
                return R.drawable.car_red;
            case "car_green":
                return R.drawable.car_green;
            case "car_black":
                return R.drawable.car_black;
            case "car_yellow":
                return R.drawable.car_yellow;

            case "poison_red":
                return R.drawable.gas_red;
            case "poison_green":
                return R.drawable.gas_green;
            case "poison_blue":
                return R.drawable.gas_blue;
            case "poison_black":
                return R.drawable.gas_black;
            case "poison_yellow":
                return R.drawable.gas_yellow;

            case "fire_red":
                return R.drawable.fires_red;
            case "fire_green":
                return R.drawable.fires_green;
            case "fire_black":
                return R.drawable.fires_black;
            case "fire_yellow":
                return R.drawable.fires_yellow;

            case "bio_red":
                return R.drawable.biohazard_red;
            case "bio_green":
                return R.drawable.biohazard_green;
            case "bio_black":
                return R.drawable.biohazard_black;
            case "bio_yellow":
                return R.drawable.biohazard_yellow;

            case "comp_red":
                return R.drawable.comp_red;
            case "comp_green":
                return R.drawable.comp_green;
            case "comp_blue":
                return R.drawable.comp_blue;
            case "comp_black":
                return R.drawable.comp_black;
            case "comp_yellow":
                return R.drawable.comp_yellow;

            case "mask_red":
                return R.drawable.thug_red;
            case "mask_green":
                return R.drawable.thug_green;
            case "mask_black":
                return R.drawable.thug_black;
            case "mask_yellow":
                return R.drawable.thug_yellow;

            case "police_blue":
                return R.drawable.police_blue;

            case "speech_red":
                return R.drawable.speech_red;
            case "speech_green":
                return R.drawable.speech_green;
            case "speech_blue":
                return R.drawable.speech_blue;
            case "speech_black":
                return R.drawable.speech_black;
            case "speech_yellow":
                return R.drawable.speech_yellow;

            case "video_red":
                return R.drawable.video_red;
            case "video_green":
                return R.drawable.video_green;
            case "video_blue":
                return R.drawable.video_blue;
            case "video_black":
                return R.drawable.video_black;
            case "video_yellow":
                return R.drawable.video_yellow;

            case "twitterico_red":
                return R.drawable.twitterico_red;
            case "twitterico_green":
                return R.drawable.twitterico_green;
            case "twitterico_blue":
                return R.drawable.twitterico_blue;
            case "twitterico_black":
                return R.drawable.twitterico_black;
            case "twitterico_yellow":
                return R.drawable.twitterico_yellow;

            case "facebookico_red":
                return R.drawable.facebookico_red;
            case "facebookico_green":
                return R.drawable.facebookico_green;
            case "facebookico_blue":
                return R.drawable.facebookico_blue;
            case "facebookico_black":
                return R.drawable.facebookico_black;
            case "facebookico_yellow":
                return R.drawable.facebookico_yellow;

            case "dead_red":
                return R.drawable.dead_red;
            case "dead_green":
                return R.drawable.dead_green;
            case "dead_blue":
                return R.drawable.dead_blue;
            case "dead_black":
                return R.drawable.dead_black;
            case "dead_yellow":
                return R.drawable.dead_yellow;

            default:
                return R.drawable.circle_red;
        }
    }



}
