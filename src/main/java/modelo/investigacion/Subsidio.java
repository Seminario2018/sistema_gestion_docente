package modelo.investigacion;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Subsidio implements ISubsidio {
	private Year fecha; // Solo año
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
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

}