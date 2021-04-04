package com.network.social_network.service.elasticsearch;

import com.network.social_network.mapping.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.repository.elasticsearch.UserElasticSearchRepository;
import org.springframework.stereotype.Service;

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
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setSubscriptions(user.getSubscriptions().size());
        userToUpdate.setSubscribers(user.getSubscribers().size());
        userToUpdate.setTracks(user.getPlaylists().get(0).getSongs().size());
        userToUpdate.setAvatar(user.getAvatar());

        userElasticSearchRepository.save(userToUpdate);
    }

    public void save (User user) {
        this.userElasticSearchRepository.save(user);
    }

    public List<User> findByUsername (String username) {
        return this.userElasticSearchRepository.findByUsername(username);
    }

    public void removeAll () {
        this.userElasticSearchRepository.deleteAll();
    }

    public void saveAll (List<com.network.social_network.model.User> users) {
        for ( var u : users) {
            userElasticSearchRepository.save(new User(
                    u.getUsername(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getSubscriptions().size(),
                    u.getSubscribers().size(),
                    u.getPlaylists().size() > 0 ? u.getPlaylists().get(0).getSongs().size() : 0,
                    u.getAvatar()
            ));
        }
    }
}
