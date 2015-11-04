package net.finesoft.react.sns;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post {
	@Id
	@GeneratedValue
	int id;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column(nullable = false)
	String title;
	
	@Size(max = 255)
	String subtitle;
	
	//@Column(length = 100000000)
	@Column(columnDefinition = "text") // mysql로 할때
	String content;
	
	Date regDate;
}	