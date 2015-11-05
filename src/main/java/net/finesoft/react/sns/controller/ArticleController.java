package net.finesoft.react.sns.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.finesoft.react.sns.article.Article;
import net.finesoft.react.sns.article.ArticleRepository;
import net.finesoft.react.sns.comment.Comment;
import net.finesoft.react.sns.comment.CommentRepository;
import net.finesoft.react.sns.good.Good;
import net.finesoft.react.sns.good.GoodRepository;
import net.finesoft.react.sns.user.User;
import net.finesoft.react.sns.user.UserRepository;
import scala.annotation.meta.getter;

@RestController
public class ArticleController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private GoodRepository goodRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(//@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){

        if (!file.isEmpty()) {
    		String name = file.getOriginalFilename();
    		

    		try {

        		byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                System.out.println("You successfully uploaded " + name + "!");
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
            	System.out.println("You failed to upload " + name + " => " + e.getMessage());
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
        	System.out.println("You failed to upload file because the file was empty.");
            return "You failed to upload file because the file was empty.";
        }
    }	
	

	/*
	case 1.
	Sort sort = new Sort(new Order(Direction.DESC, "field name"));
	Pageable pageable = new PageRequest(startPageZeroBased, pageSize, sort);

	findAll(pageable)
	
	case 2.
	 PageRequest request =
            new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "startTime");
	 */
    @RequestMapping(value = "/new")
    public List<Map<String, Object>> getNewArticle(HttpSession session) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (Article article : articleRepo.findAll(new Sort(new Order(Direction.DESC, "created")))) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("article_no", article.getArticle_no());
            map.put("author", article.getUser().getUserName());
            map.put("user_no", article.getUser().getUser_no());
            map.put("content", article.getContent());
            //map.put("comment_list", commentList(article.getArticle_no()));
            map.put("good_count", goodRepo.countByArticle(article));
            
    		User my = getMyUser(userRepo, session);
    		if (my != null) {
    			if (goodRepo.findByArticleAndUser(article,my) != null) {
    	            map.put("good_already", true);
    			}
    		}
            
            list.add(map);
        }
        
        return list;
    }
	
	@RequestMapping(value = "/article/new")
	public List<Article> newArticle() {
		return articleRepo.findAll(new Sort(new Order(Direction.DESC, "created")));
	}


	@RequestMapping(value ="/comment/{article_no}", method = RequestMethod.POST)
	public Map<String, Object> addComment(@PathVariable int article_no, @RequestParam Map<String, Object> paramMap, HttpSession session)
	{
		User my = getMyUser(userRepo, session);
		if (my == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", false);
			return map;
		}

		Article article = articleRepo.findOne(article_no);

		Comment comment = new Comment();
		comment.setComment((String)paramMap.get("comment"));
		comment.setUser(my);
		comment.setArticle(article);		
		commentRepo.save(comment);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("article_no", article_no);
		return map;
	}
	
	@RequestMapping(value = "/comment/{article_no}", method = RequestMethod.GET)
	public List<Comment> commentList(@PathVariable int article_no)
	{
		Article article = articleRepo.findOne(article_no);
		return commentRepo.findByArticle(article);
	}
	
	@RequestMapping(value ="/good/{article_no}")
	public Map<String, Object> good(@PathVariable int article_no, HttpSession session)
	{
		User my = getMyUser(userRepo, session);
		if (my == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", false);
			return map;
		}
		
		Article article = articleRepo.findOne(article_no);

		Good before = goodRepo.findByArticleAndUser(article, my);
		if (before != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			goodRepo.delete(before);
			map.put("result", true);
			map.put("article", article_no);
			map.put("good", false);
			return map;
		}
		
		Good good = new Good();
		good.setUser(my);
		good.setArticle(article);
		
		goodRepo.save(good);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("article_no", article_no);
		map.put("good", true);
		return map;
	}
	
	public static User getMyUser(UserRepository userRepo, HttpSession session) {
		// 세션이 없는 경우
		if (session.getAttribute("user_no") == null)
			return null;

		// 세션에서 내 정보 갖고오기
		Integer user_no = (Integer)session.getAttribute("user_no");
		User my = userRepo.findOne(user_no);
		
		return my;
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public Map<String, Object> write(@Valid Article article, HttpSession session) {
		User my = getMyUser(userRepo, session);
		// 세션이 없는 경우
		if (my == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", false);
			return map;
		}
		
		article.setUser(my);
		article.setCreated(new Date());
	    articleRepo.save(article);
	    
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}
}
	