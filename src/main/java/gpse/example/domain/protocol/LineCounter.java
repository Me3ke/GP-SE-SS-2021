package gpse.example.domain.protocol;

/**
 * Class to count lines.
 */
public class LineCounter {

    /**
     * vertical position of first line.
     */
    private static final int TOP_OF_PAGE = 700;

    /**
     * distance between to regular lines.
     */
    private static final int LINE_DIST = 25;


    private int count;

    public LineCounter() {
        count = TOP_OF_PAGE;
    }

    public void addLines(final float lines) {
        count -= lines * LINE_DIST;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
}
