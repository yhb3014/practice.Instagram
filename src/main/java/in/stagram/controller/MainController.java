package in.stagram.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import in.stagram.domain.Follow;
import in.stagram.domain.Heart;
import in.stagram.domain.PoCo;
import in.stagram.domain.Post;
import in.stagram.domain.Post_image;
import in.stagram.domain.User;
import in.stagram.service.CommentService;
import in.stagram.service.FollowService;
import in.stagram.service.Follow_requestService;
import in.stagram.service.HeartService;
import in.stagram.service.PostService;
import in.stagram.service.Post_imageService;
import in.stagram.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private Post_imageService post_imageService;
	@Autowired
	private FollowService followService;
	@Autowired
	private Follow_requestService follow_requestService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private HeartService heartService;
	
	@RequestMapping("/main")
	private String main_page(Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findByUserId(userId);
		
		List<Follow> follow_list = followService.findByFollowingId(u.getId()); // following한 사람 리스트
		List<Post> posting = postService.findByUserIdOrderByIdDesc(u.getId());
		
		for(Follow f : follow_list) {
			List<Post> post = postService.findByUserIdOrderByIdDesc(f.getFollower().getId());
			for(Post p : post) {
				posting.add(p);
			}
		}
		
		List<PoCo> poco = new ArrayList<>();
		for (Post po : posting) {
			PoCo p = new PoCo();
			p.setPostid(po.getId());
			p.setCnt(heartService.countByPostIdAndUserId(po.getId(), u.getId()));
			poco.add(p);
		}
		model.addAttribute("poco", poco);
		
		List<PoCo> likecnt = new ArrayList<>();
		for (Post po : posting) {
			PoCo p = new PoCo();
			p.setCnt(heartService.countByPostId(po.getId()));
			p.setPostid(po.getId());
			likecnt.add(p);
		}
		model.addAttribute("like_cnt", likecnt);
		
		List<PoCo> cmtcnt = new ArrayList<>();
		for (Post po : posting) {
			PoCo p = new PoCo();
			p.setPostid(po.getId());
			p.setCnt(commentService.countByPostId(po.getId()));
			cmtcnt.add(p);
		}
		model.addAttribute("cmt_cnt", cmtcnt);
		Post p = new Post();
		Collections.sort(posting, p);
		
		model.addAttribute("user", u);
		model.addAttribute("posting", posting);
		model.addAttribute("img", post_imageService.findAll());
		model.addAttribute("psize", posting.size());
		return "/main";
	}
	
	@RequestMapping("/main/user/{id}")
	private String main_user(@PathVariable("id") int id, Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findById(id);
		
		model.addAttribute("page_id", id);
		model.addAttribute("page_userid", user.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("post", postService.findByUserIdOrderByIdDesc(id));
		model.addAttribute("post_image", post_imageService.findByGroupbyPostId());
		model.addAttribute("post_count", postService.countByUserId(id));
		model.addAttribute("follow", followService.find(id, userId));
		model.addAttribute("follower", followService.countByFollowerId(id));
		model.addAttribute("following", followService.countByFollowingId(id));
		return "/main/user";
	}
	
	@RequestMapping("/main/user/follower/{id}")
	private String follower(@PathVariable("id") int id, Model model) throws Exception{
		model.addAttribute("follower", followService.findByFollowerId(id));
		return "/main/user/follower";
	}
	
	@RequestMapping("/main/user/following/{id}")
	private String following(@PathVariable("id") int id, Model model) throws Exception{
		model.addAttribute("following", followService.findByFollowingId(id));
		return "/main/user/following";
	}
	
	@GetMapping("/main/user/update/{id}")
	private String update_user(@PathVariable("id") int id , Model model) throws Exception{
		model.addAttribute("user", userService.findById(id));
		return "/main/user/update";
	}
	
	@RequestMapping(value = "/main/user/image_insert")
	private String image_insert(HttpServletRequest request, @RequestParam("filename") MultipartFile mFile, Model model) throws Exception{
		String upload_path = "C:/Users/User/Desktop/instagram/src/main/resources/static/images";
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		String redirect_url = "redirect:/main/user/update/"+user.getId();
		try {
			if(user.getProfile_photo() != null) {
				File file = new File(upload_path + user.getProfile_photo());
				file.delete();
			} 
			mFile.transferTo(new File(upload_path + mFile.getOriginalFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userService.img_update(userId, mFile.getOriginalFilename());
		return redirect_url;
	}
	
	@RequestMapping(value="/main/user/info_update")
	private String profile_update(HttpServletRequest request, Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		String redirect_url = "redirect:/main/user/"+user.getId();
		
		String name = request.getParameter("name");
		String website = request.getParameter("website");
		String introduce = request.getParameter("intro");
		
		userService.profile_update(userId, name, website, introduce);
		return redirect_url;
	}
	
	@GetMapping("/main/upload")
	private String upload(Model model) throws Exception{
		return "/main/upload";
	}
	
	@RequestMapping("/main/posting")
	private String posting(HttpServletRequest request, MultipartHttpServletRequest mtfRequest, Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		String path = "C:/Users/User/Desktop/instagram/src/main/resources/static/images/"+userId;
		File file = new File(path);
		
		if(!file.exists()) {
				file.mkdirs();
		}
		
		Post post = new Post();
		
		String u = request.getParameter("userid");
		int user_id = Integer.parseInt(u);
		User user = userService.findById(user_id);
		
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		
		post.setDescription(description);
		post.setLocation(location);
		post.setUser(user);
		
		post.setId(postService.save(post));
		postService.flush();
		
		List<MultipartFile> fileList = mtfRequest.getFiles("files");
		for (MultipartFile f : fileList) {
			Post_image pi = new Post_image();
			
			String original_name = f.getOriginalFilename();
			pi.setFileOriname(original_name);
			
			String newFile_name = tempfilename(original_name, f.getBytes(), path);
			pi.setFilename(newFile_name);
			pi.setPostId(post.getId());
			
			post_imageService.save(pi);
		}
		return "redirect:/main";
	}
	
	// 임의의 파일이름 생성하는 메소드
	private String tempfilename(String originalName, byte[] fileData, String path) throws Exception{
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		File target = new File(path, savedName);

		FileCopyUtils.copy(fileData, target);
		return savedName;
	}
	
	@RequestMapping("/main/recommend")
	private String recommend(Model model) throws Exception{
		model.addAttribute("post9", postService.findByPostlimit9());
		model.addAttribute("post_image", post_imageService.findByGroupbyPostId());
		return "/main/recommend";
	}
	
	@RequestMapping(value = "main/search")
	private String search(@RequestParam("word") String word, Model model) throws Exception{
		model.addAttribute("find_user", userService.findByUserIdContains(word));
		model.addAttribute("ucnt", userService.countByUserIdContains(word));
		model.addAttribute("word", word);
		
		return "main/search";
	}
	
	@RequestMapping(value = "main/post/{id}")
	private String post(@PathVariable("id") int id, Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		
		model.addAttribute("postuserid", postService.findById(id).getUser().getId());
		model.addAttribute("p", postService.findById(id));
		model.addAttribute("img", post_imageService.findBypostId(id));
		model.addAttribute("heart_cnt", heartService.countByPostId(id));
		model.addAttribute("cnt", heartService.countByPostIdAndUserId(user.getId(), id));
		return "main/post";
	}
	
	@GetMapping("/main/user/secret_user")
	private String secret_user(Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		
		model.addAttribute("id", user.getId());
		return "/main/user/secret_user";
	}
	
	@RequestMapping("main/heart")
	private String heart(Model model) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		List<Post> Lpost = postService.findByUserUserId(userId);
		
		List<Heart> Lheart = new ArrayList<>();
		
		for(Post p : Lpost) {
			List<Heart> lh = heartService.findByPostId(p.getId());
			for(Heart hh : lh) {
				Lheart.add(hh);
			}
		}
		
		int followcount = follow_requestService.countByReceiveId(user.getId());
		model.addAttribute("followcount", followcount);
		
		Heart he = new Heart();
		Collections.sort(Lheart, he);
		model.addAttribute("hearts", Lheart);
		model.addAttribute("post_image", post_imageService.findByGroupbyPostId());
		
		return "main/heart";
	}
	
	@RequestMapping("/main/delete_post")
	public String delete_post(HttpServletRequest request, Model model) throws Exception{
		String pid = request.getParameter("postid");
		int postid = Integer.parseInt(pid);
		
		commentService.deleteByPostId(postid);
		postService.deleteById(postid);
		heartService.deleteByPostId(postid);
		post_imageService.deleteByPostId(postid);
		return "redirect:/main";
	}
}
