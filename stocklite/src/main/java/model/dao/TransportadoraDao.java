package model.dao;

import java.util.List;
import model.entities.Transportadora;

public interface TransportadoraDao {

    void insert(Transportadora obj);
    void update(Transportadora obj);
    void deleteById(Transportadora obj);
    Transportadora findById(Transportadora obj);
    List<Transportadora> findAll();
}
    