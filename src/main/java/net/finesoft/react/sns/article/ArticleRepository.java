package net.finesoft.react.sns.article;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finesoft.react.sns.user.User;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	int countByUser(User user);
}
