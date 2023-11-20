package model.dao;

import java.util.List;
import model.entities.Estoque_Produto;

public interface Estoque_ProdutoDao {

    void insert(Estoque_Produto obj, int id);
    void update(Estoque_Produto obj, int id);
    void delete(Estoque_Produto obj, int id);
    List<Estoque_Produto>findAll();
    Estoque_Produto findById(Estoque_Produto obj);
}

