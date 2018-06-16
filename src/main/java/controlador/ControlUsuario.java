package controlador;

import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.usuario.GestorRol;
import modelo.usuario.GestorUsuario;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import vista.controladores.ControladorVista;

public class ControlUsuario {
    private ControladorVista vista;

    private GestorRol gestorRol = new GestorRol();
    private GestorUsuario gestorUsuario = new GestorUsuario();

    public ControlUsuario(ControladorVista vista) {
        this.vista = vista;
    }

//  Usuarios
    public EstadoOperacion guardarUsuario(IUsuario usuario) {
        if (!GestorUsuario.existeUsuario(usuario)) {
            return gestorUsuario.nuevoUsuario(usuario);
        } else {
            return gestorUsuario.modificarUsuario(usuario);
        }
    }

    public EstadoOperacion eliminarUsuario(IUsuario usuario) {
        return gestorUsuario.eliminarUsuario(usuario);
    }

    public List<IUsuario> listarUsuario(IUsuario usuario) {
        return gestorUsuario.listarUsuario(usuario);
    }

    public EstadoOperacion agregarRol(IUsuario usuario, IRol rol) {
        return gestorUsuario.agregarRol(usuario, rol);
    }

    public EstadoOperacion quitarRol(IUsuario usuario, IRol rol) {
        return gestorUsuario.quitarGrupo(usuario, rol);
    }

    public IUsuario getIUsuario() {
        return gestorUsuario.getIUsuario();
    }

//  Roles
    public EstadoOperacion guardarGrupo(IRol rol) {
        if (rol.getId() == -1) {
            return gestorRol.nuevoGrupo(rol);
        } else {
            return gestorRol.modificarGrupo(rol);
        }
    }

    public EstadoOperacion eliminarGrupo(IRol rol) {
        return gestorRol.eliminarGrupo(rol);
    }

    public List<IRol> listarGrupo(IRol rol) {
        return gestorRol.listarGrupo(rol);
    }

    public IRol getIRol() {
        return gestorRol.getIRol();
    }
    
    public IPermiso getIPermiso() {
    	return gestorRol.getIPermiso();
    }
}