package modelo.usuario;


public enum Modulo {
	GENERAL(0, "General"),
	ESTACION(1, "Estación de trabajo"),
	ROLES(2, "Roles"),
	USUARIOS(3, "Usuarios"),
	CARGOS(4, "Cargos"),
	DOCENTES(5, "Docentes"),
	INVESTIGACION(6, "Investigación"),
	INFORMES(7, "Informes"),
	PERSONAS(8, "Personas"),
	PROYECTOS(9, "Proyectos"),
	PROGRAMAS(10, "Programas");

    private int id;
    private String descripcion;

    private Modulo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return this.id;
    }

	@Override
	public String toString() {
	    return this.descripcion;
	}
}