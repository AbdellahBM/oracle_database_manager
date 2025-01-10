package com.ma.oracle.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditPrivilegeDto {
    private String privilege;
    private String success;
    private String failure;
    private String userName;
    private String proxyName;
}