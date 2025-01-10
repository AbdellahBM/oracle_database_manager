package com.ma.oracle.Service;

import com.ma.oracle.Entite.BackupHistory;
import com.ma.oracle.Repository.BackupHistoryRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Service
public class OracleServiceBackup {

    private final BackupHistoryRepository backupHistoryRepository;

    // Replace this with the name or ID of your Oracle container
    private static final String ORACLE_CONTAINER_NAME = "oracle-db";

    public OracleServiceBackup(BackupHistoryRepository backupHistoryRepository) {
        this.backupHistoryRepository = backupHistoryRepository;
    }

    // Method to execute commands inside the Docker container
    public String executeCommandInContainer(String command) {
        String dockerCommand = "docker exec -it " + ORACLE_CONTAINER_NAME + " bash -c \"" + command + "\"";
        return executeShellCommand(dockerCommand);
    }

    // Generic method to execute shell commands
    public String executeShellCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing command: " + e.getMessage();
        }
        return output.toString();
    }

    // Enable ARCHIVELOG mode
    public String enableArchiveLogMode() {
        String command = "sqlplus / as sysdba <<EOF\n" +
                "SHUTDOWN IMMEDIATE;\n" +
                "STARTUP MOUNT;\n" +
                "ALTER DATABASE ARCHIVELOG;\n" +
                "ALTER DATABASE OPEN;\n" +
                "EXIT;\nEOF";
        return executeCommandInContainer(command);
    }

    // Perform a full database backup
    public String performFullBackup() {
        String command = "rman target / <<EOF\n" +
                "BACKUP DATABASE PLUS ARCHIVELOG;\n" +
                "EXIT;\nEOF";
        String result = executeCommandInContainer(command);

        saveBackupHistory("FULL", result.contains("RMAN-") ? "FAILURE" : "SUCCESS", result);
        return result;
    }

    // Perform a backup of the USERS tablespace
    public String performUsersTablespaceBackup() {
        String command = "rman target / <<EOF\n" +
                "BACKUP TABLESPACE USERS;\n" +
                "EXIT;\nEOF";
        String result = executeCommandInContainer(command);

        saveBackupHistory("TABLESPACE_USERS", result.contains("RMAN-") ? "FAILURE" : "SUCCESS", result);
        return result;
    }

    // Perform an incremental backup
    public String performIncrementalBackup() {
        String command = "rman target / <<EOF\n" +
                "BACKUP INCREMENTAL LEVEL 1 DATABASE;\n" +
                "EXIT;\nEOF";
        String result = executeCommandInContainer(command);

        saveBackupHistory("INCREMENTAL", result.contains("RMAN-") ? "FAILURE" : "SUCCESS", result);
        return result;
    }

    // Restore the database to a specific point in time
    public String restoreBackup(String restoreDate) {
        String command = "rman target / <<EOF\n" +
                "RUN {\n" +
                "  SET UNTIL TIME \"TO_DATE('" + restoreDate + "', 'YYYY-MM-DD HH24:MI:SS')\";\n" +
                "  RESTORE DATABASE;\n" +
                "  RECOVER DATABASE;\n" +
                "}\n" +
                "EXIT;\nEOF";
        return executeCommandInContainer(command);
    }

    // Open the database
    public String openDatabase() {
        String command = "sqlplus / as sysdba <<EOF\n" +
                "ALTER DATABASE OPEN;\n" +
                "EXIT;\nEOF";
        return executeCommandInContainer(command);
    }

    // Save backup history in the database
    private void saveBackupHistory(String backupType, String status, String details) {
        BackupHistory history = new BackupHistory();
        history.setBackupType(backupType);
        history.setBackupTime(LocalDateTime.now());
        history.setStatus(status);
        history.setDetails(details);
        backupHistoryRepository.save(history);
    }
}
