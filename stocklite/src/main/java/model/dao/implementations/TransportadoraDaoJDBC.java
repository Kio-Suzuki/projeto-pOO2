package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.TransportadoraDao;
import model.entities.Transportadora;

public class TransportadoraDaoJDBC implements TransportadoraDao {

    private Connection conn;

    public TransportadoraDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Transportadora obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO transportadora (traCnpj, traRazaoSocial, traEmail, traTelefone, traLogradouro, traNumero, traCep, traCidade, traEstado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getTraCnpj());
            st.setString(2, obj.getTraRazaoSocial());
            st.setString(3, obj.getTraEmail());
            st.setString(4, obj.getTraTelefone());
            st.setString(5, obj.getTraLogradouro());
            st.setInt(6, obj.getTraNumero());
            st.setInt(7, obj.getTraCep());
            st.setString(8, obj.getTraCidade());
            st.setString(9, obj.getTraEstado());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setTraId(id);
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
    public void update(Transportadora obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE transportadora SET traCnpj = COALESCE(?, traCnpj), traRazaoSocial = COALESCE(?, traRazaoSocial), traEmail = COALESCE(?, traEmail), traTelefone = COALESCE(?, traTelefone), traLogradouro = COALESCE(?, traLogradouro), traNumero = COALESCE(?, traNumero), traCep = COALESCE(?, traCep), traCidade = COALESCE(?, traCidade), traEstado = COALESCE(?, traEstado) WHERE traId = ?");
            st.setString(1, obj.getTraCnpj());
            st.setString(2, obj.getTraRazaoSocial());
            st.setString(3, obj.getTraEmail());
            st.setString(4, obj.getTraTelefone());
            st.setString(5, obj.getTraLogradouro());
            st.setInt(6, obj.getTraNumero());
            st.setInt(7, obj.getTraCep());
            st.setString(8, obj.getTraCidade());
            st.setString(9, obj.getTraEstado());
            st.setInt(10, obj.getTraId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Transportadora obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM transportadora WHERE traId = ?");
            st.setInt(1, obj.getTraId());

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
    public List<Transportadora> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM transportadora ORDER BY traId");

            rs = st.executeQuery();
            List<Transportadora> list = new ArrayList<>();

            while (rs.next()) {
                Transportadora transportadora = pegaInfo(rs);
                list.add(transportadora);
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
    public Transportadora findById(Transportadora obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM transportadora WHERE traId = ?");
            st.setInt(1, obj.getTraId());

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

    
    
    private Transportadora pegaInfo(ResultSet rs) throws SQLException {
        Transportadora transportadora = new Transportadora();
        transportadora.setTraId(rs.getInt("traId"));
        transportadora.setTraCnpj(rs.getString("traCnpj"));
        transportadora.setTraRazaoSocial(rs.getString("traRazaoSocial"));
        transportadora.setTraEmail(rs.getString("traEmail"));
        transportadora.setTraTelefone(rs.getString("traTelefone"));
        transportadora.setTraLogradouro(rs.getString("traLogradouro"));
        transportadora.setTraNumero(rs.getInt("traNumero"));
        transportadora.setTraCep(rs.getInt("traCep"));
        transportadora.setTraCidade(rs.getString("traCidade"));
        transportadora.setTraEstado(rs.getString("traEstado"));
        return transportadora;
    }
}
