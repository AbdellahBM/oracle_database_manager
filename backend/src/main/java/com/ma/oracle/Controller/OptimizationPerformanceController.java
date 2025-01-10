package com.ma.oracle.Controller;

import com.ma.oracle.DTO.OPRequest;
import com.ma.oracle.Entite.AdvisorLog;
import com.ma.oracle.Entite.SchedulerJob;
import com.ma.oracle.Entite.SlowQuery;
import com.ma.oracle.Service.PerformanceOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/performance")
public class OptimizationPerformanceController {

    @Autowired
    private  PerformanceOptimizationService optimizationService;


    @GetMapping("/slow-queries")
    public ResponseEntity<List<SlowQuery>> getSlowQueries() {
        return ResponseEntity.ok(optimizationService.getSlowQueries());
    }

    @PostMapping("/optimize-query")
    public ResponseEntity<String> optimizeQuery(@RequestBody OPRequest opRequest) {
        boolean result = optimizationService.optimizeQuery(opRequest.getQueryId());
        return ResponseEntity.ok(result ? "Query optimized successfully" : "Query optimization failed");
    }



    // Endpoint to get SQL details
    @GetMapping("/details/{sqlId}")
    public List<Map<String, Object>> getSqlDetails(@PathVariable String sqlId) {
        return optimizationService.getSqlDetails(sqlId);
    }

    // Endpoint to create and execute tuning task
    @PostMapping("/tune/")
    public ResponseEntity<String> createTuningTask(@RequestParam String sqlId) {
        optimizationService.createAndExecuteTuningTask(sqlId);
        return  ResponseEntity.ok("Tuning task created for SQL ID: " + sqlId);
    }

    // Endpoint to get tuning task report
    @GetMapping("/report/{sqlId}")
    public String getTuningTaskReport(@PathVariable String sqlId) {
        return optimizationService.getTuningTaskReport(sqlId);
    }


















    @PostMapping("/schedule-statistics")
    public ResponseEntity<String> scheduleStatisticsRecalculation(@RequestBody OPRequest OPRequest) {
        optimizationService.scheduleStatisticsRecalculation(OPRequest.getSchedule(),OPRequest.getName());
        return ResponseEntity.ok("Statistics recalculation scheduled successfully");
    }

    @GetMapping("/scheduled-jobs")
    public ResponseEntity<List<SchedulerJob>> getScheduledJobs() {
        return ResponseEntity.ok(optimizationService.getScheduledJobs());
    }

    @GetMapping("/advisor-logs")
    public ResponseEntity<List<AdvisorLog>> getAdvisorLogs() {
        return ResponseEntity.ok(optimizationService.getAdvisorLogs());
    }
}
