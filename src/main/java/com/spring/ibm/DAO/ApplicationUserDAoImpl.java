package com.spring.ibm.DAO;

import com.spring.ibm.bean.ApplicationUserForm;
import com.spring.ibm.model.ApplicationUser;
import com.spring.ibm.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public class ApplicationUserDAoImpl {


    // Config WebSecurity Config
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Map<Long, ApplicationUser> USER_MAP = new HashMap<>();

    static {
        initDATA();
    }

    private static void initDATA() {
        String encrytedPassword = "";


        ApplicationUser amit = new ApplicationUser(1L, "amit", "AMIT", "DUBEY",
                true, Gender.MALE, "amit@gmail.com", encrytedPassword, "US");

        ApplicationUser jessy = new ApplicationUser(2L, "jessy", "jessy", "josehp",
                true, Gender.FEMALE, "jessy@gmail.com", encrytedPassword, "US");

        USER_MAP.put(amit.getUserId(), amit);
        USER_MAP.put(jessy.getUserId(), jessy);

    }


    public Long getMaxuserId() {

        long max = 0;
        for (Long id : USER_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

    public ApplicationUser findApplicationUserByUserName(String userName) {
        Collection<ApplicationUser> applicationUsers = USER_MAP.values();
        for (ApplicationUser u : applicationUsers) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }

        return null;


    }

    public ApplicationUser findApplicatonUserByEmail(String email) {
        Collection<ApplicationUser> applicationUsers = USER_MAP.values();
        for (ApplicationUser u : applicationUsers) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }


    public List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> list = new ArrayList<>();
        list.addAll(USER_MAP.values());
        return list;
    }

    public ApplicationUser createApplicationUser(ApplicationUserForm form) {
        Long userId = this.getMaxuserId() + 1;

        String encrytedPassword = this.passwordEncoder.encode(form.getConfirmPassword());

        ApplicationUser applicationUser = new ApplicationUser(userId, form.getUserName(), form.getFirstName(), form.getLastName(),
                false, form.getGender(), form.getEmail(), form.getCountryCode(), encrytedPassword);

        USER_MAP.put(userId, applicationUser);
        return applicationUser;

    }



}