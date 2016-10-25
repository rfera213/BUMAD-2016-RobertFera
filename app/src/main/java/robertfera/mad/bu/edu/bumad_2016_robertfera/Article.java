package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 10/25/16.
 */

public class Article {
    private String type;
    private String head;
    private String deck;
    private String thumbnail;
    private String link;

    public Article(String type, String head, String deck, String thumbnail, String link) {
        this.type = type;
        this.head = head;
        this.deck = deck;
        this.thumbnail = thumbnail;
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public String getHead() {
        return head;
    }

    public String getDeck() {
        return deck;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLink() {
        return link;
    }
}
