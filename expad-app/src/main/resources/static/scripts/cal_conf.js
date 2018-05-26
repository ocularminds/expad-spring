
//Define calendar(s): addCalendar ("Unique Calendar Name", "Window title", "Form element's name", Form name")
addCalendar("Calendar1", "Select Date", "date_of_purchase", "assetrecords");
addCalendar("Calendar2", "Select Date", "depreciation_start_date", "assetrecords");
addCalendar("Calendar3", "Select Date", "effective_date", "assetdisposal");
addCalendar("Calendar4", "Select Date", "processing_date", "companydefaults");
addCalendar("Calendar5", "Select Date", "period_start_date", "companydefaults");
addCalendar("Calendar6", "Select Date", "dl_issue_date", "companydrivers");
addCalendar("Calendar7", "Select Date", "dl_expiry_date", "companydrivers");
addCalendar("Calendar8", "Select Date", "warrantyStartDate", "assetrecords");

// default settings for English
// Uncomment desired lines and modify its values
 setFont("verdana", 9);
 setWidth(90, 1, 15, 1);
// setColor("#cccccc", "#cccccc", "#ffffff", "#ffffff", "#333333", "#cccccc", "#333333");
// setFontColor("#333333", "#333333", "#333333", "#ffffff", "#333333");
 setFormat("dd-mm-yyyy");
 setSize(200, 200, -200, 16);

// setWeekDay(0);
// setMonthNames("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
// setDayNames("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
// setLinkNames("[Close]", "[Clear]");
