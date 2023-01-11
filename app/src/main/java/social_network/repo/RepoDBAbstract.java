package social_network.repo;

import social_network.domain.Entity;

public abstract class RepoDBAbstract<ID, E extends Entity<ID>> implements Repo<ID, E> {
    protected String url;
    protected String username;
    protected String password;

    public RepoDBAbstract(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
