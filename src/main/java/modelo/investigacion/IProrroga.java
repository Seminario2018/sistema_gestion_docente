package modelo.investigacion;

import java.time.LocalDate;

public interface IProrroga {
    public IProrroga clone();
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public LocalDate getFechaFin();
    public void setFechaFin(LocalDate fechaFin);
}