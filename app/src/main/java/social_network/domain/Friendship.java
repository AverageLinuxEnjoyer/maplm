package social_network.domain;

import java.time.LocalDateTime;

/**
 * Class for representing a friendship between two users
 */
public class Friendship extends Entity<Long> {
    private final Long id1;
    private final Long id2;
    private static Long ids = 1L;
    private LocalDateTime friendsFrom;

    private String status;

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    /**
     * Getter for the id of the second user.
     * 
     * @return the id of the second user
     */
    public Long getId2() {
        return id2;
    }

    /**
     * Getter for the id of the first user.
     * 
     * @return the id of the first user
     */
    public Long getId1() {
        return id1;
    }

    /**
     * Constructor for Friendship. The lower id is assigned to id1, and the higher
     * id is assigned to id2.
     * 
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     */
    public Friendship(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.friendsFrom = LocalDateTime.now();
        this.status = "pending";

        setId(ids);
        ids += 1;
    }

    @Override
    public String toString() {
        return "Friendship [id=" + this.getId() + ", id1=" + id1 + ", id2=" + id2 + ", friendsFrom=" + friendsFrom
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
        result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Friendship other = (Friendship) obj;
        if (id2 == null) {
            if (other.id2 != null)
                return false;
        } else if (!id2.equals(other.id2))
            return false;
        if (id1 == null) {
            if (other.id1 != null)
                return false;
        } else if (!id1.equals(other.id1))
            return false;
        return true;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
