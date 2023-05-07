package net.javaguides.springboot.springsecurity.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.setting.GlobalSetting;
import net.javaguides.springboot.springsecurity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;
    
    @ModelAttribute("user") //New User Created
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
    
    @ModelAttribute("userObject") //The current logged User!
    public User userLogged(Principal principal) {
        return (User) userService.findByEmail(principal.getName());
    }
    @ModelAttribute("pageId")
    public String pageId() {
        return "registration";
    }


    @GetMapping("")
    public String showRegistrationForm() {
        return "index";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "index";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

}
