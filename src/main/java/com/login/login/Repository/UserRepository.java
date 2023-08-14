package com.login.login.Repository;

import com.login.login.Entity.User;
import com.login.login.Entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {


//    User findByUsername(String username);

    UserLogin findByEmail(String email);

    User findOneByEmailIgnoreCaseAndPassword(String email, String password);
}
