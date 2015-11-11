package net.finesoft.react.sns.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.finesoft.react.sns.user.User;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	int countByUser(User user);
	
	@Query("select a from Article a, Follow f where a.user = f.leader and f.lover = :user")
	List<Article> findTimeLine(@Param("user") User user);
	
	@Query(value = "SELECT *,(SELECT COUNT(1) from good where good_article_no=article_no) as good,(select count(1) from follow where lover_user_no=:userNo and leader_user_no=user_no) as follow from article", nativeQuery = true)
	List<Object> findNewArticle(@Param("userNo") int userNo);
}
