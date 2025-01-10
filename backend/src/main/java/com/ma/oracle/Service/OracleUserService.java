package com.ma.oracle.Service;


import com.ma.oracle.Entite.OracleUser;
import com.ma.oracle.Repository.OracleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OracleUserService {

    @Autowired
    private OracleUserRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<OracleUser> getAllUsers() {
        return repository.findAll();
    }

    public void createUser(String username, String password) {
        String sql = String.format("CREATE USER %s IDENTIFIED BY %s", username, password);
        jdbcTemplate.execute(sql);
    }

    public void updatePassword(String username, String password) {
        String sql = String.format("ALTER USER %s IDENTIFIED BY %s", username, password);
        jdbcTemplate.execute(sql);
    }

    public void deleteUser(String username) {
        String sql = String.format("DROP USER %s CASCADE", username);
        jdbcTemplate.execute(sql);
    }

    public void grantRole(String username, String role) {
        String sql = String.format("GRANT %s TO %s", role, username);
        jdbcTemplate.execute(sql);
    }

    public void revokeRole(String username, String role) {
        String sql = String.format("REVOKE %s FROM %s", role, username);
        jdbcTemplate.execute(sql);
    }

    public void setQuota(String username, String tablespace, int quotaMb) {
        String sql = String.format("ALTER USER %s QUOTA %dM ON %s", username, quotaMb, tablespace);
        jdbcTemplate.execute(sql);
    }
}
