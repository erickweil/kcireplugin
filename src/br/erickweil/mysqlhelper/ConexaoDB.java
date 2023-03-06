package br.erickweil.mysqlhelper;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Classes necessárias para uso de Banco de dados //

//Início da classe de conexão//
public class ConexaoDB {
	public static String status = "Não conectou...";
	public static String _servicename = "MySQL56";
	public static String _drivername ="com.mysql.cj.jdbc.Driver";// "com.mysql.jdbc.Driver";
	public static String _servername = "localhost";
	public static String _database_name ="teste";
	public static String _loginusername="root";
	public static String _loginpassword="123456";
	public static volatile boolean Conectou=false;
	//Método Construtor da Classe//
	public ConexaoDB() {
		
	}



	//Método de Conexão//
	public static Connection getConexaoMySQL() {
		Connection connection = null;          //atributo do tipo Connection
		Conectou = false;
		try {
			//Carregando o JDBC Driver padrão
			String driverName = _drivername;                        
			Class.forName(driverName);
			//Configurando a nossa conexão com um banco de dados//
			String serverName = _servername;    //caminho do servidor do BD
			String mydatabase = _database_name;        //nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase+"?useTimezone=true&serverTimezone=UTC";
			String username = _loginusername;        //nome de um usuário de seu BD      
			String password = _loginpassword;      //sua senha de acesso
			connection = DriverManager.getConnection(url, username, password);
			//Testa sua conexão//  
			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
				Conectou = true;
			} else {
				status = ("STATUS--->Não foi possivel realizar conexão");
			}
			return connection;
		} catch (ClassNotFoundException e) {  //Driver não encontrado
			System.out.println("O driver expecificado nao foi encontrado.");
			e.printStackTrace();
			return null;
		} catch (SQLException e) { //Não conseguindo se conectar ao banco
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			e.printStackTrace();
			return null;
		}
	}
	//Método que retorna o status da sua conexão//
	public static String statusConection() {
		return status;
	}
	//Método que fecha sua conexão//
	public static boolean FecharConexao() {
		try {
			ConexaoDB.getConexaoMySQL().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	//Método que reinicia sua conexão//
	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return ConexaoDB.getConexaoMySQL();

	}

}