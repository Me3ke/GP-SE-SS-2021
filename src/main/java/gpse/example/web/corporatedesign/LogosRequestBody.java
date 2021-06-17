package gpse.example.web.corporatedesign;

import java.util.Arrays;

/**
 * A request and response File for setting or getting the logos.
 */
public class LogosRequestBody {

    private byte[] logo;
    private String logoTyp;
    private byte[] logoDark;
    private String logoDarkTyp;

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

    public String getLogoTyp() {
        return logoTyp;
    }

    public void setLogoTyp(final String logoTyp) {
        this.logoTyp = logoTyp;
    }

    public String getLogoDarkTyp() {
        return logoDarkTyp;
    }

    public void setLogoDarkTyp(final String logoDarkTyp) {
        this.logoDarkTyp = logoDarkTyp;
    }
}
