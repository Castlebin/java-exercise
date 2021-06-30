package heller.java8.examples.ch01;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 艺术家   创作音乐的 个人或乐队
 */
public final class Artist {
    /**
     * 艺术家的名字（或者乐队的名字）
     */
    private String name;

    /**
     * 乐队成员（可为空）
     */
    private List<Artist> members;

    /**
     * 乐队来自哪个地方
     */
    private String nationality;

    public Artist(String name, String nationality) {
        this(name, Collections.emptyList(), nationality);
    }

    public Artist(String name, List<Artist> members, String nationality) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(members);
        Objects.requireNonNull(nationality);

        this.name = name;
        this.members = new ArrayList<>(members);
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public Stream<Artist> getMembers() {
        return members.stream();
    }

    public String getNationality() {
        return nationality;
    }

    public boolean isSolo() {
        return members.isEmpty();
    }

    public boolean isFrom(String nationality) {
        return this.nationality.equals(nationality);
    }

    @Override
    public String toString() {
        return getName();
    }

    public Artist copy() {
        // todo 理解    这一行代码就能实现多级拷贝了啊？
        List<Artist> members = getMembers().map(Artist::copy).collect(toList());
        return new Artist(name, members, nationality);
    }

}
