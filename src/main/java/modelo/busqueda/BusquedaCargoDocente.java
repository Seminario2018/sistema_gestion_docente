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
    private String codigo;
    private String cargo;

	public BusquedaCargoDocente() {
	}

	public BusquedaCargoDocente(String legajo, String apellido, String nombre,
			String area, String codigo, String cargo) {

		this.legajo = Integer.parseInt(legajo);
		this.nombre = apellido + ", " + nombre;
		
		if (area == null || "".equals(area)) {
			this.area = "-";
		}else {
			this.area = area;
		}
		
		if (codigo == null || "".equals(codigo)) {
			this.codigo = "-";
		} else {
			this.codigo = codigo;
		}
		
		if (cargo == null || "".equals(cargo)) {
			this.cargo = "-";
		} else {
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
    public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCargo() {
        return this.cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
