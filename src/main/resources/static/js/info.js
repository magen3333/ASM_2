function changePasswordValidate ()
{

	var oldPass = document.changePassword.oldPass.value;
	var newPass = document.changePassword.newPass.value;
	var confirmNewPass = document.changePassword.confirmNewPass.value;

	if (newPass.length < 6)
	{
		alert("Password is too short. You need to choose password with minimum 6 characters.");
		document.changePassword.confirmNewPass.value = "";
		document.changePassword.newPass.value = "";
		return false;
	}
	
	if (newPass !== confirmNewPass)
	{
		alert("The new password is not the same as the confirm password");
		document.changePassword.confirmNewPass.value = "";
		document.changePassword.newPass.value = "";
		return false;
	}

	document.changePassword.action = "/info/password/" + oldPass +  "/" + newPass;
	return true;
}