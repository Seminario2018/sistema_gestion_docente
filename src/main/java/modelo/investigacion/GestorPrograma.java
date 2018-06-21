package modelo.investigacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorPrograma {

	public EstadoOperacion nuevoPrograma (IPrograma programa) {
		try {
			if (programa.getId() == -1) {
				programa.setId(this.getMaxID() + 1);
			}

			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "`id`, `Nombre`, `Director`, `Estado`";
			String valores = programa.getId() + ", '" + programa.getNombre() + "', "
					+ programa.getDirector().getLegajo() + ", " + programa.getEstado().getId();

			if (((IProgramag) programa).getCodirector2() != null) {
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
			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "";
			String condicion = "id = " + programa.getId();

			if (programa.getNombre() != null && !programa.getNombre().equals("")) {
				campos += "`Nombre` = '"+ programa.getNombre() +"'";
			}

			if (((IProgramag) programa).getDirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Director` = " + programa.getDirector().getLegajo();
			}
			if (((IProgramag) programa).getEstado2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Estado` = " + programa.getEstado().getId();
			}

			if (((IProgramag) programa).getCodirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Codirector` = " + programa.getCodirector().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Disposicion` = '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Desde` = '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Hasta` = '" + Date.valueOf(programa.getFechaFin()) + "'";
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
	        // Dejar huérfanos a proyectos asociados:
	        md.update(
	            "proyectos",
	            "Programa = NULL",
	            "Programa = " + programa.getId());

	        // Eliminar programa:
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

	public List<IPrograma> listarProgramas(IPrograma programa) {
		try {
		    List<IPrograma> programas = new ArrayList<IPrograma>();

			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "*";
			String condicion = this.armarCondicion(programa);

			List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				IPrograma p = new Programa();
				p.setId(Integer.parseInt(reg.get("id")));
				p.setNombre(reg.get("Nombre"));

				if (!reg.get("Disposicion").equals("")) {
					p.setDisposicion(reg.get("Disposicion"));
				}
				if (!reg.get("Desde").equals("")) {
					p.setFechaInicio(Date.valueOf(reg.get("Desde")).toLocalDate());
				}
				if (!reg.get("Hasta").equals("")) {
					p.setFechaFin(Date.valueOf(reg.get("Hasta")).toLocalDate());
				}

				programas.add(p);
			}
			return programas;

		} catch (NumberFormatException e) {
			return new ArrayList<IPrograma>();
		}
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
				condicion += " AND " + "Codirector = " + programa.getCodirector2().getLegajo();
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
			List<Hashtable<String, String>> res = md.select(tabla, campos);
			return Integer.parseInt(res.get(0).get(campos));

		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public IPrograma getIPrograma() {
        return new Programa();
    }

    public EstadoOperacion agregarProyecto(IPrograma programa, IProyecto proyecto) {
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "`proyectos`";
            String condicion = "id = " + proyecto.getId();
            String valores = "Programa = " + programa.getId();

            md.update(tabla, valores, condicion);
            if (md.isEstado()) {
                programa.setProyectos(new ArrayList<IProyecto>());
                return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se agregó al programa correctamente");
            } else {
                return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo agregar al programa");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo agregar al programa");
        }
    }

    public EstadoOperacion quitarProyecto(IPrograma programa, IProyecto proyecto) {
        try {
            ManejoDatos md = new ManejoDatos();
            md.update(
                "`proyectos`",
                "Programa = NULL",
                "id = " + proyecto.getId());

            if (md.isEstado()) {
                programa.setProyectos(new ArrayList<IProyecto>());
                return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se quitó del programa correctamente");
            } else {
                return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo quitar del programa");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo quitar del programa");
        }
    }

}
