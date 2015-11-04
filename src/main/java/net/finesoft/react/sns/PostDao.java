package net.finesoft.react.sns;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Integer> {
}