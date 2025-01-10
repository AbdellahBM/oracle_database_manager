package com.ma.oracle.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PerformanceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Top SQL Queries by Elapsed Time (AWR)
    public List<Map<String, Object>> fetchTopSqlByElapsedTime() {
        String sql = """
                SELECT SQL_ID, SUM(ELAPSED_TIME_DELTA) AS ELAPSED_TIME_TOTAL
                FROM DBA_HIST_SQLSTAT
                GROUP BY SQL_ID
                ORDER BY ELAPSED_TIME_TOTAL DESC
                FETCH FIRST 10 ROWS ONLY
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // Top Wait Events (AWR)
    public List<Map<String, Object>> fetchTopWaitEvents() {
        String sql = """
                SELECT WAIT_CLASS, SUM(TIME_WAITED_MICRO) / 1000000 AS TIME_WAITED
                FROM DBA_HIST_SYSTEM_EVENT
                GROUP BY WAIT_CLASS
                ORDER BY TIME_WAITED DESC
                FETCH FIRST 10 ROWS ONLY
                """;
        return jdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> fetchCpuUsage() {
        String sql = """
        SELECT s.SNAP_ID, sn.BEGIN_INTERVAL_TIME, AVG(s.VALUE) AS CPU_USAGE
        FROM SYS.DBA_HIST_SYS_TIME_MODEL s
        JOIN SYS.DBA_HIST_SNAPSHOT sn
          ON s.SNAP_ID = sn.SNAP_ID
         AND s.DBID = sn.DBID
         AND s.INSTANCE_NUMBER = sn.INSTANCE_NUMBER
        WHERE s.STAT_NAME = 'DB CPU'
        GROUP BY s.SNAP_ID, sn.BEGIN_INTERVAL_TIME
        ORDER BY sn.BEGIN_INTERVAL_TIME
    """;

        try {
            // Set session-specific parameters
            jdbcTemplate.execute("ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS'");

            // Execute query
            System.out.println("Executing query: " + sql);
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            System.out.println("Query results: " + results);

            if (results.isEmpty()) {
                System.out.println("No data found for CPU usage.");
            }
            return results;
        } catch (Exception e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw new RuntimeException("Failed to fetch CPU usage data", e);
        }
    }


    // Disk I/O Activity (AWR)
    public List<Map<String, Object>> fetchDiskIoActivity() {
        String sql = """
            SELECT SNAP_ID, BEGIN_TIME, MAX(VALUE) AS PHYSICAL_IO
            FROM DBA_HIST_SYSMETRIC_HISTORY
            WHERE METRIC_NAME IN ('Physical Reads Per Sec', 'Physical Writes Per Sec')
            GROUP BY SNAP_ID, BEGIN_TIME
            ORDER BY BEGIN_TIME
            """;
        return jdbcTemplate.queryForList(sql);
    }


    // Active Sessions by Wait Class (ASH)
    public List<Map<String, Object>> fetchActiveSessionsByWaitClass() {
        String sql = """
                SELECT WAIT_CLASS, COUNT(*) AS ACTIVE_SESSIONS
                FROM V$ACTIVE_SESSION_HISTORY
                WHERE SAMPLE_TIME >= SYSDATE - INTERVAL '1' HOUR
                GROUP BY WAIT_CLASS
                ORDER BY ACTIVE_SESSIONS DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }
}
