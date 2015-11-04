package net.finesoft.react.sns.article;

import java.util.Collection;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import net.finesoft.react.sns.comment.Comment;
import net.finesoft.react.sns.good.Good;
import net.finesoft.react.sns.user.User;


@Getter
@Setter
@Entity
public class Article {
	@Id
	@GeneratedValue
	private int article_no;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_no")
	private User user;
	
	//@Column(length = 100000000)
	@Column(columnDefinition = "text") // mysql로 할때
	private String content;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<Comment> comment;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private Collection<Good> good;
	
	Date created;

}

