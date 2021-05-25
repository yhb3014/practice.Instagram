package in.stagram.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.stagram.domain.User;
import in.stagram.service.FollowService;
import in.stagram.service.Follow_requestService;
import in.stagram.service.UserService;

@Controller
public class FollowController {
	
	@Autowired
	private FollowService followService;
	@Autowired
	private UserService userService;
	@Autowired
	private Follow_requestService follow_requestService;
	
	
	@RequestMapping("/follow")
	public String follow(HttpServletRequest request, Model model) throws Exception{
		String l = request.getParameter("user_id");
		String p = request.getParameter("page_id");
		
		int login_id = Integer.parseInt(l);
		int page_id = Integer.parseInt(p);
		
		followService.save(login_id, page_id);
		
		String redirect_url = "redirect:/main/user/" + page_id;
		return redirect_url;
	}
	
	@RequestMapping("/unfollow")
	public String unfollow(HttpServletRequest request, Model model) throws Exception{
		String l = request.getParameter("user_id");
		String p = request.getParameter("page_id");
		
		int login_id = Integer.parseInt(l);
		int page_id = Integer.parseInt(p);
		
		followService.deleteByFollowingIdAndFollowerId(login_id, page_id);
		
		String redirect_url = "redirect:/main/user/" + page_id;
		return redirect_url;
	}
	
	@RequestMapping("/follow/view/{id}")
	@ResponseBody
	public Map<String, Object> follow_view(@PathVariable int id, Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		int follower = followService.countByFollowerId(id);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("booool", followService.find(id, userId));
		m.put("follower", follower);
		return m;
	}
	
	@RequestMapping("/follow/insert/{id}")
	@ResponseBody
	private int follow_insert(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		followService.save(u.getId(), id);
		return 1;
	}
	
	@RequestMapping("/follow/delete/{id}")
	@ResponseBody
	private int follow_delete(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		followService.deleteByFollowingIdAndFollowerId(id, u.getId());
		return 1;
	}
	
	@RequestMapping("/follow/request/view")
	@ResponseBody
	private Map<String, Object> follow_request_view() throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		int followcount = follow_requestService.countByReceiveId(u.getId());
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("followcnt", followcount);
		m.put("fr", follow_requestService.findByReceiveId(u.getId()));
		
		return m;
	}
	
	@RequestMapping("/follow/request/accept/{id}")
	@ResponseBody
	private User follow_request_accept(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		User followuser = userService.findById(id);
		
		follow_requestService.deleteByRequestIdAndReceiveId(id, u.getId());
		followService.save(id, u.getId());
		return followuser;
	}
	
	@RequestMapping("/follow/request/{id}")
	@ResponseBody
	private int follow_request(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		return follow_requestService.request_save(id, u.getId());
	}
}
