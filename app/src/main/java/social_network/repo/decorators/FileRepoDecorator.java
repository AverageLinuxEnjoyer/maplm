package social_network.repo.decorators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import social_network.domain.Entity;
import social_network.repo.Repo;

public class FileRepoDecorator<ID, E extends Entity<ID>> extends AbstractRepoDecorator<ID, E> {
    String filepath = null;

    public FileRepoDecorator(Repo<ID, E> repo, String filepath) {
        super(repo);
        this.filepath = filepath;

        try {
            loadFromFile();
        } catch (ClassNotFoundException | IOException e) {
            // nothing
        }
    }

    @Override
    public void add(E entity) {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void update(E entity) {
        super.update(entity);
        writeToFile();
    }

    @Override
    public void delete(ID id) {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        writeToFile();
    }

    private void writeToFile() {
        ArrayList<E> list = ((Collection<E>) super.findAll()).stream().collect(Collectors.toCollection(ArrayList::new));

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() throws ClassNotFoundException, IOException {
        FileInputStream fis;

        fis = new FileInputStream(filepath);

        ObjectInputStream ois;

        ois = new ObjectInputStream(fis);
        final ArrayList<E> list = (ArrayList<E>) ois.readObject();
        ois.close();

        list.forEach(e -> super.add(e));
    }
}
