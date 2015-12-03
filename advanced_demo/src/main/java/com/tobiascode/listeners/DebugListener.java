package com.tobiascode.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class DebugListener {
    @PrePersist
    private void preChecks(Object entity){
        System.out.println("Doing debug prechecks before persisting employee");
    }

    @PostPersist
    private void finalLogs(Object entity){
        System.out.println("Doing debug final logs" + entity.toString());
    }
}
