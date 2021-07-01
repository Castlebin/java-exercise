package heller.java8.examples.ch03;

import java.util.List;

import heller.java8.examples.ch01.Artist;

public class Iteration {

    public long countArtistFromLondon(List<Artist> artists) {
        return artists.stream()
                .filter(artist -> artist.isFrom("London"))
                .count();
    }

}
