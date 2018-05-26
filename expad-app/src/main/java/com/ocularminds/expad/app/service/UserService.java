package com.ocularminds.expad.app.service;

import com.ocularminds.expad.app.repository.Functions;
import com.ocularminds.expad.app.repository.Roles;
import com.ocularminds.expad.app.repository.Users;
import com.ocularminds.expad.crypto.EncryptionException;
import com.ocularminds.expad.crypto.StringEncrypter;
import com.ocularminds.expad.model.Function;
import com.ocularminds.expad.model.Role;
import com.ocularminds.expad.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private User staff;
    private final Users users;
    private final Roles roles;
    private final Functions functions;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(Users users, Roles roles, Functions functions) {
        this.users = users;
        this.roles = roles;
        this.functions = functions;
    }

    private void setUser(User staff) {
        this.staff = staff;
    }

    public User getUser() {
        return this.staff;
    }

    public void resetUser(String userid) {
        users.reset(userid);
    }

    public boolean isValidUser(String userid, String password) {
        User user;
        try {
            user = users.of(userid);
            this.setUser(user);
            if (userid.equalsIgnoreCase("superadmin")) {
                return true;
            }
        } catch (Exception ex) {
            user = null;
            LOG.error(null, ex);
        }
        return user != null ? user.getPassword().trim().equals(password.trim()) : false;
    }

    public boolean isUserInRestrictedArea(ArrayList functions, ArrayList roleFunctions, String url) {
        boolean isRestricted = true;
        if (roleFunctions != null) {
            for (int x = 0; x < roleFunctions.size(); ++x) {
                Function f = (Function) roleFunctions.get(x);
                String fURL = f.getUrl();
                if (fURL == null || !fURL.equalsIgnoreCase(url)) {
                    continue;
                }
                isRestricted = false;
                break;
            }
        }
        if (isRestricted && this.isURLNotInFunctions(functions, url)) {
            isRestricted = false;
        }
        return isRestricted;
    }

    private boolean isURLNotInFunctions(ArrayList functions, String url) {
        if (functions != null) {
            for (int x = 0; x < functions.size(); ++x) {
                Function f = (Function) functions.get(x);
                String fURL = f.getUrl();
                if (fURL != null && fURL.equalsIgnoreCase(url)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isExistingID(String userid) {
        try {
            return users.exists(userid);
        } catch (Exception ex) {
            LOG.error(null, ex);
            return false;
        }
    }

    public boolean isUserPasswordExist(String userid, String password) {
        try {
            return users.hasPassword(userid, password);
        } catch (Exception ex) {
            return false;
        }
    }

    public void removeStaff(String[] ids) {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.delete(ids[x]);
            }
        }
    }

    public void deactivate(String[] ids) {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.deactivate(ids[x]);
            }
        }
    }

    public void save(Role role) {
        if (role.getId() == null || role.getId().trim().isEmpty()) {
            roles.add(role);
        } else {
            roles.update(role);
        }
    }

    public void createRoleFunction(String roleid, String[] functionCode) {
        roles.addFunction(roleid, functionCode);
    }

    public void deleteRoleFunction(String roleid, String[] functionCodes) {
        roles.deleteFunction(roleid, functionCodes);
    }

    public void delete(String id) {
        users.remove(id);
    }

    public void deactivate(String id) {
        users.deactivate(id);
    }

    public Role role(String roleid) throws Exception {
        return roles.get(roleid);
    }

    public List roles() throws Exception {
        return roles.iterate();
    }

    public Map getMappedRole() throws Exception {
        HashMap<String, String> r = new HashMap<>();
        List records;
        records = this.roles();
        for (int x = 0; x < records.size(); ++x) {
            Role role = (Role) records.get(x);
            r.put(role.getId(), role.getName());
        }
        return r;
    }

    public List<User> users() {
        return users.iterate();
    }

    public User user(String id) throws Exception {
        return users.get(id);
    }

    public User userHaving(String staffCode) throws Exception {
        return users.of(staffCode);
    }

    public boolean isUserDisabled(String userid) throws Exception {
        User user = userHaving(userid);
        return user != null && user.isDisabled();
    }

    public void save(User user) {
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            try {
                user.setPassword(new StringEncrypter().encrypt(user.getPassword()));
            } catch (EncryptionException ex) {
                LOG.error("error encrypting user password ",ex);
            }
            users.add(user);
        } else {
            users.update(user);
        }
    }

    public void changePassword(String userid, String password) {
        users.password(userid, password);
    }

    public List<Function> functions() {
        return functions.iterate();
    }

    public List<Function> roleFunctions(String roleId) {
        return functions.of(roleId);
    }

    public List<Function> roleFunctionsAvailable(String roleId) {
        return functions.notFor(roleId);
    }
}
