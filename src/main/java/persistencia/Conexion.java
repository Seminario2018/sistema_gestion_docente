package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.w3c.dom.Document;
import utilidades.Utilidades;

public class Conexion {

	private Connection conn;
	private String driver;
	private String user;
	private String pass;
	private String url;


	public Conexion() {
		conn = null;
		this.leerXML();
	}

	private void leerXML() {
		try {
            Document documento = Utilidades.leerXML("Base.xml");
            this.driver = documento.getElementsByTagName("driver").item(0).getTextContent();
            this.user = documento.getElementsByTagName("usr").item(0).getTextContent();
            this.pass = documento.getElementsByTagName("password").item(0).getTextContent();
            this.url = documento.getElementsByTagName("url").item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public Connection conectar() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public void desconectar() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = null;
	}
}
