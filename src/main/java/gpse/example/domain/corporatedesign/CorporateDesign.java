package gpse.example.domain.corporatedesign;

import com.beust.jcommander.internal.Lists;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * The class corporate Design is a compound of colors and pictures which are stored in the database
 * due to the design configurations of the administrator.
 */
@Entity
public class CorporateDesign {
    private static final String DEFAULT_TEXT = "<p>Anbieter:<br />Max Mustermann<br />Musterstraße 1"
            + "<br />80999 München</p>\n"
            + "<p>Kontakt:<br />Telefon: 089/1234567-8<br />Telefax: 089/1234567-9<br />E-Mail:"
            + " mail@mustermann.de<br />Website: www.mustermann.de</p>\n"
            + "<p> </p>\n"
            + "<p>Bei redaktionellen Inhalten:</p>\n"
            + "<p>Verantwortlich nach § 55 Abs.2 RStV<br />Moritz Schreiberling<br />"
            + "Musterstraße 2<br />80999 München</p>";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> colors;

    @Lob
    private byte[] logo;

    @Column
    private String logoType;

    @Lob
    private byte[] logoDark;

    @Column
    private String logoDarkType;

    @Lob
    private String impressumsText;

    /**
     * the default constructor for a corporate Design.
     *
     * @param colors   the colors of the corporate Design.
     * @param logo     the logo of the corporate Design.
     * @param logoDark the logo in darkmode.
     */
    public CorporateDesign(final String[] colors, final byte[] logo, final byte[] logoDark) {
        if (colors != null) {
            this.colors = Lists.newArrayList(colors);
        }

        if (logo.length == 0) {
            this.logo = new byte[0];
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        if (logoDark.length == 0) {
            this.logoDark = new byte[0];
        } else {
            this.logoDark = Arrays.copyOf(logoDark, logoDark.length);
        }
        impressumsText = DEFAULT_TEXT;
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
     *
     * @param colors        the new colors of the corporate design.
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
        this.colors = Lists.newArrayList(temp);
    }

    public byte[] getLogo() {
        return Arrays.copyOf(logo, logo.length);
    }

    public void setLogo(final byte[] logo, final String logoType) {
        this.logo = Arrays.copyOf(logo, logo.length);
        this.logoType = logoType;
    }

    public byte[] getLogoDark() {
        return Arrays.copyOf(logoDark, logoDark.length);
    }

    public void setLogoDark(final byte[] logoDark, final String logoDarkType) {
        this.logoDark = Arrays.copyOf(logoDark, logoDark.length);
        this.logoDarkType = logoDarkType;
    }

    public String getLogoType() {
        return logoType;
    }

    public String getLogoDarkType() {
        return logoDarkType;
    }

    public String getImpressumsText() {
        return impressumsText;
    }

    public void setImpressumsText(final String impressumsText) {
        this.impressumsText = impressumsText;
    }
}
