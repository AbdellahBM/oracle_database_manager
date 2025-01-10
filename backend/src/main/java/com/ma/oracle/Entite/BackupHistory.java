package com.ma.oracle.Entite;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BackupHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String backupType; // FULL ou INCREMENTAL
    private LocalDateTime backupTime;
    private String status; // SUCCESS ou FAILURE
    private String details; // Informations supplémentaires


}
