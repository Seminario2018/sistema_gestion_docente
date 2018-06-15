package modelo.docente;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import persistencia.ManejoDatos;

public class Costo {


	private int id;
	private float costo;
	private LocalDate fechaCosto;

	public Costo() {
		id = -1;
		costo = -1;
		fechaCosto = null;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	public LocalDate getFechaCosto() {
		return fechaCosto;
	}
	public void setFechaCosto(LocalDate fechaCosto) {
		this.fechaCosto = fechaCosto;
	}


	public void guardar(ICargoDocenteg cd) {
		if (!existe(cd)) {
			insertar(cd);
		}
	}


	private void insertar(ICargoDocenteg cd) {
		try {
			this.id = getMaxCodigo(cd) + 1;
			ManejoDatos md = new ManejoDatos();
			String tabla = "costos";
			String campos = "id, CodigoCargo, Costo";
			String valores = this.id + ", " + cd.getId() + ", " + this.getCosto();
			
			if (this.fechaCosto != null) {
				campos += ", Fecha";
				valores += ", '" + Date.valueOf(fechaCosto) + "'";
			}
			
			md.insertar(tabla, campos, valores);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private int getMaxCodigo(ICargoDocenteg cd) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "costos";
			String campos = "MAX(id)";
			String condicion = "CodigoCargo = " + cd.getId();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			Hashtable<String, String> reg = res.get(0);
			return Integer.parseInt(reg.get("MAX(id)"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	private boolean existe(ICargoDocenteg cd) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "costos";
			String campos = "id, Costo, Fecha";
			String condicion = "id = " + this.getId() + " AND CodigoCargo = " + cd.getId();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);

			return !res.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
