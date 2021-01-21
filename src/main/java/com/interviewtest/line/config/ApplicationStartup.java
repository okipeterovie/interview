package com.interviewtest.line.config;

import com.interviewtest.line.entity.Privilege;
import com.interviewtest.line.entity.Role;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.RolePrivilegeManager;
import com.interviewtest.line.managers.UserManagement;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oki-Peter
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = Logger.getLogger(ApplicationStartup.class.getName());

    @Autowired
    private UserManagement userManagement;
    
    @Autowired
    private DataSource dataSource;

    boolean alreadySetup = false;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {

        if (alreadySetup) {
            return;
        }

//        RolePrivilegeManager rolePrivilegeManager = userManagement.getRolePrivilegeManager();
//
//        Privilege adminPrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("ADMIN_PRIVILEGE");	//Page level - Settings Page ONLY
//        Privilege accessPrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("ACCESS_PRIVILEGE");	//User login
//        Privilege writePrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//        Privilege readPrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege subAdminPrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("SUB_ADMIN_PRIVILEGE");
//        Privilege outletsUserPrivilege = rolePrivilegeManager.createPrivilegeIfNotFound("OUTLET_USER_PRIVILEGE");




//        List<Privilege> adminPrivileges = Arrays.asList(subAdminPrivilege, adminPrivilege, accessPrivilege, writePrivilege, readPrivilege);
//
//
//        Role ROLE_ADMIN = rolePrivilegeManager.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        Role ROLE_SUB_ADMIN = rolePrivilegeManager.createRoleIfNotFound("ROLE_SUB_ADMIN", Arrays.asList(accessPrivilege, writePrivilege, readPrivilege, subAdminPrivilege));
//        Role ROLE_OUTLETS_USER = rolePrivilegeManager.createRoleIfNotFound("OUTLET_USER", Arrays.asList(accessPrivilege, writePrivilege, readPrivilege, outletsUserPrivilege));
//
//
//        UsersAccount user = new UsersAccount();
//        user.setEmail("admin@3line.interview");
//        user.setPassword(userManagement.getBCryptPasswordEncoder().encode("password"));
//        user.setRoles(Arrays.asList(ROLE_ADMIN));
//        user = userManagement.persistUser(user);


//        scriptRunner("sql/state.sql");
//        scriptRunner("sql/lga.sql");
//        scriptRunner("sql/banks.sql");
//        scriptRunner("sql/sectors_data.sql");

        alreadySetup = true;
    }

     /**
     * @param sqlFile
     */
    public void scriptRunner(String sqlFile) {
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            // Give the input file to Reader
            Reader reader = new BufferedReader(new FileReader(sqlFile));
            // Exctute script
            sr.runScript(reader);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Failed to Execute{0} The error is {1}", new Object[]{sqlFile, e.getMessage()});
        }
    }

}
