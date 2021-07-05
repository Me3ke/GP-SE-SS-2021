package gpse.example.domain.users;

/**
 * Class to save setting for two-factor authentification after login.
 */
public class SettingTwoFacAuth {

    private String setting;

    public String getSetting() {
        return setting;
    }

    public void setSetting(final String setting) {
        this.setting = setting;
    }
}
