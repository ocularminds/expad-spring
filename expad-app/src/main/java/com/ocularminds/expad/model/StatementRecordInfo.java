/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class StatementRecordInfo extends RecordInfo {
    public String firstfield = "";
    public String account_id = "";
    public String tran_type = "";
    public String tran_amount = "";
    public String tran_local_datetime = "";
    public String tran_posted_datetime = "";
    public String account_type = "";

    public boolean validateForFullLoad() {
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (this.tran_type == null || !RecordInfo.isValidCHAR(2, this.tran_type) || !Validator.isValidN(this.tran_type)) {
            this.failure_reason_description = "tran_type";
            return false;
        }
        if (this.tran_amount == null || !RecordInfo.isValidFLOAT(this.tran_amount)) {
            this.failure_reason_description = "tran_amount";
            return false;
        }
        if (!(RecordInfo.isNull(this.tran_local_datetime) || this.tran_local_datetime != null && RecordInfo.isValidDATETIME(this.tran_local_datetime) && Validator.isValidNs(this.tran_local_datetime))) {
            this.failure_reason_description = "tran_local_datetime";
            return false;
        }
        if (this.tran_posted_datetime == null || !RecordInfo.isValidDATETIME(this.tran_posted_datetime) || !Validator.isValidNs(this.tran_posted_datetime)) {
            this.failure_reason_description = "tran_posted_datetime";
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
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_type) || this.tran_type != null && RecordInfo.isValidCHAR(2, this.tran_type) && Validator.isValidN(this.tran_type))) {
            this.failure_reason_description = "tran_type";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_amount) || this.tran_amount != null && RecordInfo.isValidFLOAT(this.tran_amount))) {
            this.failure_reason_description = "tran_amount";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_local_datetime) || RecordInfo.isNull(this.tran_local_datetime) || RecordInfo.isValidDATETIME(this.tran_local_datetime) && Validator.isValidNs(this.tran_local_datetime))) {
            this.failure_reason_description = "tran_local_datetime";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_posted_datetime) || RecordInfo.isNull(this.tran_posted_datetime) || RecordInfo.isValidDATETIME(this.tran_posted_datetime) && Validator.isValidNs(this.tran_posted_datetime))) {
            this.failure_reason_description = "tran_posted_datetime";
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

