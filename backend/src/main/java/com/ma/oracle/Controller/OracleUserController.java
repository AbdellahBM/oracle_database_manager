package com.ma.oracle.Controller;



import com.ma.oracle.DTO.UserRequest;
import com.ma.oracle.Entite.OracleUser;
import com.ma.oracle.Service.OracleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oracle-users")
public class OracleUserController {

    @Autowired
    private OracleUserService userService;

    @GetMapping
    public List<OracleUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        userService.createUser(userRequest.getUsername(), userRequest.getPassword());
        return "User created successfully";
    }



    @PutMapping("/update-password")
    public String updatePassword(@RequestBody UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        userService.updatePassword(userRequest.getUsername(), userRequest.getPassword());
        return "Password updated successfully";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        userService.deleteUser(userRequest.getUsername());
        return "User deleted successfully";
    }

    @PostMapping("/grant-role")
    public String grantRole(@RequestBody UserRequest userRequest) {
        userService.grantRole(userRequest.getUsername(), userRequest.getRole());
        return "Role granted successfully";
    }

    @PostMapping("/revoke-role")
    public String revokeRole(@RequestBody UserRequest userRequest) {
        userService.revokeRole(userRequest.getUsername(), userRequest.getRole());
        return "Role revoked successfully";
    }

    @PostMapping("/set-quota")
    public String setQuota(@RequestBody UserRequest userRequest) {
        userService.setQuota(userRequest.getUsername(),userRequest.getTablespace(),userRequest.getQuotaMb());
        return "Quota set successfully";
    }
}


