package info.nich.solsang.entities;

/**
 * Created by nich- on 2015/10/21.
 */
public class Emoji {
    private String emoji;
    private int id;

    public Emoji(String emoji) {
        this.emoji = emoji;
    }

    public Emoji(int id, String emoji) {
        this.id = id;
        this.emoji = emoji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
