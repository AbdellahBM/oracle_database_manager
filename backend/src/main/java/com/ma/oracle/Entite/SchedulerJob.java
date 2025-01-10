package com.ma.oracle.Entite;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SchedulerJob {
    private String jobName;
    private String scheduleType;
    private LocalDateTime startDate;
    private LocalDateTime nextRunDate;

    public SchedulerJob(String jobName, String scheduleType, LocalDateTime startDate, LocalDateTime nextRunDate) {
        this.jobName = jobName;
        this.scheduleType = scheduleType;
        this.startDate = startDate;
        this.nextRunDate = nextRunDate;
    }
}
