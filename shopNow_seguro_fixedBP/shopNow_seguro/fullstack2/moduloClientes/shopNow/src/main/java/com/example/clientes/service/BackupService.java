package com.example.clientes.service;

import org.springframework.stereotype.Service;

@Service
public class BackupService {

    public void createBackup1() {
        String dumpPath = "C:/laragon/bin/mysql/mysql-8.0.30-winx64/bin/mysqldump";
        String dbName = "shopnow";
        String dbUser = "root";
        String dbPass = "1251";
        String savePath = "C:/backups/backup_shopnow.sql";

        String command = String.format("%s -u %s -p%s --databases %s -r %s",
                dumpPath, dbUser, dbPass, dbName, savePath);

        try {
            Process process = Runtime.getRuntime().exec(command);
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup creado con éxito en: " + savePath);
            } else {
                System.err.println("Fallo al crear el backup");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBackup() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBackup'");
    }
}