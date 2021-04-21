package com.network.social_network.service.elasticsearch;

import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.elasticsearch_models.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.repository.elasticsearch.UserElasticSearchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserElasticSearchService {

    private final UserElasticSearchRepository userElasticSearchRepository;
    private final UserRepository userRepository;

    public UserElasticSearchService(UserElasticSearchRepository userElasticSearchRepository, UserRepository userRepository) {
        this.userElasticSearchRepository = userElasticSearchRepository;
        this.userRepository = userRepository;
    }

    public void update (String username, com.network.social_network.model.User user) {
        var userToUpdate = userElasticSearchRepository.findByUsername(username).get(0);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setId(user.getId());

        userElasticSearchRepository.save(userToUpdate);
    }

    public void save (User user) {
        this.userElasticSearchRepository.save(user);
    }

    public ArrayList<UserProfileDto> findByUsername (String username) {
        var userDtos = new ArrayList<UserProfileDto>();

        for (var u : this.userElasticSearchRepository.findByUsername(username)) {
            var user = userRepository.findByUsername(u.getUsername());
            userDtos.add(new UserProfileDto(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getSubscriptions(),
                    user.getSubscribers(),
                    user.getPlaylists().get(0).getSongs().size(),
                    user.getEmail(),
                    user.getAvatar(),
                    user.getRole()
            ));
        }

        return userDtos;
    }

    public void removeAll () {
        this.userElasticSearchRepository.deleteAll();
    }

    public void saveAll (List<com.network.social_network.model.User> users) {
        for ( var u : users) {
            userElasticSearchRepository.save(new User(
                    u.getId(),
                    u.getUsername()
            ));
        }
    }
}
