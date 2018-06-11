package modelo.investigacion;

import modelo.docente.ICargoDocente;

public interface IIntegranteg {

	public String getCargo();
	public String getInstitucion();
	public int getHorasSemanales();
	public String getApellido();
	public String getNombre();
	public int getId();
	public void setId(int  id);
	public ICargoDocente getCargoDocente2();
}
