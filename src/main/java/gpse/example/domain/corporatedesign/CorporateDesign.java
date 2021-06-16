package gpse.example.domain.corporatedesign;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class corporate Design is a compound of colors and pictures which are stored in the database
 * due to the design configurations of the administrator.
 */
@Entity
public class CorporateDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> colors;

    @Lob
    private byte[] logo;

    @Lob
    private byte[] logoDark;

    /**
     * the default constructor for a corporate Design.
     * @param colors the colors of the corporate Design.
     * @param logo the logo of the corporate Design.
     * @param logoDark the logo in darkmode.
     */
    public CorporateDesign(final String[] colors, final byte[] logo, final byte[] logoDark) {
        if (colors != null) {
            this.colors = new ArrayList<>(Arrays.asList(colors));
            System.out.println(this.colors.get(0));
        }
        if (logo != null) {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        if (logoDark != null) {
            this.logoDark = Arrays.copyOf(logoDark, logoDark.length);
        }
    }

    protected CorporateDesign() {

    }

    public long getId() {
        return id;
    }

    public List<String> getColors() {
        return colors;
    }

    /**
     * Sets the colors of the corporate design. If the given color is null
     * the default color is used instead.
     * @param colors the new colors of the corporate design.
     * @param defaultColors a list of default colors.
     */
    public void setColors(final String[] colors, final List<String> defaultColors) {
        String[] temp = new String[colors.length];
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == null) {
                temp[i] = defaultColors.get(i);
            } else {
                temp[i] = colors[i];
            }
        }
        this.colors = Arrays.asList(temp.clone());
    }

    public byte[] getLogo() {
        return Arrays.copyOf(logo, logo.length);
    }

    public void setLogo(final byte[] logo) {
        this.logo = Arrays.copyOf(logo, logo.length);
    }

    public byte[] getLogoDark() {
        return Arrays.copyOf(logoDark, logoDark.length);
    }

    public void setLogoDark(final byte[] logoDark) {
        this.logoDark = Arrays.copyOf(logoDark, logoDark.length);
    }
}
