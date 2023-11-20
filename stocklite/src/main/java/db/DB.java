package db;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.dao.implementations.FuncionarioDaoJDBC;
import model.entities.Funcionario;

public class DB extends JFrame {

    private static Connection conn = null;
    private static int id = 0;

    public static Connection getConnection() throws ClassNotFoundException {
        String dbUrl = "jdbc:postgresql://viaduct.proxy.rlwy.net:14335/railway";
        String pgPassword = "*Ed64E4AA4e-CbaCgf1DCcC2c316acaa";
        String pgUser = "postgres";

        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(dbUrl, pgUser, pgPassword);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        if (id == 0) {
            new DB().fazerLogin();
        }

        if (id > 0) {
            return conn;
        }
        return null;
    }

    public static int getId() {
        return id;
    }

    private void fazerLogin() {
    boolean loginBemSucedido = false;

    while (!loginBemSucedido) {
        try {
            JTextField txtCpf = new JTextField();
            JPasswordField txtSenha = new JPasswordField();

            Object[] fields = {
                "CPF:", txtCpf,
                "Senha:", txtSenha
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Login", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String cpf = txtCpf.getText();
                String senha = new String(txtSenha.getPassword());

                Funcionario func = new Funcionario();
                func.setFunCpf(cpf);
                id = new FuncionarioDaoJDBC(conn).logaFuncionario(func, senha);

                if (id > 0) {
                    JOptionPane.showMessageDialog(this, "Login bem-sucedido! ID: " + id);
                    loginBemSucedido = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Login falhou. CPF ou senha incorretos.");
                }
            } else {
                break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao fazer login: " + ex.getMessage());
        }
    }
}


    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}