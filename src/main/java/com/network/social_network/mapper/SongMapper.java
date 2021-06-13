package com.network.social_network.mapper;

import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "username", expression = "java(song.getUser().getUsername())")
    @Mapping(target = "genre", expression = "java(song.getGenre().getName())")
    @Mapping(target = "file", expression = "java(song.getSongFile().getFileName())")
    @Mapping(target = "duration", expression = "java(song.getSongFile().getDuration())")
    SongResponseDto songToSongResponseDto (Song song);
}
