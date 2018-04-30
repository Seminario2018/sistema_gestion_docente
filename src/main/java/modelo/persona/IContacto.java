package modelo.persona;

public interface IContacto {
	
	public int getId();
	public String getNombre();
	public String getTipo();
	public String getValor();
	public void setId(int id);
	public void setNombre(String nombre);
	public void setTipo(String tipo);
	public void setValor(String valor);
	public IContacto clone();

}
