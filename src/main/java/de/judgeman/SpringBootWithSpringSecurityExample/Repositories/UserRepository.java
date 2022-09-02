package de.judgeman.SpringBootWithSpringSecurityExample.Repositories;

import de.judgeman.SpringBootWithSpringSecurityExample.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findFirstByOrderByUsernameAsc();
}
