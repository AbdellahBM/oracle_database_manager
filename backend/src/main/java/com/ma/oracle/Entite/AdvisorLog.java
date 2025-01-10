package com.ma.oracle.Entite;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AdvisorLog {
    private String taskName;
    private LocalDateTime createdDate;
    private String status;

    public AdvisorLog(String taskName, LocalDateTime createdDate, String status) {
        this.taskName = taskName;
        this.createdDate = createdDate;
        this.status = status;
    }

}
