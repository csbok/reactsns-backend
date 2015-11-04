package net.finesoft.react.sns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.core.util.KeyValuePair;
import org.hibernate.envers.tools.Pair;
import org.hibernate.mapping.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.finesoft.react.sns.article.Article;
import net.finesoft.react.sns.article.ArticleRepository;
import net.finesoft.react.sns.comment.CommentRepository;
import net.finesoft.react.sns.follow.Follow;
import net.finesoft.react.sns.follow.FollowRepository;
import net.finesoft.react.sns.good.Good;
import net.finesoft.react.sns.good.GoodRepository;
import net.finesoft.react.sns.user.User;
import net.finesoft.react.sns.user.UserRepository;

@RestController
public class HelloRestController {
	@Autowired
	private HelloDao helloDao;
	
	@Autowired
	private PostDao postDao;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private FollowRepository followRepo;
	
	@Autowired
	private GoodRepository goodRepo;
	
	@RequestMapping("/add")
	public Hello add(Hello hello) {

		Hello helloData = helloDao.save(hello);

		return helloData;
	}

	@RequestMapping("/list")
	public List<Hello> list(Model model) {

		List<Hello> helloList = helloDao.findAll();

		return helloList;
	}


	@RequestMapping("/hello")
	public String index(Model model) {
		model.addAttribute("name", "SpringBlog from Millky");
		return "hello";
	}
	
	@RequestMapping("/")
	public String index() {
		return 
"<script type='text/javascript'>"+
"location.href='index.html';"+
"</script>";
	}
	
	class Test {
		public int a;
		public int b;
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

	
	
	@RequestMapping(value = "/init")//, method = RequestMethod.POST)
	public void init() {
		User user = new User();
		user.setUserName("curtis");
		user.setMail("cesilphp@hotmail.com");

		User user2 = new User();
		user2.setUserName("felix");
		user2.setMail("winmain@gmail.com");

		
		Follow follow = new Follow();
		follow.setLeader(user2);
		follow.setLover(user);
		
		
		Article article = new Article();
		article.setUser(user);
		article.setContent("first article");

		Article article2 = new Article();
		article2.setUser(user);
		article2.setContent("second article");

		
		Collection<Article> articleCollection = new ArrayList<Article>();
		articleCollection.add(article);
		articleCollection.add(article2);
		
		
		userRepo.save(user);
		userRepo.save(user2);
		followRepo.save(follow);
		articleRepo.save(article);
		articleRepo.save(article2);
	}


/*	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@Valid Post post) {
	    post.setRegDate(new Date());
	    return "redirect:/post/" + postDao.save(post).getId();
	}
	*/

	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable int id) {
		postDao.delete(id);
		return "redirect:/post/list";
	}
	
	@RequestMapping("/test")
	public Test test() {
		
		Test test = new Test();
		
		test.a = 1;
		test.b = 2;
		
		return test;
		
//		return "test";
	}
}