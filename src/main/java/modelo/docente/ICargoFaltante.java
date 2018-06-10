package modelo.docente;

import java.time.LocalDate;

public interface ICargoFaltante {

	public String getApellido();
	public int getCodigoCargo();
	public LocalDate getFechaUltimoCosto();
	public int getLegajo();
	public String getNombre();
	public float getUltimoCosto();
	public boolean isTipo();
	
	
	public void setApellido(String apellido);
	public void setCodigoCargo(int codigoCargo);
	public void setFechaUltimoCosto(LocalDate fechaUltimoCosto);
	public void setLegajo(int legajo);
	public void setNombre(String nombre);
	public void setTipo(boolean tipo);
	public void setUltimoCosto(float ultimoCosto);
	
}
