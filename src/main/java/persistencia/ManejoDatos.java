package persistencia;

import java.util.ArrayList;

import java.sql.Connection;

import java.sql.ResultSet;
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
			estado = false;
			e.printStackTrace();
			s = "Error al insertar la fila"; 
		}finally {
			con.desconectar();
		}
		return s;
		
	}
	
	public ArrayList<String> select(String tabla, String campos) {
		estado = true;
		ArrayList<String> res = new ArrayList<>();
		String[] fields = campos.split(", ");
		Connection c = con.conectar();
		try {
			Statement st = c.createStatement();
			System.out.println("select " + campos + " from " + tabla);
			ResultSet resultSet = st.executeQuery("select " + campos + " from " + tabla);
			while (resultSet.next())
			{
				String res1 = "";
				if (campos.equals("*")){
					res1 += 
					res1 += "\n";
				}else{
					for (String s : fields){

						res1 += resultSet.getString(s) + " -- ";

						res1 += resultSet.getString(s) + ",";
					}
					res.add(res1);					 
				}
				 
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
	
	public ArrayList<String> select(String tabla, String campos, String condicion){
		estado = true;
		String[] fields = new String[1];
		ArrayList<String> res = new ArrayList<>();
		if (!(campos.equals("*"))) {
			fields = campos.split(", ");
		}
		Connection c = con.conectar();
		try {
			Statement st = c.createStatement();
			String query = "select " + campos + " from " + tabla + " where " + condicion;
			System.out.println(query);
			ResultSet resultSet = st.executeQuery(query);
			while (resultSet.next())
			{
				String res1 = "";
				if (campos.equals("*")){
					res1 += resultSet.toString();
					res1 += "\n";
				}else{
					for (String s : fields){
						res1 += resultSet.getString(s) + " -- ";
					}
					res.add(res1);
					System.out.println(res1);
				}
				 
			}
		} catch (SQLException e) {
			estado = false;
			e.printStackTrace();
		}
		return res;
	}
	
	
	public ArrayList<String> parsear(String s){
		 ArrayList<String> campos = new ArrayList<String>();
		 while  (!s.equals("")){
			 int j = s.indexOf(" ");
			 String st = s.substring(j);
			 campos.add(st);
			 s = s.substring(j + 1);
		 }
		 return campos;
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
	
}
