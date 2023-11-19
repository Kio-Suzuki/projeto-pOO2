package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.FuncionarioDao;
import model.entities.Funcionario;

public class FuncionarioDaoJDBC implements FuncionarioDao {

    private Connection conn;

    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    

    @Override
    public int logaFuncionario(Funcionario obj, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
    
        try {
            st = conn.prepareStatement("SELECT * FROM funcionario WHERE funCpf = ?");
            st.setString(1, obj.getFunCpf());
            rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("funId");
                if (senha.compareTo("bancoadmin")==0) {
                    return id;
                } else {
                    return -1;
                }

            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    
        return -1;
    }
    



    @Override
    public List<Funcionario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM funcionario ORDER BY funId");

            rs = st.executeQuery();
            List<Funcionario> list = new ArrayList<>();

            while (rs.next()) {
                Funcionario funcionario = pegaInfo(rs);
                list.add(funcionario);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Funcionario pegaInfo(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setFunId(rs.getInt("funId"));
        funcionario.setFunNome(rs.getString("funNome"));
        funcionario.setFunSobrenome(rs.getString("funSobrenome"));
        funcionario.setFunCpf(rs.getString("funCpf"));
        funcionario.setFunTelefone(rs.getString("funTelefone"));
        funcionario.setFunDepartamento(rs.getString("funDepartamento"));
        funcionario.setFunSalario(rs.getFloat("funSalario"));
        return funcionario;
    }
}
