package in.stagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.stagram.domain.Comment;
import in.stagram.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	public CommentService commentService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<Comment> comment_list(Model model, int id) throws Exception{
		return commentService.findByPostId(id);
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public int c_insert(@RequestParam int pid, @RequestParam int uid, @RequestParam String content) throws Exception{
		commentService.save(pid, uid, content);
		return 1;
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public int comment_delete(@PathVariable int id) throws Exception{
		return commentService.deleteById(id);
	}
}
