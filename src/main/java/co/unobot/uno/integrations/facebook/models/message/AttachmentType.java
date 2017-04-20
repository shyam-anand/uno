package co.unobot.uno.integrations.facebook.models.message;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public enum AttachmentType {
    IMAGE("image"),
    AUDIO("audio"),
    VIDEO("video"),
    FILE("file"),
    TEMPLATE("template");

    private String name;

    AttachmentType(String n) {
        this.name = n;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
