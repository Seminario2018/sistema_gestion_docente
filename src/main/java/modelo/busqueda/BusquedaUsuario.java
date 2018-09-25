package modelo.busqueda;


public class BusquedaUsuario {

    private String usuario;

    public BusquedaUsuario(String usuario) {
    	if (usuario == null) {
    		this.usuario = "";
    	}else {
    		this.usuario = usuario;
    	}
        
    }

    public String getUsuario() {
        return this.usuario;
    }
}
