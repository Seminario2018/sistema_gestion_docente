package modelo.docente;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.persona.Persona;
import persistencia.ManejoDatos;

public class Docente implements IDocente, IDocenteg {
	private IPersona persona;
	private int legajo;
	private String observaciones;
	private CategoriaInvestigacion categoriaInvestigacion;
	private EstadoDocente estado;

	private List<IIncentivo> incentivos;
	private List<ICargoDocente> cargosDocentes;

	@Override
	public IDocente clone() {
		return new Docente(persona, legajo, observaciones, categoriaInvestigacion, estado, incentivos, cargosDocentes);
	}

	public Docente() {
		this.persona = null;
		this.legajo = -1;
		this.observaciones = null;
		this.categoriaInvestigacion = null;
		this.estado = null;

		this.cargosDocentes = new ArrayList<ICargoDocente>();
	    this.incentivos = new ArrayList<IIncentivo>();
	}

	public Docente(IPersona persona, int legajo, String observaciones, CategoriaInvestigacion categoriaInvestigacion,
			EstadoDocente estado, List<IIncentivo> incentivos,
			List<ICargoDocente> cargosDocentes) {
		super();
		this.persona = persona;
		this.legajo = legajo;
		this.observaciones = observaciones;
		this.categoriaInvestigacion = categoriaInvestigacion;
		this.estado = estado;
		this.incentivos = incentivos;
		this.cargosDocentes = cargosDocentes;
	}




	@Override
    public int getLegajo() {
		return legajo;
	}

	@Override
    public void setLegajo(int legajo) {
		this.legajo = legajo;
	}



	@Override
    public String getObservaciones() {
		return observaciones;
	}

	@Override
    public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
    public CategoriaInvestigacion getCategoriaInvestigacion() {
		if (this.categoriaInvestigacion == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("Docentes", "CategoriaInvestigacion", "Legajo = " + this.getLegajo());
			Hashtable<String, String> reg = res.get(0);
			CategoriaInvestigacion cat = new CategoriaInvestigacion();
			cat.setId(Integer.parseInt(reg.get("CategoriaInvestigacion")));
			cat = CategoriaInvestigacion.getCategoria(cat);
			this.setCategoriaInvestigacion(cat);
		}

		return categoriaInvestigacion;
	}

	@Override
    public void setCategoriaInvestigacion(CategoriaInvestigacion categoriaInvestigacion) {
		this.categoriaInvestigacion = categoriaInvestigacion;
	}

	@Override
    public EstadoDocente getEstado() {
		if (this.estado == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("Docentes", "Estado", "Legajo = " + this.getLegajo());
			Hashtable<String, String> reg = res.get(0);

			EstadoDocente estado = new EstadoDocente();
			estado.setId(Integer.parseInt(reg.get("Estado")));
			estado = EstadoDocente.getEstado(estado);
			this.setEstado(estado);
		}

		return estado;
	}

	@Override
    public void setEstado(EstadoDocente estado) {
		this.estado = estado;
	}

	@Override
    public List<ICargoDocente> getCargosDocentes() {
		if (this.cargosDocentes.isEmpty()) {
			this.cargarCargos();
		}
		return cargosDocentes;
	}

	@Override
    public void agregarCargoDocente(ICargoDocente planta) {
		this.cargosDocentes.add(planta);
		GestorDocente gd = new GestorDocente();
		gd.agregarCargoDocente(this, planta);
	}

	@Override
    public void quitarCargoDocente(ICargoDocente planta) {
		this.cargosDocentes.remove(planta);
		GestorDocente gd = new GestorDocente();
		gd.quitarCargoDocente(this, planta);
	}

	@Override
	public List<IIncentivo> getIncentivos() {
		if (incentivos.isEmpty()) {
			this.cargarIncentivos();
		}

		return incentivos;
	}


	@Override
	public void agregarIncentivo(IIncentivo incentivo) {
		this.incentivos.add(incentivo);
//		GestorDocente gd = new GestorDocente();
//		gd.agregarIncentivo(this, incentivo);
	}



	@Override
	public void quitarIncentivo(IIncentivo incentivo) {
		this.incentivos.remove(incentivo);
//		GestorDocente gd = new GestorDocente();
//		gd.quitarIncentivo(this, incentivo);
	}


	@Override
	public IPersona getPersona() {
		if (persona == null) {
			this.recuperarPersona();
		}
		return this.persona;
	}

	private void recuperarPersona() {
		ManejoDatos md = new ManejoDatos();
		ArrayList<Hashtable<String, String>> res = md.select("Docentes", "TipoDocumento, NroDocumento", "Legajo = " + this.getLegajo());
		Hashtable<String, String> reg = res.get(0);
		GestorPersona gp = new GestorPersona();
		Persona p = new Persona();
		TipoDocumento td = new TipoDocumento();
		td.setId(Integer.parseInt(reg.get("TipoDocumento")));
		p.setTipoDocumento(TipoDocumento.getTipo(td));
		p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
		p = (Persona) gp.listarPersonas(p).get(0);
		this.setPersona(p);
	}

	@Override
	public void setPersona(IPersona persona) {
        this.persona = persona;
    }


	private void cargarIncentivos() {
		GestorDocente gd = new GestorDocente();
		List<IIncentivo> incentivos = gd.listarIncentivos(this, null);
		for (IIncentivo iIncentivo : incentivos) {
			this.agregarIncentivo(iIncentivo);
		}

	}

	private void cargarCargos() {
		GestorDocente gd = new GestorDocente();
		List<ICargoDocente> cargos = gd.listarCargo(this, null);
		for (ICargoDocente iCargo : cargos) {
			this.agregarCargoDocente(iCargo);
		}

	}

	@Override
	public CategoriaInvestigacion getCategoriaInvestigacion2() {
		return this.categoriaInvestigacion;
	}

	@Override
	public EstadoDocente getEstado2() {
		// TODO Auto-generated method stub
		return this.estado;
	}

	@Override
	public IPersona getPersona2() {
		// TODO Auto-generated method stub
		return this.persona;
	}




}