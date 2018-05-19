package persistencia;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class ManejoDatos {
	private Conexion con;
	private boolean estado = true;
	
	public boolean isEstado() {
		return estado;
	}

	public ManejoDatos(){
		con = new Conexion();
	}
	
	public boolean delete(String tabla, String condicion) {
		estado = true;
		Connection c = con.conectar();
		String query = "DELETE FROM " + tabla + " WHERE " + condicion;	
		try {
			Statement st = c.createStatement();
			st.execute(query);
		}
		catch(Exception e){
			estado = false;
			e.printStackTrace();
		}finally {
			con.desconectar();
		}
		return estado;
	}
	
	
	public String insertar(String tabla, String campos, String valores) {
		estado = true;
		String s = "Se inserto la fila correctamente";
		Connection c = con.conectar();
		String query = "insert into " + tabla + " (" + campos + ") values (" + valores + ")";	
		System.out.println(query);
		try {
			Statement st = c.createStatement();
			st.execute(query);
		}
		catch(Exception e){
			e.printStackTrace();
			estado = false;
			s = "Error al insertar la fila"; 
		}finally {
			con.desconectar();
		}
		return s;
		
	}
	
	public ArrayList<Hashtable<String, String>> select(String tabla, String campos) {
		estado = true;
		ArrayList<Hashtable<String,String>> res = new ArrayList<>();
		Connection c = con.conectar();
		try {
			Statement st = c.createStatement();
			//System.out.println("select " + campos + " from " + tabla);
			ResultSet resultSet = st.executeQuery("select " + campos + " from " + tabla);
			String[] fields = this.getCampos(resultSet, campos);
			//System.out.println(fields[1]);
			while (resultSet.next())
			{
				Hashtable<String, String> reg = new Hashtable<String, String>();
				for (String s : fields){
					reg.put(s, resultSet.getString(s));
				}
				res.add(reg);		 
			}
		} catch (SQLException e) {
			estado = false;
			e.printStackTrace();
		}finally {
			c = null;
			con.desconectar();
		}
		return res;
	}
	
	public ArrayList<Hashtable<String, String>> select(String tabla, String campos, String condicion){
		estado = true;
		ArrayList<Hashtable<String,String>> res = new ArrayList<>();
		Connection c = con.conectar();
		try {
			Statement st = c.createStatement();
			String query = "select " + campos + " from " + tabla + " where " + condicion;
			System.out.println(query);
			ResultSet resultSet = st.executeQuery(query);
			System.out.println("Cantidad de filas: " + resultSet);
			String[] fields = this.getCampos(resultSet, campos);
			//System.out.println(fields[1]);
			while (resultSet.next())
			{
				Hashtable<String, String> reg = new Hashtable<String, String>();
				for (String s : fields){
					reg.put(s, resultSet.getString(s));
				}
				res.add(reg);		 
			}
		} catch (SQLException e) {
			estado = false;
			e.printStackTrace();
		}finally {
			c = null;
			con.desconectar();
		}
		return res;
	}
	
	

	public String update(String tabla,String campos) {
		String s = "Se actualizo correctamente la tabla";
		Connection c = con.conectar();
		String query = "update " + tabla + " on " + campos;	
		try {
			Statement st = c.createStatement();
			st.execute(query);
		}
		catch(Exception e){
			estado = true;
			s = "Error al insertar la fila"; 
		}finally {
			con.desconectar();
		}
		return s;

	}
	
	public String update(String tabla,String campos, String condicion) {
		estado = true;
		String s = "Se actualizo correctamente la tabla";
		Connection c = con.conectar();
		String query = "update " + tabla + " set " + campos + " where " + condicion;	
		try {
			Statement st = c.createStatement();
			st.execute(query);
		}
		catch(Exception e){
			estado = false;
			e.printStackTrace();
			s = "Error al actualizar la fila"; 
		}finally {
			con.desconectar();
		}
		return s;

	}
	
	private String[] getCampos(ResultSet resultSet, String campos) {
		String[] fields;
		try {
			if (campos.equals("*")) {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				fields = new String[columnCount];
				for (int i = 1; i <= columnCount; i++ ) {
				  fields[i-1]= rsmd.getColumnName(i);
				  //System.out.println(fields[i-1]);
				}
			}else {
				fields = campos.split(", ");
			}
		}catch (SQLException e) {
			fields = new String[1];
		}
		return fields;
	}
	
}
