package net.javaguides.springboot.springsecurity.setting;

import java.time.LocalDate;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.javaguides.springboot.springsecurity.details.CompanyDetails;
import net.javaguides.springboot.springsecurity.details.ShiftsManagerDetails;
import net.javaguides.springboot.springsecurity.details.ShiftsRegistrationDetails;

@ControllerAdvice
public class GlobalSetting {

	private static String title = "Advanced Shifts Management";
	
	@ModelAttribute("title")
	public String getTitle(Model model) {
		return title;
	}
	
	@ModelAttribute("todayMonth")
	public int getTodayMonth(Model model) {
		return LocalDate.now().getMonthValue();
	}

	@ModelAttribute("todayYear")
	public int getTodayYear(Model model) {
		return LocalDate.now().getYear();
	}

}
