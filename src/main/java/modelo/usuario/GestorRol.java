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
			md.insertar("roles", "nombre", "'"+grupo.getNombre()+"'");
			String tabla = "permisos";
			String campos = "`crear`, `eliminar`, `modificar`, `listar`, `rol`, `modulo`";
			for (IPermiso p : grupo.getPermisos()) {
				int crear = p.getCrear() ? 1 : 0;
				int eliminar = p.getCrear() ? 1 : 0;
				int modificar = p.getCrear() ? 1 : 0;
				int listar = p.getListar() ? 1 : 0;
				if (md.select("modulo", "*","Descripcion = '" + p.getModulo().name() + "'").isEmpty()) {
					md.insertar("modulo", "idmodulo, Descripcion",
							p.getModulo().ordinal() + ", '" + p.getModulo().name() + "'");
				}

				String valores = crear + ", " + eliminar + ", "
						+  modificar + ", " + listar + ", '"
						+ grupo.getNombre() + "', " + p.getModulo().ordinal();

				md.insertar(tabla, campos, valores);
			}

			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El rol se creó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el rol");
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
    		ArrayList<Hashtable<String, String>> res = new ArrayList<Hashtable<String, String>>();
    		ArrayList<IRol> roles = new ArrayList<IRol>();

    		String tabla = "roles";
            String campos = "*";
            String condicion = "TRUE";

			if (grupo != null) {
//			    condicion = "nombre = '" + grupo.getNombre() + "'";
			    int id = grupo.getId();
			    if (id != -1) {
			        condicion += "and id = " + grupo.getId();
			    }

			    String nombre = grupo.getNombre();
			    if (nombre != null && !nombre.equals("")) {
			        condicion += "nombre = '" + grupo.getNombre() + "'";
			    }
			}
			ManejoDatos md = new ManejoDatos();
			res = md.select(tabla, "*", condicion);


			for (Hashtable<String, String> reg : res) {
				Rol rol = new Rol(Integer.parseInt(reg.get("id")), reg.get("nombre").toString());
				tabla = "permisos";
	            campos = "*";
	            condicion = "rol = '" + rol.getNombre() + "'";
	            ArrayList<Hashtable<String, String>> per = md.select(tabla, campos, condicion);
	            for (Hashtable<String, String> reg2 : per) {
					Permiso p = new Permiso(Modulo.values()[Integer.parseInt(reg2.get("modulo").toString())]							);
					if(reg2.get("crear").toString().equals("1")) {
						p.setCrear(true);
					}
					if(reg2.get("eliminar").toString().equals("1")) {
						p.setEliminar(true);
					}
					if(reg2.get("modificar").toString().equals("1")) {
						p.setModificar(true);
					}
					if(reg2.get("listar").toString().equals("1")) {
						p.setListar(true);
					}
					rol.agregarPermiso(p);
				}
			}
			return roles;
		} catch (Exception e) {
			return null;
		}
    }


}