package socialnetwork.repo;

import socialnetwork.domain.Entity;
import socialnetwork.validation.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepo<ID, E extends Entity<ID>> extends InMemoryRepo<ID, E> implements Repository<ID, E> {
    private final String fileName;

    public FileRepo(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        List<E> entities;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
            entities = (List<E>) is.readObject();
            entities.forEach(super::save);
        } catch (EOFException eofe) {
            System.out.println("");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData() {
        List<E> list = new ArrayList<>();
        super.findAll().forEach(list::add);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public E save(E entity) {
        E ent = super.save(entity);
        if (ent == null)
            writeData();
        return ent;
    }

    @Override
    public E delete(ID id) {
        E ent = super.delete(id);
        if(ent != null)
            writeData();
        return ent;
    }
}