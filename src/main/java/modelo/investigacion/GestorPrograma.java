package modelo.investigacion;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorPrograma {

	public EstadoOperacion nuevoPrograma (IPrograma programa) {
		try {
			if (programa.getId() == -1) {
				programa.setId(this.getMaxID() + 1);
			}

			// programa.getEstado().guardar();

			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "`id`, `Nombre`, `Director`, `Estado`";
			String valores = programa.getId() + ", '" + programa.getNombre() + "', "
					+ programa.getDirector().getLegajo() + ", " + programa.getEstado().getId();
			if (programa.getCodirector() != null) {
				campos += ", `Codirector`";
				valores += ", " + programa.getCodirector().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				campos += ", `Disposicion`";
				valores += ", '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				campos += ", `Desde`";
				valores += ", '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				campos += ", `Hasta`";
				valores += ", '" + Date.valueOf(programa.getFechaFin()) + "'";
			}

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El programa se guardo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
			}
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
		}
	}

	public EstadoOperacion modificarPrograma(IPrograma programa) {
		try {
			// programa.getEstado().guardar();

			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "`Nombre` = '"+ programa.getNombre() +"', "
					+ "`Director` = " + programa.getDirector().getLegajo() + ", "
					+ "`Estado` = " + programa.getEstado().getId();

			String condicion = "id = " + programa.getId();


			if (programa.getCodirector() != null) {
				campos += ", `Codirector` = " + programa.getCodirector().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				campos += ", `Disposicion` = '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				campos += ", `Desde` = '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				campos += ", `Hasta` = '" + Date.valueOf(programa.getFechaFin()) + "'";
			}

			md.update(table, campos, condicion);

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El programa se guardo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo guardar el programa");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo guardar el programa");
		}
	}

	public EstadoOperacion eliminarPrograma(IPrograma programa) {
	    try {
	        ManejoDatos md = new ManejoDatos();
            md.delete("programasinvestigacion", "id = " + programa.getId());

            if (md.isEstado()) {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,"El programa se eliminó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el programa");
            }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el programa");
	    }
	}

	public List<IPrograma> listarProgramas(IPrograma programa){
		List<IPrograma> programas = new ArrayList<IPrograma>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "*";
			String condicion = this.armarCondicion(programa);

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				Programa p = new Programa();
				p.setId(Integer.parseInt(reg.get("id")));
				p.setNombre(reg.get("Nombre"));


				if (!reg.get("Disposicion").equals("")) {
					p.setDisposicion(reg.get("Disposicion"));
				}
				if (!reg.get("Desde").equals("")) {
					String[] fecha = reg.get("Desde").split("-");
					LocalDate fld = LocalDate.of(Integer.parseInt(fecha[0]),
							Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
					p.setFechaInicio(fld);
				}
				if (!reg.get("Hasta").equals("")) {
					String[] fecha = reg.get("Hasta").split("-");
					LocalDate fld = LocalDate.of(Integer.parseInt(fecha[0]),
							Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
					p.setFechaFin(fld);
				}

				programas.add(p);

			}
		} catch (NumberFormatException e) {
			programas = new ArrayList<IPrograma>();
		}

		return programas;
	}



	private String armarCondicion(IPrograma p) {

		IProgramag programa = (IProgramag) p;

		String condicion = "TRUE";
		if (programa != null) {
			if (programa.getId() != -1) {
				condicion += " AND " + "id = " + programa.getId();
			}
			if (programa.getNombre() != null && !programa.getNombre().equals("")) {
				condicion += " AND " + "Nombre = '" + programa.getNombre() + "'";
			}
			if (programa.getDirector2() != null) {
				condicion += " AND " + "Director = " + programa.getDirector2().getLegajo();
			}
			if (programa.getCodirector2() != null) {
				condicion += " AND " +  "Codirector = " + programa.getCodirector2().getLegajo();
			}
			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				condicion += " AND " + "Disposicion = '" + programa.getDisposicion() + "'";
			}
			if (programa.getFechaInicio() != null) {
				condicion += " AND " + "Desde = '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}
			if (programa.getFechaFin() != null) {
				condicion += " AND " + "Hasta = '" + Date.valueOf(programa.getFechaFin()) + "'";
			}
			if (programa.getEstado2() != null) {
				condicion += " AND " + "Estado = " + programa.getEstado2().getId();
			}

		}
		return condicion;
	}

	private int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "MAX(id)";
			int maxID = 0;
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			maxID = Integer.parseInt(res.get(0).get(campos));


			return maxID;
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	public IPrograma getIPrograma() {
        return new Programa();
    }

}
