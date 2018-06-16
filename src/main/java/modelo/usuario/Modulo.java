package modelo.usuario;


public enum Modulo {
	GENERAL,
	ROLES,
	USUARIOS,
	DOCENTES,
	INVESTIGACION,
	PERSONAS,
	PROYECTOS,
	PROGRAMAS,
	INFORMES;

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