package com.ma.oracle.DTO;

import lombok.Data;

@Data
public class SecurityRequest {
    private String tablespace;
    private String keyAlias;
    private String auditAction;
    private String tableName;
    private String policyName;
    private String predicate;
}
