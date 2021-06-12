package com.network.social_network.repository.elasticsearch;

import com.network.social_network.model.Song;
import com.network.social_network.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongElasticsearchRepositoryTest {

    @Autowired
    private SongElasticsearchRepository songElasticsearchRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    void findByName () {
//        ArgumentCaptor<Song> songArgumentCaptor = ArgumentCaptor.forClass(Song.class);
//        verify(songRepository).save(songArgumentCaptor.capture());
//        verify(songRepository).save(songArgumentCaptor.capture());
//        verify(songRepository).save(songArgumentCaptor.capture());
    }
}