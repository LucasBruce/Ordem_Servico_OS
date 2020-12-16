/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
//A linha a baixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
/**
 *
 * @author lucas
 */
public class TelaCliente extends javax.swing.JInternalFrame {
Connection conexao = null;
PreparedStatement statement = null;
ResultSet result = null;
    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
   private void adicionar(){
   String sql = "insert into tbl_cliente(nome_cliente, email_cliente, endereco_cliente, telefone_cliente) values(?,?,?,?)";
   
   try{
     statement = conexao.prepareStatement(sql);
     statement.setString(1, txtNomeCliente.getText());
     statement.setString(2, txtEmailCliente.getText());
     statement.setString(3, txtEnderecoCliente.getText());
     statement.setString(4, txtTelefoneCliente.getText());
     
     if((txtNomeCliente.getText().isEmpty())||(txtTelefoneCliente.getText().isEmpty())){
         JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
     }else{
         int adicionado = statement.executeUpdate();
         System.out.println(adicionado);
         if(adicionado > 0){
             JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
             txtNomeCliente.setText(null);
             txtEmailCliente.setText(null);
             txtEnderecoCliente.setText(null);
             txtTelefoneCliente.setText(null);
         }
     }
   }catch(Exception e){
     JOptionPane.showMessageDialog(null, e);
   }
   }
   
  private void pesquisar_cliente(){
      String sql = "select * from tbl_cliente where nome_cliente like ?";
      
      try{
         statement = conexao.prepareStatement(sql);
         //passando o conteúdo da caixa de pesquisa para o ?
         //atenção para '%' - continuação da string sql
         statement.setString(1, txtPesquisarCliente.getText() + "%");
         result = statement.executeQuery();
         //A linha a baixo utiliza um recurso da biblioteca rs2xml.jar para preencher a tabela          
         tblCliente.setModel(DbUtils.resultSetToTableModel(result));
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      }
  }
  
  //Método para setar campos do formulários com conteúdo da tabela
  private void setar_campo(){
      int setar = tblCliente.getSelectedRow();
      txtIdCliente.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
      txtNomeCliente.setText(tblCliente.getModel().getValueAt(setar, 1).toString());
      txtEmailCliente.setText(tblCliente.getModel().getValueAt(setar, 2).toString());
      txtEnderecoCliente.setText(tblCliente.getModel().getValueAt(setar, 3).toString());
      txtTelefoneCliente.setText(tblCliente.getModel().getValueAt(setar, 4).toString());
      //A linha a baixo desativa o botão adicionar
      btnCreateCliente.setEnabled(false);
  }
  
  //Método para Alterar dados do cliente em formulário
  private void alterar(){
      String sql = "update tbl_cliente set nome_cliente=?, email_cliente=?, endereco_cliente=?, telefone_cliente=? where id_cliente=?";
      try{
          statement = conexao.prepareStatement(sql);
          statement.setString(1, txtNomeCliente.getText());
          statement.setString(2, txtEmailCliente.getText());
          statement.setString(3, txtEnderecoCliente.getText());
          statement.setString(4, txtTelefoneCliente.getText());
          statement.setString(5, txtIdCliente.getText());
          
          if((txtNomeCliente.getText().isEmpty())||(txtTelefoneCliente.getText().isEmpty())){
              JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
              
          }else{
              int alterado = statement.executeUpdate();
              System.out.println(alterado);
              
              if(alterado > 0){
                JOptionPane.showMessageDialog(null, "Dados do cliente alteradas com sucesso!");
                txtNomeCliente.setText(null);
                txtEnderecoCliente.setText(null);
                txtTelefoneCliente.setText(null);
                txtEmailCliente.setText(null);
                //A linha a baixo ativa o botão adicionar
                btnCreateCliente.setEnabled(true);
              }
          }
          
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      }
  }
  //Método que remove usuário
  private void remover(){
      
      int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
      
      if(confirmar == JOptionPane.YES_OPTION){
         String sql = "delete from tbl_cliente where id_cliente=?";
         
         try{
             statement = conexao.prepareStatement(sql);
             statement.setString(1, txtIdCliente.getText());
             int confirmado = statement.executeUpdate();
             
             if(confirmado > 0){
                 JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                 txtIdCliente.setText(null);
                 txtNomeCliente.setText(null);
                 txtEnderecoCliente.setText(null);
                 txtTelefoneCliente.setText(null);
                 txtEmailCliente.setText(null);
             }
             
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPesquisarCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        txtEnderecoCliente = new javax.swing.JTextField();
        txtTelefoneCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        btnDeleteCliente = new javax.swing.JButton();
        btnCreateCliente = new javax.swing.JButton();
        btnUpdateCliente = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(577, 461));

        jLabel1.setText("* Campos obrigatórios");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N

        txtPesquisarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarClienteKeyReleased(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        jLabel3.setText("* Nome");

        jLabel4.setText("Endereco");

        jLabel5.setText("*Telefone");

        jLabel6.setText("email");

        btnDeleteCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnDeleteCliente.setToolTipText("Remover");
        btnDeleteCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteCliente.setPreferredSize(new java.awt.Dimension(70, 70));
        btnDeleteCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClienteActionPerformed(evt);
            }
        });

        btnCreateCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnCreateCliente.setToolTipText("Adicionar");
        btnCreateCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateCliente.setPreferredSize(new java.awt.Dimension(70, 70));
        btnCreateCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateClienteActionPerformed(evt);
            }
        });

        btnUpdateCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUpdateCliente.setToolTipText("Alterar");
        btnUpdateCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdateCliente.setPreferredSize(new java.awt.Dimension(70, 70));
        btnUpdateCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateClienteActionPerformed(evt);
            }
        });

        jLabel7.setText("Id Cliente");

        txtIdCliente.setEnabled(false);
        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(btnCreateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(btnUpdateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnDeleteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPesquisarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(81, 81, 81)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeCliente)
                            .addComponent(txtEnderecoCliente)
                            .addComponent(txtEmailCliente)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(8, 8, 8)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCreateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setBounds(0, 0, 577, 461);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateClienteActionPerformed
        // A linha a baixo chama o método alterar
        alterar();
    }//GEN-LAST:event_btnUpdateClienteActionPerformed

    private void btnCreateClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateClienteActionPerformed
        //Chamando o metódo adicionar
        adicionar();
    }//GEN-LAST:event_btnCreateClienteActionPerformed
    //O evento a baixo é do tipo "enquanto estiver digitando"
    private void txtPesquisarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarClienteKeyReleased
        //chamar o método pesquisar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtPesquisarClienteKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
       //chamar o método setar_campo
       setar_campo();
    }//GEN-LAST:event_tblClienteMouseClicked

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void btnDeleteClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClienteActionPerformed
        //Chamando o método remover
        remover();
    }//GEN-LAST:event_btnDeleteClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateCliente;
    private javax.swing.JButton btnDeleteCliente;
    private javax.swing.JButton btnUpdateCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEnderecoCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtPesquisarCliente;
    private javax.swing.JTextField txtTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
