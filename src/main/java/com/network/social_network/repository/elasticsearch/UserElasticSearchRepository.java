package com.network.social_network.repository.elasticsearch;

import com.network.social_network.elasticsearch_models.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserElasticSearchRepository extends ElasticsearchRepository<User, String> {

    List<User> findByUsername (String username);
}

