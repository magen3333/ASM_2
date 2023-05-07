package net.javaguides.springboot.springsecurity.web;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.setting.GlobalSetting;


@Controller  // configuring controllers for functions method
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
	@GetMapping
	public String showInfoPage(Model model) {
		return "index";
	}
	
	@ModelAttribute("userObject")
	public User getUserObject(Principal principal) {
		User usr = (User) userService.findByEmail(principal.getName());
		return usr;
	}

	@ModelAttribute("pageId")
	public String getPageId() {
		return "info";
	}
	
	@RequestMapping("/password/{oldPass}/{pass}")
	public String updatePassword(Model model, Principal principal,@PathVariable String oldPass, @PathVariable String pass) {
		
		if (pass.length() < 6)
			return "redirect:/info?error";
		
		User userToUpdate = userService.findByEmail(principal.getName());
		
	    if (!passwordEncoder.matches(oldPass,userToUpdate.getPassword())) {
			return "redirect:/info?error&wrongPass";
	    }

		//case everything is O.K:
		userToUpdate.setPassword(passwordEncoder.encode(pass));
		userService.update(userToUpdate);

		return "redirect:/info?successfuly&pass";
	}

}
