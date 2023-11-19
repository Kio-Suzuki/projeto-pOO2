package model.dao.implementations;
import aux.Pair;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import db.DB;
import db.DbException;
import java.sql.Statement;
import model.dao.Estoque_ProdutoDao;
import model.entities.Estoque_Produto;

public class Estoque_ProdutoDaoJDBC implements Estoque_ProdutoDao{

    private Connection conn;
    
    public Estoque_ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    };

    @Override
    public void insert(Estoque_Produto obj, int id){
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
            "SELECT add_produto_estoque (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getProNome());
            st.setFloat(2, obj.getProPreco());
            st.setString(3, obj.getProCategoria());
            st.setInt(4, obj.getForId());
            st.setInt(5, obj.getEstQuantidade());
            st.setString(6, obj.getEstLocal());
            st.setDate(7, new java.sql.Date(obj.getEstDataEntrada().getDate()));
            st.setDate(8, new java.sql.Date(obj.getEstDataValidade().getDate()));
            st.setInt(9, id);
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }
    @Override
    public void update(Estoque_Produto obj, int id){
        PreparedStatement st =null;
        try{
            st = conn.prepareStatement(
            "SELECT upd_produto_estoque(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.NO_GENERATED_KEYS);
            st.setString(1, obj.getProNome());
            st.setFloat(2, obj.getProPreco());
            st.setString(3, obj.getProCategoria());
            st.setInt(4, obj.getForId());
            st.setInt(5, obj.getEstQuantidade());
            st.setString(6, obj.getEstLocal());
            st.setDate(7,(Date) obj.getEstDataEntrada());
            st.setDate(8,(Date) obj.getEstDataValidade());
            st.setDate(9, (Date) obj.getOrigemDataEntrada());
            st.setDate(10, (Date) obj.getOrigemDataValidade());
            st.setInt(11, id);
            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally{
            DB.closeStatement(st);
        }
    };
    
    @Override
    public void delete(Estoque_Produto obj, int id){
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("CALL del_produto_estoque(?, ?, ?, ?)");
            st.setInt(1, obj.getProId());
            st.setDate(2, (Date) obj.getOrigemDataEntrada());
            st.setDate(3, (Date) obj.getOrigemDataValidade());
            st.setInt(4, id);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    };

    @Override
    public List<Estoque_Produto>findAll(){
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM ver_estoque_produto");
            rs = st.executeQuery();
            List<Estoque_Produto> result = new ArrayList<>();

            while(rs.next()){
                Estoque_Produto estoque = pegaInfo(rs);
                result.add(estoque);
            }
            return result;  
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    };



    public Estoque_Produto pegaInfo(ResultSet rs) throws SQLException {
        Estoque_Produto estoque = new Estoque_Produto();
        estoque.setProId(rs.getInt("produto_id"));
        estoque.setProNome(rs.getString("proNome"));
        estoque.setProPreco(rs.getFloat("proPreco"));
        estoque.setProCategoria(rs.getString("proCategoria"));
        estoque.setEstQuantidade(rs.getInt("estQuantidade"));
        estoque.setForRazaoSocial(rs.getString("forRazaoSocial"));
        estoque.setEstLocal(rs.getString("estLocal"));
        estoque.setEstDataEntrada(rs.getDate("estDataEntrada"));
        estoque.setEstDataValidade(rs.getDate("estDataValidade"));

        return estoque;
    }
}
