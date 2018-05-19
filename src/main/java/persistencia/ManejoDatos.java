package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

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
        System.out.println(query);

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

        String query =
            "insert into " + tabla + " (" + campos + ") values (" + valores
                + ")";
        System.out.println(query);

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
        System.out.printf("%s\n", query);

        Connection connection = con.conectar();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            String[] fields = this.getCampos(resultSet, campos);
            //System.out.println(fields[1]);
            while (resultSet.next()) {
                Hashtable<String, String> reg = new Hashtable<String, String>();
                for (String s : fields) {
                    reg.put(s, resultSet.getString(s));
                }
                res.add(reg);
            }
        } catch (SQLException e) {
            estado = false;
            e.printStackTrace();
        } finally {
            connection = null;
            con.desconectar();
        }
        return res;
    }

    public ArrayList<Hashtable<String, String>> select(String tabla, String campos, String condicion) {
        estado = true;
        ArrayList<Hashtable<String, String>> res = new ArrayList<>();

        String query =
            "select " + campos + " from " + tabla + " where " + condicion;
        System.out.printf("%s\n", query);

        Connection connection = con.conectar();
        try {
            ResultSet resultSet =
                connection.createStatement().executeQuery(query);
            String[] fields = this.getCampos(resultSet, campos);
            //System.out.println(fields[1]);
            while (resultSet.next()) {
                // System.out.println(resultSet.getInt(1));
                Hashtable<String, String> reg = new Hashtable<String, String>();
                for (String s : fields) {
                    reg.put(s, resultSet.getString(s));
                }
                res.add(reg);
            }
        } catch (SQLException e) {
            estado = false;
            e.printStackTrace();
        } finally {
            connection = null;
            con.desconectar();
        }
        return res;
    }

    public String update(String tabla, String campos) {
        String mensaje = "Se actualizo correctamente la tabla";

        String query = "update " + tabla + " on " + campos;
        System.out.printf("%s\n", query);

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
        System.out.printf("%s\n", query);

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

}
