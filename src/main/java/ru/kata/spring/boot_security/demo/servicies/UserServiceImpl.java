package ru.kata.spring.boot_security.demo.servicies;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> index() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        if (!user.getPassword().equals(show(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public User show(Long id) {
        return usersRepository.findById(id).orElse(null);
    }


}
