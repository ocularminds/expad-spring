package com.ocularminds.expad.model;

import java.io.Serializable;

public class CustomizedCard implements Serializable {

    private String id;
    private String name;
    private String dob;
    private String no;
    private byte[] photo;
    private byte[] signature;
    private String firstName;
    private String surname;
    private String middleName;
    private String sex;
    private String type;
    private String institute;
    private String date;
    private String position;
    private String department;
    private String faculty;
    private String session;

    public CustomizedCard() {
        this.no = "***";
        this.id = "NA";
        this.date = "";
        this.name = "STUDENT RECORD NOT FOUND";
    }

    public CustomizedCard(String id, String firstName, String middleName, String surname, String sex, String type, String institute, String date, String position, String department, String faculty, String session, String dob, String no, byte[] photo, byte[] signature) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.dob = dob;
        this.no = no;
        this.photo = photo;
        this.signature = signature;
        this.sex = sex;
        this.type = type;
        this.institute = institute;
        this.date = date;
        this.position = position;
        this.department = department;
        this.faculty = faculty;
        this.session = session;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.surname + " " + this.middleName + " " + this.firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNo() {
        return this.no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return this.faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getInstitute() {
        return this.institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof CustomizedCard) {
            return ((CustomizedCard) o).getId().equals(this.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() * 17 * this.name.hashCode();
    }
}
