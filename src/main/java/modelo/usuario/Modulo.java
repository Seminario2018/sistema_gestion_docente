package modelo.usuario;


public enum Modulo {
	GENERAL,
	ESTACION,
	ROLES,
	USUARIOS,
	CARGOS,
	DOCENTES,
	INVESTIGACION,
	VER_INFORMES,
	EDIT_INFORMES,
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
	        case VER_INFORMES: return "Ver Informes";
	        case EDIT_INFORMES: return "Editar informes";
	    }
	    return "";
	}
}