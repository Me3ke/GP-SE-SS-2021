package gpse.example.web.corporatedesign;

import java.util.Arrays;

/**
 * The color class is the response body of the corporate design color put request.
 */
public class ColorsRequestBody {

    private String[] colors;

    public String[] getColors() {
        return Arrays.copyOf(colors, colors.length);
    }

    public void setColors(final String... colors) {
        this.colors = Arrays.copyOf(colors, colors.length);
    }
}
