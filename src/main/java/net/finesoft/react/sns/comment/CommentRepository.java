package net.finesoft.react.sns.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finesoft.react.sns.article.Article;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
	List<Comment> findByArticle(Article article);
}
