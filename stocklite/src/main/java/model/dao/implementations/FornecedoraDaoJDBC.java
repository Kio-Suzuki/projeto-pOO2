package model.dao.implementations;

import auxiliar.Pair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import db.DB;
import db.DbException;
import model.dao.FornecedoraDao;
import model.entities.Fornecedora;

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
                    "INSERT INTO Fornecedora (forCnpj, forRazaoSocial, forEmail, forTelefone, forLogradouro, forNumero, forCep, forCidade, forEstado, traId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getForCnpj());
            st.setString(2, obj.getForRazaoSocial());
            st.setString(3, obj.getForEmail());
            st.setString(4, obj.getForTelefone());
            st.setString(5, obj.getForLogradouro());
            st.setInt(6, obj.getForNumero());
            st.setInt(7, obj.getForCep());
            st.setString(8, obj.getForCidade());
            st.setString(9, obj.getForEstado());
            st.setInt(10,obj.getTraId());

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
            System.out.println(e.getMessage());
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
                        "UPDATE Fornecedora SET forCnpj = COALESCE(?, forCnpj), forRazaoSocial = COALESCE(?, forRazaoSocial), forEmail = COALESCE(?, forEmail), forTelefone = COALESCE(?, forTelefone), forLogradouro = COALESCE(?, forLogradouro), forNumero = COALESCE(?, forNumero), forCep = COALESCE(?, forCep), forCidade = COALESCE(?, forCidade), forEstado = COALESCE(?, forEstado), traId=(COALESCE(?, traId) WHERE forId = ?");
                st.setString(1, obj.getForCnpj());
                st.setString(2, obj.getForRazaoSocial());
                st.setString(3, obj.getForEmail());
                st.setString(4, obj.getForTelefone());
                st.setString(5, obj.getForLogradouro());
                st.setInt(6, obj.getForNumero());
                st.setInt(7, obj.getForCep());
                st.setString(8, obj.getForCidade());
                st.setString(9, obj.getForEstado());
                st.setInt(10, obj.getTraId());
                st.setInt(11, obj.getForId());

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
    
    @Override
    public List<Pair<Integer, String>> findAllForCombobox() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT forId, forRazaoSocial FROM fornecedora");
            rs = st.executeQuery();
            List<Pair<Integer, String>> result = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("forId");
                String nome = rs.getString("forRazaoSocial");
                Pair<Integer, String> pair = new Pair<>(id, nome);
                result.add(pair);
            }
            return result;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public Fornecedora findById(Fornecedora obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM fornecedora WHERE forId = ?");
            st.setInt(1, obj.getForId());

            rs = st.executeQuery();

            if (rs.next()) {
                return pegaInfo(rs);
            }

            return null; // Retorna null se não encontrar nenhuma transportadora com o traId fornecido
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
        fornecedora.setForCep(rs.getInt("forCep"));
        fornecedora.setForCidade(rs.getString("forCidade"));
        fornecedora.setForEstado(rs.getString("forEstado"));
        return fornecedora;
    }
}
