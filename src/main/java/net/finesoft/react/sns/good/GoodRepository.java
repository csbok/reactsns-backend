package net.finesoft.react.sns.good;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finesoft.react.sns.article.Article;
import net.finesoft.react.sns.user.User;

public interface GoodRepository  extends JpaRepository<Good, Integer> {
	Good findByArticleAndUser(Article article, User user);
	int countByArticle(Article article);
}
