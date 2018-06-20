package modelo.usuario;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorRol {
    public EstadoOperacion nuevoGrupo(IRol grupo) {
        try {
			ManejoDatos md = new ManejoDatos();
			if (grupo.getId() == -1) {
				grupo.setId(this.getMaxid() + 1);
			}

			md.insertar("roles", "id, nombre", grupo.getId() + ", '"+grupo.getNombre()+"'");
			String tabla = "permisos";
			String campos = "`Crear`, `Eliminar`, `Modificar`, `Listar`, `Rol`, `Modulo`";
			for (IPermiso p : grupo.getPermisos()) {
				int crear = p.getCrear() ? 1 : 0;
				int eliminar = p.getCrear() ? 1 : 0;
				int modificar = p.getCrear() ? 1 : 0;
				int listar = p.getListar() ? 1 : 0;
				if (md.select("modulos", "*","Descripcion = '" + p.getModulo().name() + "'").isEmpty()) {
					md.insertar("modulos", "idmodulo, Descripcion",
							p.getModulo().ordinal() + ", '" + p.getModulo().name() + "'");
				}

				String valores = crear + ", " + eliminar + ", "
						+  modificar + ", " + listar + ", "
						+ grupo.getId() + ", " + p.getModulo().ordinal();

				md.insertar(tabla, campos, valores);
			}

			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El rol se creó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el rol");
		}
    }

    private int getMaxid() {
    	try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("roles", "MAX(id)");
			Hashtable<String, String> reg = res.get(0);
			int id = Integer.parseInt(reg.get("MAX(id)"));
			return id;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public EstadoOperacion modificarGrupo(IRol grupo) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "permisos";
			String campos = "`crear`, `eliminar`, `modificar`, `listar`, `rol`";
			md.delete(tabla, "rol = '" + grupo.getNombre() + "'");
			for (IPermiso p : grupo.getPermisos()) {
				int crear = p.getCrear() ? 1 : 0;
				int eliminar = p.getCrear() ? 1 : 0;
				int modificar = p.getCrear() ? 1 : 0;
				int listar = p.getListar() ? 1 : 0;
				String valores = crear + ", " + eliminar + ", " + modificar + ", " + listar + ", '" + grupo.getNombre() + "'";
				md.insertar(tabla, campos, valores);
			}
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El grupo se modificó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el grupo");
		}
    }

    public EstadoOperacion eliminarGrupo(IRol grupo) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "permisos";
			md.delete(tabla, "rol = '" + grupo.getNombre() + "'");
			tabla = "roles";
			md.delete(tabla, "nombre = '" + grupo.getNombre() + "'");
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El grupo se eliminó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el grupo");
		}
    }

    public List<IRol> listarGrupo(IRol grupo) {
    	try {
    		List<IRol> roles = new ArrayList<IRol>();

    		String tabla = "roles";
            String campos = "*";
            String condicion = "TRUE";

			if (grupo != null) {
			    int id = grupo.getId();
			    if (id != -1) {
			        condicion += " and id = " + grupo.getId();
			    }

			    String nombre = grupo.getNombre();
			    if (nombre != null && !nombre.equals("")) {
			        condicion += " and nombre = '" + grupo.getNombre() + "'";
			    }
			}

			ManejoDatos md = new ManejoDatos();
			List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);

			for (Hashtable<String, String> regRol : res) {
				IRol rol = new Rol(Integer.parseInt(regRol.get("id")), regRol.get("nombre"));
	            List<Hashtable<String, String>> per =
	                md.select(
	                    "`permisos`",
	                    "*",
	                    "`Rol` = " + rol.getId());

	            for (Hashtable<String, String> regPermiso : per) {
					IPermiso p = new Permiso(Modulo.values()[Integer.parseInt(regPermiso.get("Modulo"))]							);
					if(regPermiso.get("Crear").toString().equals("1")) {
						p.setCrear(true);
					}
					if(regPermiso.get("Eliminar").toString().equals("1")) {
						p.setEliminar(true);
					}
					if(regPermiso.get("Modificar").toString().equals("1")) {
						p.setModificar(true);
					}
					if(regPermiso.get("Listar").toString().equals("1")) {
						p.setListar(true);
					}
					rol.agregarPermiso(p);
				}
	            roles.add(rol);
			}
			return roles;

		} catch (Exception e) {
		    e.printStackTrace();
			return new ArrayList<IRol>();
		}
    }

    public IRol getIRol() {
        return new Rol();
    }

	/**
	 * @return
	 */
	public IPermiso getIPermiso() {
		return (IPermiso) new Permiso();
	}
}