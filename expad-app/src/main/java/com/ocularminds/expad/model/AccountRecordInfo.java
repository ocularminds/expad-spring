/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class AccountRecordInfo
extends RecordInfo {
    public String firstfield = "";
    public String account_id = "";
    public String account_type = "";
    public String currency_code = "";
    public String customer_id = "";

    public boolean validateForFullLoad() {
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (this.account_type == null || !RecordInfo.isValidCHAR(2, this.account_type) || !Validator.isValidN(this.account_type)) {
            this.failure_reason_description = "account_type";
            return false;
        }
        if (this.currency_code == null || !RecordInfo.isValidCHAR(3, this.currency_code) || !Validator.isValidN(this.currency_code)) {
            this.failure_reason_description = "currency_code";
            return false;
        }
        if (!(this.customer_id == null || RecordInfo.isValidVARCHAR(25, this.customer_id) && Validator.isValidAns(this.customer_id))) {
            this.failure_reason_description = "customer_id";
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
        if (!(RecordInfo.isEMPTY(this.account_type) || this.account_type != null && RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
            this.failure_reason_description = "account_type";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.currency_code) || this.currency_code != null && RecordInfo.isValidCHAR(3, this.currency_code) && Validator.isValidN(this.currency_code))) {
            this.failure_reason_description = "currency_code";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.customer_id) || RecordInfo.isNull(this.customer_id) || RecordInfo.isValidVARCHAR(25, this.customer_id) && Validator.isValidAns(this.customer_id))) {
            this.failure_reason_description = "customer_id";
            return false;
        }
        this.failure_reason_description = null;
        return true;
    }
}

