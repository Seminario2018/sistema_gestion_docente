package modelo.division;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

public class Division implements IDivision {

	private String codigo;
	private String descripcion;
	private IDocente jefe;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;

	@Override
    public IDivision clone() {
        return (IDivision) new Division(
            this.codigo,
            this.descripcion,
            this.jefe,
            this.disposicion,
            this.dispDesde,
            this.dispHasta
        );
    }

	public Division(String codigo, String descripcion, IDocente jefe,
	    String disposicion, LocalDate dispDesde, LocalDate dispHasta) {

	    this.codigo = codigo;
        this.descripcion = descripcion;
        this.jefe = jefe;
        this.disposicion = disposicion;
        this.dispDesde = dispDesde;
        this.dispHasta = dispHasta;
	}

    public Division(){
    	this.codigo = null;
        this.descripcion = null;
        this.jefe = null;
        this.disposicion = null;
        this.dispDesde = null;
        this.dispHasta = null;
    }

    @Override
    public String getCodigo() {
        return this.codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public IDocente getJefe() {
    	if (this.jefe == null) {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res =  
    				md.select("Divisiones", "Jefe", "Codigo = '" + this.getCodigo() + "'");
    		Hashtable<String, String> reg = res.get(0);
            if (!reg.get("Jefe").equals("")) {
					GestorDocente gd = new GestorDocente();
					Docente profesor = new Docente(null, Integer.parseInt(reg.get("Jefe")), null, null, null, null,
							null);
					profesor = (Docente) gd.listarDocente(profesor).get(0);
					this.setJefe(profesor);
			}
    	}
        return this.jefe;
    }

    @Override
    public void setJefe(IDocente jefe) {
        this.jefe = jefe;
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

}