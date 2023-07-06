package ru.kata.spring.boot_security.demo.servicies;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", email)));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = findByEmail(email);
        Hibernate.initialize(user.getAuthorities());
        return user;
    }
}
