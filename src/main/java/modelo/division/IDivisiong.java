package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public interface IDivisiong {
	
	
	public String getCodigo();
    public String getDescripcion();
    public IDocente getJefe2();
    public String getDisposicion();
    public LocalDate getDispDesde();
    public LocalDate getDispHasta();
    

}
