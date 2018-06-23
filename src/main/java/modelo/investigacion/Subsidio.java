package modelo.investigacion;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Subsidio implements ISubsidio {
	private Year fecha; // Solo año
	private String disposicion;
	private float montoTotal;
	private String observaciones;

	private List<IRendicion> rendiciones;

	public Subsidio(Year fecha, String disposicion, float montoTotal,
	        String observaciones, List<IRendicion> rendiciones) {
	    this.fecha = fecha;
	    this.disposicion = disposicion;
	    this.montoTotal = montoTotal;
	    this.observaciones = observaciones;
	    this.rendiciones = new ArrayList<IRendicion>(rendiciones);
	}

    public Subsidio() {
    	this.fecha = null;
	    this.disposicion = null;
	    this.montoTotal = -1;
	    this.observaciones = null;
	    this.rendiciones = new ArrayList<IRendicion>();
	}

	@Override
    public ISubsidio clone() {
        return (ISubsidio) new Subsidio(
            this.fecha,
            this.disposicion,
            this.montoTotal,
            this.observaciones,
            this.rendiciones
            );
    }

    @Override
    public Year getFecha() {
        return this.fecha;
    }

    @Override
    public void setFecha(Year fecha) {
        this.fecha = fecha;
    }

    @Override
    public String getDisposicion() {
        return this.disposicion;
    }

    @Override
    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    @Override
    public float getMontoTotal() {
        return this.montoTotal;
    }

    @Override
    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String getObservaciones() {
        return this.observaciones;
    }

    @Override
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public List<IRendicion> getRendiciones() {
//        return this.rendiciones;
        if (this.rendiciones.isEmpty()) {
            GestorProyecto gp = new GestorProyecto();
            for (IRendicion ren : gp.listarRendiciones(null, this, null)) {
                this.rendiciones.add(ren);
            }
        }
        return this.rendiciones;
    }

    @Override
    public void agregarRendicion(IRendicion rendicion) {
        this.rendiciones.add(rendicion);
    }

    @Override
    public void quitarRendicion(IRendicion rendicion) {
        this.rendiciones.remove(rendicion);
    }

    @Override
    public void setRendiciones(List<IRendicion> rendiciones) {
        this.rendiciones = rendiciones;
    }

    @Override
    public String toString() {
        return this.fecha.toString();
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Subsidio)) {
            return false;
        }

        Subsidio s = (Subsidio) objeto;
        return this.fecha.equals(s.fecha);
    }
}