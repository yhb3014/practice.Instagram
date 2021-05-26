package in.stagram.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.stagram.model.UserRegistrationModel;
import in.stagram.service.UserService;

@Controller
public class GuestController {

	@Autowired
	private UserService userService;
	
	@GetMapping({"/guest/login", "/"})
	public String login(Model model) throws Exception{
		return "/guest/login";
	}
	
	@GetMapping("/guest/register")
	public String register(UserRegistrationModel userModel, Model model) throws Exception{
		return "/guest/register";
	}
	
	@PostMapping("/guest/register")
	public String register(@Valid UserRegistrationModel userModel, BindingResult bindingResult) throws Exception{
		if(userService.hasErrors(userModel, bindingResult)) {
			return "/guest/register";
		}
		userService.save(userModel);
		return "redirect:/guest/login";
	}
}
