package model.dao.implementations;

import db.DB;
import db.DB;
import model.dao.EstoqueDao;
import model.dao.Estoque_ProdutoDao;
import model.dao.FornecedoraDao;
import model.dao.FuncionarioDao;
import model.dao.ProdutoDao;
import model.dao.TransportadoraDao;
import model.entities.AuditoriaDao;

public class DaoFactory {

    public static AuditoriaDao createAuditoriaDao() throws ClassNotFoundException {
        return new AuditoriaDaoJDBC(DB.getConnection());
    }

    public static FuncionarioDao createFuncionarioDao() throws ClassNotFoundException {
        return new FuncionarioDaoJDBC(DB.getConnection());
    }

    public static ProdutoDao createProdutoDao() throws ClassNotFoundException {
        return new ProdutoDaoJDBC(DB.getConnection());
    }

    public static Estoque_ProdutoDao createEstoque_ProdutoDao() throws ClassNotFoundException {
        return new Estoque_ProdutoDaoJDBC(DB.getConnection());
    }

    public static EstoqueDao createEstoquueDao() throws ClassNotFoundException {
        return new EstoqueDaoJDBC(DB.getConnection());
    }

    public static TransportadoraDao createTransportadoraDao() throws ClassNotFoundException {
        return new TransportadoraDaoJDBC(DB.getConnection());
    }
    
    public static FornecedoraDao createFornecedoraDao() throws ClassNotFoundException {
        return new FornecedoraDaoJDBC(DB.getConnection());
    }
}
