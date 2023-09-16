package com.example.dht11esp8266firebasejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RemoteControl extends AppCompatActivity {

    private TextView temperatureTextView;
    private int currentTemperature = 21;
    private ImageView powerImageView;
    private boolean isPowerOn = false;

    private static final String AC_BASE_URL = "http://your_ac_ip_address"; // Replace with actual IP

    Button currentlySelectedButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        temperatureTextView = (TextView) findViewById(R.id.tvTemperature);
        temperatureTextView.setText(currentTemperature +"°C");

        Button increaseButton = findViewById(R.id.btnIncreaseTemp);
        Button decreaseButton = findViewById(R.id.btnDecreaseTemp);
        Button lowFanButton = findViewById(R.id.btnLowFan);
        Button mediumFanButton = findViewById(R.id.btnMediumFan);
        Button highFanButton = findViewById(R.id.btnHighFan);


        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTemperature < 30) { // Maximum temperature limit
                    currentTemperature++;
                    updateTemperatureDisplay();
                    sendHttpRequest("temperature", String.valueOf(currentTemperature));

                }
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentTemperature > 16) { // Minimum temperature limit
                    currentTemperature--;
                    updateTemperatureDisplay();
                    sendHttpRequest("temperature", String.valueOf(currentTemperature));

                }
            }
        });
        powerImageView = findViewById(R.id.imgPower);

        powerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPowerOn = !isPowerOn;
                updatePowerImage();
                sendHttpRequest("power", isPowerOn ? "on" : "off");
            }
        });


        lowFanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(lowFanButton);
                sendHttpRequest("fan_speed", "low");
            }
        });

        mediumFanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(mediumFanButton);
                sendHttpRequest("fan_speed", "medium");
            }
        });

        highFanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(highFanButton);
                sendHttpRequest("fan_speed", "high");
            }
        });

    }

    private void updateTemperatureDisplay() {
        temperatureTextView.setText(currentTemperature + "°C");
    }

    private void updatePowerImage() {
        if (isPowerOn) {
            powerImageView.setImageResource(R.drawable.power_on_image);
        } else {
            powerImageView.setImageResource(R.drawable.power_off_image);
        }
    }


    private void sendHttpRequest(String parameter, String value) {
        new HttpRequestTask().execute(AC_BASE_URL + "/control", parameter, value);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String urlStr = params[0];
            String parameter = params[1];
            String value = params[2];

            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String requestData = parameter + "=" + value;

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                // Handle response if needed
                // ...

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void handleButtonClick(Button clickedButton) {
        // Check if a different button is currently selected
        if (currentlySelectedButton != null && currentlySelectedButton != clickedButton) {
            // Reset the background of the previously selected button
            currentlySelectedButton.setBackgroundResource(R.drawable.button_background);
        }

        // Set the selected state of the clicked button
        clickedButton.setSelected(!clickedButton.isSelected());

        // Update the background drawable based on the selected state
        clickedButton.setBackgroundResource(clickedButton.isSelected() ? R.drawable.button_background_selected : R.drawable.button_background);

        // Update the currently selected button
        currentlySelectedButton = clickedButton;

    }

}