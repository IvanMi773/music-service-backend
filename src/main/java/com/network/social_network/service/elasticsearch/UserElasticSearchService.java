package com.network.social_network.service.elasticsearch;

import com.network.social_network.mapping.User;
import com.network.social_network.repository.elasticsearch.UserElasticSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserElasticSearchService {

    private final UserElasticSearchRepository userElasticSearchRepository;

    public UserElasticSearchService(UserElasticSearchRepository userElasticSearchRepository) {
        this.userElasticSearchRepository = userElasticSearchRepository;
    }

    public void save (User user) {
        this.userElasticSearchRepository.save(user);
    }

    public List<User> findByUsername (String username) {
        return this.userElasticSearchRepository.findByUsername(username);
    }
}
