package persistence;

import java.util.ArrayList;

public interface GenericRepository<G>{
    public void add(G Entity);
    public G get(int id);
    public ArrayList<G> getAll();
    public void update(G Entity);
    public void delete(G Entity);
}
