package modelo.persona;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */

public class Domicilio implements IDomicilio {
	private int id;
	private String provincia;
	private String ciudad;
	private String codigoPostal;
	private String direccion;

	public Domicilio(int id, String provincia, String ciudad, String codigoPostal, String direccion) {
		super();
		this.id = id;
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
	}

	public Domicilio() {
	    this.id = -1;
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
	public String getProvincia() {
		return provincia;
	}

	@Override
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public String getCiudad() {
		return ciudad;
	}

	@Override
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public String getCodigoPostal() {
		return codigoPostal;
	}

	@Override
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Override
	public String getDireccion() {
		return direccion;
	}

	@Override
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public IDomicilio clone() {
		return new Domicilio(id, provincia, ciudad, codigoPostal, direccion);
	}

}