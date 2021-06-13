package com.network.social_network.service.search;

import com.network.social_network.model.SearchCriteria;
import com.network.social_network.model.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.searchSpecification.UserSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchByUsername (String username) {
        var userSpecification = new UserSpecification(new SearchCriteria("username", ":", username));
        return userRepository.findAll(userSpecification);
    }
}
