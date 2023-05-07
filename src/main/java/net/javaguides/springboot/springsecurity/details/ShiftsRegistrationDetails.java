package net.javaguides.springboot.springsecurity.details;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ShiftsRegistrationDetails {
	private static boolean isShiftsRegistrationOpen = false;
	private static int year = 2020; // for open registration
	private static int month = 8; // for open registration
	private static int numberOfShifts = 3; // for open registration
	
	
	public static int getNumberOfShifts() {
		return numberOfShifts;
	}
	public static void setNumberOfShifts(int numberOfShifts) {
		ShiftsRegistrationDetails.numberOfShifts = numberOfShifts;
	}
	public static boolean isShiftsRegistrationOpen() {
		return isShiftsRegistrationOpen;
	}
	public static void setShiftsRegistrationOpen(boolean isShiftsRegistrationOpen) {
		ShiftsRegistrationDetails.isShiftsRegistrationOpen = isShiftsRegistrationOpen;
	}
	public static int getYear() {
		return year;
	}
	public static void setYear(int year) {
		ShiftsRegistrationDetails.year = year;
	}
	public static int getMonth() {
		return month;
	}
	public static void setMonth(int month) {
		ShiftsRegistrationDetails.month = month;
	}
	
	
	
}
