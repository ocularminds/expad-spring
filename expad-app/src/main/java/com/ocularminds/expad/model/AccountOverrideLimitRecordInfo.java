/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class AccountOverrideLimitRecordInfo
extends RecordInfo {
    public String firstfield = "";
    public String account_id = "";
    public String goods_nr_trans_limit = "";
    public String goods_limit = "";
    public String goods_offline_limit = "";
    public String cash_nr_trans_limit = "";
    public String cash_limit = "";
    public String cash_offline_limit = "";
    public String card_not_present_limit = "";
    public String card_not_present_offline_limit = "";
    public String deposit_credit_limit = "";
    public String account_type = "";
    public String weekly_goods_nr_trans_limit = "";
    public String weekly_goods_limit = "";
    public String weekly_goods_offline_limit = "";
    public String weekly_cash_nr_trans_limit = "";
    public String weekly_cash_limit = "";
    public String weekly_cash_offline_limit = "";
    public String weekly_card_not_present_limit = "";
    public String weekly_card_not_present_offline_limit = "";
    public String weekly_deposit_credit_limit = "";
    public String monthly_goods_nr_trans_limit = "";
    public String monthly_goods_limit = "";
    public String monthly_goods_offline_limit = "";
    public String monthly_cash_nr_trans_limit = "";
    public String monthly_cash_limit = "";
    public String monthly_cash_offline_limit = "";
    public String monthly_card_not_present_limit = "";
    public String monthly_card_not_present_offline_limit = "";
    public String monthly_deposit_credit_limit = "";
    public String tran_goods_limit = "";
    public String tran_goods_offline_limit = "";
    public String tran_cash_limit = "";
    public String tran_cash_offline_limit = "";
    public String tran_card_not_present_limit = "";
    public String tran_card_not_present_offline_limit = "";
    public String tran_deposit_credit_limit = "";

    public boolean validateForFullLoad() {
        if (this.account_id == null || !RecordInfo.isValidVARCHAR(28, this.account_id) || !Validator.isValidAns(this.account_id)) {
            this.failure_reason_description = "account_id";
            return false;
        }
        if (this.goods_nr_trans_limit == null || !RecordInfo.isValidINT(this.goods_nr_trans_limit) || !Validator.isValidN(this.goods_nr_trans_limit)) {
            this.failure_reason_description = "goods_nr_trans_limit";
            return false;
        }
        if (this.goods_limit == null || !RecordInfo.isValidNUMERIC(12, this.goods_limit) || !Validator.isValidN(this.goods_limit)) {
            this.failure_reason_description = "goods_limit";
            return false;
        }
        if (this.goods_offline_limit == null || !RecordInfo.isValidNUMERIC(12, this.goods_offline_limit) || !Validator.isValidN(this.goods_offline_limit)) {
            this.failure_reason_description = "goods_offline_limit";
            return false;
        }
        if (this.cash_nr_trans_limit == null || !RecordInfo.isValidINT(this.cash_nr_trans_limit) || !Validator.isValidN(this.cash_nr_trans_limit)) {
            this.failure_reason_description = "cash_nr_trans_limit";
            return false;
        }
        if (this.cash_limit == null || !RecordInfo.isValidNUMERIC(12, this.cash_limit) || !Validator.isValidN(this.cash_limit)) {
            this.failure_reason_description = "cash_limit";
            return false;
        }
        if (this.cash_offline_limit == null || !RecordInfo.isValidNUMERIC(12, this.cash_offline_limit) || !Validator.isValidN(this.cash_offline_limit)) {
            this.failure_reason_description = "cash_offline_limit";
            return false;
        }
        if (this.card_not_present_limit == null || !RecordInfo.isValidNUMERIC(12, this.card_not_present_limit) || !Validator.isValidN(this.card_not_present_limit)) {
            this.failure_reason_description = "card_not_present_limit";
            return false;
        }
        if (this.card_not_present_offline_limit == null || !RecordInfo.isValidNUMERIC(12, this.card_not_present_offline_limit) || !Validator.isValidN(this.card_not_present_offline_limit)) {
            this.failure_reason_description = "card_not_present_offline_limit";
            return false;
        }
        if (this.deposit_credit_limit == null || !RecordInfo.isValidNUMERIC(12, this.deposit_credit_limit) || !Validator.isValidN(this.deposit_credit_limit)) {
            this.failure_reason_description = "deposit_credit_limit";
            return false;
        }
        if (!(RecordInfo.isNull(this.account_type) || RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
            this.failure_reason_description = "account_type";
            return false;
        }
        if (this.weekly_goods_nr_trans_limit == null) {
            if (this.weekly_goods_limit != null || this.weekly_goods_offline_limit != null || this.weekly_cash_nr_trans_limit != null || this.weekly_cash_limit != null || this.weekly_cash_offline_limit != null || this.weekly_card_not_present_limit != null || this.weekly_card_not_present_offline_limit != null || this.weekly_deposit_credit_limit != null) {
                this.failure_reason_description = "weekly limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.weekly_goods_limit == null || this.weekly_goods_offline_limit == null || this.weekly_cash_nr_trans_limit == null || this.weekly_cash_limit == null || this.weekly_cash_offline_limit == null || this.weekly_card_not_present_limit == null || this.weekly_card_not_present_offline_limit == null || this.weekly_deposit_credit_limit == null) {
                this.failure_reason_description = "weekly limits: not all required limits are specified.";
                return false;
            }
            if (!RecordInfo.isValidINT(this.weekly_goods_nr_trans_limit) || !Validator.isValidN(this.weekly_goods_nr_trans_limit)) {
                this.failure_reason_description = "weekly_goods_nr_trans_limit";
                return false;
            }
        }
        if (!(this.weekly_goods_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_goods_limit) && Validator.isValidN(this.weekly_goods_limit))) {
            this.failure_reason_description = "weekly_goods_limit";
            return false;
        }
        if (!(this.weekly_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_goods_offline_limit) && Validator.isValidN(this.weekly_goods_offline_limit))) {
            this.failure_reason_description = "weekly_goods_offline_limit";
            return false;
        }
        if (!(this.weekly_cash_nr_trans_limit == null || RecordInfo.isValidINT(this.weekly_cash_nr_trans_limit) && Validator.isValidN(this.weekly_cash_nr_trans_limit))) {
            this.failure_reason_description = "weekly_cash_nr_trans_limit";
            return false;
        }
        if (!(this.weekly_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_cash_limit) && Validator.isValidN(this.weekly_cash_limit))) {
            this.failure_reason_description = "weekly_cash_limit";
            return false;
        }
        if (!(this.weekly_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_cash_offline_limit) && Validator.isValidN(this.weekly_cash_offline_limit))) {
            this.failure_reason_description = "weekly_cash_offline_limit";
            return false;
        }
        if (!(this.weekly_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_card_not_present_limit) && Validator.isValidN(this.weekly_card_not_present_limit))) {
            this.failure_reason_description = "weekly_card_not_present_limit";
            return false;
        }
        if (!(this.weekly_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_card_not_present_offline_limit) && Validator.isValidN(this.weekly_card_not_present_offline_limit))) {
            this.failure_reason_description = "weekly_card_not_present_offline_limit";
            return false;
        }
        if (!(this.weekly_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_deposit_credit_limit) && Validator.isValidN(this.weekly_deposit_credit_limit))) {
            this.failure_reason_description = "weekly_deposit_credit_limit";
            return false;
        }
        if (this.monthly_goods_nr_trans_limit == null) {
            if (this.monthly_goods_limit != null || this.monthly_goods_offline_limit != null || this.monthly_cash_nr_trans_limit != null || this.monthly_cash_limit != null || this.monthly_cash_offline_limit != null || this.monthly_card_not_present_limit != null || this.monthly_card_not_present_offline_limit != null || this.monthly_deposit_credit_limit != null) {
                this.failure_reason_description = "monthly limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.monthly_goods_limit == null || this.monthly_goods_offline_limit == null || this.monthly_cash_nr_trans_limit == null || this.monthly_cash_limit == null || this.monthly_cash_offline_limit == null || this.monthly_card_not_present_limit == null || this.monthly_card_not_present_offline_limit == null || this.monthly_deposit_credit_limit == null) {
                this.failure_reason_description = "monthly limits: not all required limits are specified.";
                return false;
            }
            if (!RecordInfo.isValidINT(this.monthly_goods_nr_trans_limit) || !Validator.isValidN(this.monthly_goods_nr_trans_limit)) {
                this.failure_reason_description = "monthly_goods_nr_trans_limit";
                return false;
            }
        }
        if (!(this.monthly_goods_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_goods_limit) && Validator.isValidN(this.monthly_goods_limit))) {
            this.failure_reason_description = "monthly_goods_limit";
            return false;
        }
        if (!(this.monthly_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_goods_offline_limit) && Validator.isValidN(this.monthly_goods_offline_limit))) {
            this.failure_reason_description = "monthly_goods_offline_limit";
            return false;
        }
        if (!(this.monthly_cash_nr_trans_limit == null || RecordInfo.isValidINT(this.monthly_cash_nr_trans_limit) && Validator.isValidN(this.monthly_cash_nr_trans_limit))) {
            this.failure_reason_description = "monthly_cash_nr_trans_limit";
            return false;
        }
        if (!(this.monthly_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_cash_limit) && Validator.isValidN(this.monthly_cash_limit))) {
            this.failure_reason_description = "monthly_cash_limit";
            return false;
        }
        if (!(this.monthly_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_cash_offline_limit) && Validator.isValidN(this.monthly_cash_offline_limit))) {
            this.failure_reason_description = "monthly_cash_offline_limit";
            return false;
        }
        if (!(this.monthly_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_card_not_present_limit) && Validator.isValidN(this.monthly_card_not_present_limit))) {
            this.failure_reason_description = "monthly_card_not_present_limit";
            return false;
        }
        if (!(this.monthly_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_card_not_present_offline_limit) && Validator.isValidN(this.monthly_card_not_present_offline_limit))) {
            this.failure_reason_description = "monthly_card_not_present_offline_limit";
            return false;
        }
        if (!(this.monthly_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_deposit_credit_limit) && Validator.isValidN(this.monthly_deposit_credit_limit))) {
            this.failure_reason_description = "monthly_deposit_credit_limit";
            return false;
        }
        if (this.tran_goods_limit == null) {
            if (this.tran_goods_offline_limit != null || this.tran_cash_limit != null || this.tran_cash_offline_limit != null || this.tran_card_not_present_limit != null || this.tran_card_not_present_offline_limit != null || this.tran_deposit_credit_limit != null) {
                this.failure_reason_description = "tran limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.tran_goods_offline_limit == null || this.tran_cash_limit == null || this.tran_cash_offline_limit == null || this.tran_card_not_present_limit == null || this.tran_card_not_present_offline_limit == null || this.tran_deposit_credit_limit == null) {
                this.failure_reason_description = "tran limits: not all required limits are specified.";
                return false;
            }
            if (!RecordInfo.isValidINT(this.tran_goods_limit) || !Validator.isValidN(this.tran_goods_limit)) {
                this.failure_reason_description = "tran_goods_limit";
                return false;
            }
        }
        if (!(this.tran_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_goods_offline_limit) && Validator.isValidN(this.tran_goods_offline_limit))) {
            this.failure_reason_description = "tran_goods_offline_limit";
            return false;
        }
        if (!(this.tran_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_cash_limit) && Validator.isValidN(this.tran_cash_limit))) {
            this.failure_reason_description = "tran_cash_limit";
            return false;
        }
        if (!(this.tran_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_cash_offline_limit) && Validator.isValidN(this.tran_cash_offline_limit))) {
            this.failure_reason_description = "tran_cash_offline_limit";
            return false;
        }
        if (!(this.tran_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_card_not_present_limit) && Validator.isValidN(this.tran_card_not_present_limit))) {
            this.failure_reason_description = "tran_card_not_present_limit";
            return false;
        }
        if (!(this.tran_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_card_not_present_offline_limit) && Validator.isValidN(this.tran_card_not_present_offline_limit))) {
            this.failure_reason_description = "tran_card_not_present_offline_limit";
            return false;
        }
        if (!(this.tran_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_deposit_credit_limit) && Validator.isValidN(this.tran_deposit_credit_limit))) {
            this.failure_reason_description = "tran_deposit_credit_limit";
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
        if (!(RecordInfo.isEMPTY(this.goods_nr_trans_limit) || this.goods_nr_trans_limit != null && RecordInfo.isValidINT(this.goods_nr_trans_limit) && Validator.isValidN(this.goods_nr_trans_limit))) {
            this.failure_reason_description = "goods_nr_trans_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.goods_limit) || this.goods_limit != null && RecordInfo.isValidNUMERIC(12, this.goods_limit) && Validator.isValidN(this.goods_limit))) {
            this.failure_reason_description = "goods_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.goods_offline_limit) || this.goods_offline_limit != null && RecordInfo.isValidNUMERIC(12, this.goods_offline_limit) && Validator.isValidN(this.goods_offline_limit))) {
            this.failure_reason_description = "goods_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cash_nr_trans_limit) || this.cash_nr_trans_limit != null && RecordInfo.isValidINT(this.cash_nr_trans_limit) && Validator.isValidN(this.cash_nr_trans_limit))) {
            this.failure_reason_description = "cash_nr_trans_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cash_limit) || this.cash_limit != null && RecordInfo.isValidNUMERIC(12, this.cash_limit) && Validator.isValidN(this.cash_limit))) {
            this.failure_reason_description = "cash_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cash_offline_limit) || this.cash_offline_limit != null && RecordInfo.isValidNUMERIC(12, this.cash_offline_limit) && Validator.isValidN(this.cash_offline_limit))) {
            this.failure_reason_description = "cash_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.card_not_present_limit) || this.card_not_present_limit != null && RecordInfo.isValidNUMERIC(12, this.card_not_present_limit) && Validator.isValidN(this.card_not_present_limit))) {
            this.failure_reason_description = "card_not_present_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.card_not_present_offline_limit) || this.card_not_present_offline_limit != null && RecordInfo.isValidNUMERIC(12, this.card_not_present_offline_limit) && Validator.isValidN(this.card_not_present_offline_limit))) {
            this.failure_reason_description = "card_not_present_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.deposit_credit_limit) || this.deposit_credit_limit != null && RecordInfo.isValidNUMERIC(12, this.deposit_credit_limit) && Validator.isValidN(this.deposit_credit_limit))) {
            this.failure_reason_description = "deposit_credit_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.account_type) || RecordInfo.isNull(this.account_type) || RecordInfo.isValidCHAR(2, this.account_type) && Validator.isValidN(this.account_type))) {
            this.failure_reason_description = "account_type";
            return false;
        }
        if (this.weekly_goods_nr_trans_limit == null) {
            if (this.weekly_goods_limit != null || this.weekly_goods_offline_limit != null || this.weekly_cash_nr_trans_limit != null || this.weekly_cash_limit != null || this.weekly_cash_offline_limit != null || this.weekly_card_not_present_limit != null || this.weekly_card_not_present_offline_limit != null || this.weekly_deposit_credit_limit != null) {
                this.failure_reason_description = "weekly limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.weekly_goods_limit == null || this.weekly_goods_offline_limit == null || this.weekly_cash_nr_trans_limit == null || this.weekly_cash_limit == null || this.weekly_cash_offline_limit == null || this.weekly_card_not_present_limit == null || this.weekly_card_not_present_offline_limit == null || this.weekly_deposit_credit_limit == null) {
                this.failure_reason_description = "weekly limits: not all required limits are specified.";
                return false;
            }
            if (!(RecordInfo.isEMPTY(this.weekly_goods_nr_trans_limit) || RecordInfo.isValidINT(this.weekly_goods_nr_trans_limit) && Validator.isValidN(this.weekly_goods_nr_trans_limit))) {
                this.failure_reason_description = "weekly_goods_nr_trans_limit";
                return false;
            }
        }
        if (!(RecordInfo.isEMPTY(this.weekly_goods_limit) || this.weekly_goods_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_goods_limit) && Validator.isValidN(this.weekly_goods_limit))) {
            this.failure_reason_description = "weekly_goods_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_goods_offline_limit) || this.weekly_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_goods_offline_limit) && Validator.isValidN(this.weekly_goods_offline_limit))) {
            this.failure_reason_description = "weekly_goods_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_cash_nr_trans_limit) || this.weekly_cash_nr_trans_limit == null || RecordInfo.isValidINT(this.weekly_cash_nr_trans_limit) && Validator.isValidN(this.weekly_cash_nr_trans_limit))) {
            this.failure_reason_description = "weekly_cash_nr_trans_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_cash_limit) || this.weekly_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_cash_limit) && Validator.isValidN(this.weekly_cash_limit))) {
            this.failure_reason_description = "weekly_cash_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_cash_offline_limit) || this.weekly_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_cash_offline_limit) && Validator.isValidN(this.weekly_cash_offline_limit))) {
            this.failure_reason_description = "weekly_cash_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_card_not_present_limit) || this.weekly_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_card_not_present_limit) && Validator.isValidN(this.weekly_card_not_present_limit))) {
            this.failure_reason_description = "weekly_card_not_present_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_card_not_present_offline_limit) || this.weekly_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_card_not_present_offline_limit) && Validator.isValidN(this.weekly_card_not_present_offline_limit))) {
            this.failure_reason_description = "weekly_card_not_present_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.weekly_deposit_credit_limit) || this.weekly_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.weekly_deposit_credit_limit) && Validator.isValidN(this.weekly_deposit_credit_limit))) {
            this.failure_reason_description = "weekly_deposit_credit_limit";
            return false;
        }
        if (this.monthly_goods_nr_trans_limit == null) {
            if (this.monthly_goods_limit != null || this.monthly_goods_offline_limit != null || this.monthly_cash_nr_trans_limit != null || this.monthly_cash_limit != null || this.monthly_cash_offline_limit != null || this.monthly_card_not_present_limit != null || this.monthly_card_not_present_offline_limit != null || this.monthly_deposit_credit_limit != null) {
                this.failure_reason_description = "monthly limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.monthly_goods_limit == null || this.monthly_goods_offline_limit == null || this.monthly_cash_nr_trans_limit == null || this.monthly_cash_limit == null || this.monthly_cash_offline_limit == null || this.monthly_card_not_present_limit == null || this.monthly_card_not_present_offline_limit == null || this.monthly_deposit_credit_limit == null) {
                this.failure_reason_description = "monthly limits: not all required limits are specified.";
                return false;
            }
            if (!(RecordInfo.isEMPTY(this.monthly_goods_nr_trans_limit) || RecordInfo.isValidINT(this.monthly_goods_nr_trans_limit) && Validator.isValidN(this.monthly_goods_nr_trans_limit))) {
                this.failure_reason_description = "monthly_goods_nr_trans_limit";
                return false;
            }
        }
        if (!(RecordInfo.isEMPTY(this.monthly_goods_limit) || this.monthly_goods_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_goods_limit) && Validator.isValidN(this.monthly_goods_limit))) {
            this.failure_reason_description = "monthly_goods_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_goods_offline_limit) || this.monthly_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_goods_offline_limit) && Validator.isValidN(this.monthly_goods_offline_limit))) {
            this.failure_reason_description = "monthly_goods_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_cash_nr_trans_limit) || this.monthly_cash_nr_trans_limit == null || RecordInfo.isValidINT(this.monthly_cash_nr_trans_limit) && Validator.isValidN(this.monthly_cash_nr_trans_limit))) {
            this.failure_reason_description = "monthly_cash_nr_trans_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_cash_limit) || this.monthly_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_cash_limit) && Validator.isValidN(this.monthly_cash_limit))) {
            this.failure_reason_description = "monthly_cash_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_cash_offline_limit) || this.monthly_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_cash_offline_limit) && Validator.isValidN(this.monthly_cash_offline_limit))) {
            this.failure_reason_description = "monthly_cash_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_card_not_present_limit) || this.monthly_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_card_not_present_limit) && Validator.isValidN(this.monthly_card_not_present_limit))) {
            this.failure_reason_description = "monthly_card_not_present_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_card_not_present_offline_limit) || this.monthly_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_card_not_present_offline_limit) && Validator.isValidN(this.monthly_card_not_present_offline_limit))) {
            this.failure_reason_description = "monthly_card_not_present_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.monthly_deposit_credit_limit) || this.monthly_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.monthly_deposit_credit_limit) && Validator.isValidN(this.monthly_deposit_credit_limit))) {
            this.failure_reason_description = "monthly_deposit_credit_limit";
            return false;
        }
        if (this.tran_goods_limit == null) {
            if (this.tran_goods_offline_limit != null || this.tran_cash_limit != null || this.tran_cash_offline_limit != null || this.tran_card_not_present_limit != null || this.tran_card_not_present_offline_limit != null || this.tran_deposit_credit_limit != null) {
                this.failure_reason_description = "tran limits: not all required limits are specified.";
                return false;
            }
        } else {
            if (this.tran_goods_offline_limit == null || this.tran_cash_limit == null || this.tran_cash_offline_limit == null || this.tran_card_not_present_limit == null || this.tran_card_not_present_offline_limit == null || this.tran_deposit_credit_limit == null) {
                this.failure_reason_description = "tran limits: not all required limits are specified.";
                return false;
            }
            if (!(RecordInfo.isEMPTY(this.tran_goods_limit) || RecordInfo.isValidINT(this.tran_goods_limit) && Validator.isValidN(this.tran_goods_limit))) {
                this.failure_reason_description = "tran_goods_limit";
                return false;
            }
        }
        if (!(RecordInfo.isEMPTY(this.tran_goods_offline_limit) || this.tran_goods_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_goods_offline_limit) && Validator.isValidN(this.tran_goods_offline_limit))) {
            this.failure_reason_description = "tran_goods_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_cash_limit) || this.tran_cash_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_cash_limit) && Validator.isValidN(this.tran_cash_limit))) {
            this.failure_reason_description = "tran_cash_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_cash_offline_limit) || this.tran_cash_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_cash_offline_limit) && Validator.isValidN(this.tran_cash_offline_limit))) {
            this.failure_reason_description = "tran_cash_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_card_not_present_limit) || this.tran_card_not_present_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_card_not_present_limit) && Validator.isValidN(this.tran_card_not_present_limit))) {
            this.failure_reason_description = "tran_card_not_present_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_card_not_present_offline_limit) || this.tran_card_not_present_offline_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_card_not_present_offline_limit) && Validator.isValidN(this.tran_card_not_present_offline_limit))) {
            this.failure_reason_description = "tran_card_not_present_offline_limit";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.tran_deposit_credit_limit) || this.tran_deposit_credit_limit == null || RecordInfo.isValidNUMERIC(12, this.tran_deposit_credit_limit) && Validator.isValidN(this.tran_deposit_credit_limit))) {
            this.failure_reason_description = "tran_deposit_credit_limit";
            return false;
        }
        this.failure_reason_description = null;
        return true;
    }
}

