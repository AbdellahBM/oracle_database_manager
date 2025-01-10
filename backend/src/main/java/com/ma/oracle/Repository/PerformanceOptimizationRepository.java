package com.ma.oracle.Repository;

import com.ma.oracle.Entite.AdvisorLog;
import com.ma.oracle.Entite.SchedulerJob;
import com.ma.oracle.Entite.SlowQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PerformanceOptimizationRepository {

    private final JdbcTemplate jdbcTemplate;

    public PerformanceOptimizationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SlowQuery> fetchSlowQueries() {
        String query = "SELECT SQL_ID, SQL_TEXT, ELAPSED_TIME " +
                "FROM v$sql WHERE ELAPSED_TIME > 1000000 ORDER BY ELAPSED_TIME DESC"; // Adjust threshold if needed
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new SlowQuery(rs.getString("SQL_ID"), rs.getString("SQL_TEXT"), rs.getLong("ELAPSED_TIME")));
    }


//
    public boolean executeSQLTuningAdvisor(String queryId) {
        String tuningCommand = "BEGIN " +
                "DBMS_SQLTUNE.TUNE_SQL(SQL_ID => '" + queryId + "'); " +
                "END;";
        try {
            jdbcTemplate.execute(tuningCommand);
            return true;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public void scheduleStatisticsRecalculation(String schedule,String name) {
        String jobCommand = "BEGIN DBMS_SCHEDULER.CREATE_JOB (" +
                "job_name => '"+name+"'," +
                "job_type => 'PLSQL_BLOCK'," +
                "job_action => 'BEGIN DBMS_STATS.GATHER_DATABASE_STATS; END;', " +
                "start_date => SYSDATE, " +
                "repeat_interval => '" + schedule + "', " +
                "enabled => TRUE); END;";
        jdbcTemplate.execute(jobCommand);
    }

    public List<SchedulerJob> fetchScheduledJobs() {
        String query = """
        SELECT JOB_NAME, SCHEDULE_TYPE, START_DATE, NEXT_RUN_DATE
        FROM DBA_SCHEDULER_JOBS
    """;
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new SchedulerJob(
                        rs.getString("JOB_NAME"),
                        rs.getString("SCHEDULE_TYPE"),
                        rs.getTimestamp("START_DATE") != null ? rs.getTimestamp("START_DATE").toLocalDateTime() : null,
                        rs.getTimestamp("NEXT_RUN_DATE") != null ? rs.getTimestamp("NEXT_RUN_DATE").toLocalDateTime() : null
                )
        );
    }


    public List<AdvisorLog> fetchAdvisorLogs() {
        String query = """
            SELECT TASK_NAME, CREATED, STATUS
            FROM DBA_ADVISOR_LOG
            WHERE TASK_NAME LIKE '%TUNE_SQL%'
        """;
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new AdvisorLog(
                        rs.getString("TASK_NAME"),
                        rs.getTimestamp("CREATED").toLocalDateTime(),
                        rs.getString("STATUS")
                )
        );
    }
}

