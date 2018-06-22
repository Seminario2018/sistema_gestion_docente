package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.busqueda.BusquedaArea;
import modelo.busqueda.BusquedaCargo;
import modelo.busqueda.BusquedaDocente;
import modelo.busqueda.BusquedaPersona;
import modelo.busqueda.BusquedaPrograma;
import modelo.busqueda.BusquedaProyecto;
import modelo.busqueda.BusquedaRole;
import modelo.busqueda.BusquedaUsuario;
import modelo.busqueda.GestorBusqueda;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.division.GestorArea;
import modelo.division.IArea;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import modelo.investigacion.GestorPrograma;
import modelo.investigacion.GestorProyecto;
import modelo.investigacion.IPrograma;
import modelo.investigacion.IProyecto;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.usuario.GestorRol;
import modelo.usuario.GestorUsuario;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import vista.controladores.Busqueda;
import vista.controladores.ControladorVista;
import vista.controladores.Docentes;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class ControlBusqueda {

	@SuppressWarnings("unused")
    private ControladorVista vista;

	private GestorBusqueda gestorBusqueda = new GestorBusqueda();
	private GestorDocente gestorDocente;
	private GestorPersona gestorPersona;
	private GestorArea gestorArea;
	private GestorCargo gestorCargo;

	public ControlBusqueda(ControladorVista vista) {
		super();
		this.vista = vista;
	}

	public List<BusquedaDocente> listarDocentes(String criterio) {
		return gestorBusqueda.listarDocentes(criterio);
	}

	public List<BusquedaPersona> listarPersonas(String criterio) {
		List<BusquedaPersona> personas = this.gestorBusqueda.listarPersonas(criterio);
		/* Hotfix para no asignar dos Docentes a una Persona */
		if (this.vista instanceof Busqueda) {
			Busqueda busqueda = (Busqueda) this.vista;
			if (busqueda.getControladorRespuesta() instanceof Docentes) {
				List<BusquedaPersona> resultado = new ArrayList<BusquedaPersona>();
				for (BusquedaPersona p : personas) {
					this.gestorDocente = new GestorDocente();
					this.gestorPersona = new GestorPersona();
					IDocente docenteSelect = this.gestorDocente.getIDocente();
					IPersona personaSelect = this.gestorPersona.getIPersona();
					personaSelect.setNroDocumento(p.getDocumento());
					docenteSelect.setPersona(personaSelect);
					if (this.gestorDocente.listarDocentes(docenteSelect).isEmpty())
						resultado.add(p);
				}
				return resultado;
			}
		}
		return personas;
	}

	public List<BusquedaArea> listarAreas(String criterio) {
		return gestorBusqueda.listarAreas(criterio);
	}

	public List<BusquedaCargo> listarCargos(String criterio) {
		return gestorBusqueda.listarCargos(criterio);
	}

	public List<BusquedaPrograma> listarProgramas(String criterio) {
	    return gestorBusqueda.listarProgramas(criterio);
	}

	public List<BusquedaProyecto> listarProyectos(String criterio) {
	    return gestorBusqueda.listarProyectos(criterio);
	}

	public List<BusquedaRole> listarRoles(String criterio) {
	    return gestorBusqueda.listarRoles(criterio);
	}

	public List<BusquedaUsuario> listarUsuarios(String criterio) {
	    return gestorBusqueda.listarUsuarios(criterio);
	}

	public Object seleccionar(Object fila) {
		if (fila instanceof BusquedaDocente) {
			BusquedaDocente bd = (BusquedaDocente) fila;
			this.gestorDocente = new GestorDocente();
			IDocente doc = this.gestorDocente.getIDocente();
			doc.setLegajo(bd.getLegajo());
			return this.gestorDocente.listarDocentes(doc).get(0);
		}

		if (fila instanceof BusquedaPersona) {
			BusquedaPersona bp = (BusquedaPersona) fila;
			this.gestorPersona = new GestorPersona();
			IPersona per = this.gestorPersona.getIPersona();
			per.setNroDocumento(bp.getDocumento());
			return this.gestorPersona.listarPersonas(per).get(0);
		}

		if (fila instanceof BusquedaArea) {
			BusquedaArea ba = (BusquedaArea) fila;
			this.gestorArea = new GestorArea();
			IArea area = this.gestorArea.getIArea();
			area.setCodigo(ba.getCodigo());
			return this.gestorArea.listarAreas(area).get(0);
		}

		if (fila instanceof BusquedaCargo) {
			BusquedaCargo bc = (BusquedaCargo) fila;
			this.gestorCargo = new GestorCargo();
			ICargo Cargo = this.gestorCargo.getICargo();
			Cargo.setCodigo(bc.getCodigo());
			return this.gestorCargo.listarCargos(Cargo).get(0);
		}

		if (fila instanceof BusquedaProyecto) {
		    BusquedaProyecto bp = (BusquedaProyecto) fila;
		    GestorProyecto gestorProyecto = new GestorProyecto();
		    IProyecto proyecto = gestorProyecto.getIProyecto();
		    proyecto.setId(bp.getId());
		    return gestorProyecto.listarProyecto(proyecto, null).get(0);
		}

		if (fila instanceof BusquedaPrograma) {
		    BusquedaPrograma bp = (BusquedaPrograma) fila;
		    GestorPrograma gestorPrograma = new GestorPrograma();
		    IPrograma programa = gestorPrograma.getIPrograma();
		    programa.setId(bp.getId());
		    return gestorPrograma.listarProgramas(programa).get(0);
		}

		if (fila instanceof BusquedaUsuario) {
		    BusquedaUsuario bu = (BusquedaUsuario) fila;
		    GestorUsuario gestorUsuario = new GestorUsuario();
		    IUsuario usuario = gestorUsuario.getIUsuario();
		    usuario.setUser(bu.getUsuario());
		    return gestorUsuario.listarUsuario(usuario).get(0);
		}

		if (fila instanceof BusquedaRole) {
		    BusquedaRole br = (BusquedaRole) fila;
		    GestorRol gestorRol = new GestorRol();
		    IRol rol = gestorRol.getIRol();
		    rol.setId(br.getId());
		    return gestorRol.listarGrupo(rol).get(0);
		}

		return null;
	}
}
