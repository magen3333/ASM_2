function onSubmitCompanyAndManagerDetails() {
	var companyName = document.getElementById("companyName").value;
	var city = document.getElementById("city").value;
	var street = document.getElementById("street").value;
	var country = document.getElementById("country").value;
	var managerName = document.getElementById("managerName").value;
	var managerPhone = document.getElementById("managerPhone").value;
	var managerEmail = document.getElementById("managerEmail").value;

	document.getElementById("companyAndManagerDetailsForm").action = 
		"/admin/save/" + companyName + "/" + city + "/" + street + "/" + country + "/" +
		managerName + "/" + managerPhone + "/" + managerEmail + "/";
}

function openRegistration() {
	document.getElementById("openRegistration").action = "/admin/registration/open/" + document.getElementById("dateRegistrationInput").value + "/" + document.getElementById("shiftsNumber").value;
}
