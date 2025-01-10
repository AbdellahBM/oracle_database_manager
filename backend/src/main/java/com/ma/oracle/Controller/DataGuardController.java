package com.ma.oracle.Controller;


import com.ma.oracle.Service.DataGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dataguard")
public class DataGuardController {

    @Autowired
    private DataGuardService dataGuardService;

    public DataGuardController(DataGuardService dataGuardService) {
        this.dataGuardService = dataGuardService;
    }

    @GetMapping("/status")
    public List<Map<String, Object>> getDataGuardStatus() {
        return dataGuardService.getDataGuardStatus();
    }

    @PostMapping("/switch-over")
    public String switchOver() {
        return dataGuardService.performSwitchOver();
    }

    @PostMapping("/failover")
    public String failOver() {
        return dataGuardService.performFailOver();
    }

    @GetMapping("/simulate")
    public String simulate() {
        return dataGuardService.simulateDataGuard();
    }
}
