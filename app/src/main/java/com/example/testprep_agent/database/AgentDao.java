package com.example.testprep_agent.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testprep_agent.data.Agent;

import java.util.List;

@Dao
public interface AgentDao {
    @Query("SELECT * FROM agents")
    List<Agent> getAll();

    @Insert
    List<Long> insertAll(List<Agent> agents);

    @Delete
    int delete(List<Agent> agents);
}
