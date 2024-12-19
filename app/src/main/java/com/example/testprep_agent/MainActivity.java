package com.example.testprep_agent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testprep_agent.data.Agent;
import com.example.testprep_agent.network.AgentParser;
import com.example.testprep_agent.network.AsyncTaskRunner;
import com.example.testprep_agent.network.Callback;
import com.example.testprep_agent.network.HttpManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    private static final String url = "https://api.npoint.io/efcea1d46c36fa555d04";
    private FloatingActionButton fab;
    private Spinner spinner;
    private TextView tv;
    private EditText et;
    private Button bttn;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private List<Agent> agents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();

        fab.setOnClickListener(click -> {
            Callable<String> httpManager = new HttpManager(url);
            Callback<String> httpManagerCallback = onMainThreadOperation();
            asyncTaskRunner.executeAsync(httpManager, httpManagerCallback);
        });
    }

    private Callback<String> onMainThreadOperation() {
        return result -> {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            List<Agent> parsedAgents = AgentParser.getFromJSON(result);
            agents.addAll(parsedAgents);
            Log.i("Agents", agents.toString());
        };
    }

    private void initComponents() {
        fab = findViewById(R.id.surugiu_george_alexandru_fab);
        spinner = findViewById(R.id.surugiu_george_alexandru_spinner);
        tv = findViewById(R.id.surugiu_george_alexandru_tv);
        et = findViewById(R.id.surugiu_george_alexandru_et);
        bttn = findViewById(R.id.surugiu_george_alexandru_bttn);
    }
}