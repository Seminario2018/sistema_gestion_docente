package modelo.usuario;


public enum Modulo {
	GENERAL(0, "General"),
	ESTACION(1, "Estación"),
	ROLES(2, "Grupos"),
	USUARIOS(3, "Usuarios"),
	CARGOS(4, "Cargos"),
	DOCENTES(5, "Docentes"),
	INVESTIGACION(6, "Investigación"),
	INFORMES(7, "Ver Informes"),
	INFORMES2(8, "Editar Informes"),
	PERSONAS(9, "Personas"),
	PROYECTOS(10, "Proyectos"),
	PROGRAMAS(11, "Programas");

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