package social_network.service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.repo.Repo;
import social_network.repo.RepoMemory;

/**
 * Class for managing users and friendships.
 */
public class Service {
    private Repo<Long, User> users;
    private Repo<Long, Friendship> friendships;
    private Repo<Long, Message> messages;
    private Long loggedInUserId = null;
    private Long messagedUser = null;

    public Long getMessagedUser() {
        return messagedUser;
    }

    public void setMessagedUser(Long messagedUser) {
        this.messagedUser = messagedUser;
    }

    /**
     * Constructor for the service.
     */
    public Service() {
        users = new RepoMemory<>();
        friendships = new RepoMemory<>();
    }

    public Service(Repo<Long, User> users, Repo<Long, Friendship> friendships, Repo<Long, Message> messages) {
        this.users = users;
        this.friendships = friendships;
        this.messages = messages;
    }

    /**
     * Creates a friendship between two users
     * 
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     */
    public void sendFriendRequest(Long id1, Long id2) {
        Friendship friendship = new Friendship(id1, id2);
        friendship.setStatus("pending");

        for (Friendship f : friendships.findAll()) {
            if (f.getId1().equals(id1) && f.getId2().equals(id2)) {
                return;
            } else if (f.getId1().equals(id2) && f.getId2().equals(id1)) {
                return;
            }
        }

        friendships.add(friendship);
    }

    public void acceptFriendRequest(Long id1, Long id2) {
        Friendship friendship = new Friendship(id1, id2);

        for (Friendship f : friendships.findAll()) {
            if ((f.getId1().equals(id1) && f.getId2().equals(id2)) || (f.getId1().equals(id2) && f.getId2().equals(id1))){
                if (f.getStatus().equals("pending")) {
                    f.setStatus("accepted");
                    f.setFriendsFrom(LocalDateTime.now());
                    friendships.update(f);
                }
                break;
            }
        }
    }

    public void declineFriendRequest(Long id1, Long id2) {
        Friendship friendship = new Friendship(id1, id2);

        for (Friendship f : friendships.findAll()) {
            if ((f.getId1().equals(id1) && f.getId2().equals(id2)) || (f.getId1().equals(id2) && f.getId2().equals(id1))) {
                if (f.getStatus().equals("pending")) {
                    f.setStatus("declined");
                    friendships.update(f);
                }
                break;
            }
        }
    }

    /**
     * Adds a user to the repository.
     * 
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public void addUser(String firstName, String lastName, String email, Long id, String password) throws IllegalArgumentException {
        for (User user : getAllUsers())  {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
                throw new IllegalArgumentException("There is already a user with the same name.");
            }

            if (user.getEmail().equals(email)) {
                throw new IllegalArgumentException("The email is already used");
            }
        }

        String pass = String.valueOf(password.hashCode());
        System.out.println(pass);
        User user = new User(firstName, lastName, email, id, pass);
        users.add(user);
    }

    public void addMessage(Long id1, Long id2, String content) {
        Message message = new Message(id1, id2, LocalDateTime.now(), content);
        this.messages.add(message);
    }

    public Iterable<Message> getAllMessagesBetween(Long id1, Long id2) {
        ArrayList<Message> res = new ArrayList<>();
        for (Message m : this.messages.findAll()) {
            if ((m.getId1().equals(id1) && m.getId2().equals(id2)) || (m.getId1().equals(id2) && m.getId2().equals(id1))) {
                res.add(m);
            }
        }

        return res;
    }

    /**
     * Getter for all users.
     *
     * @return all users
     */
    public Iterable<User> getAllUsers() {
        return users.findAll();
    }

    /**
     * Getter for all friendships.
     * 
     * @return all friendships
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendships.findAll();
    }

    /**
     * Deletes a user
     * 
     * @param id the id of the user
     */
    public void deleteUser(Long id) {
        users.delete(id);

        ArrayList<Friendship> friendshipsToDelete = new ArrayList<>();
        for (Friendship friendship : this.friendships.findAll()) {
            if (friendship.getId1().equals(id) || friendship.getId2().equals(id)) {
                friendshipsToDelete.add(friendship);
            }
        }

        ArrayList<Message> messagesToDelete = new ArrayList<>();
        for (Message message : this.messages.findAll()) {
            if (message.getId1().equals(id) || message.getId2().equals(id)) {
                messagesToDelete.add(message);
            }
        }

        for (Friendship friendship : friendshipsToDelete) {
            this.friendships.delete(friendship.getId());
        }

        for (Message message : messagesToDelete) {
            this.messages.delete(message.getId());
        }
    }

    public void deleteFriendship(Long id1, Long id2) {
        Friendship friendship = new Friendship(id1, id2);

        for (Friendship f : friendships.findAll()) {
            if ((f.getId1().equals(id1) && f.getId2().equals(id2)) || (f.getId1().equals(id2) && f.getId2().equals(id1))) {
                friendships.delete(f.getId());
                break;
            }
        }
    }

    /**
     * Updates a user
     * 
     * @param id        the id of the user
     * @param firstName the new first name
     * @param lastName  the new last name
     * @param email     the new email
     */
    public void updateUser(Long id, String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, id, String.valueOf(password.hashCode()));
        users.update(user);
    }

    /**
     * Updates a friendship
     * 
     * @param id  the id of the friendship
     * @param id1 the new id of the first user
     * @param id2 the new id of the second user
     */
    public void updateFriendship(Long id, Long id1, Long id2, LocalDateTime friendsFrom, String status) {
        Friendship friendship = new Friendship(id1, id2);
        friendship.setId(id);
        friendship.setFriendsFrom(friendsFrom);
        friendship.setStatus(status);
        friendships.update(friendship);
    }

    /**
     * Returns the user with a certain id
     * 
     * @param id the id of the user
     * @return the user with the given id
     */
    public User getUser(Long id) {
        return users.findOne(id);
    }

    /**
     * Returns the friendship with a certain id
     * 
     * @param id the id of the friendship
     * @return the friendship with the given id
     */

    public Friendship getFriendshipBetween(Long id1, Long id2) {
        for (Friendship fr : getAllFriendships()) {
            if (Math.min(fr.getId1(), fr.getId2()) == Math.min(id1, id2) && Math.max(fr.getId1(), fr.getId2()) == Math.max(id1, id2)) {
                return fr;
            }
        }
        return null;
    }

    /**
     * Returns the number of communities
     */
    public int getCommunityCount() {
        return getCommunities().size();
    }

    /**
     * Returns the communities
     * 
     * @return the communities
     */
    public ArrayList<ArrayList<Long>> getCommunities() {
        ArrayList<ArrayList<Long>> communities = new ArrayList<>();
        ArrayList<Long> visited = new ArrayList<>();
        for (User user : users.findAll()) {
            if (!visited.contains(user.getId())) {
                ArrayList<Long> community = new ArrayList<>();
                community.add(user.getId());
                visited.add(user.getId());
                for (int i = 0; i < community.size(); i++) {
                    for (Friendship friendship : friendships.findAll()) {
                        if (friendship.getId1().equals(community.get(i)) && !visited.contains(friendship.getId2())) {
                            community.add(friendship.getId2());
                            visited.add(friendship.getId2());
                        }
                        if (friendship.getId2().equals(community.get(i)) && !visited.contains(friendship.getId1())) {
                            community.add(friendship.getId1());
                            visited.add(friendship.getId1());
                        }
                    }
                }
                communities.add(community);
            }
        }
        return communities;
    }

    /**
     * Splits the graph of all users into graphs of communities
     * 
     * @return a list of graphs of communities
     */
    private ArrayList<ArrayList<Friendship>> getFriendshipsByCommunities() {
        ArrayList<ArrayList<Long>> users_in_communities = getCommunities();

        ArrayList<ArrayList<Friendship>> communities = new ArrayList<>();

        for (ArrayList<Long> users_in_community : users_in_communities) {
            ArrayList<Friendship> community = new ArrayList<>();
            for (Friendship friendship : friendships.findAll()) {
                if (users_in_community.contains(friendship.getId1())
                        && users_in_community.contains(friendship.getId2())) {
                    community.add(friendship);
                }
            }
            communities.add(community);
        }

        return communities;
    }

    /**
     * Returns the longegst path of friendships
     * 
     * @return the longest path of friendships
     */
    public ArrayList<Long> getLongestFriendshipPath() {
        ArrayList<ArrayList<Friendship>> communities = getFriendshipsByCommunities();

        ArrayList<Long> longest_path = new ArrayList<>();

        for (ArrayList<Friendship> community : communities) {
            ArrayList<Long> path = new ArrayList<>();
            for (Friendship friendship : community) {
                if (!path.contains(friendship.getId1())) {
                    path.add(friendship.getId1());
                }
                if (!path.contains(friendship.getId2())) {
                    path.add(friendship.getId2());
                }
            }
            if (path.size() > longest_path.size()) {
                longest_path = path;
            }
        }

        return longest_path;
    }

    /**
     * Returns the community containing the longest friendship path
     * 
     * @return the community containing the longest friendship path
     */
    public ArrayList<Long> getMostSocialCommunity() {
        ArrayList<Long> longest_path = getLongestFriendshipPath();
        ArrayList<ArrayList<Long>> users_in_communities = getCommunities();

        for (ArrayList<Long> users_in_community : users_in_communities) {
            if (users_in_community.containsAll(longest_path)) {
                return users_in_community;
            }
        }

        return null;

    }

    public Long getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(Long loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
