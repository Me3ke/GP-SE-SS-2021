package gpse.example.domain.users;

/**
 * Class to save setting for two-factor authentification after login.
 */
public class SettingTwoFacAuth {

    private String setting;

    public SettingTwoFacAuth() {
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
}
