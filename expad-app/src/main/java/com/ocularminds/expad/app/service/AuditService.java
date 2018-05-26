package com.ocularminds.expad.app.service;

import com.ocularminds.expad.app.repository.Audits;
import com.ocularminds.expad.model.AuditElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    final Audits audits;

    @Autowired
    public AuditService(final Audits audits) {
        this.audits = audits;
    }

    public void log(AuditElement data) {
        audits.add(data);
    }

    public String login(String userid, String actionType, String loginid, String branchCode, int attemptCount, String status, String workstation) {
        return audits.audit(userid, userid, loginid, branchCode, attemptCount, status, userid);
    }

    public boolean isModuleVisited(String module, String userid) {
        try {
            return audits.isVisited(module, userid);
        } catch (Exception er) {
            return false;
        }
    }

    public void module(String module, String userid, String branchCode) {
        audits.module(module, userid, branchCode);
    }
}
