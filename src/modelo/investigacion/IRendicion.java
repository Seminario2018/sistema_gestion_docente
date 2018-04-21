package modelo.investigacion;

import java.time.LocalDate;

public interface IRendicion {
    public IRendicion clone();
    public LocalDate getFecha();
    public void setFecha(LocalDate fecha);
    public float getMonto();
    public void setMonto(float monto);
    public String getObservaciones();
    public void setObservaciones(String observaciones);
}