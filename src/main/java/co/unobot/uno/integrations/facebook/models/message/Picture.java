package co.unobot.uno.integrations.facebook.models.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
class Picture {
    private String url;

    @JsonProperty("sticker_id")
    private long stickerId;

    @JsonProperty("is_reusable")
    private boolean isReusable;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStickerId() {
        return stickerId;
    }

    public void setStickerId(long stickerId) {
        this.stickerId = stickerId;
    }
}
