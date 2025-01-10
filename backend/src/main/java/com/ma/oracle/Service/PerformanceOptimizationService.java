package com.ma.oracle.Service;

import com.ma.oracle.Entite.AdvisorLog;
import com.ma.oracle.Entite.SchedulerJob;
import com.ma.oracle.Entite.SlowQuery;
import com.ma.oracle.Repository.PerformanceOptimizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PerformanceOptimizationService {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceOptimizationService.class);

    @Autowired
    private  PerformanceOptimizationRepository optimizationRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<SlowQuery> getSlowQueries() {
        return optimizationRepository.fetchSlowQueries();
    }

    public boolean optimizeQuery(String queryId) {
        return optimizationRepository.executeSQLTuningAdvisor(queryId);
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Get SQL details
    public List<Map<String, Object>> getSqlDetails(String sqlId) {
        String query = "SELECT SQL_ID, SQL_TEXT FROM V$SQL WHERE SQL_ID = ?";
        return jdbcTemplate.queryForList(query, sqlId);
    }



    // Create and execute tuning task using dynamic taskName
    public void createAndExecuteTuningTask(String sqlId) {
        sqlId = "b39m8n96gxk7c";
        String taskName = "TUNING_TASK_" + sqlId;

        String query = "BEGIN " +
                "    DBMS_SQLTUNE.CREATE_TUNING_TASK(" +
                "      task_name => '" + taskName + "', " +
                "      sql_id =>  TO_NUMBER(HEXTORAW('" + sqlId.replace("'", "''") + "'))," + // Escaped ' and used replace
                "      tuning_mode => 'COMPREHENSIVE', " +
                "      time_limit => 300);" +
                "   DBMS_SQLTUNE.EXECUTE_TUNING_TASK(task_name => '" + taskName + "');" +
                "END;";

        try {
            jdbcTemplate.update(query);
            logger.info("Tuning task created and executed for SQL ID: {}", sqlId);
        } catch (DataAccessException ex) {
            logger.error("Error creating and executing tuning task for SQL ID: {}", sqlId, ex);
            throw new RuntimeException("Error creating and executing tuning task for SQL ID: " + sqlId, ex);
        }
    }

    // Get tuning task report
    public String getTuningTaskReport(String sqlId) {
        String taskName = "TUNING_TASK_" + sqlId;
        String query = "SELECT DBMS_SQLTUNE.REPORT_TUNING_TASK(?) FROM DUAL";
        try {
            return jdbcTemplate.queryForObject(query, String.class, taskName);
        } catch (DataAccessException ex) {
            logger.error("Error retrieving tuning task report for SQL ID: {}", sqlId, ex);
            // check if the tuning task exists
            String checkTaskExistsQuery = "SELECT COUNT(*) FROM dba_advisor_tasks where task_name = ?";
            Integer taskCount = jdbcTemplate.queryForObject(checkTaskExistsQuery, Integer.class, taskName);

            if(taskCount == 0){
                return "Tuning task for SQL ID: " + sqlId + " not found. Please create a tuning task first.";

            } else{
                return "Error retrieving tuning task report for SQL ID: " + sqlId + ". An administrator has been notified. Please try again later.";
            }
        }
    }



















    public void scheduleStatisticsRecalculation(String schedule,String name) {
        optimizationRepository.scheduleStatisticsRecalculation(schedule,name);
    }

    public List<SchedulerJob> getScheduledJobs() {
        return optimizationRepository.fetchScheduledJobs();
    }

    public List<AdvisorLog> getAdvisorLogs() {
        return optimizationRepository.fetchAdvisorLogs();
    }




}
