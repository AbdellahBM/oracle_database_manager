package com.ma.oracle.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class DataGuardRepository {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getDataGuardStatus() {
        String query = "SELECT DATABASE_ROLE, OPEN_MODE FROM V$DATABASE";
        return jdbcTemplate.queryForList(query);
    }



    public String switchOver() {
        try {
            jdbcTemplate.execute("ALTER DATABASE SWITCHOVER TO standby_name");
            return "Switch-over completed successfully.";
        } catch (Exception e) {
            return "Switch-over failed: " + e.getMessage();
        }
    }

    public String failOver() {
        try {
            jdbcTemplate.execute("ALTER DATABASE FAILOVER TO standby_name");
            return "Failover completed successfully.";
        } catch (Exception e) {
            return "Failover failed: " + e.getMessage();
        }
    }

    public String simulate() {
        try {
            jdbcTemplate.execute(String.valueOf(new CallableStatementCallback<Void>() {
                @Override
                public Void doInCallableStatement(CallableStatement cs) throws SQLException {
                    cs.execute("BEGIN DBMS_DG.INITIATE_REDO_FAILOVER(); END;");
                    return null;
                }
            }));
            return "Simulation triggered successfully.";
        } catch (Exception e) {
            return "Simulation failed: " + e.getMessage();
        }
    }
}