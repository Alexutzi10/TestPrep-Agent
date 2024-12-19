package com.example.testprep_agent.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.testprep_agent.data.Agent;
import com.example.testprep_agent.data.DateConverter;

@Database(entities = {Agent.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager connection;

    public static DatabaseManager getInstance(Context context) {
        if (connection != null) {
            return connection;
        }

        connection = Room.databaseBuilder(context, DatabaseManager.class, "dam_db").fallbackToDestructiveMigration().build();

        return connection;
    }

    public abstract AgentDao getAgentDao();
}
