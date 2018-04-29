package modelo.docente;

import java.time.Year;

public interface IIncentivo {
    public IIncentivo clone();
    public Year getFecha();
    public void setFecha(Year fecha);
}