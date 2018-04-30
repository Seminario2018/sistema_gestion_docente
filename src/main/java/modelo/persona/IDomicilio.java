package modelo.persona;

public interface IDomicilio {

	public int getId();
	public String getProvincia();
	public String getCodigoPostal();
	public String getCiudad();
	public String getDireccion();
	public void setId(int id);
	public void setCodigoPostal(String codigoPostal);
	public void setDireccion(String Direccion);
	public void setCiudad(String Ciudad);
	public void setProvincia(String Provincia);
	public IDomicilio clone();
	
	
	
}
