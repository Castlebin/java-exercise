package heller.java8.examples.ch03;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import heller.java8.examples.ch01.Album;
import heller.java8.examples.ch01.Track;

public class Refactor {

    /**
     * 找出一批专辑中长度大于 60s 的歌曲
     */
    public Set<String> findLongTracks(List<Album> albums) {
        return albums.stream()
                .flatMap(Album::getTracks)
                .filter(track -> track.getLength() > 60)
                .map(Track::getName)
                .collect(Collectors.toSet());
    }

}
