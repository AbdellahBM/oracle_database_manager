package com.ma.oracle.Controller;

import com.ma.oracle.DTO.AuditPrivilegeDto;
import com.ma.oracle.DTO.SecurityRequest;
import com.ma.oracle.Entite.Employee;
import com.ma.oracle.Service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/configure-tde")
    public ResponseEntity<String> configureTDE(@RequestBody SecurityRequest security) {
        securityService.configureTDE(security.getTablespace(), security.getKeyAlias());
        return ResponseEntity.ok("TDE configuration applied successfully.");
    }




    private boolean isValidAuditAction(String auditAction) {
        return auditAction.matches("^(SELECT|INSERT|UPDATE|DELETE)( ON [A-Za-z_\\.]+)?$");
    }


    @PostMapping("/enable-audit")
    public ResponseEntity<String> enableAudit(@RequestParam String auditAction) {
        System.out.println("Received auditAction: " + auditAction); // Log input
        if (!isValidAuditAction(auditAction)) {
            return ResponseEntity.badRequest().body("Invalid audit action: " + auditAction);
        }
        System.out.println("test");
        securityService.enableAudit(auditAction);
        return ResponseEntity.ok("Audit enabled successfully.");
    }


    @GetMapping("/privileges")
    public List<AuditPrivilegeDto> getPrivilegeAuditOptions() {
        return securityService.getPrivilegeAuditOptions();
    }


    @PostMapping("/configure-vpd")
    public ResponseEntity<String> configureVPD(@RequestParam String tableName, @RequestParam String policyName, @RequestParam String predicate) {
        securityService.configureVPD(tableName, policyName, predicate);
        return ResponseEntity.ok("VPD policy applied successfully.");
    }


    @GetMapping("/GetEmp")
    public ResponseEntity<List<Employee>> getEmp() {
        List<Employee> data = securityService.getAllEmployees();
        return ResponseEntity.ok(data);
    }
    

}