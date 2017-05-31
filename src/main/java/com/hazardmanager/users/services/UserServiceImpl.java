package com.hazardmanager.users.services;

import com.hazardmanager.users.custom_exceptions.PasswordMismatchException;
import com.hazardmanager.users.custom_exceptions.UsernameNotRegisteredException;
import com.hazardmanager.users.models.User;
import com.hazardmanager.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll();
    }

    @Override
    public User getById(String id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    @Override
    public String getUserId(String username, String password) throws UsernameNotRegisteredException, PasswordMismatchException {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        if(user == null){
            throw new UsernameNotRegisteredException("There is no user registered with the provided username.");
        } else if (!passwordEncoder.matches(password,user.getPassword())){
            throw new PasswordMismatchException("The provided password did not match with the username's password.");
        }else{
            return user.getId();
        }
    }
}
