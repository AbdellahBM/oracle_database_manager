package com.ma.oracle.Repository;

import com.ma.oracle.Entite.OracleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OracleUserRepository extends JpaRepository<OracleUser, String> {

    @Query("SELECT u FROM OracleUser u WHERE u.accountStatus = 'OPEN'")
    List<OracleUser> findAllActiveUsers();
}