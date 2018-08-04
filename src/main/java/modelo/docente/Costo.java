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
			String tabla2 = "CargosDocentes";
			String campos = "id, CodigoCargo, Costo";
			String campos2 = "`UltimoCosto` = " + this.getCosto();
			String valores = this.id + ", " + cd.getId() + ", " + this.getCosto();
			String condicion = "Codigo = " + cd.getId();
			
			if (this.fechaCosto != null) {
				campos += ", Fecha";
				valores += ", '" + Date.valueOf(fechaCosto) + "'";
				campos2 += ", `FechaUltimoCosto` = '" + Date.valueOf(fechaCosto) + "'";
			}
			
			md.insertar(tabla, campos, valores);
			md.update(tabla2, campos2, condicion);
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
			if (reg.get("MAX(id)").equals(""))
				return 0;
			else
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


	public String guardar2(ICargoDocenteg cd) {
		if (!existe(cd)) {
			this.id = getMaxCodigo(cd) + 1;
			ManejoDatos md = new ManejoDatos();
			String tabla = "costos";
			String campos = "id, CodigoCargo, Costo";
			String valores = this.id + ", " + cd.getId() + ", " + this.getCosto();
			String condicion = "Codigo = " + cd.getId();
			
			if (this.fechaCosto != null) {
				campos += ", Fecha";
				valores += ", '" + Date.valueOf(fechaCosto) + "'";
			}
			return md.insertQuery(tabla, campos, valores);
		}
		return null;
	}


}
