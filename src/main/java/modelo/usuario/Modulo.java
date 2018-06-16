package modelo.usuario;


public enum Modulo {
	GENERAL,
	ESTACION,
	ROLES,
	USUARIOS,
	CARGOS,
	DOCENTES,
	INVESTIGACION,
	INFORMES,
	PERSONAS,
	PROYECTOS,
	PROGRAMAS;

	@Override
	public String toString() {
	    switch (this) {
	        case GENERAL: return "General";
	        case ROLES: return "Roles";
	        case USUARIOS: return "Usuarios";
	        case DOCENTES: return "Docentes";
	        case INVESTIGACION: return "Investigación";
	        case INFORMES: return "Informes";
	    }
	    return "";
	}
}