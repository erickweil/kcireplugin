package br.erickweil.mysqlhelper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Tabela {
	public HelperDB db;
	public Tabela(HelperDB db)
	{
		this.db = db;
	}
	public void SetFromQuery(ResultSet rs) throws SQLException
	{
		
	}
	public Tabela Novo(HelperDB d)
	{
	return new Tabela(d);	
	}
	public String GetQuery()
	{
		return null;
	}
	public String defin()
	{
		return "";
	}
	public long getId()
	{
		return -1;
	}
	public String[] arraydefin()
	{
		return null;
	}
}
