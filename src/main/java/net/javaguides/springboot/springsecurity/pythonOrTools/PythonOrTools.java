package net.javaguides.springboot.springsecurity.pythonOrTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import net.javaguides.springboot.springsecurity.details.ShiftsRegistrationDetails;
import net.javaguides.springboot.springsecurity.model.GeneratedShifts;
import net.javaguides.springboot.springsecurity.model.Shifts;

public class PythonOrTools {

	public String generateShifts(int month, int numberOfShifts, List<Shifts> usersShiftsData)
			throws IOException, InterruptedException {

		LocalDate date = LocalDate.of(ShiftsRegistrationDetails.getYear(), month, 1);
		
		int numberOfUsers = usersShiftsData.size();
		int numberOfDays = date.lengthOfMonth();

		// Transport users shifts to string
		String shifts = usersShiftsData.get(0).getShifts();
		for (int i = 1; i < numberOfUsers; i++) {
			shifts += " " + usersShiftsData.get(i).getShifts();
		}

		String currentDirectory = System.getProperty("user.dir");
		ProcessBuilder pb = new ProcessBuilder("C:\\Users\\SIORZ90\\AppData\\Local\\Programs\\Python\\Python39\\python.exe",
				"C:\\Users\\SIORZ90\\Documents\\workspace-spring-tool-suite-4-4.12.0.RELEASE\\ASM_2\\src\\main\\java\\net\\javaguides\\springboot\\python\\or_tools.py",
				Integer.toString(numberOfUsers), Integer.toString(numberOfShifts), Integer.toString(numberOfDays),
				shifts);

		Process p = pb.start();

		String s;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		// System.out.println("Here is the standard output of the command:\n");
		String shiftsGenerated = "";
		while ((s = stdInput.readLine()) != null) {
			if (shiftsGenerated == "")
				shiftsGenerated = usersShiftsData.get(Integer.parseInt(s)).getEmail();
			else
				shiftsGenerated += "," + usersShiftsData.get(Integer.parseInt(s)).getEmail();
		}

	
		// read any errors from the attempted command
		// System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);

		}
		return shiftsGenerated;
	}
}
