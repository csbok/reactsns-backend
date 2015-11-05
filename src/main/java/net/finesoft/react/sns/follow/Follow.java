package net.finesoft.react.sns.follow;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import net.finesoft.react.sns.user.User;

@Getter
@Setter
@Entity
public class Follow {
	// id가 피룡없다. 나중에는 leader와 lover의 복합키로 바꿀것
	// http://kwonnam.pe.kr/wiki/java/jpa/composite_primary_key 참고
	@Id
	@GeneratedValue
	int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_user_no")
	private User leader;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lover_user_no")
	private User lover;
}
