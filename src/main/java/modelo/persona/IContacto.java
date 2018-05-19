package modelo.persona;

import modelo.auxiliares.TipoContacto;

public interface IContacto {
	
	public int getId();
	public void setId(int id);
	public TipoContacto getTipo();
	public void setTipo(TipoContacto tipo);
	public String getDato();
	public void setDato(String dato);
	public IContacto clone();
	

}
