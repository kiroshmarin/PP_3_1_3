package ru.kata.spring.boot_security.demo.servicies;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> index();

    void add(User user);

    void update(User user);

    void delete(Long id);

    User show(Long id);

}
