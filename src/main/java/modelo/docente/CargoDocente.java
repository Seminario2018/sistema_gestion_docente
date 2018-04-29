package modelo.docente;

import java.time.LocalDate;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;

public class CargoDocente implements ICargoDocente {
	private IArea area;
	private ICargo cargo;
	private TipoCargo tipoCargo;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
	private float ultimoCosto;
	private LocalDate fechaUltCost;
	private String resolucion;
	private LocalDate resDesde;
	private LocalDate resHasta;
	private EstadoCargo estado;

	public CargoDocente(IArea area, ICargo cargo, TipoCargo tipoCargo,
	        String disposicion, LocalDate dispDesde, LocalDate dispHasta,
	        float ultimoCosto, LocalDate fechaUltCost, String resolucion,
	        LocalDate resDesde, LocalDate resHasta, EstadoCargo estado) {

	    this.area = area;
	    this.cargo = cargo;
	    this.tipoCargo = tipoCargo;
	    this.disposicion = disposicion;
	    this.dispDesde = dispDesde;
	    this.dispHasta = dispHasta;
	    this.ultimoCosto = ultimoCosto;
	    this.fechaUltCost = fechaUltCost;
	    this.resolucion = resolucion;
	    this.resDesde = resDesde;
	    this.resHasta = resHasta;
	    this.estado = estado;
	}

    @Override
    public ICargoDocente clone() {
        return (ICargoDocente) new CargoDocente(
            this.area,
            this.cargo,
            this.tipoCargo,
            this.disposicion,
            this.dispDesde,
            this.dispHasta,
            this.ultimoCosto,
            this.fechaUltCost,
            this.resolucion,
            this.resDesde,
            this.resHasta,
            this.estado
            );
    }

    @Override
    public IArea getArea() {
        return this.area;
    }

    @Override
    public void setArea(IArea area) {
        this.area = area;
    }

    @Override
    public ICargo getCargo() {
        return this.cargo;
    }

    @Override
    public void setCargo(ICargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public TipoCargo getTipoCargo() {
        return this.tipoCargo;
    }

    @Override
    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
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
    public LocalDate getDispDesde() {
        return this.dispDesde;
    }

    @Override
    public void setDispDesde(LocalDate dispDesde) {
        this.dispDesde = dispDesde;
    }

    @Override
    public LocalDate getDispHasta() {
        return this.dispHasta;
    }

    @Override
    public void setDispHasta(LocalDate dispHasta) {
        this.dispHasta = dispHasta;
    }

    @Override
    public float getUltimoCosto() {
        return this.ultimoCosto;
    }

    @Override
    public void setUltimoCosto(float ultimoCosto) {
        this.ultimoCosto = ultimoCosto;
    }

    @Override
    public LocalDate getFechaUltCost() {
        return this.fechaUltCost;
    }

    @Override
    public void setFechaUltCost(LocalDate fechaUltCost) {
        this.fechaUltCost = fechaUltCost;
    }

    @Override
    public String getResolucion() {
        return this.resolucion;
    }

    @Override
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    @Override
    public LocalDate getResDesde() {
        return this.resDesde;
    }

    @Override
    public void setResDesde(LocalDate resDesde) {
        this.resDesde = resDesde;
    }

    @Override
    public LocalDate getResHasta() {
        return this.resHasta;
    }

    @Override
    public void setResHasta(LocalDate resHasta) {
        this.resHasta = resHasta;
    }

    @Override
    public EstadoCargo getEstado() {
        return this.estado;
    }

    @Override
    public void setEstado(EstadoCargo estado) {
        this.estado = estado;
    }

}