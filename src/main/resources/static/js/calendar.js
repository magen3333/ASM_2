
monthsNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
weekDaysNames = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

var firstDayOfMonth = new Date(window["year"] + "-" + window["month"] + "-01").getDay() + 1; //return 1-7
var lastDayOfMonth = new Date(window["year"] + "-" + window["month"] + "-" + window["daysMonths"]).getDay() + 1; //return 1-7

for (var i = 0; i < weekDaysNames.length; i++) //print the days name
{
    document.write("<span class='day-name'>" + weekDaysNames[i] + "</span>");
}

for (var i = 1; i < firstDayOfMonth; i++) //print the disabled days of the previous month
{
    document.write("<div class='day day--disabled'></div>");
}

for (var i = 1; i <= window["daysMonths"]; i++) //print the current month days date
{
    document.write("<div class='day'>" + i + "</div>");
}

for (var i = 1; i <= 7 - lastDayOfMonth; i++) //print the disabled days of the next month
{
    document.write("<div class='day day--disabled'></div>");
}

document.getElementById("year").innerHTML = year; //set the year
document.getElementById("monthTitle").innerHTML = monthsNames[month - 1]; //set the year








