/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public final class Dates {

    Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static final Logger LOG = LoggerFactory.getLogger(Dates.class);

    public Dates() {
        this(new Date());
    }

    public Dates(final Date date) {
        this.date = date;
    }

    public Date months(int months) {
        java.util.Date now = new Date();
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(now);
        myCal.add(Calendar.MONTH, months);
        now = myCal.getTime();
        return now;
    }

    public String formatDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(2);
        int day = calendar.get(5);
        int year = calendar.get(1);
        String mt = String.valueOf(month + 1);
        String dy = String.valueOf(day);
        String yr = String.valueOf(year);
        int min = calendar.get(10);
        int sec = calendar.get(12);
        String mn = String.valueOf(min);
        String sc = String.valueOf(sec);
        if (Integer.parseInt(mt) < 10) {
            mt = "0" + mt;
        }
        if (Integer.parseInt(dy) < 10) {
            dy = "0" + dy;
        }
        String formatedDate = dy + mt + yr;
        LOG.info("Done formating date.");
        return formatedDate;
    }

    public String getStringDateTimeStamp() {
        LOG.info("INFO:Using CalendarDate.formatDate()...");
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(2);
        int day = calendar.get(5);
        int year = calendar.get(1);
        String mt = String.valueOf(month + 1);
        String dy = String.valueOf(day);
        String yr = String.valueOf(year);
        int hrs = calendar.get(10);
        int min = calendar.get(12);
        int sec = calendar.get(13);
        String hr = String.valueOf(hrs);
        String mn = String.valueOf(min);
        String sc = String.valueOf(sec);
        if (Integer.parseInt(mt) < 10) {
            mt = "0" + mt;
        }
        if (Integer.parseInt(dy) < 10) {
            dy = "0" + dy;
        }
        if (Integer.parseInt(hr) < 10) {
            hr = "0" + hr;
        }
        if (Integer.parseInt(mn) < 10) {
            mn = "0" + mn;
        }
        if (Integer.parseInt(sc) < 10) {
            sc = "0" + sc;
        }
        String formatedDate = dy + "-" + mt + "-" + yr + " " + hr + ":" + mn + ":" + sc;
        LOG.info("Done formating date.");
        return formatedDate;
    }

    public String getDateTimeStamp() {
        LOG.info("INFO:Using CalendarDate.formatDate()...");
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(2);
        int day = calendar.get(5);
        int year = calendar.get(1);
        String mt = String.valueOf(month + 1);
        String dy = String.valueOf(day);
        String yr = String.valueOf(year);
        int min = calendar.get(10);
        int sec = calendar.get(12);
        String mn = String.valueOf(min);
        String sc = String.valueOf(sec);
        if (Integer.parseInt(mt) < 10) {
            mt = "0" + mt;
        }
        if (Integer.parseInt(dy) < 10) {
            dy = "0" + dy;
        }
        if (Integer.parseInt(mn) < 10) {
            mn = "0" + mn;
        }
        if (Integer.parseInt(sc) < 10) {
            sc = "0" + sc;
        }
        String formatedDate = dy + mt + yr + mn + sc;
        LOG.info("Done formating date.");
        return formatedDate;
    }

    public String getTimeStamp() {
        GregorianCalendar calendar = new GregorianCalendar();
        int min = calendar.get(10);
        int sec = calendar.get(12);
        String mn = String.valueOf(min);
        String sc = String.valueOf(sec);
        if (Integer.parseInt(mn) < 10) {
            mn = "0" + mn;
        }
        if (Integer.parseInt(sc) < 10) {
            sc = "0" + sc;
        }
        String formatedTime = mn + ":" + sc;
        return formatedTime;
    }

    public boolean isAValidDate(String input) {
        boolean isValid;
        try {
            Date dDate = this.sdf.parse(input);
            int day = Integer.parseInt(input.substring(0, 2));
            int mon = Integer.parseInt(input.substring(3, 5));
            int year = Integer.parseInt(input.substring(6, 10));
            isValid = day > 28 && mon == 2 ? false : (day > 30 && mon != 4 && mon != 6 && mon != 9 && mon != 11 ? false : (mon > 12 ? false : day <= 31));
        } catch (NumberFormatException | ParseException e) {
            isValid = false;
        }
        return isValid;
    }

    public String textDate() {
        LOG.info("INFO: Using CalendarDate.textDate()...");
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(2);
        int day = calendar.get(5);
        int year = calendar.get(1);
        String mt = String.valueOf(month + 1);
        String dy = String.valueOf(day);
        String yr = String.valueOf(year);
        if (Integer.parseInt(mt) < 10) {
            mt = "0" + mt;
        }
        if (Integer.parseInt(dy) < 10) {
            dy = "0" + dy;
        }
        String formatedDate = dy + "/" + mt + "/" + yr;
        LOG.info("Done formating date.");
        return formatedDate;
    }

    public java.sql.Date getSQLFormatedDate(Date tDate) {
        DateFormat formatter = DateFormat.getDateInstance(3, Locale.ENGLISH);
        java.sql.Date formatedDate = null;
        if (tDate != null) {
            String dDate = formatter.format(tDate);
            String strDate = dDate.replaceAll("/", "-");
            int year = Integer.parseInt(strDate.substring(strDate.lastIndexOf("-") + 1, strDate.length()));
            int mon = Integer.parseInt(strDate.substring(0, strDate.indexOf("-")));
            int day = Integer.parseInt(strDate.substring(strDate.indexOf("-") + 1, strDate.lastIndexOf("-")));
            String strDay;
            String strMon;
            if (year < 1000) {
                year += 2000;
            }
            strMon = mon < 10 ? "0" + Integer.toString(mon) : Integer.toString(mon);
            strDay = day < 10 ? "0" + Integer.toString(day) : Integer.toString(day);
            strDate = strDay + "-" + strMon + "-" + Integer.toString(year);
            try {
                formatedDate = new java.sql.Date(this.sdf.parse(strDate).getTime());
            } catch (ParseException ee) {
                // empty catch block
            }
        }
        return formatedDate;
    }

    public boolean isOpenDateExceedBOD(String openDate, String BODDate) {
        boolean isRight = false;
        LOG.info("INFO : Date1 = " + openDate + " Date2 = " + BODDate);
        try {
            String day1 = openDate.substring(6, 8);
            String mon1 = openDate.substring(4, 6);
            String year1 = openDate.substring(0, 4);
            int dayAdder = Integer.parseInt(day1);
            int mon = Integer.parseInt(mon1);
            int year = Integer.parseInt(year1);
            String temp = openDate;
            int yearInt = Integer.parseInt(BODDate.substring(0, 4));
            int monInt = Integer.parseInt(BODDate.substring(4, 6));
            int dayInt = Integer.parseInt(BODDate.substring(6, 8));
            if (Integer.parseInt(temp.substring(0, 4)) > yearInt) {
                isRight = true;
            } else if (Integer.parseInt(temp.substring(0, 4)) >= yearInt) {
                if (Integer.parseInt(temp.substring(4, 6)) > monInt) {
                    isRight = true;
                } else if (Integer.parseInt(mon1) == monInt && dayAdder > dayInt) {
                    isRight = true;
                }
            } else {
                isRight = false;
            }
        } catch (NumberFormatException er) {
            isRight = false;
            LOG.info("WARN : Error comparing dates -> " + er);
        }
        return isRight;
    }

    public boolean isDateExceedOther(String firstDate, String secondDate) {
        boolean isRight = false;
        try {
            String day1 = firstDate.substring(0, 2);
            String mon1 = firstDate.substring(3, 5);
            String year1 = firstDate.substring(6, 10);
            int dayAdder = Integer.parseInt(day1);
            int mon = Integer.parseInt(mon1);
            int year = Integer.parseInt(year1);
            String temp = firstDate;
            int yearInt = Integer.parseInt(secondDate.substring(6, 10));
            int monInt = Integer.parseInt(secondDate.substring(3, 5));
            int dayInt = Integer.parseInt(secondDate.substring(0, 2));
            if (Integer.parseInt(temp.substring(6, 10)) > yearInt) {
                isRight = true;
            } else if (Integer.parseInt(temp.substring(6, 10)) >= yearInt) {
                if (Integer.parseInt(temp.substring(3, 5)) > monInt) {
                    isRight = true;
                } else if (Integer.parseInt(mon1) == monInt && dayAdder > dayInt) {
                    isRight = true;
                }
            } else {
                isRight = false;
            }
        } catch (NumberFormatException er) {
            isRight = false;
            LOG.info("WARN : Error comparing dates -> " + er);
        }
        return isRight;
    }

    public boolean isDateBetween(String date1, String date2, String testDate) {
        boolean isYes = false;
        return isYes;
    }

    public java.sql.Date getCurrentSQLDate() {
        Date transDateObj;
        java.sql.Date valDate = null;
        try {
            transDateObj = this.sdf.parse(this.textDate().replaceAll("/", "-"));
            valDate = this.getSQLFormatedDate(transDateObj);
        } catch (ParseException ee) {
            LOG.info("error getting current Date-> ", ee);
        }
        return valDate;
    }

    public int getDayDifference(String strFirstDate, String strSecondDate) {
        long dayDiffMills = this.getDateDiffrenceMillis(strFirstDate, strSecondDate);
        return (int) (Math.abs(dayDiffMills) / 86400000);
    }

    public int getMonthDifference(String strFirstDate, String strSecondDate) {
        long yearDiffMills = this.getDateDiffrenceMillis(strFirstDate, strSecondDate);
        return (int) (Math.abs(yearDiffMills) * 12 / 1471228928);
    }

    public int getYearDifference(String strFirstDate, String strSecondDate) {
        long yearDiffMills = this.getDateDiffrenceMillis(strFirstDate, strSecondDate);
        return  (int) (Math.abs(yearDiffMills) / 1471228928);
    }

    public int getYearDifference(Date firstDate, Date secondDate) {
        int yearDifference = 0;
        if (firstDate != null && secondDate != null) {
            long yearDiffMills = firstDate.getTime() - secondDate.getTime();
            yearDifference = (int) (Math.abs(yearDiffMills) / 1471228928);
        }
        return yearDifference;
    }

    public long getDateDiffrenceMillis(String strFirstDate, String strSecondDate) {
        long dateDifferencesMills = 0;
        try {
            Date firstDate = this.sdf.parse(strFirstDate);
            Date secondDate = this.sdf.parse(strSecondDate);
            dateDifferencesMills = firstDate.getTime() - secondDate.getTime();
        } catch (ParseException e) {
            LOG.info("WARNING:Error finding Date - days different :" + e);
        }
        return dateDifferencesMills;
    }

    public String getFirstDateOfMonth() {
        String d = format();
        return getFirstDateOfMonth(d);
    }

    public String getLastDateOfMonth() {
        String d = format();
        return this.getLastDateOfMonth(d);
    }

    public String getCurrentDate() {
        return this.sdf.format(new Date());
    }

    public String getFirstDateOfMonth(String input) {
        GregorianCalendar calendarDate1;
        String endDate = "";
        int lastDay ;
        if (input == null) {
            date = new Date();
            input = format();
        }
        input = input.replaceAll("/", "-");
        try {
            Date s1Date = this.sdf.parse(input);
            calendarDate1 = new GregorianCalendar();
            calendarDate1.setTime(s1Date);
            lastDay = calendarDate1.getActualMinimum(5);
            String prefix;
            prefix = lastDay < 10 ? "0" + Integer.toString(lastDay) : Integer.toString(lastDay);
            endDate = prefix + "-" + input.substring(3, input.length());
        } catch (ParseException er) {
            LOG.info("WARN:Error getting month last date ->" + er);
        }
        return endDate;
    }

    public String getLastDateOfMonth(String input) {
        GregorianCalendar calendarDate1;
        String endDate = "";
        int lastDay ;
        if (date == null) {
            date = new Date();
            input = format();
        }
        input = input.replaceAll("/", "-");
        try {
            Date s1Date = this.sdf.parse(input);
            calendarDate1 = new GregorianCalendar();
            calendarDate1.setTime(s1Date);
            lastDay = calendarDate1.getActualMaximum(5);
            String prefix;
            prefix = lastDay < 10 ? "0" + Integer.toString(lastDay) : Integer.toString(lastDay);
            endDate = prefix + "-" + input.substring(3, input.length());
        } catch (ParseException er) {
            LOG.info("WARN:Error getting month last date ->" + er);
        }
        return endDate;
    }

    public String addMonthToDate(String date, int month) {
        GregorianCalendar calendarDate ;
        String added = null;
        if (date == null) {
            added = null;
        } else {
            try {
                Date dDate = this.sdf.parse(date);
                calendarDate = new GregorianCalendar();
                calendarDate.setTime(dDate);
                calendarDate.add(2, month);
                dDate = calendarDate.getTime();
                added = this.sdf.format(dDate);
            } catch (ParseException er) {
                LOG.info("WARN:Error adding date ->" + er);
            }
        }
        return added;
    }

    public String addMonthToDate(Date dDate, int month) {
        GregorianCalendar calendarDate;
        String added = null;
        if (dDate == null) {
            added = null;
        } else {
            try {
                calendarDate = new GregorianCalendar();
                calendarDate.setTime(dDate);
                calendarDate.add(2, month);
                dDate = calendarDate.getTime();
                added = this.sdf.format(dDate);
            } catch (Exception er) {
                LOG.info("WARN:Error adding date ->" + er);
            }
        }
        return added;
    }

    public Date getDateAddByMonth(Date dDate, int month) {
        GregorianCalendar calendarDate ;
        Date added = null;
        if (dDate == null) {
            added = null;
        } else {
            try {
                calendarDate = new GregorianCalendar();
                calendarDate.setTime(dDate);
                calendarDate.add(2, month);
                added = calendarDate.getTime();
            } catch (Exception er) {
                LOG.info("WARN:Error adding date ->" + er);
            }
        }
        return added;
    }

    public Date addDayToDate(Date dDate, int day) {
        GregorianCalendar calendarDate;
        Date added = null;
        if (dDate == null) {
            added = null;
        } else {
            try {
                calendarDate = new GregorianCalendar();
                calendarDate.setTime(dDate);
                calendarDate.add(5, day);
                added = dDate = calendarDate.getTime();
            } catch (Exception er) {
                LOG.info("WARN:Error adding date ->" + er);
            }
        }
        return added;
    }

    public java.sql.Date toSQL(String strDate) {
        if (strDate == null) {
            strDate = this.sdf.format(new Date());
        }
        strDate = strDate.replaceAll("/", "-");
        Date inputDate = null;
        try {
            inputDate = this.sdf.parse(strDate);
        } catch (ParseException e) {
            LOG.info(" Error formating Date:" + e.getMessage());
        }
        return toSQL(inputDate);
    }

    public java.sql.Date toSQL(Date inputDate) {
        return this.getSQLFormatedDate(inputDate);
    }

    public java.sql.Date toSQL(long longDate) {
        Date inputDate = new Date();
        inputDate.setTime(longDate);
        return this.toSQL(inputDate);
    }

    public String format() {
        String formated = "";
        if (date == null) {
            date = new Date();
        }
        try {
            formated = this.sdf.format(date);
        } catch (Exception e) {
            LOG.info("WARNING:Error formating Date ->" + e.getMessage());
        }
        return formated;
    }

    public long toTime(String inputDate) {
        long inputTime = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
            String strDate = dateFormat.format(new Date());
            String transInputDate = inputDate + strDate.substring(10, strDate.length());
            inputTime = dateFormat.parse(transInputDate).getTime();
        } catch (ParseException ex) {
            LOG.info("Error getting datetime ->", ex);
        }
        return inputTime;
    }

    /**
     *
     * @return Long time timestamp
     */
    public long toTime() {
        String strDate = "";
        try {
            strDate = date == null ? this.sdf.format(new Date()) : this.sdf.format(date);
        } catch (Exception ex) {
            LOG.info("WARN : error occured -> " + ex);
        }
        return toTime(strDate);
    }
}
