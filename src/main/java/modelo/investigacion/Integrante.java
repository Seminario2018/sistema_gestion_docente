package modelo.investigacion;

import java.util.ArrayList;
import java.util.Hashtable;
import modelo.docente.CargoDocente;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import persistencia.ManejoDatos;

public class Integrante implements IIntegrante, IIntegranteg {
	private int id;
	private ICargoDocente cargoDocente;
	private String apellido;
	private String nombre;
	private String cargo;
	private String institucion;
	private int horasSemanales;

	/* Constructor para docentes de la UNLu */
	public Integrante(ICargoDocente cargoDocente, int horas) {
		this.horasSemanales = horas;
		this.cargoDocente = cargoDocente;
		this.institucion = "UNLu";
		if (cargoDocente != null) {
			if (cargoDocente.getDocente() != null && cargoDocente.getDocente().getPersona() != null) {
				this.apellido = cargoDocente.getDocente().getPersona().getApellido();
			}
			if (cargoDocente.getDocente() != null && cargoDocente.getDocente().getPersona() != null) {
				this.nombre = cargoDocente.getDocente().getPersona().getNombre();
			}
			if (cargoDocente.getCargo() != null) {
				this.cargo = cargoDocente.getCargo().getDescripcion();
			}
		}
	}

	/* Constructor para no docentes / docentes que no son de la UNLu */
	public Integrante(int id, String apellido, String nombre, String cargo, String institucion, int horasSemanales) {
		this.id = id;
		this.apellido = apellido;
		this.nombre = nombre;
		this.cargo = cargo;
		this.institucion = institucion;
		this.horasSemanales = horasSemanales;
	}

	/* Constructor para el clone() */
	public Integrante(int id, ICargoDocente cargoDocente, String apellido, String nombre, String cargo, String institucion,
			int horasSemanales) {
		super();
		this.id = id;
		this.cargoDocente =  cargoDocente;
		this.apellido = apellido;
		this.nombre = nombre;
		this.cargo = cargo;
		this.institucion = institucion;
		this.horasSemanales = horasSemanales;
	}

	public Integrante() {
		this.id = -1;
		this.cargoDocente = null;
		this.apellido = null;
		this.nombre = null;
		this.cargo = null;
		this.institucion = null;
		this.horasSemanales = -1;
	}

	@Override
	public IIntegrante clone() {
		return (IIntegrante) new Integrante(this.id,
				this.cargoDocente,
				this.apellido,
				this.nombre,
				this.cargo,
				this.institucion,
				this.horasSemanales);
	}

	@Override
	public String getApellido() {
		return this.apellido;
	}

	@Override
	public String getCargo() {
		return this.cargo;
	}

	@Override
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public String getInstitucion() {
		return this.institucion;
	}

	@Override
	public int getHorasSemanales() {
		return this.horasSemanales;
	}

	@Override
	public void setHorasSemanales(int horasSemanales) {
		this.horasSemanales = horasSemanales;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}

	@Override
	public void setApellido(String apellido) {
		this.apellido=apellido;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
    public ICargoDocente getCargoDocente2() {
		return this.cargoDocente;
	}

	@Override
    public void setCargoDocente(ICargoDocente cd) {
		this.cargoDocente = cd;
	}

	@Override
    public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	@Override
    public ICargoDocente getCargoDocente() {

		if (this.cargoDocente == null) {
			ICargoDocente cargo = new CargoDocente();
			ManejoDatos md = new ManejoDatos();
			String tabla = "Integrantes";
			String campos = "CargoDocente";
			String condicion = "id = " + this.id;
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			Hashtable<String, String> reg = res.get(0);
			cargo.setId(Integer.parseInt(reg.get("CargoDocente")));
			GestorDocente gd = new GestorDocente();
			cargo = gd.listarCargo(null, cargo).get(0);
			this.cargoDocente = cargo;
		}

		return this.cargoDocente;
	}


}