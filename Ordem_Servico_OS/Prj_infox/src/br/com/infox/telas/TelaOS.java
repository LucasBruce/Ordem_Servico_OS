/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;
import br.com.infox.dal.ModuloConexao;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author lucas
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement statement = null;
    ResultSet result = null;

    //A linha abaixo cria uma variável para armazenar um texto de acordo com o radio button selecionado
    private String tipo;
    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
        
    }
    
    private void pesquisar_cliente(){
        String sql = "select id_cliente, nome_cliente, telefone_cliente"
                   + " from tbl_cliente where nome_cliente like ?";
        
        try{
            statement = conexao.prepareStatement(sql);
            statement.setString(1, txtClientePesquisar.getText() + "%");
            result = statement.executeQuery();
            tblCliente.setModel(DbUtils.resultSetToTableModel(result));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setar_campo(){
        int setar = tblCliente.getSelectedRow();
        txtIdCliente.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
    }
    
    //metodo para cadastrar ordem e serviço
    private void emitir_ordem_servico(){
        String sql = "insert into tbl_ordem_servico(tipo_ordem_servico,situacao_ordem_servico,"
                + "equipamento,defeito,servico,tecnico,valor,id_cliente) values(?,?,?,?,?,?,?,?)";
        
        try{
            statement = conexao.prepareStatement(sql);
            statement.setString(1, tipo);
            statement.setString(2, cboOrdemServicoSituacao.getSelectedItem().toString());
            statement.setString(3, txtOrdemServicoEquipamento.getText());
            statement.setString(4, txtOrdemServicoDefeito.getText());
            statement.setString(5, txtOrdemServicoServico.getText());
            statement.setString(6, txtOrdemServicoTecnico.getText());
            //.replace() substitui a vírgula pelo ponto
            statement.setString(7, txtOrdemServicoValor.getText().replace(",", "."));
            statement.setString(8, txtIdCliente.getText());
            
            if((txtOrdemServicoEquipamento.getText().isEmpty())
                    ||(txtOrdemServicoDefeito.getText().isEmpty())
                    ||(txtIdCliente.getText().isEmpty())){
                 
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                                    
            }else{
                
                int adicionado = statement.executeUpdate();
                System.out.println(adicionado);
                JOptionPane.showMessageDialog(null, "Ordem de serviço emitida com sucesso!");
            
                txtIdCliente.setText(null);
                txtOrdemServicoEquipamento.setText(null);
                txtOrdemServicoDefeito.setText(null);
                txtOrdemServicoServico.setText(null);
                txtOrdemServicoTecnico.setText(null);
                txtOrdemServicoValor.setText(null); 
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
//método para pesquisar ordem de serviço
    private void pesquisar_ordem_servico(){
       String numero_ordem_servico = JOptionPane.showInputDialog("Número da ordem de serviço?");
       String sql = "select * from tbl_ordem_servico where id_ordem_servico = "+numero_ordem_servico;
       
       try{
          statement = conexao.prepareStatement(sql);
          result = statement.executeQuery();
          
          if(result.next()){
              txtOrdemServico.setText(result.getString(1));
              txtData.setText(result.getString(2));
              //Setando os radio button
              String rbtTipo = result.getString(3);
              if(rbtTipo.equals("Ordem de Serviço")){
                  rbtOrdemServico.setSelected(true);
                  tipo = "Ordem de Serviço";
                  
              }else{
                  rbtOrcamento.setSelected(true);
                  tipo = "Orçamento";              
              }
              cboOrdemServicoSituacao.setSelectedItem(result.getString(4));
              txtOrdemServicoEquipamento.setText(result.getString(5));
              txtOrdemServicoDefeito.setText(result.getString(6));
              txtOrdemServicoServico.setText(result.getString(7));
              txtOrdemServicoTecnico.setText(result.getString(8));
              txtOrdemServicoValor.setText(result.getString(9));
              txtIdCliente.setText(result.getString(10));
              //desabilitando o botão adicionar, campo pesquisar cliente e tabela
              btnOrdemServicoAdicionar.setEnabled(false);
              txtClientePesquisar.setEnabled(false);
              tblCliente.setVisible(false);
              txtIdCliente.setEnabled(false);
              txtData.setEnabled(false);
              txtOrdemServico.setEnabled(false);
              
          }else{
              JOptionPane.showMessageDialog(null, "Ordem de serviço não cadastrada!");
          }
          
       }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
           JOptionPane.showMessageDialog(null, "Ordem de serviço inválida!");
           //System.out.println(e);
           
       }catch(Exception e1){
           JOptionPane.showMessageDialog(null, e1);
       }
    }
    
    //Método alterar ordem de serviço
    private void alterar_ordem_servico(){
        String sql = "update tbl_ordem_servico set tipo_ordem_servico=?,situacao_ordem_servico=?"
                + ",equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where id_ordem_servico=?";
        
        try{
            statement = conexao.prepareStatement(sql);
            statement.setString(1, tipo);
            statement.setString(2, cboOrdemServicoSituacao.getSelectedItem().toString());
            statement.setString(3, txtOrdemServicoEquipamento.getText());
            statement.setString(4, txtOrdemServicoDefeito.getText());
            statement.setString(5, txtOrdemServicoServico.getText());
            statement.setString(6, txtOrdemServicoTecnico.getText());
            statement.setString(7, txtOrdemServicoValor.getText().replace(",","."));
            statement.setString(8, txtOrdemServico.getText());
            
            if((txtOrdemServicoEquipamento.getText().isEmpty())||
               txtOrdemServicoDefeito.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                
            }else{
                int adicionado = statement.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Ordem de serviço alterada com sucesso!");
                    
                    txtIdCliente.setText(null);
                    txtData.setText(null);
                    txtOrdemServico.setText(null);
                    txtOrdemServicoDefeito.setText(null);
                    txtOrdemServicoEquipamento.setText(null);
                    txtOrdemServicoServico.setText(null);
                    txtOrdemServicoTecnico.setText(null);
                    txtOrdemServicoValor.setText(null);
                    //Habilitanto recursos desabilitados
                    btnOrdemServicoAdicionar.setEnabled(true);
                    txtClientePesquisar.setEnabled(true);
                    txtIdCliente.setEnabled(true);
                    tblCliente.setEnabled(true);
                }
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método remover ordem de serviço
    private void excluir_ordem_servico(){
        int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja"
                + " excluir esta ordem de serviço?","Atenção!",JOptionPane.YES_NO_OPTION);
        
        if(confirmacao == JOptionPane.YES_OPTION){     
            String sql = "delete from tbl_ordem_servico where id_ordem_servico=?";

            try{
                if(txtOrdemServico.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Pesquise uma ordem de serviço!");

                }else{
                    statement = conexao.prepareStatement(sql);
                    statement.setString(1, txtOrdemServico.getText());
                    int apagado = statement.executeUpdate();
                    
                    if(apagado > 0){
                        JOptionPane.showMessageDialog(null, "Ordem de serviço excluída com sucesso!");
                        txtIdCliente.setText(null);
                        txtData.setText(null);
                        txtOrdemServico.setText(null);
                        txtOrdemServicoDefeito.setText(null);
                        txtOrdemServicoEquipamento.setText(null);
                        txtOrdemServicoServico.setText(null);
                        txtOrdemServicoTecnico.setText(null);
                        txtOrdemServicoValor.setText(null);
                        /*Hibilitando a tblCliente, botão adicionar,
                        campo txtIdCliente, txtPesquisarCliente,
                        txtData e txtOrdemServico */
                        btnOrdemServicoAdicionar.setEnabled(true);
                        txtOrdemServico.setEnabled(true);
                        txtData.setEnabled(true);
                        txtIdCliente.setEnabled(true);
                        tblCliente.setVisible(true);
                        txtClientePesquisar.setEnabled(true);
                       
                        
                    }
                   

                }

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
           }
        }
     }
    private void imprimir_os(){
        // Imprimindo uma OS
       int confirma = JOptionPane.showConfirmDialog(null,"Confirma a impressão deste OS?","Atenção!", JOptionPane.YES_NO_OPTION); 
       
        if(confirma == JOptionPane.YES_OPTION){
           //Imprimindo o relatório com o framework JasperReports
            try{
               
                //usando a classe HashMap para ciar um filtro   
                HashMap filtro = new HashMap();
                filtro.put("os",Integer.parseInt(txtOrdemServico.getText()));
                
                //Usando a classe JasperPrint para preparar a impressão de um relatório
                JasperPrint print = JasperFillManager.fillReport("/home/lucas/reports/os.jasper",filtro,conexao);
                //A linha abaixo exibe o relatório através da classe JasperViewer
                JasperViewer.viewReport(print, false);
                
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOrdemServico = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrcamento = new javax.swing.JRadioButton();
        rbtOrdemServico = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOrdemServicoSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtClientePesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtOrdemServicoValor = new javax.swing.JTextField();
        txtOrdemServicoTecnico = new javax.swing.JTextField();
        txtOrdemServicoEquipamento = new javax.swing.JTextField();
        txtOrdemServicoDefeito = new javax.swing.JTextField();
        txtOrdemServicoServico = new javax.swing.JTextField();
        btnOrdemServicoAdicionar = new javax.swing.JButton();
        btnOrdemServicoPesquisar = new javax.swing.JButton();
        btnOrdemServicoAlterar = new javax.swing.JButton();
        btnOrdemServicoExcluir = new javax.swing.JButton();
        btnOrdemServicoImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(577, 461));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtOrdemServico.setEnabled(false);

        txtData.setFont(new java.awt.Font("DejaVu Sans", 1, 9)); // NOI18N
        txtData.setEnabled(false);

        buttonGroup1.add(rbtOrcamento);
        rbtOrcamento.setText("Orçamento");
        rbtOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOrdemServico);
        rbtOrdemServico.setText("Ordem de Serviço");
        rbtOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrdemServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtOrdemServico, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(rbtOrdemServico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtOrcamento)
                .addContainerGap())
        );

        jLabel3.setText("Situação:");

        cboOrdemServicoSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N

        txtClientePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientePesquisarKeyReleased(evt);
            }
        });

        jLabel5.setText("* Id");

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        jScrollPane5.setViewportView(tblCliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(txtClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        jLabel6.setText("* Equipamento");

        jLabel7.setText("* Defeito");

        jLabel8.setText("Técnico");

        jLabel9.setText("Serviço");

        jLabel10.setText("Valor Total");

        txtOrdemServicoValor.setText("0");

        txtOrdemServicoDefeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdemServicoDefeitoActionPerformed(evt);
            }
        });

        btnOrdemServicoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOrdemServicoAdicionar.setToolTipText("Adicionar");
        btnOrdemServicoAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrdemServicoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemServicoAdicionarActionPerformed(evt);
            }
        });

        btnOrdemServicoPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnOrdemServicoPesquisar.setToolTipText("Consultar");
        btnOrdemServicoPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrdemServicoPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemServicoPesquisarActionPerformed(evt);
            }
        });

        btnOrdemServicoAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnOrdemServicoAlterar.setToolTipText("Alterar");
        btnOrdemServicoAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrdemServicoAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemServicoAlterarActionPerformed(evt);
            }
        });

        btnOrdemServicoExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnOrdemServicoExcluir.setToolTipText("Remover");
        btnOrdemServicoExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrdemServicoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemServicoExcluirActionPerformed(evt);
            }
        });

        btnOrdemServicoImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/impressora.png"))); // NOI18N
        btnOrdemServicoImprimir.setToolTipText("Imprimir OS");
        btnOrdemServicoImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOrdemServicoImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdemServicoImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addComponent(cboOrdemServicoSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtOrdemServicoDefeito, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOrdemServicoServico, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtOrdemServicoTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtOrdemServicoValor, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtOrdemServicoEquipamento)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnOrdemServicoAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOrdemServicoPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOrdemServicoAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOrdemServicoExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOrdemServicoImprimir)))
                .addGap(255, 255, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboOrdemServicoSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOrdemServicoEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOrdemServicoDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOrdemServicoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtOrdemServicoTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtOrdemServicoValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnOrdemServicoExcluir, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnOrdemServicoAdicionar)
                            .addComponent(btnOrdemServicoPesquisar)
                            .addComponent(btnOrdemServicoAlterar)))
                    .addComponent(btnOrdemServicoImprimir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        setBounds(0, 0, 577, 461);
    }// </editor-fold>//GEN-END:initComponents

    private void txtClientePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientePesquisarKeyReleased
        // Chamando o método pesquisar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtClientePesquisarKeyReleased

    private void rbtOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoActionPerformed
        //atribuindo um texto a variável tipo se selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcamentoActionPerformed

    private void rbtOrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrdemServicoActionPerformed
        //a linha abaixo atribui a avariável tipo um texto se o radio button estiver selecionado
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rbtOrdemServicoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao iniciar o formulário o radio button orçamento é selecionado
        rbtOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOrdemServicoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemServicoAdicionarActionPerformed
        // Chamar o método emitir ordem de serviço
        emitir_ordem_servico();
    }//GEN-LAST:event_btnOrdemServicoAdicionarActionPerformed

    private void btnOrdemServicoPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemServicoPesquisarActionPerformed
        // Chamando o método ordem de serviço
        pesquisar_ordem_servico();
    }//GEN-LAST:event_btnOrdemServicoPesquisarActionPerformed

    private void btnOrdemServicoAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemServicoAlterarActionPerformed
        // chamando o método alterar:
        alterar_ordem_servico();
    }//GEN-LAST:event_btnOrdemServicoAlterarActionPerformed

    private void btnOrdemServicoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemServicoExcluirActionPerformed
        // Chamando o método remover ordem de serviço
        excluir_ordem_servico();
    }//GEN-LAST:event_btnOrdemServicoExcluirActionPerformed

    private void btnOrdemServicoImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdemServicoImprimirActionPerformed
        //chamando o método imprimir_os
        imprimir_os();
    }//GEN-LAST:event_btnOrdemServicoImprimirActionPerformed

    private void txtOrdemServicoDefeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdemServicoDefeitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrdemServicoDefeitoActionPerformed
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrdemServicoAdicionar;
    private javax.swing.JButton btnOrdemServicoAlterar;
    private javax.swing.JButton btnOrdemServicoExcluir;
    private javax.swing.JButton btnOrdemServicoImprimir;
    private javax.swing.JButton btnOrdemServicoPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOrdemServicoSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JRadioButton rbtOrcamento;
    private javax.swing.JRadioButton rbtOrdemServico;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtClientePesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtOrdemServico;
    private javax.swing.JTextField txtOrdemServicoDefeito;
    private javax.swing.JTextField txtOrdemServicoEquipamento;
    private javax.swing.JTextField txtOrdemServicoServico;
    private javax.swing.JTextField txtOrdemServicoTecnico;
    private javax.swing.JTextField txtOrdemServicoValor;
    // End of variables declaration//GEN-END:variables
}
