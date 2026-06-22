package com.example.moduloPedidos.controller;

import com.example.moduloPedidos.service.BackupService;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.moduloPedidos.service.BackupService;

@RestController
@RequestMapping("/admin/db")
public class DataBaseAdminController {

    @Autowired
    private BackupService backupService;

    @Autowired
    private Flyway flyway;

    @PostMapping("/repair")
    public String repairDatabase() {
        flyway.repair();
        return "Historial de Flyway reparado. Ya puedes reintentar la migración.";
    }
    @PostMapping("/backup")
    public String backupDatabase() {
        backupService.createBackup1();
        return "Backup ejecutado correctamente.";
    }

}