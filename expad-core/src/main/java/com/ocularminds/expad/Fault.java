package com.ocularminds.expad;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class Fault implements Serializable {

    private String error;
    private String fault;
    private String group;
    private String value;
    private Object data;
    private static Map<String, String> errors = Fault.load();

    public Fault() {}

    public Fault(String error, String fault) {
        this.error = error;
        this.fault = fault;
    }

    public Fault(String error, String fault, Object data) {
        this.error = error;
        this.fault = fault;
        this.data = data;
    }

    public Fault(String value, Long data) {
        this.value = value;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.error.equals("00");
    }

    public boolean isFailed() {
        return !this.error.equals("00");
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object o) {
        data = o;
    }

    public static String error(String err) {
        return errors.get(err) != null ? errors.get(err) : "Unknown error";
    }

    private static Map<String, String> load() {
        Map<String, String> responses = new HashMap<>();
        responses.put("00", "Successful approval");
        responses.put("01", "Refer to card issuer");
        responses.put("02", "Refer to card issuer, special condition");
        responses.put("03", "Invalid merchant or service provider");
        responses.put("04", "Pickup card");
        responses.put("05", "Do not honor");
        responses.put("06", "Error");
        responses.put("07", "Pickup card, special condition");
        responses.put("10", "Partial Approval");
        responses.put("11", "V.I.P. approval");
        responses.put("12", "Invalid transaction");
        responses.put("13", "Invalid amount ");
        responses.put("14", "Invalid account number");
        responses.put("15", "No such issuer");
        responses.put("17", "Customer cancellation");
        responses.put("19", "Re-enter transaction");
        responses.put("20", "Invalid response");
        responses.put("21", "No action taken ");
        responses.put("22", "Suspected Malfunction");
        responses.put("25", "Unable to locate record in file");
        responses.put("28", "File is temporarily unavailable");
        responses.put("30", "Format Error");
        responses.put("41", "Pickup card (lost card)");
        responses.put("43", "Pickup card (stolen card)");
        responses.put("51", "Insufficient funds");
        responses.put("52", "No checking account");
        responses.put("53", "No savings account");
        responses.put("54", "Expired card");
        responses.put("55", "Incorrect PIN");
        responses.put("57", "Transaction not permitted to cardholder");
        responses.put("58", "Transaction not allowed at terminal");
        responses.put("59", "Suspected fraud");
        responses.put("61", "Activity amount limit exceeded");
        responses.put("62", "Restricted card ");
        responses.put("63", "Security violation");
        responses.put("65", "Activity count limit exceeded");
        responses.put("68", "Response received too late");
        responses.put("75", "Allowable number of PIN-entry tries exceeded");
        responses.put("76", "Unable to locate previous message");
        responses.put("77", "Previous message located for a repeat or reversal");
        responses.put("78", "Blocked, card not activated");
        responses.put("80", "Visa transactions: credit issuer unavailable");
        responses.put("81", "PIN cryptographic error found");
        responses.put("82", "Negative CAM, dCVV, iCVV, or CVV");
        responses.put("83", "Unable to verify PIN");
        responses.put("85", "No reason to decline a request ");
        responses.put("91", "Issuer unavailable ");
        responses.put("92", "Destination cannot be found for routing");
        responses.put("93", "Transaction cannot be completed");
        responses.put("94", "Duplicate Transmission");
        responses.put("95", "Reconcile error");
        responses.put("96", "System malfunction");
        responses.put("B1", "Surcharge amount not permitted");
        responses.put("N0", "Force STIP");
        responses.put("N3", "Cash service not available");
        responses.put("N4", "Cashback request exceeds issuer limit");
        responses.put("N7", "Decline for CVV2 failure");
        responses.put("P2", "Invalid biller information");
        responses.put("P5", "PIN Change/Unblock request declined");
        responses.put("P6", "Unsafe PIN");
        responses.put("Q1", "Card Authentication failed");
        responses.put("R0", "Stop Payment Order");
        responses.put("R1", "Revocation of Authorization Order");
        responses.put("R3", "Revocation of All Authorizations Order");
        responses.put("XA", "Forward to issuer");
        responses.put("XD", "Forward to issuer");
        responses.put("Z3", "Unable to go online");
        return responses;

    }
}
