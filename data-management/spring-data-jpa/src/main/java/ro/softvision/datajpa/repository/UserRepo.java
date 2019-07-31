package ro.softvision.datajpa.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import ro.softvision.datajpa.entity.User;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepo {

    List<User> findAllByUsername(String username);

    List<User> findAllByUsernameIsLike(String username);

    @Query("SELECT COUNT(u) FROM User u")
    Long countAll();

    @Query("SELECT u FROM User u WHERE u.username= :username")
    User findOneByUsername(@Param("username") String username);

    @Query("SELECT u.username FROM User u WHERE u.id= ?1")
    Optional<String> findUsernameById(Long id);

}
