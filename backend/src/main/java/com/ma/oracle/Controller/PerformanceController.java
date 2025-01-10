package com.ma.oracle.Controller;

import com.ma.oracle.Service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/top-sql")
    public ResponseEntity<List<Map<String, Object>>> getTopSqlByElapsedTime() {
        return ResponseEntity.ok(performanceService.getTopSqlByElapsedTime());
    }

    @GetMapping("/top-wait-events")
    public ResponseEntity<List<Map<String, Object>>> getTopWaitEvents() {
        return ResponseEntity.ok(performanceService.getTopWaitEvents());
    }

    @GetMapping("/cpu-usage")
    public ResponseEntity<List<Map<String, Object>>> getCpuUsage() {
        List<Map<String, Object>> data = performanceService.getCpuUsage();
        System.out.println("Service Output: " + data);
        return ResponseEntity.ok(data);
    }


    @GetMapping("/disk-io")
    public ResponseEntity<List<Map<String, Object>>> getDiskIoActivity() {
        return ResponseEntity.ok(performanceService.getDiskIoActivity());
    }

    @GetMapping("/active-sessions")
    public ResponseEntity<List<Map<String, Object>>> getActiveSessionsByWaitClass() {
        return ResponseEntity.ok(performanceService.getActiveSessionsByWaitClass());
    }
}
