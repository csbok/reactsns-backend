package net.finesoft.react.sns.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.finesoft.react.sns.article.ArticleRepository;
import net.finesoft.react.sns.follow.Follow;
import net.finesoft.react.sns.follow.FollowRepository;
import net.finesoft.react.sns.good.GoodRepository;
import net.finesoft.react.sns.user.User;
import net.finesoft.react.sns.user.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private GoodRepository goodRepo;
	
	@Autowired
	private FollowRepository followRepo;
	
	@RequestMapping(value = "/info/{name}")
	public Map<String, String> getUserInfo(@PathVariable String name)
	{
		User user = userRepo.findFirstByUserName(name);
		
		Map<String, String> map = new HashMap<String,String>();
		
		map.put("name", user.getUserName());
		map.put("email", user.getMail());
		
//		map.put("article_count", String.valueOf(user.getArticle().size()));
		map.put("article_count", String.valueOf(articleRepo.countByUser(user)));
		
		map.put("follower", String.valueOf(followRepo.countByLeader(user)));
		map.put("following", String.valueOf(followRepo.countByLover(user)));
		
		return map;
	}
	


	@RequestMapping(value = "/search/{name}")
	public List<String> search(@PathVariable String name) {
		List<String> emailList = new ArrayList<String>();
		for (User user : userRepo.findByUserName(name)) {
			emailList.add(user.getMail());
		}
		
		return emailList;
	}
	
	@RequestMapping(value = "/login",  method = RequestMethod.POST)
	public Map<String,Object> login(@RequestParam Map<String, Object> paramMap, HttpSession session) {
		String id = (String)paramMap.get("id");
		String password = (String)paramMap.get("pw");
		
		User user = userRepo.findFirstByUserName(id);
		if (user == null) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("result", false);
			map.put("message", "아이디를 찾을 수 없습니다.");
			return map;
		}
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (!passwordEncoder.matches(password, user.getPassword())) {			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("result", false);
			map.put("message", "비밀번호가 틀렸습니다.");
			return map;
		}
		
		session.setAttribute("user_no", user.getUser_no());
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("result", true);
		return map;
	}
	
	@RequestMapping(value = "/join")
	public Map<String,Object> join(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	FieldError fieldError = bindingResult.getFieldErrors().get(0);
        	String field = fieldError.getField();
        	
        	ObjectError objectError = bindingResult.getAllErrors().get(0);
        	String message = objectError.getDefaultMessage();

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result", false);
			map.put("message", String.format("%s %s", field, message));
			return map;			

        	
        	/*
        	for (FieldError fieldError :			bindingResult.getFieldErrors()) {
				System.out.println("field : " + fieldError.getField());
			}
			
			for(ObjectError error :  bindingResult.getAllErrors()) {
				System.out.println("object   : " + 				error.getObjectName());
				System.out.println("message  : " + error.getDefaultMessage());
				System.out.println("code     : " + error.getCode());
				
				for(String str : error.getCodes())
				{
					System.out.println("codes str   : " + str);
									
				}
				for(Object obj : error.getArguments()) {
					System.out.println("obj : " + obj);
				}
			}
			System.out.println(" Not Valid!!!");
			*/
        }

		
		if (userRepo.findFirstByUserName(user.getUserName()) != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result", false);
			map.put("message", "중복되는 아이디가 있습니다.");
			return map;			
		}
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		user.setCreated(new Date());
		userRepo.save(user);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", true);
		return map;
	}

	@RequestMapping(value = "/follow/{leader_no}")
	public boolean follow(@PathVariable Integer leader_no, HttpSession session) {
		if (session.getAttribute("user_no") == null) {
			return false;
		}
		
		Integer user_no = (Integer)session.getAttribute("user_no");
		User my = userRepo.findOne(user_no);
		User leader = userRepo.findOne(leader_no);

		if (followRepo.countByLeaderAndLover(leader, my) > 0) {
			return false;
		}
		
		Follow follow = new Follow();
		follow.setLeader(leader);
		follow.setLover(my);
		
		followRepo.save(follow);
		
		return true;
	}
	
	@RequestMapping(value = "/check")
	public Map<String, String> check(HttpSession session) {
		Map<String, String> map = new HashMap<String,String>();
		
		map.put("user_no", String.valueOf(session.getAttribute("user_no")));
		
		return map;
	}
	

	@RequestMapping(value = "/testmap")
	public List<User> testmap() {
		return userRepo.findAll();
	}
	
	
}