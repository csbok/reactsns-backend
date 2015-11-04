package net.finesoft.react.sns.comment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import net.finesoft.react.sns.article.Article;
import net.finesoft.react.sns.user.User;

@Getter
@Setter
@Entity
public class Comment {
	@Id
	@GeneratedValue
	private int comment_no;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="article_no")
	@JsonIgnore
	private Article article;
	
	private String comment;
	
//	private Date created;
}