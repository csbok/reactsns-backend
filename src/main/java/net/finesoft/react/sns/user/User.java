package net.finesoft.react.sns.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import net.finesoft.react.sns.article.Article;
import net.finesoft.react.sns.follow.Follow;
import net.finesoft.react.sns.good.Good;


@Getter
@Setter
@Entity
public class User {
	@Id
	@GeneratedValue
	private int user_no;

	@JsonIgnore
	private int oauthProvider;
	
	@JsonIgnore
	private String oauthAccessToken;

	@Size(min=3, max=30)
	@NotNull
	@Column(unique=true, nullable=false)
	private String userName;
	
	@NotNull
// max가 안된다. 왜지?
//	@Size(min=4, max=30)
	@Size(min=4, max=255)
	@Column(nullable=false)
	@JsonIgnore
	private String password;

	@Email
	@Column(unique=true, nullable=false)
	private String mail;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<Article> article;
	
	@JsonIgnore
	@OneToMany(mappedBy = "leader", cascade = CascadeType.ALL)
	private Collection<Follow> leader;
	
	@JsonIgnore
	@OneToMany(mappedBy = "lover", cascade = CascadeType.ALL)
	private Collection<Follow> lover;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<Good> good;
	
	Date created;
	Date lastLogin;
}