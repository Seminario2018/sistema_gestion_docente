package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import utilidades.LogQuery;

public class ManejoDatos {

    private Conexion con;
    private boolean estado = true;

    public boolean isEstado() {
        return estado;
    }

    public ManejoDatos() {
        con = new Conexion();
    }

    public boolean delete(String tabla, String condicion) {
        estado = true;

        String query = "DELETE FROM " + tabla + " WHERE " + condicion;
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            estado = false;
            e.printStackTrace();
        } finally {
            con.desconectar();
        }
        return estado;
    }

    public String insertar(String tabla, String campos, String valores) {
        estado = true;
        String mensaje = "Se inserto la fila correctamente";

        String query = "insert into " + tabla + " (" + campos + ") values (" + valores + ")";
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            estado = false;
            mensaje = "Error al insertar la fila";
        } finally {
            con.desconectar();
        }
        return mensaje;

    }

    public ArrayList<Hashtable<String, String>> select(String tabla, String campos) {
        estado = true;
        ArrayList<Hashtable<String, String>> res = new ArrayList<>();

        String query = "select " + campos + " from " + tabla;
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            String[] fields = this.getCampos(resultSet, campos);
            //System.out.println(fields[1]);
            while (resultSet.next()) {
                Hashtable<String, String> reg = new Hashtable<String, String>();
                for (String s : fields) {
                	reg.put(s, resultSet.getString(s) == null ? "" : resultSet.getString(s));
                }
                res.add(reg);
            }
        } catch (SQLException e) {
            estado = false;
            e.printStackTrace();
        } finally {
        	con.desconectar();
            connection = null;

        }
        return res;
    }

    public ArrayList<Hashtable<String, String>> select(String tabla, String campos, String condicion) {
        estado = true;
        ArrayList<Hashtable<String, String>> res = new ArrayList<>();

        String query =
            "select " + campos + " from " + tabla + " where " + condicion;
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            ResultSet resultSet =
                connection.createStatement().executeQuery(query);
            String[] fields = this.getCampos(resultSet, campos);
            while (resultSet.next()) {
                Hashtable<String, String> reg = new Hashtable<String, String>();
                for (String campo : fields) {
                    reg.put(campo, resultSet.getString(campo) == null ? "" : resultSet.getString(campo));
                }
                res.add(reg);
            }
        } catch (SQLException e) {
            estado = false;
            e.printStackTrace();
        } finally {
        	con.desconectar();
            connection = null;

        }
        return res;
    }

    public String update(String tabla, String campos) {
        String mensaje = "Se actualizo correctamente la tabla";

        String query = "update " + tabla + " on " + campos;
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            estado = true;
            mensaje = "Error al insertar la fila";
            e.printStackTrace();
        } finally {
            con.desconectar();
        }
        return mensaje;
    }

    public String update(String tabla, String campos, String condicion) {
        estado = true;
        String mensaje = "Se actualizo correctamente la tabla";

        String query =
            "update " + tabla + " set " + campos + " where " + condicion;
        LogQuery.log(query);

        Connection connection = con.conectar();
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            estado = false;
            e.printStackTrace();
            mensaje = "Error al actualizar la fila";
        } finally {
            con.desconectar();
        }
        return mensaje;

    }

    private String[] getCampos(ResultSet resultSet, String campos) {
        String[] fields;
        try {
            if (campos.equals("*")) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                fields = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    fields[i - 1] = rsmd.getColumnName(i);
                    //System.out.println(fields[i-1]);
                }
            } else {
                fields = campos.split(", ");
            }
        } catch (SQLException e) {
            fields = new String[1];
        }
        return fields;
    }
    
    public boolean ejecutarQuerys(ArrayList<String> querys) {

		Connection conn = con.conectar();
		try
		{
			conn.setAutoCommit(false);
			for (String string : querys) {
				Statement s = conn.createStatement();
				s.execute(string);
				s.close();
			}
			conn.commit();
		}
		catch (SQLException e)
		{
			if (conn != null)
			{
				try
				{
					conn.rollback();
				}
				catch (SQLException i)
				{
				}
			}
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				try
				{
					//con.rollback();
					conn.close();
					return true;
				} catch (SQLException e)
				{
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
    
    
    public String insertQuery(String tabla, String campos, String valores) {
		return "insert into " + tabla + " (" + campos + ") values (" + valores + ")";
	}

}
