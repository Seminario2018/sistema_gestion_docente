package modelo.costeo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorCargosFaltantes {

	public EstadoOperacion agregarCargoFaltante(ICargoFaltante cargo) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "cargosfaltantes";
			String campos = "`Legajo`, `Cargo`, `FechaUltimoCosto`, `Tipo`";
			String valores = cargo.getLegajo() + ", " + cargo.getCodigoCargo() + ", "
					+ "'" + Date.valueOf(cargo.getFechaUltimoCosto()) + "', " + (cargo.isTipo() ? 1 : 0);
			if (cargo.getApellido() != null && !cargo.getApellido().equals("")) {
				campos += ", Apellido";
				valores += ", '" + cargo.getApellido() + "'";
			}
			if (cargo.getNombre() != null && !cargo.getNombre().equals("")) {
				campos += ", Nombre";
				valores += ", '" + cargo.getNombre() + "'";
			}
			if (cargo.getUltimoCosto() != -1) {
				campos += ", UltimoCosto";
				valores += ", " + cargo.getUltimoCosto();
			}

			md.insertar(tabla, campos, valores);

			return md.isEstado() ? 
					new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "Cargo Faltante guardado") : 
						new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "Cargo Faltante no guardado");
		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "Cargo Faltante no guardado");
		}


	}

	public EstadoOperacion eliminarCargoFaltante(ICargoFaltante cargo) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "cargosfaltantes";
			String condicion = "`Legajo`  = " + cargo.getLegajo() + " AND "
					+ "`Cargo` = " + cargo.getCodigoCargo() + " AND "
					+ "`FechaUltimoCosto` = '" +  Date.valueOf(cargo.getFechaUltimoCosto()) + "'";  

			md.delete(tabla, condicion);

			return md.isEstado() ? 
					new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "Cargo Faltante eliminado") : 
						new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "Cargo Faltante no eliminado");
		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "Cargo Faltante no eliminado");
		}
	}
	
	

	public List<ICargoFaltante> listarCargosFaltantes(ICargoFaltante cargo) {
		List<ICargoFaltante> cargos = new ArrayList<ICargoFaltante>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "cargosfaltantes";

			String condicion = "TRUE";
			
			if (cargo != null) {
				if (cargo.getFechaUltimoCosto() != null)
					condicion += " AND FechaUltimoCosto = '" + 
							Date.valueOf(cargo.getFechaUltimoCosto()) + "'";
			}
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				CargoFaltante c = new CargoFaltante();
				c.setLegajo(Integer.parseInt(reg.get("Legajo")));
				c.setCodigoCargo(Integer.parseInt(reg.get("Cargo")));
				c.setFechaUltimoCosto(Date.valueOf(reg.get("FechaUltimoCosto")).toLocalDate());
				c.setTipo(Integer.parseInt(reg.get("Tipo")) == 1);

				if (!"".equals(reg.get("Apellido"))) {
					c.setApellido(reg.get("Apellido"));
				}
				if (!"".equals(reg.get("Nombre"))) {
					c.setNombre(reg.get("Nombre"));
				}
				if (!"".equals(reg.get("UltimoCosto"))) {
					c.setUltimoCosto(Float.parseFloat(reg.get("UltimoCosto")));
				}
				cargos.add(c);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			cargos = new ArrayList<ICargoFaltante>();
		}
		
		
		return cargos;
	}


	
	public LocalDate getMaxFechaUltimoCosto() {
		LocalDate max = null;
		try {
			ManejoDatos md = new ManejoDatos();
			List<Hashtable<String, String>> res = md.select("cargosfaltantes", "MAX(FechaUltimoCosto)");
			if (res != null && !res.isEmpty()) {
				Hashtable<String, String> reg = res.get(0);
				if (!reg.isEmpty() && !reg.get("MAX(FechaUltimoCosto)").equals(""))
					max = Date.valueOf(reg.get("MAX(FechaUltimoCosto)")).toLocalDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return max;
	}

	/**
	 * @return
	 */
	public ICargoFaltante getICargoFaltante() {
		return (ICargoFaltante) new CargoFaltante();
	}

	/**
	 * @return los últimos cargos importados
	 */
	public List<ICargoFaltante> listarUltimosCargosFaltantes() {
		ICargoFaltante cargo = new CargoFaltante();
		cargo.setFechaUltimoCosto(getMaxFechaUltimoCosto());
		return listarCargosFaltantes(cargo);
	}

}
