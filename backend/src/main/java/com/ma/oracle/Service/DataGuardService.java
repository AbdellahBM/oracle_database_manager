package com.ma.oracle.Service;


import com.ma.oracle.Repository.DataGuardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class DataGuardService {

    @Autowired
    private DataGuardRepository dataGuardRepository;

    public DataGuardService(DataGuardRepository dataGuardRepository) {
        this.dataGuardRepository = dataGuardRepository;
    }

    public List<Map<String, Object>> getDataGuardStatus() {
        return dataGuardRepository.getDataGuardStatus();
    }

    public String performSwitchOver() {
        return dataGuardRepository.switchOver();
    }

    public String performFailOver() {
        return dataGuardRepository.failOver();
    }

    public String simulateDataGuard() {
        return dataGuardRepository.simulate();
    }
}
