package modelo.investigacion;

import java.time.LocalDate;

public class Rendicion implements IRendicion {
	private LocalDate fecha;
	private float monto;
	private String observaciones;

	public Rendicion(LocalDate fecha, float monto, String observaciones) {
	    this.fecha = fecha;
	    this.monto = monto;
	    this.observaciones = observaciones;
	}

    @Override
    public IRendicion clone() {
        return (IRendicion) new Rendicion(
            this.fecha,
            this.monto,
            this.observaciones
            );
    }

    @Override
    public LocalDate getFecha() {
        return this.fecha;
    }

    @Override
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public float getMonto() {
        return this.monto;
    }

    @Override
    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public String getObservaciones() {
        return this.observaciones;
    }

    @Override
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}