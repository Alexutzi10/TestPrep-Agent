package com.example.testprep_agent.network;

import android.util.Log;

import com.example.testprep_agent.data.Agent;
import com.example.testprep_agent.data.DateConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentParser {
    public static List<Agent> getFromJSON(String json) {
        List<Agent> agents = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject details = root.getJSONObject("details");
            JSONArray datasets = details.getJSONArray("datasets");

            for (int i = 0; i < datasets.length(); i++) {
                JSONObject object = datasets.getJSONObject(i);
                JSONObject agentData = object.getJSONObject("agent");

                String name = agentData.getString("name");
                int payment = agentData.getInt("payment");
                String employmentDateString = agentData.getString("employementDate");

                Date employmentDate;
                try {
                    employmentDate = DateConverter.toDate(employmentDateString);
                } catch (Exception ex) {
                    Log.e("DateConversion", "Error converting date: " + employmentDateString, ex);
                    continue;
                }

                Agent agent = new Agent(name, payment, employmentDate);
                agents.add(agent);
            }
            return agents;
        } catch (JSONException ex) {
            Log.e("JSONParse", "Error when parsing the JSON", ex);
        }
        return new ArrayList<>();
    }
}