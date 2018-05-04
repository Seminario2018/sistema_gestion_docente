package modelo.persona;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */

public class Contacto implements IContacto{
	private String tipo; //FACE
	private String nombre; //pagina de facebook
	private String valor; //https://facebook.com/usuario
	
	public Contacto(int id, String tipo, String nombre, String valor) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
		this.valor = valor;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return valor;
	}
	
	
	@Override
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	@Override
	public IContacto clone() {
		return new Contacto(id, tipo, nombre, valor);
	}

}
