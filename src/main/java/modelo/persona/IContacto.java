package modelo.persona;

public interface IContacto {
	
	public String getTipo();
	public String getNombre();
	public String getValor();
	public void setTipo(String tipo);
	public void setNombre(String nombre);
	public void setValor(String valor);
	public IContacto clone();

}
