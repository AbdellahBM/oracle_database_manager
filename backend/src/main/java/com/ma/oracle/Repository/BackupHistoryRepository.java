package com.ma.oracle.Repository;



import com.ma.oracle.Entite.BackupHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackupHistoryRepository extends JpaRepository<BackupHistory, Long> {
    List<BackupHistory> findAllByOrderByBackupTimeDesc();
}
