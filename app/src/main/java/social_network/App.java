package social_network;

// import java.time.LocalDateTime;

// import social_network.domain.Friendship;
// import social_network.domain.User;
// import social_network.domain.validators.FriendshipValidator;
// import social_network.domain.validators.UserValidator;
// import social_network.repo.Repo;
// import social_network.repo.RepoDBFriendship;
// import social_network.repo.RepoDBUser;
// import social_network.repo.RepoMemory;
// import social_network.repo.decorators.AutoIncrementRepoDecorator;
// import social_network.repo.decorators.FileRepoDecorator;
// import social_network.repo.decorators.ValidatorRepoDecorator;
// import social_network.service.Service;

// public class App {
//     public static void main(String[] args) {
//         // Repo<Long, User> repoUsers = new RepoMemory<Long, User>();
//         // repoUsers = new FileRepoDecorator<>(repoUsers, "users.txt");
//         // repoUsers = new AutoIncrementRepoDecorator<>(repoUsers, 0L, (id) -> id + 1);
//         // repoUsers = new ValidatorRepoDecorator<>(repoUsers, new UserValidator());

//         // Repo<Long, Friendship> repoFriendships = new RepoMemory<>();
//         // repoFriendships = new FileRepoDecorator<>(repoFriendships,
//         // "friendships.txt");
//         // repoFriendships = new AutoIncrementRepoDecorator<>(repoFriendships, 5L, (id)
//         // -> id + 1);
//         // repoFriendships = new ValidatorRepoDecorator<>(repoFriendships, new
//         // FriendshipValidator(repoUsers));

//         RepoDBUser repoDBUser = new RepoDBUser("jdbc:postgresql://127.0.0.1:5432/social_network", "postgres", null);

//         // repoDBUser.add(new User("Florin", "Mihai", "florin.mihai@gmail.com", null));
//         // repoDBUser.update(new User("Mircea", "Vasile", "mircea.vasile@gmail.com",
//         // 5L));
//         // repoDBUser.delete(6L);
//         for (User user : repoDBUser.findAll()) {
//             System.out.println(user);
//         }

//         RepoDBFriendship repoDBFriendship = new RepoDBFriendship("jdbc:postgresql://127.0.0.1:5432/social_network",
//                 "postgres", null);

//         // Friendship f = new Friendship(1L, 6L);
//         // f.setFriendsFrom(LocalDateTime.of(2020, 1, 1, 0, 0));
//         // f.setId(1L);
//         // repoDBFriendship.update(f);
//         // repoDBFriendship.add(f);
//         // repoDBFriendship.delete(1L);

//         System.out.println("Total friendships: " + repoDBFriendship.findAll().size());
//         for (Friendship friendship : repoDBFriendship.findAll()) {
//             System.out.println(friendship);
//         }

//         // System.out.println(repoDBFriendship.findOne(1L));

//         // var service = new Service(repoUsers, repoFriendships);

//         // service.addUser("Lucian", "Galan", "lucian.galan@gmail.com", 0L);
//         // service.addUser("Andrei", "Stefan", "andrei.stefan@gmail.com", 1L);
//         // service.addUser("Mircea", "Popescu", "mirceapopescu@gmail.com", 2L);
//         // service.addUser("Vasile", "Oltean", "vasileoltean@gmail.com", 3L);
//         // service.addUser("Ion", "Ilie", "ionilie@gmail.com", 4L);
//         // service.addUser("Mihai", "Oprisor", "mihaioprisor@gmail.com", 5L);
//         // service.addUser("Flavius", "Odobasian", "flaviusodobasian@gmail.com", 6L);
//         // service.addUser("Ernest", "Stefanescu", "ernest.stefanescu@gmail.com", 7L);
//         // service.addUser("Constantin", "Viorel", "constantin.viorel@gmail.com", 8L);
//         // service.addUser("Eusebiu", "Titus", "eusebiu.titus@gmail.com", 9L);

//         // for (User user : service.getAllUsers()) {
//         // System.out.println(user);
//         // }

//         // try {
//         // service.addFriendship(1L, 2L);
//         // service.addFriendship(2L, 3L);
//         // service.addFriendship(3L, 4L);
//         // service.addFriendship(3L, 5L);
//         // service.addFriendship(4L, 5L);
//         // service.addFriendship(6L, 7L);
//         // } catch (Exception e) {
//         // // print stack trace

//         // e.printStackTrace();
//         // }

//         // for (Friendship friendship : service.getAllFriendships()) {
//         // System.out.println(friendship);
//         // }

//         // service.updateUser(1L, "vasile", "mocanu", "vasilemocanu@gmail.com");
//         // service.updateFriendship(6L, 1L, 2L, LocalDateTime.now());

//         // for (User user : service.getAllUsers()) {
//         // System.out.println(user);
//         // }

//         // for (Friendship friendship : service.getAllFriendships()) {
//         // System.out.println(friendship);
//         // }

//         // System.out.println("Communities:" + service.getCommunities());
//         // System.out.println("Biggest community:" + service.getCommunityCount());
//         // System.out.println("Most social community: " +
//         // service.getMostSocialCommunity());
//         // System.out.println("Longest path: " + service.getLongestFriendshipPath());
//     }

//     private static void test() {
//         Repo<Long, User> repoUsers = new RepoMemory<Long, User>();
//         repoUsers = new FileRepoDecorator<>(repoUsers, "users.txt");
//         repoUsers = new AutoIncrementRepoDecorator<>(repoUsers, 0L, (id) -> id + 1);
//         repoUsers = new ValidatorRepoDecorator<>(repoUsers, new UserValidator());

//         Repo<Long, Friendship> repoFriendships = new RepoMemory<>();
//         repoFriendships = new FileRepoDecorator<>(repoFriendships, "friendships.txt");
//         repoFriendships = new AutoIncrementRepoDecorator<>(repoFriendships, 5L, (id) -> id + 1);

//         var service = new Service(repoUsers, repoFriendships);

//         for (User user : service.getAllUsers()) {
//             System.out.println(user);
//         }

//         service.addFriendship(1L, 2L);
//         service.addFriendship(2L, 3L);
//         service.addFriendship(3L, 4L);

//         for (Friendship friendship : service.getAllFriendships()) {
//             System.out.println(friendship);
//         }
//     }
// }
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import social_network.controllers.Controller;
import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.repo.Repo;
import social_network.repo.RepoDBFriendship;
import social_network.repo.RepoDBMessages;
import social_network.repo.RepoDBUser;
import social_network.service.Service;

import java.io.IOException;

public class App extends Application {
    private static Scene scene = null;
    private static Service service = null;

    public static void main(String[] args) {
        Repo<Long, User> repoUsers = new RepoDBUser("jdbc:postgresql://127.0.0.1:5432/social_network", "postgres",
                null);
        Repo<Long, Friendship> repoFriendships = new RepoDBFriendship("jdbc:postgresql://127.0.0.1:5432/social_network",
                "postgres", null);
        Repo<Long, Message> repoMessages = new RepoDBMessages("jdbc:postgresql://127.0.0.1:5432/social_network",
                "postgres", null);

        App.service = new Service(repoUsers, repoFriendships, repoMessages);

        launch(args);
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getClassLoader().getResource(fxml + ".fxml"));

        Parent root = (Parent) loader.load();

        Controller controller = loader.getController();
        controller.setService(service);
        controller.update();

        if (scene == null) {
            scene = new Scene(root);
        } else {
            scene.setRoot(root);
        }

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.setRoot("login");

        primaryStage.setTitle("Hopefully works");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
