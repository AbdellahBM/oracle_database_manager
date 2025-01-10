package com.ma.oracle.Entite;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dba_users") // Utilisez cette table pour lire les informations des utilisateurs
public class OracleUser {

    @Id
    private String username; // Nom d'utilisateur Oracle

    private String accountStatus; // Statut du compte
    private String profile; // Profil de l'utilisateur
    private String defaultTablespace; // Tablespace par défaut
    private String temporaryTablespace; // Tablespace temporaire

}

