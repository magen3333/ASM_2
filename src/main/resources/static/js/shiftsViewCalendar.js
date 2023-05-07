var row = 2;
var col = firstDayOfMonth;
window["shiftsAssigned"] = window["shiftsAssigned"].split(",");
for (var i = 1; i <= window["daysMonths"]; i++) //add the events
{
    document.write("<div id ='" + i + "' class='buttonsContainer'>");
    for (var j = 1; j <= window["shiftsNum"]; j++) {
    	if (window["userId"] !== window["shiftsAssigned"][i+j].split(" ")[1])
    		document.write("<label class='label label-info' id ='shiftDetail'>" + window["shiftsAssigned"][i+j] + "</label>");
    	else
    		document.write("<label class='label label-success' id ='shiftDetail'>" + window["shiftsAssigned"][i+j] + "</label>");
    }
    document.write("</div>");
    document.getElementById(i).style.gridColumn = col;
    document.getElementById(i).style.gridRow = row;

    col++;
    if (col > 7) {
        col = 1;
        row++;
    }
}


