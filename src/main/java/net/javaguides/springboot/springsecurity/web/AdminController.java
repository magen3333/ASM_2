package net.javaguides.springboot.springsecurity.web;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.springsecurity.details.CompanyDetails;
import net.javaguides.springboot.springsecurity.details.ShiftsManagerDetails;
import net.javaguides.springboot.springsecurity.details.ShiftsRegistrationDetails;
import net.javaguides.springboot.springsecurity.model.GeneratedShifts;
import net.javaguides.springboot.springsecurity.model.Shifts;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.pythonOrTools.PythonOrTools;
import net.javaguides.springboot.springsecurity.repository.GeneratedShiftsRepository;
import net.javaguides.springboot.springsecurity.repository.ShiftsRepository;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.setting.GlobalSetting;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public ShiftsRepository shiftsRepository;

	@Autowired
	public GeneratedShiftsRepository generatedShiftsRepository;

	@ModelAttribute("userObject")
	public User getUserObject(Principal principal) {
		User usr = (User) userService.findByEmail(principal.getName());
		return usr;
	}

	@ModelAttribute("pageId")
	public String getPageId() {
		return "admin";
	}

	@ModelAttribute("minimalRegistrationDate")
	public String minimalRegistrationDate(@ModelAttribute("todayYear") int year,
			@ModelAttribute("todayMonth") int month) {
		month++;
		if (month == 13) {
			month = 1;
			year++;
		}
		return year + "-" + (month < 10 ? "0" : "") + month;
	}

	@ModelAttribute("percentOfUsersRegisteredTheirShifts")
	public int getPercentOfUsersRegisteredTheirShifts() {
		return (int) ((100.0 * shiftsRepository
				.findByYearAndMonth(ShiftsRegistrationDetails.getYear(), ShiftsRegistrationDetails.getMonth()).size()
				/ userRepository.findAll().size()));
	}

	@ModelAttribute("isRegistrationOpen")
	public boolean isRegistrationOpen() {
		return ShiftsRegistrationDetails.isShiftsRegistrationOpen();
	}

	@ModelAttribute("users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping
	public String showAdminPage(Model model) {
		return "index";
	}

	@RequestMapping("/delete/{email}")
	public String DeleteUser(@PathVariable String email) {
		User userToDelete = userRepository.findByEmail(email);
		if ((userToDelete != null) && (!userToDelete.checkIfUserHasRole("Admin")))
			userRepository.delete(userToDelete);
		return "redirect:/admin?successfuly&deleted";
	}

	@RequestMapping("/generate")
	public String generateShifts() {

//		Shifts Generate
		try {

			PythonOrTools pot = new PythonOrTools();
			String shiftsGenerated = pot.generateShifts(ShiftsRegistrationDetails.getMonth(),
					ShiftsRegistrationDetails.getNumberOfShifts(), shiftsRepository.findByYearAndMonth(
							ShiftsRegistrationDetails.getYear(), ShiftsRegistrationDetails.getMonth()));

			GeneratedShifts existingGeneratedShifts = generatedShiftsRepository
					.findByYearAndMonth(ShiftsRegistrationDetails.getYear(), ShiftsRegistrationDetails.getMonth());
			if (existingGeneratedShifts != null)
				generatedShiftsRepository.delete(existingGeneratedShifts);

			GeneratedShifts shiftsGeneratedDto = new GeneratedShifts(ShiftsRegistrationDetails.getYear(),
					ShiftsRegistrationDetails.getMonth(), ShiftsRegistrationDetails.getNumberOfShifts(),shiftsGenerated
					);
			generatedShiftsRepository.save(shiftsGeneratedDto);

			ShiftsRegistrationDetails.setShiftsRegistrationOpen(false);

			return "redirect:/admin?successfuly&generated";

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "redirect:/admin?error";
	}

	@RequestMapping("/save/{companyName}/{companyCity}/{companyStreet}/{companyCountry}/{managerName}/{managerPhone}/{managerEmail}")
	public String saveCompanyAndMangerDetails(@PathVariable String companyName, @PathVariable String companyCity,
			@PathVariable String companyStreet, @PathVariable String companyCountry, @PathVariable String managerName,
			@PathVariable String managerPhone, @PathVariable String managerEmail) {

		CompanyDetails.setCompanyName(companyName);
		CompanyDetails.setCompanyCounry(companyCountry);
		CompanyDetails.setCompanyCity(companyCity);
		CompanyDetails.setCompanyStreet(companyStreet);

		ShiftsManagerDetails.setManagerFullName(managerName);
		ShiftsManagerDetails.setManagerEmail(managerEmail);
		ShiftsManagerDetails.setManagerPhoneNumber(managerPhone);

		return "redirect:/admin?successfuly&saved";
	}

	@RequestMapping("/registration/close")
	public String registrationClose() {
		ShiftsRegistrationDetails.setShiftsRegistrationOpen(false);
		return "redirect:/admin?successfuly&registrationClosed";
	}

	@Transactional
	@RequestMapping(value = { "/registration/open/{date}/{numberOfShifts}",
			"/registration/{confirm}/{date}/{numberOfShifts}" })
	public String registrationOpen(Model model, @PathVariable Optional<Boolean> confirm, @PathVariable String date,
			@PathVariable int numberOfShifts) {
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		
		if (confirm.isPresent()) {
			shiftsRepository.deleteByYearAndMonth(year,month);
			return openRegistration(year,month,numberOfShifts);
		} else {

			List<Shifts> shifts = shiftsRepository.findByYearAndMonth(year, month);
			int shiftsLen = LocalDate.of(year,month,1).lengthOfMonth() * numberOfShifts * 2 - 1;

			if (shifts.size() != 0 && shifts.get(0).getShifts().length() != shiftsLen) {
				return "redirect:/admin?alert&shiftsNum=" + numberOfShifts + "&open_registration=" + year + "-"
						+ (month < 10 ? "0" : "") + month;
			} else {
				return openRegistration(year,month,numberOfShifts);
			}
		}
	}

	public String openRegistration(int year, int month, int numberOfShifts) {
		try {

			if (numberOfShifts > 4 || numberOfShifts < 1)
				return "redirect:/admin?error";

			ShiftsRegistrationDetails.setYear(year);
			ShiftsRegistrationDetails.setMonth(month);
			ShiftsRegistrationDetails.setNumberOfShifts(numberOfShifts);
			ShiftsRegistrationDetails.setShiftsRegistrationOpen(true);

			return "redirect:/admin?successfuly&registrationOpened";
		} catch (Exception e) {
			return "redirect:/admin?error";
		}
	}
}
