
package com.ocularminds.expad.app.service;

import com.ocularminds.expad.app.aop.ShouldBeAudited;
import com.ocularminds.expad.app.repository.Audits;
import com.ocularminds.expad.model.Script;
import com.ocularminds.expad.app.repository.Configurator;
import com.ocularminds.expad.model.Branch;
import com.ocularminds.expad.app.repository.Branches;
import com.ocularminds.expad.vao.Company;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    Configurator configurator;
    Branches branches;
    Audits audits;
    static final Logger LOG = LoggerFactory.getLogger(AdminService.class.getName());
    
    @Autowired
    public AdminService(Configurator configurator, Branches branches, Audits audits) {
        this.configurator = configurator;
        this.branches = branches;
        this.audits = audits;
    }

    public Map<String, String> mappedBranches() {
        try {
            return branches.mapped();
        } catch (Exception ex) {
            return new HashMap<>();
        }
    }

    public Branch branch(String code) {
        try {
            return branches.find(code);
        } catch (Exception ex) {
            LOG.error("branch not found ", ex);
            return null;
        }
    }

    public List<Branch> branches() {
        try {
            return branches.findAll();
        } catch (Exception ex) {
            LOG.error("branch not found ", ex);
            return null;
        }
    }

    public Company config() {
        try {
            return configurator.get();
        } catch (Exception ex) {
            LOG.error(null, ex);
            return null;
        }
    }

    @ShouldBeAudited
    public void update(Company company) {
        LOG.info("update(company) - this should fire audit...");
        configurator.update(company);
    }

    @ShouldBeAudited
    public void update(Script script) {
        configurator.save(script);
    }

    public Company getCompany() throws Exception {
        return configurator.get();     
    }

    public Script getScript() throws Exception {
        return configurator.script();     
    }

    public boolean isProfileSetup() throws Exception {
        return configurator.exists();
    }

    public boolean createBranch(String code, String name) throws Exception {
        return branches.add(code, name);
    }

    public void updateBranch(String code, String desc) throws Exception {
        branches.update(code, desc);
    }

    public Branch findBranchByCode(String code) throws Exception {
        return branches.find(code);
    }

    public List<Branch> findFinacleBranchBySearchName(String name) throws Exception {
        return branches.findBySearchName(name);
    }

    public List<Branch> findFinacleBranches() throws Exception {
        return branches.findAll();
    }

    public Map getMappedBranch() throws Exception {
        return branches.mapped();
    }

    public boolean isBranchExists(String branchCode) throws Exception {
        return branches.exists(branchCode);
    }

    public String getWorkingBranch(String branchCode) throws Exception {
        return branches.describe(branchCode);
    }
}
