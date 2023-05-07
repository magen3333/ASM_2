package net.javaguides.springboot.springsecurity.web;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.service.UserService;


@Controller
public class MainController {

    @Autowired
    private UserService userService;
    
    
	@GetMapping("/")
	public String root(Model model, Principal principal) {
		return "redirect:/shifts/view";
	}
	
	@GetMapping("/cal")
	public String cal(Model model, Principal principal) {
		User usr = (User) userService.findByEmail(principal.getName());
		model.addAttribute("userObject", usr);
		return 	"calendar";
	}
	
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}

	@GetMapping("/about")
	public String about(Model model, Principal principal) {
		User usr = (User) userService.findByEmail(principal.getName());
		model.addAttribute("userObject", usr);
		model.addAttribute("pageId", "about");
		return "index";
	}
	
}
