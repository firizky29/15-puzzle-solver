package enums;

public enum Direction {
    UP(-1, 0, "UP"),
    DOWN(1, 0, "DOWN"),
    RIGHT(0, 1, "RIGHT"),
    LEFT(0, -1, "LEFT");

    public final int dr;
    public final int dc;
    public final String label;

    Direction(int dr, int dc, String label){
        this.dr = dr;
        this.dc = dc;
        this.label = label;
    }

}
