/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.*;
/**
 *
 * @author lucas
 */
public class ModuloConexao {
    
    //metodo responsavel por estabelecer a conexão com o banco
   public static Connection conector(){
       
        java.sql.Connection conexao = null;
        
        //a linha abaixo chama o driver
        String driver = "com.mysql.jdbc.Driver";
        
        //Armazenanso informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/db_infox";
        
        String usuario = "root";
        
        String senha = "";
        
        //estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            return conexao;
            
        } catch (Exception e) {
            //a linha abaixo serve de apoio para esclarecer o erro
            System.out.println(e);
            return null;
        }
    }
}
