package com.example.testprep_agent.database;

import android.content.Context;

import com.example.testprep_agent.data.Agent;
import com.example.testprep_agent.network.AsyncTaskRunner;
import com.example.testprep_agent.network.Callback;

import java.util.List;
import java.util.concurrent.Callable;

public class AgentService {
    private final AgentDao agentDao;

    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public AgentService(Context context) {
        agentDao = DatabaseManager.getInstance(context).getAgentDao();
    }

    public void getAll(Callback<List<Agent>> callback) {
        Callable<List<Agent>> callable = () -> {
          List<Agent> agents = agentDao.getAll();
          return agents;
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insertAll(List<Agent> agents, Callback<List<Agent>> callback) {
        Callable<List<Agent>> callable = () -> {
            List<Long> ids = agentDao.insertAll(agents);
            for (int i = 0; i < agents.size(); i++) {
                agents.get(i).setId(ids.get(i));
            }
            return agents;
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(List<Agent> agents, Callback<List<Agent>> callback) {
        Callable<List<Agent>> callable = () -> {
          int count = agentDao.delete(agents);
          if (count <= 0) {
              return null;
          } else {
              return agents;
          }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
