/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class AccountBalanceRecordInfo
extends RecordInfo {
    public String firstfield = "";
    public String account_id = "";
    public String ledger_balance = "";
    public String available_balance = "";
    public String account_type = "";

    public boolean validateForFullLoad() {
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (this.ledger_balance == null || !RecordInfo.isValidNUMERIC(12, this.ledger_balance)) {
            this.failure_reason_description = "ledger_balance";
            return false;
        }
        if (this.available_balance == null || !RecordInfo.isValidNUMERIC(12, this.available_balance)) {
            this.failure_reason_description = "available_balance";
            return false;
        }
        if (!(this.account_type == null || RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
            this.failure_reason_description = "account_type";
            return false;
        }
        this.failure_reason_description = null;
        return true;
    }

    public boolean validateForPartialLoad() {
        if (!RecordInfo.isValidOperationIndicator(this.firstfield)) {
            this.failure_reason_description = "operation indicator";
            return false;
        }
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.ledger_balance) || this.ledger_balance != null && RecordInfo.isValidNUMERIC(12, this.ledger_balance))) {
            this.failure_reason_description = "ledger_balance";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.available_balance) || this.available_balance != null && RecordInfo.isValidNUMERIC(12, this.available_balance))) {
            this.failure_reason_description = "available_balance";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.account_type) || RecordInfo.isNull(this.account_type) || RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
            this.failure_reason_description = "account_type";
            return false;
        }
        this.failure_reason_description = null;
        return true;
    }
}

