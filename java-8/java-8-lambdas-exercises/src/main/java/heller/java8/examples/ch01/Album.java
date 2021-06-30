package heller.java8.examples.ch01;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 专辑，包含若干首曲目
 */
public final class Album implements Performance {

    /** 专辑名字 */
    private String name;

    /** 专辑中包含的曲目列表 */
    private List<Track> tracks;

    /** 参与创作该专辑的艺术家列表 */
    private List<Artist> musicians;

    public Album(String name, List<Track> tracks, List<Artist> musicians) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(tracks);
        Objects.requireNonNull(musicians);

        this.name = name;
        this.tracks = new ArrayList<>(tracks);
        this.musicians = new ArrayList<>(musicians);
    }

    @Override
    public String getName() {
        return name;
    }

    public Stream<Track> getTracks() {
        return tracks.stream();
    }

    public List<Track> getTrackList() {
        return unmodifiableList(tracks);
    }

    @Override
    public Stream<Artist> getMusicians() {
        return musicians.stream();
    }

    public List<Artist> getMusicianList() {
        return unmodifiableList(musicians);
    }

    public Artist getMainMusician() {
        return musicians.get(0);
    }

    public Album copy() {
        List<Track> tracks = getTracks().map(Track::copy).collect(toList());
        List<Artist> musicians = getMusicians().map(Artist::copy).collect(toList());
        return new Album(name, tracks, musicians);
    }

}
