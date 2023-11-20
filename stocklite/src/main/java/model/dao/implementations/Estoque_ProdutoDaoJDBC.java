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
import model.dao.Estoque_ProdutoDao;
import model.entities.Estoque_Produto;

public class Estoque_ProdutoDaoJDBC implements Estoque_ProdutoDao{

    private Connection conn;
    
    public Estoque_ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    };

    @Override
    public void insert(Estoque_Produto obj, int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT add_produto_estoque (?, ?, ?, ?, ?, ?, ?, ?, ?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, obj.getProNome());
            st.setFloat(2, obj.getProPreco());
            st.setString(3, obj.getProCategoria());
            st.setInt(4, obj.getForId());
            st.setInt(5, obj.getEstQuantidade());
            st.setString(6, obj.getEstLocal());
            st.setDate(7, new java.sql.Date(obj.getEstDataEntrada().getTime()));
            st.setDate(8, new java.sql.Date(obj.getEstDataValidade().getTime()));
            st.setInt(9, id);

            st.execute();

            rs = st.getGeneratedKeys();
        } catch (SQLException e) {
            throw new DbException("Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
    @Override
    public void update(Estoque_Produto obj, int id){
        PreparedStatement st =null;
        try{
            st = conn.prepareStatement(
            "SELECT upd_produto_estoque(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.NO_GENERATED_KEYS);
            st.setInt(1, obj.getProId());
            st.setString(2, obj.getProNome());
            st.setFloat(3, obj.getProPreco());
            st.setString(4, obj.getProCategoria());
            st.setInt(5, obj.getForId());
            st.setInt(6, obj.getEstQuantidade());
            st.setString(7, obj.getEstLocal());
            st.setDate(8,new java.sql.Date(obj.getEstDataEntrada().getTime()));
            st.setDate(9,new java.sql.Date(obj.getEstDataValidade().getTime()));
            st.setDate(10, new java.sql.Date(obj.getOrigemDataEntrada().getTime()));
            st.setDate(11, new java.sql.Date(obj.getOrigemDataValidade().getTime()));
            st.setInt(12, id);
            st.execute();
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
            st = conn.prepareStatement("SELECT delete_produto_estoque(?, ?, ?, ?)");
            st.setInt(1, obj.getProId());
            st.setDate(2, new java.sql.Date(obj.getOrigemDataEntrada().getTime()));
            st.setDate(3, new java.sql.Date(obj.getOrigemDataValidade().getTime()));
            st.setInt(4, id);
            st.execute();
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
    @Override
    public Estoque_Produto findById(Estoque_Produto obj) {
      try (PreparedStatement st = conn.prepareStatement("SELECT * FROM ver_estoque_produto WHERE estId = ?")) {
            st.setInt(1, obj.getEstId());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return pegaInfo(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }




    public Estoque_Produto pegaInfo(ResultSet rs) throws SQLException {
        Estoque_Produto estoque = new Estoque_Produto();
        estoque.setEstId(rs.getInt("estid"));
        estoque.setProNome(rs.getString("proNome"));
        estoque.setProPreco(rs.getFloat("proPreco"));
        estoque.setProCategoria(rs.getString("proCategoria"));
        estoque.setEstQuantidade(rs.getInt("estQuantidade"));
        estoque.setForRazaoSocial(rs.getString("forRazaoSocial"));
        estoque.setEstLocal(rs.getString("estLocal"));
        estoque.setEstDataEntrada(rs.getDate("estDataEntrada"));
        estoque.setEstDataValidade(rs.getDate("estDataValidade"));
        estoque.setProId(rs.getInt("produto_id"));

        return estoque;
    }
}
