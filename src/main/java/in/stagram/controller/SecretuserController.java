package in.stagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.stagram.service.UserService;

@Controller
public class SecretuserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/secret/view/{id}")
	@ResponseBody
	public boolean sercret_view(@PathVariable int id) throws Exception{
		boolean b = userService.IsSecret(id);
		return b;
	}
	
	@RequestMapping("/secret/insert/{id}")
	@ResponseBody
	public int sercret_insert(@PathVariable int id) throws Exception{
		userService.enable_user(id, 2); // 1 - 공개 계정, 2 - 비공개 계정
		return 1;
	}
	
	@RequestMapping("/secret/delete/{id}")
	@ResponseBody
	public int sercret_delete(@PathVariable int id) throws Exception{
		userService.enable_user(id, 1);
		return 1;
	}
}
