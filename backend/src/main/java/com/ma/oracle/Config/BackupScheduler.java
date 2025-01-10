package com.ma.oracle.Config;

import com.ma.oracle.Service.OracleServiceBackup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackupScheduler {

    private final OracleServiceBackup oracleService;

    public BackupScheduler(OracleServiceBackup oracleService) {
        this.oracleService = oracleService;
    }

    // Sauvegarde complète tous les jours à 2h
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduleFullBackup() {
        oracleService.performFullBackup();
    }

}
