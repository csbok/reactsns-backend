package net.finesoft.react.sns.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finesoft.react.sns.user.User;

public interface FollowRepository  extends JpaRepository<Follow, Integer> {
	int countByLeader(User user);
	int countByLover(User user);
	
	int countByLeaderAndLover(User leader, User lover);
}