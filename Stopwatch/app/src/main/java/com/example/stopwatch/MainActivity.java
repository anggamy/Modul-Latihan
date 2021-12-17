package com.example.stopwatch_hendriyanihsanmutakin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button start, pause, reset, lap;
    long MillisecondTime, StratTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Second, Minutes, MilliSecond;
    ListView listView;
    String[] ListElements = new String[]{};
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        start = (Button) findViewById(R.id.button);
        pause = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);
        lap = (Button) findViewById(R.id.button4);
        listView = (ListView) findViewById(R.id.listView);

        handler = new Handler();
        ListElementsArrayList = new CopyOnWriteArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView.setAdapter(adapter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StratTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
            }

        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable, 0);

                reset.setEnabled(true);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L;
                StratTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Second = 0;
                Minutes = 0;
                MilliSecond = 0;
                textView.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementsArrayList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StratTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Second = (int) (UpdateTime / 1000);
            Minutes = Second / 60;
            Second = Second % 60;
            MilliSecond = (int) (UpdateTime % 1000);
            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Second) + ":"
                    + String.format("%03d", MilliSecond));
            handler.postDelayed(this, 0);
        }

    };

}