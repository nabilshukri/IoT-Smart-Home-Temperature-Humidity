package com.example.dht11esp8266firebasejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.github.anastr.speedviewlib.SpeedView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Analysis extends AppCompatActivity {

        private DatabaseReference ref,ref2;

        private LineChart chart,chart2;

        private TextView temperature;
        private BarChart barChart;

        float f1, f2,f3,f4,f5,f6,f7,f8,f9,f10;
        float h1, h2,h3,h4,h5,h6,h7,h8,h9,h10;

        float ff1, ff2,ff3,ff4,ff5,ff6,ff7,ff8,ff9,ff10,ff11,ff12,ff13,ff14,ff15,
                ff16,ff17,ff18,ff19,ff20,ff21,ff22,ff23,ff24;

        float hh1, hh2,hh3,hh4,hh5,hh6,hh7,hh8,hh9,hh10,hh11,hh12,hh13,hh14,hh15,
                hh16,hh17,hh18,hh19,hh20,hh21,hh22,hh23,hh24;





        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_analysis);


            init();

            readTemperature();

            drawLineChart();

            drawBarChart();

        }

        private void init() {

            temperature=findViewById(R.id.temperature);
            chart=findViewById(R.id.chart);

            barChart = findViewById(R.id.bar);



        }
        private void readTemperature() {

            ref= FirebaseDatabase.getInstance().getReference("temperature");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                Float f=Float.parseFloat(data);

                //temperature.setText("Temperature Now : " + f +" °C");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed");
            }
        });


    }

    private void drawLineChart() {

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        String mon[]= {"min 01","min 02","min 03","min 04","min 05","min 06","min 07","min 08","min 09","min 10"};

        ArrayList<Entry> yvalues = new ArrayList<>();
        ArrayList<Entry> xvalues=new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            final int index = i;

            ref = FirebaseDatabase.getInstance().getReference("/temp/temperature" + i);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String value = dataSnapshot.getValue().toString();
                        float temperature = Float.parseFloat(value);

                        switch (index) {
                            case 1:
                                f1 = temperature;
                                break;
                            case 2:
                                f2 = temperature;
                                break;
                            case 3:
                                f3 = temperature;
                                break;
                            case 4:
                                f4 = temperature;
                                break;
                            case 5:
                                f5 = temperature;
                                break;
                            case 6:
                                f6 = temperature;
                                break;
                            case 7:
                                f7 = temperature;
                                break;

                            case 8:
                                f8 = temperature;
                                break;

                            case 9:
                                f9 = temperature;
                                break;

                            case 10:
                                f10 = temperature;
                                break;
                            default:
                                break;
                        }

                        // Update yvalues list with the new values
                        yvalues.clear();
                        yvalues.add(new Entry(0, f1));
                        yvalues.add(new Entry(1, f2));
                        yvalues.add(new Entry(2, f3));
                        yvalues.add(new Entry(3, f4));
                        yvalues.add(new Entry(4, f5));
                        yvalues.add(new Entry(5, f6));
                        yvalues.add(new Entry(6, f7));
                        yvalues.add(new Entry(6, f8));
                        yvalues.add(new Entry(6, f9));
                        yvalues.add(new Entry(6, f10));

                        for (int j = 1; j <= 10; j++) {

                            final int index2 = j;
                            ref2 = FirebaseDatabase.getInstance().getReference("/humid/humidity" + j);
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String value2 = snapshot.getValue().toString();
                                        float humid = Float.parseFloat(value2);

                                        switch (index2) {
                                            case 1:
                                                h1 = humid;
                                                break;
                                            case 2:
                                                h2 = humid;
                                                break;
                                            case 3:
                                                h3 = humid;
                                                break;
                                            case 4:
                                                h4 = humid;
                                                break;
                                            case 5:
                                                h5 = humid;
                                                break;
                                            case 6:
                                                h6 = humid;
                                                break;
                                            case 7:
                                                h7 = humid;
                                                break;

                                            case 8:
                                                h8 = humid;
                                                break;

                                            case 9:
                                                h9 = humid;
                                                break;

                                            case 10:
                                                h10 = humid;
                                                break;
                                            default:
                                                break;
                                        }

                                        // Update yvalues list with the new values
                                        xvalues.clear();
                                        xvalues.add(new Entry(0, h1));
                                        xvalues.add(new Entry(1, h2));
                                        xvalues.add(new Entry(2, h3));
                                        xvalues.add(new Entry(3, h4));
                                        xvalues.add(new Entry(4, h5));
                                        xvalues.add(new Entry(5, h6));
                                        xvalues.add(new Entry(6, h7));
                                        xvalues.add(new Entry(6, h8));
                                        xvalues.add(new Entry(6, h9));
                                        xvalues.add(new Entry(6, h10));

                                        LineDataSet set1= new LineDataSet(yvalues,"Temperature in °C");
                                        set1.setColor(Color.RED);
                                        set1.setValueTextColor(Color.WHITE);
                                        set1.setValueTextSize(10f);

                                        LineDataSet set2= new LineDataSet(xvalues,"Humidity in %");
                                        set2.setColor(Color.YELLOW);
                                        set2.setValueTextColor(Color.WHITE);
                                        set2.setValueTextSize(10f);

                                        ArrayList<ILineDataSet> datasets= new ArrayList<>();
                                        datasets.add(set1);
                                        datasets.add(set2);
                                        LineData data=new LineData(datasets);

                                        XAxis xaxis=chart.getXAxis();
                                        xaxis.setValueFormatter(new MyFormatter(mon));
                                        xaxis.setGranularity(1f);

                                        chart.animateX(1000);
                                        chart.getXAxis().setTextColor(-1);
                                        chart.getLegend().setTextColor(-1);

                                        chart.setData(data);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("Failed");
                }
            });
        }

    }

    private void drawBarChart() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 1; i <= 24; i++)
        {
            final int index = i;
            ref = FirebaseDatabase.getInstance().getReference("/temp24hours/temperature" + i);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String value = snapshot.getValue().toString();
                        float temperature = Float.parseFloat(value);

                        switch (index) {
                            case 1:
                                ff1 = temperature;
                                break;
                            case 2:
                                ff2 = temperature;
                                break;
                            case 3:
                                ff3 = temperature;
                                break;
                            case 4:
                                ff4 = temperature;
                                break;
                            case 5:
                                ff5 = temperature;
                                break;
                            case 6:
                                ff6 = temperature;
                                break;
                            case 7:
                                ff7 = temperature;
                                break;

                            case 8:
                                ff8 = temperature;
                                break;

                            case 9:
                                ff9 = temperature;
                                break;

                            case 10:
                                ff10 = temperature;
                                break;

                            case 11:
                                ff11 = temperature;
                                break;
                            case 12:
                                ff12 = temperature;
                                break;
                            case 13:
                                ff13 = temperature;
                                break;
                            case 14:
                                ff14 = temperature;
                                break;
                            case 15:
                                ff15 = temperature;
                                break;
                            case 16:
                                ff16 = temperature;
                                break;
                            case 17:
                                ff17 = temperature;
                                break;

                            case 18:
                                ff18 = temperature;
                                break;

                            case 19:
                                ff19 = temperature;
                                break;

                            case 20:
                                ff20 = temperature;
                                break;

                            case 21:
                                ff21 = temperature;
                                break;

                            case 22:
                                ff22 = temperature;
                                break;

                            case 23:
                                ff23 = temperature;
                                break;

                            case 24:
                                ff24 = temperature;
                                break;
                            default:
                                break;
                        }

                        float average1 = (ff1 + ff2 + ff3 + ff4) / 4.0f;
                        float average2 = (ff5 + ff6 + ff7 + ff8) / 4.0f;
                        float average3 = (ff9 + ff10 + ff11 + ff12) / 4.0f;
                        float average4 = (ff13 + ff14 + ff15 + ff16) / 4.0f;
                        float average5 = (ff17 + ff18 + ff19 + ff20) / 4.0f;
                        float average6 = (ff21 + ff22 + ff23 + ff24) / 4.0f;


                        entries.clear();
                        entries.add(new BarEntry(0f, average1));
                        entries.add(new BarEntry(1f, average2));
                        entries.add(new BarEntry(2f, average3));
                        entries.add(new BarEntry(3f, average4));
                        entries.add(new BarEntry(4f, average5));
                        entries.add(new BarEntry(5f, average6));

                        BarDataSet barDataSet = new BarDataSet(entries, "Average Temperature in °C");
                        barDataSet.setBarBorderWidth(0.9f);
                        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        BarData barData = new BarData(barDataSet);
                        barData.setValueTextColor(-1);
                        barData.setValueTextSize(15f);
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        final String[] months = new String[]{"12am-4am", "4am-8am", "8am-12pm", "12pm-4pm", "4pm-8pm", "8pm-12am"};
                        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
                        xAxis.setGranularity(1f);
                        xAxis.setValueFormatter(formatter);
                        barChart.setData(barData);
                        barChart.setFitBars(true);
                        barChart.animateXY(5000, 5000);
                        barChart.invalidate();
                        barChart.getXAxis().setTextColor(-1);
                        barChart.getLegend().setTextColor(-1);



                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("Failed");
                }
            });
        }




    }

}


class MyFormatter extends ValueFormatter {

    public String valus[];

    public MyFormatter(String[] values) {
        this.valus = values;
    }

    @Override
    public String getAxisLabel( float value, AxisBase axis) {
        return valus[(int)value];
    }
}