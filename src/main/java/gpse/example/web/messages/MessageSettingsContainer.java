package gpse.example.web.messages;

import javax.persistence.*;

/**
 * A class for a put request to change settings regarding the messages.
 */
@Entity
public class MessageSettingsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private boolean toDo;

    @Column
    private boolean sign;

    @Column
    private boolean read;

    @Column
    private boolean progress;

    @Column
    private boolean newVersion;

    public boolean isToDo() {
        return toDo;
    }

    public void setToDo(final boolean toDo) {
        this.toDo = toDo;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(final boolean sign) {
        this.sign = sign;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(final boolean read) {
        this.read = read;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(final boolean progress) {
        this.progress = progress;
    }

    public boolean isNewVersion() {
        return newVersion;
    }

    public void setNewVersion(final boolean newVersion) {
        this.newVersion = newVersion;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }
}
