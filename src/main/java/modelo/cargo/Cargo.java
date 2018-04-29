package modelo.cargo;


public class Cargo implements ICargo {
	private int codigo;
	private String descripcion;
	private int cargaHoraria;

	public Cargo(int codigo, String descripcion, int cargaHoraria) {
	    this.codigo = codigo;
	    this.descripcion = descripcion;
	    this.cargaHoraria = cargaHoraria;
	}

    @Override
    public ICargo clone() {
        return (ICargo) new Cargo(
            this.codigo,
            this.descripcion,
            this.cargaHoraria
            );
    }

    @Override
    public int getCodigo() {
        return this.codigo;
    }

    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    @Override
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}