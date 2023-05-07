package net.javaguides.springboot.springsecurity.details;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CompanyDetails {
	private static String companyName = " Gas Station - Yellow paz";
	private static String companyCounry = "Israel";
	private static String companyCity = "Ashkelon";
	private static String companyStreet = "Bialik 12 ";
	

	@ModelAttribute("companyName")
	public static String getCompanyName() {
		return companyName;
	}
	public static void setCompanyName(String companyName) {
		CompanyDetails.companyName = companyName;
	}
	
	@ModelAttribute("companyCountry")
	public static String getCompanyCounry() {
		return companyCounry;
	}
	public static void setCompanyCounry(String companyCounry) {
		CompanyDetails.companyCounry = companyCounry;
	}
	
	@ModelAttribute("companyCity")
	public static String getCompanyCity() {
		return companyCity;
	}
	public static void setCompanyCity(String companyCity) {
		CompanyDetails.companyCity = companyCity;
	}
	
	@ModelAttribute("companyStreet")
	public static String getCompanyStreet() {
		return companyStreet;
	}
	public static void setCompanyStreet(String companyStreet) {
		CompanyDetails.companyStreet = companyStreet;
	}
}
