package com.velas.velashome.model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.velas.velashome.db.DB;
import com.velas.velashome.db.DbException;
import com.velas.velashome.model.dao.FornecedoraDao;
import com.velas.velashome.model.entities.Fornecedora;

public class FornecedoraDaoJDBC implements FornecedoraDao {

    private Connection conn;

    public FornecedoraDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Fornecedora obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Fornecedora (forCnpj, forRazaoSocial, forEmail, forTelefone, forLogradouro, forNumero, forCep, forCidade, forEstado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getForCnpj());
            st.setString(2, obj.getForRazaoSocial());
            st.setString(3, obj.getForEmail());
            st.setString(4, obj.getForTelefone());
            st.setString(5, obj.getForLogradouro());
            st.setInt(6, obj.getForNumero());
            st.setString(7, obj.getForCep());
            st.setString(8, obj.getForCidade());
            st.setString(9, obj.getForEstado());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setForId(id);
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
        public void update(Fornecedora obj) {
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement(
                        "UPDATE Fornecedora SET forCnpj = ?, forRazaoSocial = ?, forEmail = ?, forTelefone = ?, forLogradouro = ?, forNumero = ?, forCep = ?, forCidade = ?, forEstado = ? WHERE forId = ?");
                st.setString(1, obj.getForCnpj());
                st.setString(2, obj.getForRazaoSocial());
                st.setString(3, obj.getForEmail());
                st.setString(4, obj.getForTelefone());
                st.setString(5, obj.getForLogradouro());
                st.setInt(6, obj.getForNumero());
                st.setString(7, obj.getForCep());
                st.setString(8, obj.getForCidade());
                st.setString(9, obj.getForEstado());
                st.setInt(10, obj.getForId());

                st.executeUpdate();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

    @Override
    public void delete(Fornecedora obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Fornecedora WHERE forId = ?");
            st.setInt(1, obj.getForId());

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
    public List<Fornecedora> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Fornecedora ORDER BY forId");

            rs = st.executeQuery();
            List<Fornecedora> list = new ArrayList<>();

            while (rs.next()) {
                Fornecedora fornecedora = pegaInfo(rs);
                list.add(fornecedora);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Fornecedora pegaInfo(ResultSet rs) throws SQLException {
        Fornecedora fornecedora = new Fornecedora();
        fornecedora.setForId(rs.getInt("forId"));
        fornecedora.setForCnpj(rs.getString("forCnpj"));
        fornecedora.setForRazaoSocial(rs.getString("forRazaoSocial"));
        fornecedora.setForEmail(rs.getString("forEmail"));
        fornecedora.setForTelefone(rs.getString("forTelefone"));
        fornecedora.setForLogradouro(rs.getString("forLogradouro"));
        fornecedora.setForNumero(rs.getInt("forNumero"));
        fornecedora.setForCep(rs.getString("forCep"));
        fornecedora.setForCidade(rs.getString("forCidade"));
        fornecedora.setForEstado(rs.getString("forEstado"));
        return fornecedora;
    }
}
