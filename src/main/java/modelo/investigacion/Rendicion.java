package modelo.investigacion;

import java.time.LocalDate;

public class Rendicion implements IRendicion {
	private int id;
	private LocalDate fecha;
	private float monto;
	private String observaciones;

	public Rendicion(int id, LocalDate fecha, float monto, String observaciones) {
		this.id = id;
	    this.fecha = fecha;
	    this.monto = monto;
	    this.observaciones = observaciones;
	}

    public Rendicion() {
    	this.id = -1;
	    this.fecha = null;
	    this.monto = -1;
	    this.observaciones = null;
	}

	@Override
    public IRendicion clone() {
        return (IRendicion) new Rendicion(
        		this.id,
        		this.fecha,
        		this.monto,
        		this.observaciones
        		);
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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