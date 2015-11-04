package net.finesoft.react.sns.good;

import javax.persistence.CascadeType;
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

@Setter
@Getter
@Entity
public class Good {
	@Id
	@GeneratedValue
	private int good_no;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "good_user_no")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "good_article_no")
	@JsonIgnore
	private Article article;
}
