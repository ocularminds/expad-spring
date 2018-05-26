/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.app.validator.Validator;
import com.ocularminds.expad.model.RecordInfo;

public class CardRecordInfo extends RecordInfo {
    public String firstfield = "";
    public String pan = "";
    public String seq_nr = "";
    public String card_program = "";
    public String default_account_type = "";
    public String card_status = "";
    public String expiry_date = "";
    public String hold_rsp_code = "";
    public String track2_value = "";
    public String track2_value_offset = "";
    public String pvki_or_pin_length = "";
    public String pvv_or_pin_offset_secure = "";
    public String pvv_or_pin_offset_insecure = "";
    public String validation_data_question = "";
    public String validation_data = "";
    public String cardholder_rsp_info = "";
    public String cardholder_title = "";
    public String cardholder_first_name = "";
    public String cardholder_middle_initials = "";
    public String cardholder_last_name = "";
    public String cardholder_name_on_card = "";
    public String cardholder_address_1 = "";
    public String cardholder_address_2 = "";
    public String cardholder_city = "";
    public String cardholder_region = "";
    public String cardholder_postal_code = "";
    public String cardholder_country = "";
    public String cardholder_date_of_birth = "";
    public String mailer_destination = "";
    public String company_name = "";
    public String vip = "";
    public String vip_lapse_date = "";
    public String discretionary_data = "";
    public String date_issued = "";
    public String date_activated = "";
    public String issuer_reference = "";
    public String branch_code = "";
    public String card_custom_state = "";
    public String customer_id = "";

    public boolean validateForFullLoad() {
        if (this.pan == null || !RecordInfo.isValidVARCHAR(19, this.pan) || !Validator.isValidN(this.pan)) {
            this.failure_reason_description = "pan";
            return false;
        }
        if (!(RecordInfo.isNull(this.seq_nr) || RecordInfo.isValidCHAR(3, this.seq_nr) && Validator.isValidN(this.seq_nr))) {
            this.failure_reason_description = "seq_nr";
            return false;
        }
        if (this.card_program == null || !RecordInfo.isValidVARCHAR(20, this.card_program) || !Validator.isValidAns(this.card_program)) {
            this.failure_reason_description = "card_program";
            return false;
        }
        if (this.default_account_type == null || !RecordInfo.isValidCHAR(2, this.default_account_type) || !Validator.isValidN(this.default_account_type)) {
            this.failure_reason_description = "default_account_type";
            return false;
        }
        if ("00".equals(this.default_account_type)) {
            this.failure_reason_description = "default_account_type: may not be 00";
            return false;
        }
        if (!(this.card_status != null && RecordInfo.isNull(this.card_status) && Validator.isValidN(this.card_status) || RecordInfo.isValidINT(this.card_status))) {
            this.failure_reason_description = "card_status";
            return false;
        }
        if (!RecordInfo.isNull(this.card_custom_state) && !RecordInfo.isValidINT(this.card_custom_state)) {
            this.failure_reason_description = "card_custom_state";
            return false;
        }
        if (!(RecordInfo.isNull(this.expiry_date) || RecordInfo.isValidCHAR(4, this.expiry_date) && Validator.isValidN(this.expiry_date))) {
            this.failure_reason_description = "expiry_date";
            return false;
        }
        if (!(RecordInfo.isNull(this.hold_rsp_code) || RecordInfo.isValidCHAR(2, this.hold_rsp_code) && Validator.isValidN(this.hold_rsp_code))) {
            this.failure_reason_description = "hold_rsp_code";
            return false;
        }
        if (!(RecordInfo.isNull(this.track2_value) || RecordInfo.isValidVARCHAR(20, this.track2_value) && Validator.isValidAns(this.track2_value))) {
            this.failure_reason_description = "track2_value";
            return false;
        }
        if (!RecordInfo.isNull(this.track2_value_offset) && !RecordInfo.isValidINT(this.track2_value_offset)) {
            this.failure_reason_description = "track2_value_offset";
            return false;
        }
        if (!(RecordInfo.isNull(this.pvki_or_pin_length) || RecordInfo.isValidINT(this.pvki_or_pin_length) && Validator.isValidN(this.pvki_or_pin_length))) {
            this.failure_reason_description = "pvki_or_pin_length";
            return false;
        }
        if (!(RecordInfo.isNull(this.pvv_or_pin_offset_secure) || RecordInfo.isValidVARCHAR(12, this.pvv_or_pin_offset_secure) && Validator.isValidN(this.pvv_or_pin_offset_secure))) {
            this.failure_reason_description = "pvv_or_pin_offset_secure";
            return false;
        }
        if (!(RecordInfo.isNull(this.pvv_or_pin_offset_insecure) || RecordInfo.isValidVARCHAR(12, this.pvv_or_pin_offset_insecure) && Validator.isValidN(this.pvv_or_pin_offset_insecure))) {
            this.failure_reason_description = "pvv_or_pin_offset_insecure";
            return false;
        }
        if (!(RecordInfo.isNull(this.validation_data_question) || RecordInfo.isValidVARCHAR(50, this.validation_data_question) && Validator.isValidAns(this.validation_data_question))) {
            this.failure_reason_description = "validation_data_question";
            return false;
        }
        if (!(RecordInfo.isNull(this.validation_data) || RecordInfo.isValidVARCHAR(50, this.validation_data) && Validator.isValidAns(this.validation_data))) {
            this.failure_reason_description = "validation_data";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_rsp_info) || RecordInfo.isValidVARCHAR(50, this.cardholder_rsp_info) && Validator.isValidAns(this.cardholder_rsp_info))) {
            this.failure_reason_description = "cardholder_rsp_info";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_title) || RecordInfo.isValidVARCHAR(5, this.cardholder_title) && Validator.isValidAns(this.cardholder_title))) {
            this.failure_reason_description = "cardholder_title";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_first_name) || RecordInfo.isValidVARCHAR(20, this.cardholder_first_name) && Validator.isValidAns(this.cardholder_first_name))) {
            this.failure_reason_description = "cardholder_first_name";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_middle_initials) || RecordInfo.isValidVARCHAR(10, this.cardholder_middle_initials) && Validator.isValidAns(this.cardholder_middle_initials))) {
            this.failure_reason_description = "cardholder_middle_initials";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_last_name) || RecordInfo.isValidVARCHAR(20, this.cardholder_last_name) && Validator.isValidAns(this.cardholder_last_name))) {
            this.failure_reason_description = "cardholder_last_name";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_name_on_card) || RecordInfo.isValidVARCHAR(26, this.cardholder_name_on_card) && Validator.isValidAns(this.cardholder_name_on_card))) {
            this.failure_reason_description = "cardholder_name_on_card";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_address_1) || RecordInfo.isValidVARCHAR(30, this.cardholder_address_1) && Validator.isValidAns(this.cardholder_address_1))) {
            this.failure_reason_description = "cardholder_address_1";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_address_2) || RecordInfo.isValidVARCHAR(30, this.cardholder_address_2) && Validator.isValidAns(this.cardholder_address_2))) {
            this.failure_reason_description = "cardholder_address_2";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_city) || RecordInfo.isValidVARCHAR(20, this.cardholder_city) && Validator.isValidAns(this.cardholder_city))) {
            this.failure_reason_description = "cardholder_city";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_region) || RecordInfo.isValidVARCHAR(20, this.cardholder_region) && Validator.isValidAns(this.cardholder_region))) {
            this.failure_reason_description = "cardholder_region";
            return false;
        }
        if (!RecordInfo.isNull(this.cardholder_postal_code) && !RecordInfo.isValidVARCHAR(9, this.cardholder_postal_code)) {
            this.failure_reason_description = "cardholder_postal_code";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_country) || RecordInfo.isValidCHAR(3, this.cardholder_country) && Validator.isValidAns(this.cardholder_country))) {
            this.failure_reason_description = "cardholder_country";
            return false;
        }
        if (!(RecordInfo.isNull(this.cardholder_date_of_birth) || RecordInfo.isValidCHAR(8, this.cardholder_date_of_birth) && Validator.isValidN(this.cardholder_date_of_birth))) {
            this.failure_reason_description = "cardholder_date_of_birth";
            return false;
        }
        if (!RecordInfo.isValidINT(this.mailer_destination) || !Validator.isValidN(this.mailer_destination)) {
            this.failure_reason_description = "mailer_destination";
            return false;
        }
        if (!(RecordInfo.isNull(this.company_name) || RecordInfo.isValidVARCHAR(26, this.company_name) && Validator.isValidAns(this.company_name))) {
            this.failure_reason_description = "company_name";
            return false;
        }
        if (!RecordInfo.isValidINT(this.vip) || !Validator.isValidN(this.vip)) {
            this.failure_reason_description = "vip";
            return false;
        }
        if (!RecordInfo.isNull(this.vip_lapse_date) && !RecordInfo.isValidDATETIME(this.vip_lapse_date)) {
            this.failure_reason_description = "vip_lapse_date";
            return false;
        }
        if (!(RecordInfo.isNull(this.discretionary_data) || RecordInfo.isValidVARCHAR(13, this.discretionary_data) && Validator.isValidAns(this.discretionary_data))) {
            this.failure_reason_description = "discretionary_data";
            return false;
        }
        if (!RecordInfo.isNull(this.date_issued) && !RecordInfo.isValidDATETIME(this.date_issued)) {
            this.failure_reason_description = "date_issued";
            return false;
        }
        if (!RecordInfo.isNull(this.date_activated) && !RecordInfo.isValidDATETIME(this.date_activated)) {
            this.failure_reason_description = "date_activated";
            return false;
        }
        if (!(RecordInfo.isNull(this.issuer_reference) || RecordInfo.isValidVARCHAR(20, this.issuer_reference) && Validator.isValidAns(this.issuer_reference))) {
            this.failure_reason_description = "issuer_reference";
            return false;
        }
        if (!(RecordInfo.isNull(this.branch_code) || RecordInfo.isValidVARCHAR(10, this.branch_code) && Validator.isValidAns(this.branch_code))) {
            this.failure_reason_description = "branch_code";
            return false;
        }
        if (!(RecordInfo.isNull(this.customer_id) || RecordInfo.isValidVARCHAR(25, this.customer_id) && Validator.isValidAns(this.customer_id))) {
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
        if (RecordInfo.isEMPTY(this.pan) || RecordInfo.isEMPTY(this.seq_nr) || RecordInfo.isEMPTY(this.expiry_date)) {
            StringBuffer errant_fields = new StringBuffer();
            if (RecordInfo.isEMPTY(this.pan)) {
                errant_fields.append("[pan] ");
            }
            if (RecordInfo.isEMPTY(this.seq_nr)) {
                errant_fields.append("[seq_nr] ");
            }
            if (RecordInfo.isEMPTY(this.expiry_date)) {
                errant_fields.append("[expiry_date] ");
            }
            this.failure_reason_description = "pan, seq_nr or expiry_date. One of the Key Fields do not have specific non-wildcard values. Errant field(s): " + errant_fields.toString();
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
        if (!(RecordInfo.isEMPTY(this.card_program) || this.card_program != null && RecordInfo.isValidVARCHAR(20, this.card_program) && Validator.isValidAns(this.card_program))) {
            this.failure_reason_description = "card_program";
            return false;
        }
        if (!RecordInfo.isEMPTY(this.default_account_type)) {
            if (this.default_account_type == null || !RecordInfo.isValidCHAR(2, this.default_account_type) || !Validator.isValidN(this.default_account_type)) {
                this.failure_reason_description = "default_account_type";
                return false;
            }
            if ("00".equals(this.default_account_type)) {
                this.failure_reason_description = "default_account_type: may not be 00";
                return false;
            }
        }
        if (!(RecordInfo.isEMPTY(this.card_custom_state) || RecordInfo.isNull(this.card_custom_state) || RecordInfo.isValidINT(this.card_custom_state))) {
            this.failure_reason_description = "card_custom_state";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.card_status) || RecordInfo.isNull(this.card_status) || RecordInfo.isValidINT(this.card_status) && Validator.isValidN(this.card_status))) {
            this.failure_reason_description = "card_status";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.expiry_date) || RecordInfo.isNull(this.expiry_date) || RecordInfo.isValidCHAR(4, this.expiry_date) && Validator.isValidN(this.expiry_date))) {
            this.failure_reason_description = "expiry_date";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.hold_rsp_code) || RecordInfo.isNull(this.hold_rsp_code) || RecordInfo.isValidCHAR(2, this.hold_rsp_code) && Validator.isValidN(this.hold_rsp_code))) {
            this.failure_reason_description = "hold_rsp_code";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.track2_value) || RecordInfo.isNull(this.track2_value) || RecordInfo.isValidVARCHAR(20, this.track2_value) && Validator.isValidAns(this.track2_value))) {
            this.failure_reason_description = "track2_value";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.track2_value_offset) || RecordInfo.isNull(this.track2_value_offset) || RecordInfo.isValidINT(this.track2_value_offset))) {
            this.failure_reason_description = "track2_value_offset";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.pvki_or_pin_length) || RecordInfo.isNull(this.pvki_or_pin_length) || RecordInfo.isValidINT(this.pvki_or_pin_length) && Validator.isValidN(this.pvki_or_pin_length))) {
            this.failure_reason_description = "pvki_or_pin_length";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.pvv_or_pin_offset_secure) || RecordInfo.isNull(this.pvv_or_pin_offset_secure) || RecordInfo.isValidVARCHAR(12, this.pvv_or_pin_offset_secure) && Validator.isValidN(this.pvv_or_pin_offset_secure))) {
            this.failure_reason_description = "pvv_or_pin_offset_secure";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.pvv_or_pin_offset_insecure) || RecordInfo.isNull(this.pvv_or_pin_offset_insecure) || RecordInfo.isValidVARCHAR(12, this.pvv_or_pin_offset_insecure) && Validator.isValidN(this.pvv_or_pin_offset_insecure))) {
            this.failure_reason_description = "pvv_or_pin_offset_insecure";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.validation_data_question) || RecordInfo.isNull(this.validation_data_question) || RecordInfo.isValidVARCHAR(50, this.validation_data_question) && Validator.isValidAns(this.validation_data_question))) {
            this.failure_reason_description = "validation_data_question";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.validation_data) || RecordInfo.isNull(this.validation_data) || RecordInfo.isValidVARCHAR(50, this.validation_data) && Validator.isValidAns(this.validation_data))) {
            this.failure_reason_description = "validation_data";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_rsp_info) || RecordInfo.isNull(this.cardholder_rsp_info) || RecordInfo.isValidVARCHAR(50, this.cardholder_rsp_info) && Validator.isValidAns(this.cardholder_rsp_info))) {
            this.failure_reason_description = "cardholder_rsp_info";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_title) || RecordInfo.isNull(this.cardholder_title) || RecordInfo.isValidVARCHAR(5, this.cardholder_title) && Validator.isValidAns(this.cardholder_title))) {
            this.failure_reason_description = "cardholder_title";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_first_name) || RecordInfo.isNull(this.cardholder_first_name) || RecordInfo.isValidVARCHAR(20, this.cardholder_first_name) && Validator.isValidAns(this.cardholder_first_name))) {
            this.failure_reason_description = "cardholder_first_name";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_middle_initials) || RecordInfo.isNull(this.cardholder_middle_initials) || RecordInfo.isValidVARCHAR(10, this.cardholder_middle_initials) && Validator.isValidAns(this.cardholder_middle_initials))) {
            this.failure_reason_description = "cardholder_middle_initials";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_last_name) || RecordInfo.isNull(this.cardholder_last_name) || RecordInfo.isValidVARCHAR(20, this.cardholder_last_name) && Validator.isValidAns(this.cardholder_last_name))) {
            this.failure_reason_description = "cardholder_last_name";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_name_on_card) || RecordInfo.isNull(this.cardholder_name_on_card) || RecordInfo.isValidVARCHAR(26, this.cardholder_name_on_card) && Validator.isValidAns(this.cardholder_name_on_card))) {
            this.failure_reason_description = "cardholder_name_on_card";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_address_1) || RecordInfo.isNull(this.cardholder_address_1) || RecordInfo.isValidVARCHAR(30, this.cardholder_address_1) && Validator.isValidAns(this.cardholder_address_1))) {
            this.failure_reason_description = "cardholder_address_1";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_address_2) || RecordInfo.isNull(this.cardholder_address_2) || RecordInfo.isValidVARCHAR(30, this.cardholder_address_2) && Validator.isValidAns(this.cardholder_address_2))) {
            this.failure_reason_description = "cardholder_address_2";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_city) || RecordInfo.isNull(this.cardholder_city) || RecordInfo.isValidVARCHAR(20, this.cardholder_city) && Validator.isValidAns(this.cardholder_city))) {
            this.failure_reason_description = "cardholder_city";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_region) || RecordInfo.isNull(this.cardholder_region) || RecordInfo.isValidVARCHAR(20, this.cardholder_region) && Validator.isValidAns(this.cardholder_region))) {
            this.failure_reason_description = "cardholder_region";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_postal_code) || RecordInfo.isNull(this.cardholder_postal_code) || RecordInfo.isValidVARCHAR(9, this.cardholder_postal_code))) {
            this.failure_reason_description = "cardholder_postal_code";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_country) || RecordInfo.isNull(this.cardholder_country) || RecordInfo.isValidCHAR(3, this.cardholder_country) && Validator.isValidAns(this.cardholder_country))) {
            this.failure_reason_description = "cardholder_country";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.cardholder_date_of_birth) || RecordInfo.isNull(this.cardholder_date_of_birth) || RecordInfo.isValidCHAR(8, this.cardholder_date_of_birth) && Validator.isValidN(this.cardholder_date_of_birth))) {
            this.failure_reason_description = "cardholder_date_of_birth";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.mailer_destination) || RecordInfo.isValidINT(this.mailer_destination) && Validator.isValidN(this.mailer_destination))) {
            this.failure_reason_description = "mailer_destination";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.company_name) || RecordInfo.isNull(this.company_name) || RecordInfo.isValidVARCHAR(26, this.company_name) && Validator.isValidAns(this.company_name))) {
            this.failure_reason_description = "company_name";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.vip) || RecordInfo.isValidINT(this.vip) && Validator.isValidN(this.vip))) {
            this.failure_reason_description = "vip";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.vip_lapse_date) || RecordInfo.isNull(this.vip_lapse_date) || RecordInfo.isValidDATETIME(this.vip_lapse_date))) {
            this.failure_reason_description = "vip_lapse_date";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.discretionary_data) || RecordInfo.isNull(this.discretionary_data) || RecordInfo.isValidVARCHAR(13, this.discretionary_data) && Validator.isValidAns(this.discretionary_data))) {
            this.failure_reason_description = "discretionary_data";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.date_issued) || RecordInfo.isNull(this.date_issued) || RecordInfo.isValidDATETIME(this.date_issued))) {
            this.failure_reason_description = "date_issued";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.date_activated) || RecordInfo.isNull(this.date_activated) || RecordInfo.isValidDATETIME(this.date_activated))) {
            this.failure_reason_description = "date_activated";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.issuer_reference) || RecordInfo.isNull(this.issuer_reference) || RecordInfo.isValidVARCHAR(20, this.issuer_reference) && Validator.isValidAns(this.issuer_reference))) {
            this.failure_reason_description = "issuer_reference";
            return false;
        }
        if (!(RecordInfo.isEMPTY(this.branch_code) || RecordInfo.isNull(this.branch_code) || RecordInfo.isValidVARCHAR(10, this.branch_code) && Validator.isValidAns(this.branch_code))) {
            this.failure_reason_description = "branch_code";
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

