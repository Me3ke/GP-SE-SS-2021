package gpse.example.web.corporatedesign;

import java.util.Arrays;

/**
 * A request and response File for setting or getting the logos.
 */
public class Logos {

    private byte[] logo;
    private byte[] logoDark;

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
