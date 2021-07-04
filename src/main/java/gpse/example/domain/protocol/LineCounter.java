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
    private static final int MARGIN_BOTTOM = 100;

    private int count;

    public LineCounter() {
        count = TOP_OF_PAGE;
    }

    public void addLines(float lines) {
        count -= lines * LINE_DIST;
    }

    public boolean isNewPage() {
        return (count <= MARGIN_BOTTOM);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
