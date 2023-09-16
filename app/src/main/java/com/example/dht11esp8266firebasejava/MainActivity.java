package com.example.dht11esp8266firebasejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dht11esp8266firebasejava.ui.main.SectionsPagerAdapter;
import com.example.dht11esp8266firebasejava.databinding.ActivityMainBinding;


import static com.example.dht11esp8266firebasejava.App.CHANNEL_1_ID;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    //private static final String CHANNEL_1_ID = "channel_1";

    private NotificationManagerCompat notificationManager;
    //private NotificationManager notificationManager;
    BottomNavigationView bottom;
    private ImageView image1, image2;
    private DatabaseReference ref, warning;
    public static String AlertTopic = "Feedback";


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);

        bottom = findViewById(R.id.menu_bar);

        notificationManager = NotificationManagerCompat.from(this);

        bottom.setOnNavigationItemSelectedListener(nav_listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        warning = FirebaseDatabase.getInstance().getReference("Warning");
        /*firealert();
        intrusion();
        gas();
        airquality();*/
        temperature();
        humidity();



    }

    private void humidity() {

        ref = FirebaseDatabase.getInstance().getReference("humidity");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                int humid = Integer.parseInt(data);
                int limit = 70;
                if (humid > limit) {
                    notification("Humidity Alert", "Humidity is higher than the limit value.", "Humidity", 6);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void temperature() {

        ref = FirebaseDatabase.getInstance().getReference("temperature");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                Double temp = Double.parseDouble(data);
                Double limit = 40.00d;
                if (temp > limit) {
                    notification("Temperature Alert", "Temperature is higher than the limit value!", "Temperature", 4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void notification(String title, String message, String type,int id) {

        Map<String,Object> insertvalues=new HashMap<>();
        insertvalues.put("Type",type);
        String date = (new Date()).toString();
        date = date.substring(4,16);
        insertvalues.put("Time",date);
        String key=warning.push().getKey();
        warning.child(key).setValue(insertvalues);

        //Intent activityintent = new Intent(MainActivity.this,Login.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,activityintent,0);

        Intent activityIntent = new Intent(this, Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.smart_home)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(Color.BLACK)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(id, notification);

    }
    

    private class SendSmsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                // Construct data
                String apiKey = "apikey=" + "NzI1MjMwNzQ2MzM2NDkzMjRhNTczMTZiNjU0ODdhNDM=";
                String message = "&message=" + "hometest";
                String sender = "&sender=" + "SmartHome";
                String numbers = "&numbers=" + "+60199362101";

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                    Log.e("test", line);
                }
                rd.close();

                return stringBuffer.toString();
            } catch (Exception e) {
                Log.e("error", "ERROR!", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Process the result or update UI accordingly
            } else {
                // Handle error case or update UI accordingly
            }
        }
    }


    private void airquality() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/AirQuality");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                int temp = Integer.parseInt(data);
                int limit = 500;
                if (temp > limit) {
                    notification("Don’t let our future go up in the smoke", "Air Quality is higher than the limit value, Stay Safe", "Air Quality", 5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void gas() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Gas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if (data.equals("1")) {
                    notification_with_call("I want to grow my own food but I can't find Butter Chicken seeds", "A Gas leakage has been detected, please don't waste gas", "Gas", 3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intrusion() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Intrusion");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if (data.equals("1")) {
                    notification_with_call("Intrusion Alert", "A Intrusion has been detected", "Intrusion", 2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firealert() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Fire");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String data = dataSnapshot.getValue(String.class);
                if (data.equals("0")) {

                    notification_with_call("Fire Alert", "A fire has been detected", "Fire", 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void notification_with_call(String title, String message, String type, int id) {
        Map<String, Object> insertvalues = new HashMap<>();
        insertvalues.put("Type", type);
        String date = (new Date()).toString();
        date = date.substring(4, 16);
        insertvalues.put("Time", date);
        String key = warning.push().getKey();
        warning.child(key).setValue(insertvalues);

        Intent activityintent = new Intent(MainActivity.this, Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, activityintent, 0);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0199362101"));

        PendingIntent pcallIntent = PendingIntent.getActivity(MainActivity.this, 0, callIntent, 0);

        Intent EmergencyIntent = new Intent(Intent.ACTION_CALL);
        EmergencyIntent.setData(Uri.parse("tel:112"));

        PendingIntent ecallIntent = PendingIntent.getActivity(MainActivity.this, 0, EmergencyIntent, 0);


        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.smart_home)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(Color.BLACK)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .addAction(R.mipmap.ic_launcher, "Call Neighbour", pcallIntent)
                .addAction(R.mipmap.ic_launcher, "Call Emergency", ecallIntent)
                .setAutoCancel(true)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(id, notification);
    }




    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Confirm Exit..!!");


        //SendSmsTask sendSmsTask = new SendSmsTask();
        //sendSmsTask.execute();


        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setMessage("Are you sure you want to exit ?");

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });


        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert= alertDialogBuilder.create();
        alert.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selecteditem = null;
            switch (item.getItemId()){
                case R.id.nav_home:         selecteditem= new HomeFragment();
                    break;
                case R.id.nav_appliance:     selecteditem= new ApplianceFragment();
                    break;
                case R.id.nav_warning:      selecteditem= new WarningFragment();
                    break;
                case R.id.nav_profile:      selecteditem = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selecteditem).commit();
            return  true;
        }
    };

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent i= new Intent(MainActivity.this,Login.class);
        startActivity(i);

    }

    public void feedback(View view) {
        AlertTopic = "Feedback";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }

    public void request(View view) {

        AlertTopic = "Request";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }

    public void problem(View view) {
        AlertTopic = "Problem";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }
}