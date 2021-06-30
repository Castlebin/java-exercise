package heller.java8.examples.ch01;

/**
 * 专辑里的一只 曲目
 **/
public final class Track {

    /** 歌曲名字 */
    private final String name;
    /** 歌曲时长 */
    private final int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Track copy() {
        return new Track(name, length);
    }

}
