package modelo.docente;

import java.time.Year;

public class Incentivo implements IIncentivo {
	private Year fecha; // Solo año

	public Incentivo(Year fecha) {
	    this.fecha = fecha;
	}

    public Incentivo() {

    }


    @Override
    public IIncentivo clone() {
        return (IIncentivo) new Incentivo(this.fecha);
    }

    @Override
    public Year getFecha() {
        return this.fecha;
    }

    @Override
    public void setFecha(Year fecha) {
        this.fecha = fecha;
    }
}