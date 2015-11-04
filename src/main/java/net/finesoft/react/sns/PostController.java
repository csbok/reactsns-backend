package net.finesoft.react.sns;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostDao postDao;

	@RequestMapping("/write")
	public String write(Post post) {
		post.setRegDate(new Date());
		return "redirect:/post/" + postDao.save(post).getId();
	}

	@RequestMapping("/list")
	public String list(Model model) {
		List<Post> postList = postDao.findAll();
		model.addAttribute("postList", postList);
		return "blog";
	}

	@RequestMapping("/{id}")
	public String view(Model model, @PathVariable int id) {
		Post post = postDao.findOne(id);
		model.addAttribute("post", post);
		return "post";
	}
	
	@RequestMapping("/jsonlist")
	public List<Post> jsonlist(Model model) {

		List<Post> helloList = postDao.findAll();

		return helloList;
	}

	@RequestMapping("/jsonview/{id}")
	public Post jsonview(Model model, @PathVariable int id) {
		Post post = postDao.findOne(id);
		return post;
	}


}