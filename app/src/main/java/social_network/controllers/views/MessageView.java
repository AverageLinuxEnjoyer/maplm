package social_network.controllers.views;

import java.time.LocalDateTime;

public class MessageView {
    private final Long id;
    private final Long id1;
    private final String username1;
    private final Long id2;
    private final String username2;
    private final LocalDateTime sent_at;
    private final String content;

    public MessageView(Long id, Long id1, String username1, Long id2, String username2, LocalDateTime sent_at, String content) {
        this.id = id;
        this.id1 = id1;
        this.username1 = username1;
        this.id2 = id2;
        this.username2 = username2;
        this.sent_at = sent_at;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getId1() {
        return id1;
    }

    public String getUsername1() {
        return username1;
    }

    public Long getId2() {
        return id2;
    }

    public String getUsername2() {
        return username2;
    }

    @Override
    public String toString() {
        return "MessageView{" +
                "id=" + id +
                ", id1=" + id1 +
                ", username1='" + username1 + '\'' +
                ", id2=" + id2 +
                ", username2='" + username2 + '\'' +
                ", sent_at=" + sent_at +
                ", content='" + content + '\'' +
                '}';
    }

    public LocalDateTime getSent_at() {
        return sent_at;
    }

    public String getContent() {
        return content;
    }
}
