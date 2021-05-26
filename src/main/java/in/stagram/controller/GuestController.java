package in.stagram.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/guest/pswd")
	public String pswd_find(Model model) throws Exception{
		return "/guest/pswd";
	}
	
	@RequestMapping("/guest/find_pswd")
	public String find_pswd(HttpServletRequest request, Model model) throws Exception{
		String userid = request.getParameter("userId");
		String phone = request.getParameter("phone");
		
		if(userService.user_exist(userid, phone)) {
			HttpSession session = request.getSession();
			session.setAttribute("userid", userid);
			return "redirect:/guest/change_pswd";
		}
		return "/guest/pswd?error";
	}
	
	@RequestMapping("/guest/change_pswd")
	public String change_pswd(Model model) throws Exception{
		return "/guest/change_pswd";
	}
	
	@RequestMapping("/guest/change_pswd_correct")
	public String change_pswd_correct(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		String pswd = request.getParameter("newpswd");
		
		userService.pswd_update(pswd, userid);
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/guest/login";
	}
}
