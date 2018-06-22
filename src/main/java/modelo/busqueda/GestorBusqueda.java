package modelo.busqueda;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import persistencia.ManejoDatos;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class GestorBusqueda {

	public List<BusquedaDocente> listarDocentes(String criterio) {
		String tabla = "ViewDocente";
		String campos = "Legajo, Apellido, Nombre";
		String condicion = "TRUE";
		List<BusquedaDocente> docentes = new ArrayList<BusquedaDocente>();

		if (criterio != null && !criterio.equals("")) {
			condicion = armarCondicion(campos.split(", "), criterio);
		}

		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				BusquedaDocente doc = new BusquedaDocente(
						Integer.parseInt(reg.get("Legajo")),
						reg.get("Apellido"),
						reg.get("Nombre"));
				docentes.add(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return docentes;
	}

	public List<BusquedaPersona> listarPersonas(String criterio) {
		String tabla = "ViewPersona";
		String campos = "NroDocumento, Apellido, Nombre";
		String condicion = "TRUE";
		List<BusquedaPersona> personas = new ArrayList<BusquedaPersona>();

		if (criterio != null && !criterio.equals("")) {
			condicion = armarCondicion(campos.split(", "), criterio);
		}

		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				BusquedaPersona per = new BusquedaPersona(
						Integer.parseInt(reg.get("NroDocumento")),
						reg.get("Apellido"),
						reg.get("Nombre"));
				personas.add(per);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return personas;
	}

	public List<BusquedaArea> listarAreas(String criterio) {
		String tabla = "ViewArea";
		String campos = "Codigo, Descripcion";
		String condicion = "TRUE";
		List<BusquedaArea> areas = new ArrayList<BusquedaArea>();

		if (criterio != null && !criterio.equals("")) {
			condicion = armarCondicion(campos.split(", "), criterio);
		}

		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				BusquedaArea area = new BusquedaArea(
						reg.get("Codigo"),
						reg.get("Descripcion"));
				areas.add(area);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return areas;
	}

	public List<BusquedaCargo> listarCargos(String criterio) {
		String tabla = "ViewCargo";
		String campos = "Codigo, Descripcion";
		String condicion = "TRUE";
		List<BusquedaCargo> cargos = new ArrayList<BusquedaCargo>();

		if (criterio != null && !criterio.equals("")) {
			condicion = armarCondicion(campos.split(", "), criterio);
		}

		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				BusquedaCargo cargo = new BusquedaCargo(
						Integer.parseInt(reg.get("Codigo")),
						reg.get("Descripcion"));
				cargos.add(cargo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cargos;
	}

	public List<BusquedaPrograma> listarProgramas(String criterio) {
	    String tabla = "viewprogramas";
        String campos = "id, Nombre";
        String condicion = "TRUE";
        List<BusquedaPrograma> programas = new ArrayList<BusquedaPrograma>();

        if (criterio != null && !criterio.equals("")) {
            condicion = armarCondicion(campos.split(", "), criterio);
        }

        ManejoDatos md = new ManejoDatos();
        ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
        for (Hashtable<String, String> reg : res) {
            BusquedaPrograma pro = new BusquedaPrograma(
                    Integer.parseInt(reg.get("id")),
                    reg.get("Nombre"));
            programas.add(pro);
        }

        return programas;
	}

	public List<BusquedaProyecto> listarProyectos(String criterio) {
	    String tabla = "ViewProyecto";
	    String campos = "id, Nombre";
	    String condicion = "TRUE";
	    List<BusquedaProyecto> proyectos = new ArrayList<BusquedaProyecto>();

	    if (criterio != null && !criterio.equals("")) {
            condicion = armarCondicion(campos.split(", "), criterio);
        }

        ManejoDatos md = new ManejoDatos();
        ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
        for (Hashtable<String, String> reg : res) {
            BusquedaProyecto pro = new BusquedaProyecto(
                    Integer.parseInt(reg.get("id")),
                    reg.get("Nombre"));
            proyectos.add(pro);
        }

        return proyectos;
	}

	public List<BusquedaRole> listarRoles(String criterio) {
	    String tabla = "ViewRol";
        String campos = "id, nombre";
        String condicion = "TRUE";
        List<BusquedaRole> roles = new ArrayList<BusquedaRole>();

        if (criterio != null && !criterio.equals("")) {
            condicion = armarCondicion(campos.split(", "), criterio);
        }

        try {
            ManejoDatos md = new ManejoDatos();
            ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
            for (Hashtable<String, String> reg : res) {
                BusquedaRole doc = new BusquedaRole(
                        Integer.parseInt(reg.get("id")),
                        reg.get("nombre"));
                roles.add(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
	}

	public List<BusquedaUsuario> listarUsuarios(String criterio) {
	    String tabla = "ViewUsuario";
        String campos = "Usuario";
        String condicion = "TRUE";
        List<BusquedaUsuario> usuarios = new ArrayList<BusquedaUsuario>();

        if (criterio != null && !criterio.equals("")) {
            condicion = armarCondicion(campos.split(", "), criterio);
        }

        try {
            ManejoDatos md = new ManejoDatos();
            ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
            for (Hashtable<String, String> reg : res) {
                BusquedaUsuario bUs = new BusquedaUsuario(
                    reg.get("Usuario"));
                usuarios.add(bUs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
	}

	public List<BusquedaCargoDocente> listarCargosDocentes(String criterio) {
	    String tabla = "ViewCargoDocente";
        String campos = "Legajo, Nombre, Apellido, Area, Codigo, descripcion";
        String condicion = "TRUE";
        List<BusquedaCargoDocente> cargosDocentes = new ArrayList<BusquedaCargoDocente>();

        if (criterio != null && !criterio.equals("")) {
            condicion = armarCondicion(campos.split(", "), criterio);
        }

        try {
            ManejoDatos md = new ManejoDatos();
            List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
            for (Hashtable<String, String> reg : res) {
                BusquedaCargoDocente bCD = new BusquedaCargoDocente(
                    reg.get("Legajo"),
                    reg.get("Nombre"),
                    reg.get("Apellido"),
                    reg.get("Area"),
                    reg.get("Codigo"),
                    reg.get("descripcion")

                );
                cargosDocentes.add(bCD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cargosDocentes;
	}

	private String armarCondicion(String[] campos, String criterio) {
		String[] condicion = new String[campos.length];
		for (int i = 0; i < campos.length; i++) {
			condicion[i] = campos[i] + " LIKE '%" + criterio + "%'";
		}
		return Utilidades.joinString(condicion, " OR ");
	}
}
