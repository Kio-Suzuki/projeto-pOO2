package model.dao.implementations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.EstoqueDao;
import model.entities.Estoque;

public class EstoqueDaoJDBC implements EstoqueDao {

    private Connection conn;

    public EstoqueDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Estoque obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO estoque (estQuantidade, estLocal, estDataEntrada, estDataValidade, proId) VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getEstQuantidade());
            st.setString(2, obj.getEstLocal());
            st.setDate(3, new java.sql.Date(obj.getEstDataEntrada().getTime()));
            st.setDate(4, new java.sql.Date(obj.getEstDataValidade().getTime()));
            st.setInt(5, obj.getProId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setEstId(id);
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
    public void update(Estoque obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE estoque SET estQuantidade = COALESCE(?, estQuantidade), local = COALESCE(?, estLocal), estDataEntrada = COALESCE(?, estDataEntrada), estDataValidade = COALESCE(?, estDataValidade) WHERE estId = COALESCE(?, estId)");
            st.setInt(1, obj.getEstQuantidade());
            st.setString(2, obj.getEstLocal());
            st.setDate(3, (Date) obj.getEstDataEntrada());
            st.setDate(4, (Date) obj.getEstDataValidade());
            st.setInt(5, obj.getEstId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void delete(Estoque obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM estoque WHERE estId = ?");
            st.setInt(1, obj.getEstId());

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
    public List<Estoque> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM estoque ORDER BY estId");

            rs = st.executeQuery();
            List<Estoque> list = new ArrayList<>();

            while (rs.next()) {
                Estoque estoque = pegaInfo(rs);
                list.add(estoque);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Estoque pegaInfo(ResultSet rs) throws SQLException {
        Estoque estoque = new Estoque();
        estoque.setEstId(rs.getInt("estId"));
        estoque.setEstQuantidade(rs.getInt("quantidade"));
        estoque.setEstLocal(rs.getString("local"));
        estoque.setEstDataEntrada(rs.getDate("data_entrada"));
        estoque.setEstDataValidade(rs.getDate("data_validade"));
        estoque.setProId(rs.getInt("proId"));
        return estoque;
    }
}
