package com.ma.oracle.Service;

import com.ma.oracle.Repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    public List<Map<String, Object>> getTopSqlByElapsedTime() {
        return performanceRepository.fetchTopSqlByElapsedTime();
    }

    public List<Map<String, Object>> getTopWaitEvents() {
        return performanceRepository.fetchTopWaitEvents();
    }

    public List<Map<String, Object>> getCpuUsage() {
        return performanceRepository.fetchCpuUsage();
    }

    public List<Map<String, Object>> getDiskIoActivity() {
        return performanceRepository.fetchDiskIoActivity();
    }

    public List<Map<String, Object>> getActiveSessionsByWaitClass() {
        return performanceRepository.fetchActiveSessionsByWaitClass();
    }
}
