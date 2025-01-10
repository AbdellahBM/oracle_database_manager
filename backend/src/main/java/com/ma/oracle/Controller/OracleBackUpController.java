package com.ma.oracle.Controller;




import com.ma.oracle.Entite.BackupHistory;
import com.ma.oracle.Repository.BackupHistoryRepository;
import com.ma.oracle.Service.OracleServiceBackup;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oracle")
public class OracleBackUpController {

    private final OracleServiceBackup oracleService;
    private final BackupHistoryRepository backupHistoryRepository;



    public OracleBackUpController(OracleServiceBackup oracleService, BackupHistoryRepository backupHistoryRepository) {
        this.oracleService = oracleService;
        this.backupHistoryRepository = backupHistoryRepository;
    }


    // Activer le mode ARCHIVELOG
    @PostMapping("/enable-archivelog")
    public String enableArchiveLogMode() {
        return oracleService.enableArchiveLogMode();
    }

    // Sauvegarde complète
    @PostMapping("/backup/full")
    public String fullBackup() {
        return oracleService.performFullBackup();
    }

    // Sauvegarde spécifique du tablespace USERS
    @PostMapping("/backup/tablespace/users")
    public String usersTablespaceBackup() {
        return oracleService.performUsersTablespaceBackup();
    }


    // Sauvegarde incrémentielle
    @PostMapping("/backup/incremental")
    public String incrementalBackup() {
        return oracleService.performIncrementalBackup();
    }

    // Restauration
    @PostMapping("/restore")
    public String restoreBackup(@RequestParam String date) {
        return oracleService.restoreBackup(date);
    }

    // Ouvrir la base de données
    @PostMapping("/open-database")
    public String openDatabase() {
        return oracleService.openDatabase();
    }

    // Historique des sauvegardes
    @GetMapping("/history")
    public List<BackupHistory> getBackupHistory() {
        return backupHistoryRepository.findAllByOrderByBackupTimeDesc();
    }
}
