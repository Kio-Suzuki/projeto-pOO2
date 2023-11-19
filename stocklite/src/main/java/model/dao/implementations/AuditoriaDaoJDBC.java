package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.entities.Auditoria;
import db.DB;
import db.DbException;
import model.entities.Auditoria;
import model.entities.AuditoriaDao;

public class AuditoriaDaoJDBC implements AuditoriaDao {

    private Connection conn;

    public AuditoriaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Auditoria> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM auditoria ORDER BY audId");

            rs = st.executeQuery();
            List<Auditoria> list = new ArrayList<>();

            while (rs.next()) {
                Auditoria auditoria = pegaInfo(rs);
                list.add(auditoria);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Auditoria pegaInfo(ResultSet rs) throws SQLException {
        int audId = rs.getInt("audId");
        int funId = rs.getInt("funId");
        int proId = rs.getInt("proId");
        Date datahora = rs.getTimestamp("datahora");

        return new Auditoria(audId, funId, proId, datahora);
    }
}
