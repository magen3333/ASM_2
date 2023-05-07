var row = 2;
var col = firstDayOfMonth;
for (var i = 1; i <= window["daysMonths"]; i++) //add the events
{
    document.write("<div id ='" + i + "' class='buttonsContainer'>");
    for (var j = 1; j <= window["shiftsNum"]; j++) {
        var elementID = i + "." + j;
        document.write("<button id ='" + elementID + "' class='task task--notchosen' onclick='chosen(" + elementID + ") ' '>Shift " + j + "</button>");
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

//Shifts arrays:
shiftsArr = new Array(window["daysMonths"]);
for (var i = 0; i < shiftsArr.length; i++) {
    shiftsArr[i] = new Array(shiftsNum);
    for (var j = 0; j < shiftsNum; j++)
        shiftsArr[i][j] = 0;
}

//Case the user already have signed his shifts:
if (window["shiftsAssigned"] != "null") {
    window["shiftsAssigned"] = window["shiftsAssigned"].split(",");
	for (var i = 0; i<window["shiftsAssigned"].length;i++)
	{
		var day = parseInt(i/shiftsNum);
		var shift = i%shiftsNum;
		
		if (window["shiftsAssigned"][i] != shiftsArr[day][shift])
			chosen((day+1) + "." + (shift+1));
	}
}

function chosen(id) {
    var btn = document.getElementById(id);
    var dayAndShift = id.toString().split(".");
    var d = dayAndShift[0] - 1;
    var s = dayAndShift[1] - 1;
    if (shiftsArr[d][s] === 1) {
        btn.className = "task task--notchosen";
        shiftsArr[d][s] = 0;
    } else {
        btn.className = "task task--chosen";
        shiftsArr[d][s] = 1;
    }
}

function onSubmit() {
	document.getElementById("submitShifts").action = "/shifts/registration/" + shiftsArr;
}