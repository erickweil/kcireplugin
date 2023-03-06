/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.erickweil.spigot.kcire;

import br.erickweil.mysqlhelper.ConexaoDB;
import br.erickweil.mysqlhelper.HelperDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class ConexaoBanco {
	public static void main(String[] args) throws SQLException
	{
		try {
			declararDatabase("kcireserver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        public static HelperDB getDB(String serverName, String username, String passw, String Database) throws SQLException
        {  
            ConexaoDB._servername = serverName;
            ConexaoDB._database_name ="";
            ConexaoDB._loginusername=username;
            ConexaoDB._loginpassword=passw;
            declararDatabase(Database);
            ConexaoDB._database_name =Database;
            return new HelperDB();
        }
	
	public static boolean deleteDatabase(String Database) throws SQLException
	{
	HelperDB mydb = null;
		mydb = new HelperDB();
		
		ResultSet rs = mydb.ExecQuery("SHOW DATABASES");
		
		while (rs.next())
		{
			String name = rs.getString(1);
			if(name.equals(Database)) 
			{
				mydb.CloseQuery();
				mydb.Exec("DROP DATABASE "+Database);
				mydb.CloseConn();
				return true;
			}
		}
		mydb.CloseQuery();
		mydb.CloseConn();
		return false;
	}
        
        public static boolean checkDatabase(String Database) throws SQLException
	{		
		HelperDB mydb = null;
		mydb = new HelperDB();
		
		ResultSet rs = mydb.ExecQuery("SHOW DATABASES");
		
		while (rs.next())
		{
			String name = rs.getString(1);
			if(name.equals(Database)) 
			{
				mydb.CloseQuery();
				mydb.CloseConn();
				return true;
			}
		}
		mydb.CloseQuery();
		mydb.CloseConn();
		return false;
	}
	
	public static void declararDatabase(String Database) throws SQLException
	{
            boolean declarou = checkDatabase(Database);
            HelperDB mydb = null;
            mydb = new HelperDB();
            if(!declarou)
            {

                System.out.println("N�o Declarou a Database...\n Criando database "+Database);

                mydb.Exec("CREATE DATABASE "+Database);
            }

            mydb.Exec("USE "+Database);

            ResultSet rs = mydb.ExecQuery("SHOW TABLES");

            Set<String> tables_declaradas = new HashSet<>();
            while (rs.next())
            {
                    String name = rs.getString(1);
                    tables_declaradas.add(name);
            }
            mydb.CloseQuery();

            if(!tables_declaradas.contains("login"))
            {
                
                String query = "CREATE TABLE login(\n"+
                "id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"+
                "nick VARCHAR(255) NOT NULL,\n"+
                "senha VARCHAR(32) NOT NULL,\n"+
                "nivel_acesso INTEGER NOT NULL);\n"
                                ;

                mydb.Exec(query);
            }
            mydb.CloseConn();
		
	}
	
}
