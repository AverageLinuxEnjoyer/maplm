package social_network.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Long> {
    private static Long ids = 1L;
    private final Long id1;
    private final Long id2;
    private final LocalDateTime sent_at;
    private String content;

    public Message(Long id1, Long id2, LocalDateTime sent_at, String content) {
        this.id1 = id1;
        this.id2 = id2;
        this.sent_at = sent_at;
        this.content = content;

        setId(ids);
        ids+=1;
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public LocalDateTime getSent_at() {
        return sent_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
