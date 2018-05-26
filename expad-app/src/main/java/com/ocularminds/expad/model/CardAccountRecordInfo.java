/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class CardAccountRecordInfo
extends RecordInfo {
    public String firstfield = "";
    public String pan = "";
    public String seq_nr = "";
    public String account_id = "";
    public String account_type_nominated = "";
    public String account_type_qualifier = "";
    public String account_type = "";

    public boolean validateForFullLoad() {
        if (this.pan == null || !RecordInfo.isValidVARCHAR(19, this.pan) || !Validator.isValidN(this.pan)) {
            this.failure_reason_description = "pan";
            return false;
        }
        if (!(RecordInfo.isNull(this.seq_nr) || RecordInfo.isValidCHAR(3, this.seq_nr) && Validator.isValidN(this.seq_nr))) {
            this.failure_reason_description = "seq_nr";
            return false;
        }
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (this.account_type_nominated == null || !RecordInfo.isValidCHAR(2, this.account_type_nominated) || !Validator.isValidN(this.account_type_nominated)) {
            this.failure_reason_description = "account_type_nominated";
            return false;
        }
        if (this.account_type_qualifier == null || !RecordInfo.isValidINT(this.account_type_qualifier) || !Validator.isValidN(this.account_type_qualifier)) {
            this.failure_reason_description = "account_type_qualifier";
            return false;
        }
        if (!(RecordInfo.isNull(this.account_type) || RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
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
        if (this.pan == null || !RecordInfo.isValidVARCHAR(19, this.pan) || !Validator.isValidN(this.pan)) {
            this.failure_reason_description = "pan";
            return false;
        }
        if (!(RecordInfo.isNull(this.seq_nr) || RecordInfo.isValidCHAR(3, this.seq_nr) && Validator.isValidN(this.seq_nr))) {
            this.failure_reason_description = "seq_nr";
            return false;
        }
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.account_type_nominated) || this.account_type_nominated != null && RecordInfo.isValidCHAR(2, this.account_type_nominated) && Validator.isValidN(this.account_type_nominated))) {
            this.failure_reason_description = "account_type_nominated";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.account_type_qualifier) || this.account_type_qualifier != null && RecordInfo.isValidINT(this.account_type_qualifier) && Validator.isValidN(this.account_type_qualifier))) {
            this.failure_reason_description = "account_type_qualifier";
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

