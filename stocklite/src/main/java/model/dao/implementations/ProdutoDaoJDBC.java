package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

    private Connection conn;

    public ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Produto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO produto (proNome, proPreco, proCategoria, forId) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getProNome());
            st.setFloat(2, obj.getProPreco());
            st.setString(3, obj.getProCategoria());
            st.setInt(4, obj.getForId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setProId(id);
                }
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Produto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE produto SET proNome = COALESCE(?, proNome), proPreco = COALESCE(?, proPreco), proCategoria = COALESCE(?, proCategoria), forId = COALESCE(?, forId) WHERE proId = ?");
            st.setString(1, obj.getProNome());
            st.setFloat(2, obj.getProPreco());
            st.setString(3, obj.getProCategoria());
            st.setInt(4, obj.getForId());
            st.setInt(5, obj.getProId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Produto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM produto WHERE proId = ?");
            st.setInt(1, obj.getProId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DbException("ID não existe!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Produto> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM produto ORDER BY proId");

            rs = st.executeQuery();
            List<Produto> list = new ArrayList<>();

            while (rs.next()) {
                Produto produto = pegaInfo(rs);
                list.add(produto);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Produto pegaInfo(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setProId(rs.getInt("proId"));
        produto.setProNome(rs.getString("proNome"));
        produto.setProPreco(rs.getFloat("proPreco"));
        produto.setProCategoria(rs.getString("proCategoria"));
        produto.setForId(rs.getInt("forId"));
        return produto;
    }
}
