package com.network.social_network.mapper;

import com.network.social_network.dto.playlist.PlaylistResponseDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(target = "username", expression = "java(playlist.getUser().getUsername())")
    @Mapping(target = "state", expression = "java(playlist.getState().name())")
    @Mapping(target = "songs", expression = "java(songs)")
    PlaylistResponseDto playlistToPlaylistResponseDto (Playlist playlist, List<SongResponseDto> songs);
}
