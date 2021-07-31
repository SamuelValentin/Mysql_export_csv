/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD_projeto1;

import java.sql.*; 
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 *
 * @author Samuel Valentin
 */
public class Consulta1 {
    
    public static void main(String[] args) throws Exception{
        String name, password, servidor, bd, table;
        int i;
        
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
        
        ResultSet rs, rs_tables;
        
        //Caso seja todas as tabelas
        if(table == " "){
            rs_tables = null;
            rs = null;
            //pega todo o banco de dados
            DatabaseMetaData meta = (DatabaseMetaData) con.getMetaData();
            rs_tables = meta.getTables(null, null, null, new String[] {
             "TABLE"
            });
            //pega a primeira tabela
            rs_tables.next(); 
            while (rs_tables.next()) {
                table = rs_tables.getString("TABLE_NAME");
                //System.out.println(table);
                
                //cria o arquivo
                PrintWriter pw = new PrintWriter(new File("C:\\Users\\Samuel Valentin\\Desktop\\"+bd+"-"+table+".csv"));
                StringBuilder sb=new StringBuilder();
                
                //faz a query de cada tabela
                rs = stmt.executeQuery("select * from "+ table);
                
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
                //numero de colunas
                int numberOfColumn = rsmd.getColumnCount();
                String ColumnName;
                
                //cria o cabecalho
                    for (i = 1; i <= numberOfColumn; i++){
                        if(i != 1)
                            sb.append(" , ");
                        ColumnName = rsmd.getColumnName(i);
                        sb.append(ColumnName);
                    }
                    sb.append("\r\n");
                    
                //preenche a tabela
                    while(rs.next()){
                        for (i = 1; i <= numberOfColumn; i++){
                            if(i != 1)
                                sb.append(" , ");
                            //System.out.println(rs.getString(i));
                            //caso o campo seja um char
                            if(rsmd.getColumnTypeName(i) == rsmd.getColumnTypeName(1)){
                                sb.append(" '' ");
                                sb.append(rs.getString(i));
                                sb.append(" '' ");
                            }else
                                sb.append(rs.getString(i));
                        }
                        sb.append("\r\n");
                    }
                //terminas de escrever no arquivo
                pw.write(sb.toString());
                //fecha o arquivo
                pw.close();
            }
        }else{
            // Caso seja uma tabela
            // Executar um Query
            rs = stmt.executeQuery("select * from "+ table);
            
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\Samuel Valentin\\Desktop\\"+bd+"-"+table+".csv"));
            StringBuilder sb=new StringBuilder();

            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int numberOfColumn = rsmd.getColumnCount();
            String ColumnName;

            //cria o cabecalho
                for (i = 1; i <= numberOfColumn; i++){
                    if(i != 1)
                        sb.append(" , ");
                    ColumnName = rsmd.getColumnName(i);
                    //System.out.println(ColumnName);
                    sb.append(ColumnName);
                }
                sb.append("\r\n");

            //preenche a tabela
            while(rs.next()){
                for (i = 1; i <= numberOfColumn; i++){
                    if(i != 1)
                        sb.append(" , ");
                    //System.out.println(rs.getString(i));
                    //caso o campo seja um char
                    sb.append(rs.getString(i));
                    if(rsmd.getColumnTypeName(i) == rsmd.getColumnTypeName(1)){
                        sb.append(" '' ");
                        sb.append(rs.getString(i));
                        sb.append(" '' ");
                    }else
                        sb.append(rs.getString(i));
                }
                sb.append("\r\n");
            }
            //terminas de escrever no arquivo
            pw.write(sb.toString());
            //fecha o arquivo
            pw.close();
        }
            
        // Fechar a conexão
        con.close();
        
    }
    
}
