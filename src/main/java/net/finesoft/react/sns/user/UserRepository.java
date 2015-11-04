package net.finesoft.react.sns.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
// https://spring.io/guides/gs/accessing-data-rest/
// https://github.com/spring-projects/spring-data-rest/blob/master/src/main/asciidoc/configuring-the-rest-url-path.adoc
public interface UserRepository  extends JpaRepository<User, Integer> {
	List<User> findByUserName(String userName);
	User findFirstByUserName(String userName);

	/*
	  @Override
	  @RestResource(exported = false)
	  void delete(Integer id);

	  @Override
	  @RestResource(exported = false)
	  void delete(User user);
	  */
}
