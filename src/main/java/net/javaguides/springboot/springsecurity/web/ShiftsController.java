package net.javaguides.springboot.springsecurity.web;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.springsecurity.details.ShiftsRegistrationDetails;
import net.javaguides.springboot.springsecurity.model.GeneratedShifts;
import net.javaguides.springboot.springsecurity.model.Shifts;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.repository.GeneratedShiftsRepository;
import net.javaguides.springboot.springsecurity.repository.ShiftsRepository;
import net.javaguides.springboot.springsecurity.service.UserService;

@Controller
@RequestMapping("/shifts")
public class ShiftsController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShiftsRepository shiftsRepository;

	@Autowired
	private GeneratedShiftsRepository generatedShiftsRepository;

	@ModelAttribute("userObject")
	public User getUserObject(Principal principal) {
		User usr = (User) userService.findByEmail(principal.getName());
		return usr;
	}

	@ModelAttribute("pageId")
	public String getPageId() {
		return "shifts";
	}

	@GetMapping("/registration")
	public String showShiftsRegistrationPage(Model model, @ModelAttribute("userObject") User user) {

		int year = ShiftsRegistrationDetails.getYear();
		int month = ShiftsRegistrationDetails.getMonth();

		model.addAttribute("subPageId", "registration");
		model.addAttribute("numberOfShiftsPerDay", ShiftsRegistrationDetails.getNumberOfShifts());
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("isRegistrationOpen", ShiftsRegistrationDetails.isShiftsRegistrationOpen());

		LocalDate date = LocalDate.of(year, month, 1);
		model.addAttribute("daysInMonth", date.lengthOfMonth());

		Shifts existingShifts = shiftsRepository.findByEmailAndYearAndMonth(user.getEmail(), year, month);
		String shiftsStringResult;
		if (existingShifts != null)
			shiftsStringResult = existingShifts.getShifts();
		else
			shiftsStringResult = "null";
		model.addAttribute("shiftsAssigned", shiftsStringResult);

		return "index";
	}

	@RequestMapping("/view/{year}/{month}")
	public String showShiftsViewPage(Model model, @PathVariable int year, @PathVariable int month) {
			
		LocalDate date = LocalDate.of(year, month, 1);

		model.addAttribute("subPageId", "shiftsView");
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("daysInMonth", date.lengthOfMonth());

		GeneratedShifts existingShifts = generatedShiftsRepository.findByYearAndMonth(year, month);
		String shiftsStringResult;
		if (existingShifts != null) {
			shiftsStringResult = castShiftsListFromEmailToNameAndId(existingShifts.getShifts());
			model.addAttribute("numberOfShiftsPerDay", existingShifts.getNumberOfShiftsPerDay());
		} else
			shiftsStringResult = "null";
		model.addAttribute("shiftsAssigned", shiftsStringResult);

		return "index";
	}
	
	@GetMapping("/view")
	public String showCurrentMonthShiftsCalendar(@ModelAttribute("todayYear") int year, @ModelAttribute("todayMonth") int month)
	{
		return "redirect:/shifts/view/" + year + "/" + month;
	}
	
	@RequestMapping("/registration/{shifts}")
	public String registerUserShifts(@PathVariable String shifts, @ModelAttribute("userObject") User user) {
		try {

			Shifts existingShifts = shiftsRepository.findByEmailAndYearAndMonth(user.getEmail(),
					ShiftsRegistrationDetails.getYear(), ShiftsRegistrationDetails.getMonth());
			if (existingShifts != null)
				shiftsRepository.delete(existingShifts);

			shiftsRepository.save(new Shifts(user.getEmail(), ShiftsRegistrationDetails.getYear(),
					ShiftsRegistrationDetails.getMonth(), shifts));
			return "redirect:/shifts/registration?success";
		} catch (Exception e) {
			return "redirect:/shifts/registration?error";
		}
	}

	//Method calculate the users "Name (ID)" for each shift;
	private String castShiftsListFromEmailToNameAndId(String shifts) {
		String[] shiftsArray = shifts.split(",");
		
		//The first User:
		User user = userService.findByEmail(shiftsArray[0]);
		String usersDetails = "";
		if (user != null)
			usersDetails = user.getFirstName() + " (" + user.getId() + ")";
		else //For deleted users:
			usersDetails = shiftsArray[0];
		
		
		for (int i = 1; i < shiftsArray.length; i++) {
			user = userService.findByEmail(shiftsArray[i]);
			if (user != null)
				usersDetails += "," + user.getFirstName() + " (" + user.getId() + ")";
			else //For deleted users:
				usersDetails += "," + shiftsArray[i];
		}
		return usersDetails;
	}

}
