package com.ma.oracle.Service;

import com.ma.oracle.DTO.AuditPrivilegeDto;
import com.ma.oracle.Entite.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {

    private final JdbcTemplate jdbcTemplate;

    public SecurityService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public void configureTDE(String tablespace, String keyAlias) {
// "Wallet1234"
        // Open the wallet (ensure the password is valid)
        String openWalletSql = "ADMINISTER KEY MANAGEMENT SET KEYSTORE OPEN IDENTIFIED BY \"Wallet1234\" ";
        jdbcTemplate.execute(openWalletSql);
        System.out.println("1");
        String sql = "ALTER TABLESPACE " + tablespace + " ENCRYPTION USING 'AES256' ENCRYPT";
        jdbcTemplate.execute(sql);
        System.out.println("2");

        String keySql = "ADMINISTER KEY MANAGEMENT SET KEY IDENTIFIED BY 'Wallet1234' ";
        jdbcTemplate.execute(keySql);
        System.out.println("3");
    }




    public void enableAudit(String auditAction) {

//        if (!isValidAuditAction(auditAction)) {
//            throw new IllegalArgumentException("Invalid audit action: " + auditAction);
//        }

            String sql = "AUDIT " + auditAction;
            System.out.println("Executing SQL: " + sql); // Log the SQL
            jdbcTemplate.execute(sql);

    }

//    // Example validation method
//    private boolean isValidAuditAction(String auditAction) {
//        // Validates format like "SELECT ON EMPLOYEES" or "INSERT ON ORDERS"
//        return auditAction.matches("^(SELECT|INSERT|UPDATE|DELETE) ON [A-Za-z_]+$");
//    }

    private boolean isValidAuditAction(String auditAction) {
        // Allow schema-qualified table names
        return auditAction.matches("^(SELECT|INSERT|UPDATE|DELETE)( ON [A-Za-z_\\.]+)?$");
    }





    public void configureVPD(String tableName, String policyName, String predicate) {
        String sql =
                "BEGIN DBMS_RLS.ADD_POLICY(" +
                "OBJECT_SCHEMA => USER, " +
                "OBJECT_NAME => '" + tableName + "', " +
                "POLICY_NAME => '" + policyName + "'," +
                " FUNCTION_SCHEMA => USER," +
                " POLICY_FUNCTION => '" + predicate + "'); " +
                "END;";

        jdbcTemplate.execute(sql);
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT employee_id, first_name, last_name FROM EMPLOYEES";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmployeeId(rs.getInt("employee_id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            return employee;
        });
    }





    public List<AuditPrivilegeDto> getPrivilegeAuditOptions() {
        String sql = """
            SELECT
                privilege,
                success,
                failure,
                user_name,
                proxy_name
            FROM
                dba_priv_audit_opts
            WHERE
                privilege IN ('CREATE ANY TABLE', 'ALTER ANY TABLE', 'DROP ANY TABLE')
            ORDER BY
                privilege, user_name
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AuditPrivilegeDto dto = new AuditPrivilegeDto();
            dto.setPrivilege(rs.getString("privilege"));
            dto.setSuccess(rs.getString("success"));
            dto.setFailure(rs.getString("failure"));
            dto.setUserName(rs.getString("user_name"));
            dto.setProxyName(rs.getString("proxy_name"));
            return dto;
        });
    }

    }
