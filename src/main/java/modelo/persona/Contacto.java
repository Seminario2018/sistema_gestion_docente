package modelo.persona;

import modelo.auxiliares.TipoContacto;

/**
 * @author MartÃ­n TomÃ¡s Juran
 * @version 1.0, 28 de abr. de 2018
 */

public class Contacto implements IContacto{
	private int id;
	private TipoContacto tipo;
	private String dato; //https://facebook.com/usuario
	

	public Contacto(int id, TipoContacto tipo, String dato, Persona persona) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.dato = dato;
		this.setPersona(persona);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoContacto getTipo() {
		return tipo;
	}

	public void setTipo(TipoContacto tipo) {
		this.tipo = tipo;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	@Override
	public IContacto clone() {
		return new Contacto(this.id, this.tipo, this.dato,this.persona);
	}



}