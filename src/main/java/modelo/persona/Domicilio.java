package modelo.persona;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */
public class Domicilio implements IDomicilio {
	private String provincia;
	private String ciudad;
	private String codigoPostal;
	private String direccion;
	
	
	public Domicilio(String provincia, String ciudad, String codigoPostal, String direccion) {
		super();
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
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
		return new Domicilio(provincia, ciudad, codigoPostal, direccion);
	}
	
}