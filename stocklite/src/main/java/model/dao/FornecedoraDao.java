package model.dao;

import aux.Pair;
import java.util.List;

import model.entities.Fornecedora;

public interface FornecedoraDao {
    void insert(Fornecedora obj);
    void update(Fornecedora obj);
    void delete(Fornecedora obj);
    List<Fornecedora>findAll();
    public List<Pair<Integer, String>> findAllForCombobox();
}
