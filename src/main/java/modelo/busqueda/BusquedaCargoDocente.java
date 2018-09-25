package modelo.busqueda;

/**
 *
 * @author LeoAM
 *
 */
public class BusquedaCargoDocente {

    private int legajo;
    private String nombre;
    private String area;
    private int codigo;
    private String cargo;

	public BusquedaCargoDocente() {
	}

	public BusquedaCargoDocente(
	    String legajo,
	    String apellido,
	    String nombre,
	    String area,
	    String codigo,
	    String cargo) {

		
		if (legajo == null) {
			this.legajo = 0;
		}else {
			this.legajo = Integer.parseInt(legajo);
		}
		
		this.nombre = apellido + ", " + nombre;
		
		if (area == null) {
			this.area = "";
		}else {
			this.area = area;
		}
		
		if (codigo == null) {
			this.codigo = 0;
		}else {
			this.codigo = Integer.parseInt(codigo);
		}
		if (cargo == null) {
			this.cargo = "";
		}else {
			this.cargo = cargo;
		}
		
	}

	public int getLegajo() {
		return this.legajo;
	}
	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public int getCodigo() {
        return this.codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getCargo() {
        return this.cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
