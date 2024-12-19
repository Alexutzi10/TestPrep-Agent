package com.example.testprep_agent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import com.example.testprep_agent.database.AgentService;
import com.example.testprep_agent.network.AgentParser;
import com.example.testprep_agent.network.AsyncTaskRunner;
import com.example.testprep_agent.network.Callback;
import com.example.testprep_agent.network.HttpManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private static final String url = "https://api.npoint.io/efcea1d46c36fa555d04";
    public static final String APP_SHARED_PREFERENCES = "app_shared_preferences";
    public static final String SPINNER_KEY = "spinner_key";
    public static final String EDIT_TEXT_KEY = "editText_key";
    private FloatingActionButton fab;
    private Spinner spinner;
    private TextView tv;
    private EditText et;
    private Button bttn;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private List<Agent> agents = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private AgentService agentService;

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

        bttn.setOnClickListener(click -> {
            if (!isValid()) {
                Toast.makeText(getApplicationContext(), R.string.introdu_o_valoare, Toast.LENGTH_SHORT).show();
            }

            String money = et.getText().toString();
            int moneyInt = Integer.parseInt(money);
            String spinnerValue = spinner.getSelectedItem().toString();

            List<Agent> deletedAgents = new ArrayList<>();
            if (spinnerValue.equals("are valoarea mai mare decat")) {
                deletedAgents = agents.stream().filter(m -> m.getPayment() > moneyInt).collect(Collectors.toList());
            } else if (spinnerValue.equals("are valoarea mai mica decat")) {
                deletedAgents = agents.stream().filter(m -> m.getPayment() > moneyInt).collect(Collectors.toList());
            } else {
                deletedAgents = agents.stream().filter(m -> m.getPayment() == moneyInt).collect(Collectors.toList());
            }

            if (!deletedAgents.isEmpty()) {
                agentService.delete(deletedAgents, callbackDelete(moneyInt, spinnerValue));
            }
        });
    }



    private boolean isValid() {
        if (et.getText() == null || et.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private Callback<String> onMainThreadOperation() {
        return result -> {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            List<Agent> parsedAgents = AgentParser.getFromJSON(result);
            List<Agent> listToInsert = parsedAgents.stream().filter(m -> !agents.contains(m)).collect(Collectors.toList());

            if (!listToInsert.isEmpty()) {
                agentService.insertAll(listToInsert, callbackInsertAll());
            }
        };
    }

    private void initComponents() {
        fab = findViewById(R.id.surugiu_george_alexandru_fab);
        spinner = findViewById(R.id.surugiu_george_alexandru_spinner);
        tv = findViewById(R.id.surugiu_george_alexandru_tv);
        et = findViewById(R.id.surugiu_george_alexandru_et);
        bttn = findViewById(R.id.surugiu_george_alexandru_bttn);

        sharedPreferences = getApplicationContext().getSharedPreferences(APP_SHARED_PREFERENCES, MODE_PRIVATE);

        String comparison = sharedPreferences.getString(SPINNER_KEY, "");
        int money = sharedPreferences.getInt(EDIT_TEXT_KEY, 0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (!comparison.isEmpty()) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(comparison)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }

        if (money != 0) {
            et.setText(String.valueOf(money));
        }

        agentService = new AgentService(getApplicationContext());
        agentService.getAll(callbackGetAll());
    }

    private Callback<List<Agent>> callbackInsertAll() {
        return result -> {
            if (result != null) {
                agents.addAll(result);
                Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
            }
        };
    }


    private Callback<List<Agent>> callbackGetAll() {
        return result -> {
            agents.clear();
            agents.addAll(result);
        };
    }

    private Callback<List<Agent>> callbackDelete(int moneyInt, String spinnerValue) {
        return result -> {
            if (result != null) {
                agents.removeAll(result);
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(EDIT_TEXT_KEY, moneyInt);
                editor.putString(SPINNER_KEY, spinnerValue);
                editor.apply();
            }
        };
    }
}