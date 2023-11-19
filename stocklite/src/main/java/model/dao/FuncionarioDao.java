package model.dao;

import java.util.List;

import model.entities.Funcionario;

public interface FuncionarioDao {


    int logaFuncionario(Funcionario obj, String senha);
    List<Funcionario>findAll();
    
}
