package modelo.persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import persistencia.ManejoDatos;;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */
public class Persona implements IPersona {

	private String apellido;
	private String nombre;
	private LocalDate fechaNacimiento;
	private TipoDocumento tipoDocumento;
	private int nroDocumento;
	private List<IDomicilio> domicilios;
	private List<IContacto> contactos;
	private List<ITitulo> titulos;
	private EstadoPersona estado;

	public Persona(String apellido, String nombre, LocalDate fechaNacimiento, TipoDocumento tipoDocumento,
			int nroDocumento, List<IDomicilio> domicilios, List<IContacto> contactos, List<ITitulo> titulos,
			EstadoPersona estado) {
		super();
		this.apellido = apellido;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoDocumento = tipoDocumento;
		this.nroDocumento = nroDocumento;
		this.domicilios = domicilios;
		this.contactos = contactos;
		this.titulos = titulos;
		this.estado = estado;
	}

	public Persona() {
		super();
		this.apellido = null;
		this.nombre = null;
		this.fechaNacimiento = null;
		this.tipoDocumento = null;
		this.nroDocumento = 0;
		this.domicilios = null;
		this.contactos = null;
		this.titulos = null;
		this.estado = null;
	}

	@Override
	public String getApellido() {
		return apellido;
	}

	@Override
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
    public String getNombreCompleto() {
	    return this.apellido + " " + this.nombre;
	}

	@Override
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	@Override
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}


	@Override
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	@Override
	public int getNroDocumento() {
		return nroDocumento;
	}

	@Override
	public void setNroDocumento(int nroDocumento) {
		this.nroDocumento = nroDocumento;
	}


	@Override
	public List<IDomicilio> getDomicilios() {
	    if (this.domicilios == null) {
	        GestorPersona gestorPersona = new GestorPersona();
	        return gestorPersona.getDomicilios(this);
	    }
		return domicilios;
	}

	@Override
	public void setDomicilios(List<IDomicilio> domicilios) {
		this.domicilios = domicilios;
	}


	@Override
	public List<IContacto> getContactos() {
		if (this.contactos == null) {
			List<IContacto> contactos = new ArrayList<IContacto>();
			try {
				ManejoDatos md = new ManejoDatos();
				String table = "Contactos";
				String campos = "*";
				String condicion = " TipoDocumento =" + this.tipoDocumento.getId()
						+ " AND NroDocumento = '" + this.nroDocumento + "'";
				ArrayList<Hashtable<String, String>> res =
						md.select(table, campos, condicion);
				for (Hashtable<String, String> reg : res) {
					TipoContacto tc = new TipoContacto();
					tc.setId(Integer.parseInt(reg.get("Tipo")));;
					tc = TipoContacto.getTipoContacto(tc);
					IContacto c =
							new Contacto(Integer.parseInt(reg.get("idcontacto")), tc, reg.get("Valor"));
					contactos.add(c);
				}
				return contactos;
			} catch (Exception e) {
				return new ArrayList<IContacto>();
			}
		}
		return this.contactos;
	}

	@Override
	public void setContactos(List<IContacto> contactos) {
		this.contactos = contactos;
	}

	@Override
	public List<ITitulo> getTitulos() {
	    if (titulos == null) {
	        GestorPersona gestorPersona = new GestorPersona();
	        return gestorPersona.getTitulos(this);
	    }
		return titulos;
	}

	@Override
	public void setTitulos(List<ITitulo> titulos) {
		this.titulos = titulos;
	}

	@Override
	public EstadoPersona getEstado() {
		return estado;
	}

	@Override
	public void setEstado(EstadoPersona estado) {
		this.estado = estado;
	}


	@Override
	public IPersona clone(){
		return new Persona(apellido, nombre, fechaNacimiento, tipoDocumento, nroDocumento, domicilios, contactos, titulos, estado);
	}

}
