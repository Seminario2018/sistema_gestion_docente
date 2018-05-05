package modelo.persona;

import modelo.auxiliares.TipoContacto;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */

public class Contacto implements IContacto{
	private int id;
	private TipoContacto tipo;
	private String dato; //https://facebook.com/usuario
	
	public Contacto(String tipo, String nombre, String valor) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.dato = valor;
	}

	@Override
	public String getTipo() {
		return tipo;
	}
	
	@Override
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getValor() {
		return dato;
	}
	
	
	@Override
	public void setValor(String valor) {
		this.dato = valor;
	}
	
	
	@Override
	public IContacto clone() {
		return new Contacto(tipo, nombre, dato);
	}

}
