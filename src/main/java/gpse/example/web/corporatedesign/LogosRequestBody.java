package gpse.example.web.corporatedesign;

import java.util.Arrays;

/**
 * A request and response File for setting or getting the logos.
 */
public class LogosRequestBody {

    private byte[] logo;
    private String logoType;
    private byte[] logoDark;
    private String logoDarkType;
    private boolean dark;


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

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(final String logoType) {
        this.logoType = logoType;
    }

    public String getLogoDarkType() {
        return logoDarkType;
    }

    public void setLogoDarkType(final String logoDarkType) {
        this.logoDarkType = logoDarkType;
    }

    public boolean isDark() {
        return dark;
    }

    public void setDark(boolean dark) {
        this.dark = dark;
    }
}
