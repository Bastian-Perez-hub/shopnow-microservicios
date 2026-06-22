package com.example.clientes.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BackupScheduler {

    private final BackupService backupService1;

    public BackupScheduler(BackupService backupService) {
        this.backupService1 = backupService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        backupService1.createBackup();
    }
}