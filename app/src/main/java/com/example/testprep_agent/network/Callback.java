package com.example.testprep_agent.network;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
