package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Chronometer chronometer;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button lapButton;
    private TextView lapTitle;
    private ListView lapList;

    private long lastPause;
    private ArrayList<String> lapTimes;
    private ArrayAdapter<String> lapListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar vistas
        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);
        lapButton = findViewById(R.id.lap_button);
        lapTitle = findViewById(R.id.lap_title);
        lapList = findViewById(R.id.lap_list);

        // Establecer clicks
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        lapButton.setOnClickListener(this);

        // Desactivar botones
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        lapButton.setEnabled(false);

        // Inicializar la lista de tiempos
        lapTimes = new ArrayList<>();

        // Inicializar adaptador de lista de vueltas
        lapListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lapTimes);
        lapList.setAdapter(lapListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                chronometer.setBase(SystemClock.elapsedRealtime() - lastPause);
                chronometer.start();

                // Activar o desactivar los botones
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(false);
                lapButton.setEnabled(true);
                break;

            case R.id.stop_button:
                lastPause = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();

                // Activar o desactivar los botones
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                lapButton.setEnabled(false);
                break;

            case R.id.reset_button:
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;

                // Limpiar la lista de tiempos
                lapTimes.clear();
                lapListAdapter.notifyDataSetChanged();

                // Activar o desactivar los botones
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
                lapButton.setEnabled(false);
                break;

            case R.id.lap_button:
                // Añadir tiempo de vuelta a la lista y adaptador
                lapTimes.add(0, chronometer.getText().toString());
                lapListAdapter.notifyDataSetChanged();
                break;
        }
    }
}
