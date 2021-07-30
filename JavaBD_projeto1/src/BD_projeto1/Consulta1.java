/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD_projeto1;

import java.sql.*; 
import java.util.Scanner;

/**
 *
 * @author Samuel Valentin
 */
public class Consulta1 {
    
    public static void main(String[] args) throws Exception{
        String name, password, servidor, bd, table;
        // Carregar o driver Mysql
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Pega parametros pela linha de comando
        Scanner ler = new Scanner(System.in);
        System.out.printf("Servidor:\n");
        servidor = ler.next();//localhost
        System.out.printf("Usuario:\n");
        name = ler.next();
        System.out.printf("Senha:\n");
        password = ler.next();
        System.out.printf("Banco de Dados:\n");
        bd = ler.next();//university
        System.out.printf("Table:\n");
        table = ler.next();//university
        
        // Iniciar conexao com o servidor
        Connection con = DriverManager.getConnection("jdbc:mysql://" + servidor +"/" + bd , name, password);
        // Criar um statement
        Statement stmt = con.createStatement();
        
        // Executar um Query
        ResultSet rs = stmt.executeQuery("select * from " + table);
        while(rs.next()){
            System.out.println(rs.getString("dept_name"));
        }
        
        // Fechar a conexão
        con.close();
        
    }
    
}
