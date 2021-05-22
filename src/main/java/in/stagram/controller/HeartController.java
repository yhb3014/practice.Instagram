package in.stagram.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.stagram.domain.Post;
import in.stagram.domain.User;
import in.stagram.service.HeartService;
import in.stagram.service.PostService;
import in.stagram.service.UserService;

@Controller
public class HeartController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HeartService heartService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/like/view")
	@ResponseBody
	private Map<String, Object> like_view(Model model, int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		int cnt = heartService.countByPostIdAndUserId(id, u.getId());
		int total_cnt = heartService.countByPostId(id);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("cnt", cnt);
		m.put("total_cnt", total_cnt);
		
		return m;
	}
	
	@RequestMapping("/like/insert/{id}")
	@ResponseBody
	private int like_insert(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		Post p = postService.findById(id);
		
		heartService.save(p, u);
		return 1;
	}
	
	@RequestMapping("/like/delete/{id}")
	@ResponseBody
	private int like_delete(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		heartService.deleteByPostIdAndUserId(id, u.getId());
		return 1;
	}
}
