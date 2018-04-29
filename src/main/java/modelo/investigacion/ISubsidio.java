package modelo.investigacion;

import java.time.Year;
import java.util.List;

public interface ISubsidio {
    public ISubsidio clone();
    public Year getFecha();
    public void setFecha(Year fecha);
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public float getMontoTotal();
    public void setMontoTotal(float montoTotal);
    public String getObservaciones();
    public void setObservaciones(String observaciones);

    public List<IRendicion> getRendiciones();
    public void agregarRendicion(IRendicion rendicion);
    public void quitarRendicion(IRendicion rendicion);
}