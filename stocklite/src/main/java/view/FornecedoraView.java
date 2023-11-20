package view;

import auxiliar.Pair;
import db.DB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.dao.FornecedoraDao;
import model.dao.TransportadoraDao;
import model.dao.implementations.FornecedoraDaoJDBC;
import model.dao.implementations.TransportadoraDaoJDBC;
import model.entities.Fornecedora;


public class FornecedoraView extends javax.swing.JFrame {

 private static FornecedoraView fornUnic;

    private FornecedoraView() {
        initComponents();
     try {
         popularComboBox();
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(FornecedoraView.class.getName()).log(Level.SEVERE, null, ex);
     }
    }

    public static FornecedoraView getForn() {
        if (fornUnic == null) {
            fornUnic = new FornecedoraView();
        }
        return fornUnic;
    }

    public void mostrar() {
        this.setVisible(true);
        try {
            popularComboBox();
            preencherTabela();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransportadoraView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void preencherTabela() throws ClassNotFoundException {
        FornecedoraDao fornecedoraDao = new FornecedoraDaoJDBC(DB.getConnection());
        List<Fornecedora> fornecedoras = fornecedoraDao.findAll();

        DefaultTableModel model = (DefaultTableModel) tabelaFornecedora.getModel();
        model.setRowCount(0);
        
        for (Fornecedora fornecedora : fornecedoras) {
            Object[] row = {
                fornecedora.getForId(),
                fornecedora.getForCnpj(),
                fornecedora.getForRazaoSocial(),
                fornecedora.getForEmail(),
                fornecedora.getForTelefone(),
                fornecedora.getForLogradouro(),
                fornecedora.getForNumero(),
                fornecedora.getForCep(),
                fornecedora.getForCidade(),
                fornecedora.getForEstado(),
                fornecedora.getTraId(),
                "Alterar",
                "Excluir"
                
            };
            model.addRow(row);
        }
    }
    
    private void popularComboBox() throws ClassNotFoundException {
        TransportadoraDao transportadoraDao = new TransportadoraDaoJDBC(DB.getConnection());
        List<Pair<Integer, String>> transportadoras = transportadoraDao.findAllForCombobox();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (Pair<Integer, String> transportadora : transportadoras) {
            model.addElement(transportadora.getSecond());
        }

        jComboBoxTransportadoras.setModel(model);
    }
    
    private void adicionarFornecedora() {
        try {
            TransportadoraDao transportadoraDao = new TransportadoraDaoJDBC(DB.getConnection());
            List<Pair<Integer, String>> fornecedores = transportadoraDao.findAllForCombobox();
            int idTransportadora=0;
            String origem = (String) jComboBoxTransportadoras.getSelectedItem();
            for(Pair<Integer, String> fornecedor : fornecedores){
                if(fornecedor.getSecond().equals(origem)){
                    idTransportadora= fornecedor.getFirst();
                }
            }
            
            String cnpj = jTextFieldCnpj.getText();
            String razaoSocial = jTextFieldRazaoSocial.getText();
            String email = jTextFieldEmail.getText();
            String telefone = jTextFieldTelefone.getText();
            String logradouro = jTextFieldLogradouro.getText();
            int numero = Integer.parseInt(jTextFieldNumero.getText());
            int cep = Integer.parseInt(jTextFieldCep.getText());
            String cidade = jTextFieldCidade.getText();
            String estado = jTextFieldEstado.getText();
            int traId = idTransportadora;
            
            Fornecedora novaFornecedora = new Fornecedora();
            novaFornecedora.setForCnpj(cnpj);
            novaFornecedora.setForRazaoSocial(razaoSocial);
            novaFornecedora.setForEmail(email);
            novaFornecedora.setForTelefone(telefone);
            novaFornecedora.setForLogradouro(logradouro);
            novaFornecedora.setForNumero(numero);
            novaFornecedora.setForCep(cep);
            novaFornecedora.setForCidade(cidade);
            novaFornecedora.setForEstado(estado);
            novaFornecedora.setTraId(traId);

            FornecedoraDao fornecedoraDao = new FornecedoraDaoJDBC(DB.getConnection());
            fornecedoraDao.insert(novaFornecedora);
            
            limparCampos();
            preencherTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido. Certifique-se de fornecer valores numéricos para campos como Número e CEP.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar a transportadora: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        jTextFieldCnpj.setText("");
        jTextFieldRazaoSocial.setText("");
        jTextFieldEmail.setText("");
        jTextFieldTelefone.setText("");
        jTextFieldLogradouro.setText("");
        jTextFieldNumero.setText("");
        jTextFieldCep.setText("");
        jTextFieldCidade.setText("");
        jTextFieldEstado.setText("");
    }
    
    private void editarFornecedora() {
    try {
        int selectedRow = tabelaFornecedora.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma transportadora para editar.");
            return;
        }
        int traId = (int) tabelaFornecedora.getValueAt(selectedRow, 0);
        FornecedoraDao fornecedoraDao = new FornecedoraDaoJDBC(DB.getConnection());
        
        Fornecedora fornecedoraExistente = new Fornecedora();
        fornecedoraExistente.setForId(traId);
        fornecedoraExistente = fornecedoraDao.findById(fornecedoraExistente);
        Fornecedora novaFornecedora = new Fornecedora();

        JTextField txtCnpj = new JTextField(fornecedoraExistente.getForCnpj());
        JTextField txtRazaoSocial = new JTextField(fornecedoraExistente.getForRazaoSocial());
        JTextField txtEmail = new JTextField(fornecedoraExistente.getForEmail());
        JTextField txtTelefone = new JTextField(fornecedoraExistente.getForTelefone());
        JTextField txtLogradouro = new JTextField(fornecedoraExistente.getForLogradouro());
        JTextField txtNumero = new JTextField(String.valueOf(fornecedoraExistente.getForNumero()));
        JTextField txtCep = new JTextField(String.valueOf(fornecedoraExistente.getForCep()));
        JTextField txtCidade = new JTextField(fornecedoraExistente.getForCidade());
        JTextField txtEstado = new JTextField(fornecedoraExistente.getForEstado());

        // Crie um array de objetos com os campos de texto
        Object[] fields = {
            "CNPJ:", txtCnpj,
            "Razão Social:", txtRazaoSocial,
            "Email:", txtEmail,
            "Telefone:", txtTelefone,
            "Logradouro:", txtLogradouro,
            "Número:", txtNumero,
            "CEP:", txtCep,
            "Cidade:", txtCidade,
            "Estado:", txtEstado
        };

        // Exiba o popup para editar
        int result = JOptionPane.showConfirmDialog(this, fields, "Editar Transportadora", JOptionPane.OK_CANCEL_OPTION);

        // Se o usuário pressionar OK, atualize a transportadora
        if (result == JOptionPane.OK_OPTION) {
            novaFornecedora.setForId(traId);
            novaFornecedora.setForCnpj(txtCnpj.getText());
            novaFornecedora.setForRazaoSocial(txtRazaoSocial.getText());
            novaFornecedora.setForEmail(txtEmail.getText());
            novaFornecedora.setForTelefone(txtTelefone.getText());
            novaFornecedora.setForLogradouro(txtLogradouro.getText());
            novaFornecedora.setForNumero(Integer.parseInt(txtNumero.getText()));
            novaFornecedora.setForCep(Integer.parseInt(txtCep.getText()));
            novaFornecedora.setForCidade(txtCidade.getText());
            novaFornecedora.setForEstado(txtEstado.getText());

            // Atualize a transportadora no banco de dados
            fornecedoraDao.update(novaFornecedora);

            // Atualize a tabela após a edição
            preencherTabela();
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Número inválido. Certifique-se de fornecer valores numéricos para campos como Número e CEP.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao editar a transportadora: " + ex.getMessage());
    }
}   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelCnpj = new javax.swing.JLabel();
        jLabelRazaoSocial = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelTelefone = new javax.swing.JLabel();
        jLabelLogradouro = new javax.swing.JLabel();
        jLabelNumero = new javax.swing.JLabel();
        jLabelCep = new javax.swing.JLabel();
        jLabelCidade = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();
        jTextFieldCnpj = new javax.swing.JTextField();
        jTextFieldRazaoSocial = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldTelefone = new javax.swing.JTextField();
        jTextFieldLogradouro = new javax.swing.JTextField();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldCep = new javax.swing.JTextField();
        jTextFieldCidade = new javax.swing.JTextField();
        jTextFieldEstado = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaFornecedora = new javax.swing.JTable();
        jComboBoxTransportadoras = new javax.swing.JComboBox<>();
        jLabelTransportadora = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(1600, 900));

        jLabelCnpj.setText("CNPJ :");

        jLabelRazaoSocial.setText("Razão Social : ");

        jLabelEmail.setText("E-mail : ");

        jLabelTelefone.setText("Telefone : ");

        jLabelLogradouro.setText("Logradouro : ");

        jLabelNumero.setText("Número :");

        jLabelCep.setText("CEP : ");

        jLabelCidade.setText("Cidade : ");

        jLabelEstado.setText("Estado :");

        jTextFieldRazaoSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRazaoSocialActionPerformed(evt);
            }
        });

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCadastrarMouseClicked(evt);
            }
        });

        tabelaFornecedora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CNPJ", "Razão Social", "Email", "Telefone", "Logradouro", "Numero", "CEP", "Cidade", "Estado", "ID Transportadora", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaFornecedora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFornecedoraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaFornecedora);

        jComboBoxTransportadoras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelTransportadora.setText("Transportadora: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCnpj)
                            .addComponent(jLabelEmail))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelRazaoSocial))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelTelefone))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelLogradouro)
                                    .addComponent(jLabelCep))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabelEstado))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCidade)
                            .addComponent(jLabelNumero)
                            .addComponent(jLabelTransportadora))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxTransportadoras, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextFieldRazaoSocial, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextFieldNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 572, Short.MAX_VALUE)
                .addComponent(btnCadastrar)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCnpj)
                    .addComponent(jTextFieldCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRazaoSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefone)
                    .addComponent(jTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogradouro)
                    .addComponent(jTextFieldLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNumero)
                    .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCep)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelCidade)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEstado)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCadastrar)
                        .addComponent(jLabelTransportadora)
                        .addComponent(jComboBoxTransportadoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldRazaoSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRazaoSocialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRazaoSocialActionPerformed

    private void btnCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCadastrarMouseClicked
        adicionarFornecedora();
    }//GEN-LAST:event_btnCadastrarMouseClicked

    private void tabelaFornecedoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFornecedoraMouseClicked
        try {
            int row = tabelaFornecedora.rowAtPoint(evt.getPoint());
            int col = tabelaFornecedora.columnAtPoint(evt.getPoint());
            Fornecedora fornecedora = new Fornecedora();
            FornecedoraDao fornecedoraDao = new FornecedoraDaoJDBC(DB.getConnection());
            if (col == tabelaFornecedora.getColumnCount() - 2) {
                editarFornecedora();
            } else if (col == tabelaFornecedora.getColumnCount() - 1) {
                int traId = (int) tabelaFornecedora.getValueAt(row, 0);
                System.out.print(traId);
                fornecedora.setForId(traId);
                fornecedoraDao.delete(fornecedora);
            }
            preencherTabela();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FornecedoraView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaFornecedoraMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JComboBox<String> jComboBoxTransportadoras;
    private javax.swing.JLabel jLabelCep;
    private javax.swing.JLabel jLabelCidade;
    private javax.swing.JLabel jLabelCnpj;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelLogradouro;
    private javax.swing.JLabel jLabelNumero;
    private javax.swing.JLabel jLabelRazaoSocial;
    private javax.swing.JLabel jLabelTelefone;
    private javax.swing.JLabel jLabelTransportadora;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldCep;
    private javax.swing.JTextField jTextFieldCidade;
    private javax.swing.JTextField jTextFieldCnpj;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldEstado;
    private javax.swing.JTextField jTextFieldLogradouro;
    private javax.swing.JTextField jTextFieldNumero;
    private javax.swing.JTextField jTextFieldRazaoSocial;
    private javax.swing.JTextField jTextFieldTelefone;
    private javax.swing.JTable tabelaFornecedora;
    // End of variables declaration//GEN-END:variables
}
