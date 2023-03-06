package br.erickweil.mysqlhelper;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperDB {
	private Connection conn=null;
	private Statement LastStatement = null;
	public HelperDB()
	{
		this.conn = ConexaoDB.getConexaoMySQL();
	}
	public void CloseConn() throws SQLException
	{
		conn.close();
	}
	
	public void CloseQuery()
	{
		try {
			LastStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] GetFields(String table)
	{
		List<String> ret = new ArrayList<String>();
		try {
			ResultSet rs = ExecQuery("describe "+table);
			while (rs.next())
			{
				ret.add(rs.getString(1));
			}
			CloseQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] result = new String[ret.size()];
		result = ret.toArray(result);
		return result;
	}
	public String[] ShowTables()
	{
		List<String> ret = new ArrayList<String>();
		try {
			ResultSet rs = ExecQuery("show tables");
			while (rs.next())
			{
				ret.add(rs.getString(1));
			}
			CloseQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] result = new String[ret.size()];
		result = ret.toArray(result);
		return result;
	}
	public void simpleExec(String comand)
	{
		try {
			Exec(comand);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int simpleColumnSelect(String comand,int def)
	{
		try {
			ResultSet rs = ExecQuery(comand);
			if(rs.next())
			{
				def = rs.getInt(1); // o id da coluna é baseado em 1
			}
			CloseQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return def;
	}
	
	public long simpleColumnSelect(String comand,long def)
	{
		try {
			ResultSet rs = ExecQuery(comand);
			if(rs.next())
			{
				def = rs.getLong(1); // o id da coluna é baseado em 1
			}
			CloseQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return def;
	}
	
	public String simpleColumnSelect(String comand,String def)
	{
		try {
			ResultSet rs = ExecQuery(comand);
			if(rs.next())
			{
				def = rs.getString(1); // o id da coluna é baseado em 1
			}
			CloseQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return def;
	}
	
	public void Exec(String comand) throws SQLException
	{
			if(LastStatement!=null&&!LastStatement.isClosed())
			{
				System.err.println("Esqueceu de fechar o statement");
				 CloseQuery();
			}
				
			// create the java statement
			LastStatement = conn.createStatement();

			// execute the query, and get a java resultset
			LastStatement.execute(comand);
			
			LastStatement.close();
	}
	public ResultSet ExecQuery(String query) throws SQLException
	{
			if(LastStatement!=null&&!LastStatement.isClosed())
			{
				System.err.println("Esqueceu de fechar o statement");
				 CloseQuery();
			}
			// create the java statement
			LastStatement = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet ret = LastStatement.executeQuery(query);
			//LastStatement.closeOnCompletion();
			return ret;
	}
	public long Insert(String query,String table) throws SQLException
	{
		if(LastStatement!=null&&!LastStatement.isClosed())
		{
			System.err.println("Esqueceu de fechar o statement");
			 CloseQuery();
		}
			
		// create the java statement
		LastStatement = conn.createStatement();

		// execute the query, and get a java resultset
		LastStatement.execute(query);
		
		ResultSet rs = LastStatement.executeQuery("select last_insert_id() as last_id from "+table);
		rs.next();
		long lastid = rs.getLong("last_id");
		
		LastStatement.close();
		return lastid;
	}
	
	public List<Map<String,Object>> getResult(String Query) throws SQLException
	{
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		ResultSet rs = ExecQuery(Query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int nCols = rsmd.getColumnCount();
		String[] colunas = new String[nCols];
		int[] tipos_colunas = new int[nCols];
		for(int i=0;i<nCols;i++)
		{
			colunas[i] = rsmd.getColumnLabel(i+1);
			tipos_colunas[i] = rsmd.getColumnType(i+1);
		}
		
		while (rs.next())
		{
			Map<String,Object> row = new HashMap<>();
			for(int i=0;i<nCols;i++)
			{
				String colName = colunas[i];
				int colType = tipos_colunas[i];
				row.put(colName, rs.getObject(i+1)); // o id da coluna é baseado em 1
			}
			ret.add(row);
		}
		CloseQuery();
		return ret;
	}
	
	public List<Tabela> GetTableList(String Query,Tabela REF) throws SQLException
	{
			List<Tabela> ret = new ArrayList<Tabela>();
			ResultSet rs = ExecQuery(Query);
				while (rs.next())
				{
					Tabela e = REF.Novo(this);
					e.SetFromQuery(rs);
					ret.add(e);
				}
				CloseQuery();
			return ret;
	}
	public List<Tabela> GetTableList(Tabela REF) throws SQLException
	{
			List<Tabela> ret = new ArrayList<Tabela>();
			ResultSet rs = ExecQuery(REF.GetQuery());
				while (rs.next())
				{
					Tabela e = REF.Novo(this);
					e.SetFromQuery(rs);
					ret.add(e);
				}
				CloseQuery();
			return ret;
	}
	
    public static String escapeString(String x, boolean escapeDoubleQuotes) {
        StringBuilder sBuilder = new StringBuilder(x.length() * 11/10);

        int stringLength = x.length();

        for (int i = 0; i < stringLength; ++i) {
            char c = x.charAt(i);

            switch (c) {
            case 0: /* Must be escaped for 'mysql' */
                sBuilder.append('\\');
                sBuilder.append('0');

                break;

            case '\n': /* Must be escaped for logs */
                sBuilder.append('\\');
                sBuilder.append('n');

                break;

            case '\r':
                sBuilder.append('\\');
                sBuilder.append('r');

                break;

            case '\\':
                sBuilder.append('\\');
                sBuilder.append('\\');

                break;

            case '\'':
                sBuilder.append('\\');
                sBuilder.append('\'');

                break;

            case '"': /* Better safe than sorry */
                if (escapeDoubleQuotes) {
                    sBuilder.append('\\');
                }

                sBuilder.append('"');

                break;

            case '\032': /* This gives problems on Win32 */
                sBuilder.append('\\');
                sBuilder.append('Z');

                break;

            case '\u00a5':
            case '\u20a9':
                // escape characters interpreted as backslash by mysql
                // fall through

            default:
                sBuilder.append(c);
            }
        }

        return sBuilder.toString();
    }
}
