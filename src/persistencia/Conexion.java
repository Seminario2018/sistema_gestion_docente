package persistencia;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import java.sql.Connection;

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
            File archivo = new File("Base.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            this.driver = document.getElementsByTagName("driver").item(0).getTextContent();
            this.user = document.getElementsByTagName("usr").item(0).getTextContent();
            this.pass = document.getElementsByTagName("password").item(0).getTextContent();
            this.url = document.getElementsByTagName("url").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public Connection conectar() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void desconectar() {
		conn = null;
	}
}
