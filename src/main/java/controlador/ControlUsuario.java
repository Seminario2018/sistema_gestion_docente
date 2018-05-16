package controlador;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.usuario.GestorRol;
import modelo.usuario.GestorUsuario;
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
    public EstadoOperacion nuevoUsuario(IUsuario usuario) {
        return this.gestorUsuario.nuevoUsuario(usuario);
    }

    public EstadoOperacion modificarUsuario(IUsuario usuario) {
        return this.gestorUsuario.modificarUsuario(usuario);
    }

    public EstadoOperacion eliminarUsuario(IUsuario usuario) {
        return this.gestorUsuario.eliminarUsuario(usuario);
    }

    public List<IUsuario> listarUsuario(IUsuario usuario) {
        return this.gestorUsuario.listarUsuario(usuario);
    }

    public EstadoOperacion agregarRol(IUsuario usuario, IRol rol) {
        return this.gestorUsuario.agregarRol(usuario, rol);
    }

    public EstadoOperacion quitarRol(IUsuario usuario, IRol rol) {
        return this.gestorUsuario.quitarGrupo(usuario, rol);
    }

//  Roles
    public EstadoOperacion nuevoGrupo(IRol rol) {
        return this.gestorRol.nuevoGrupo(rol);
    }

    public EstadoOperacion modificarGrupo(IRol rol) {
        return this.gestorRol.modificarGrupo(rol);
    }

    public EstadoOperacion eliminarGrupo(IRol rol) {
        return this.gestorRol.eliminarGrupo(rol);
    }

    public List<IRol> listarGrupo(IRol rol) {
        return this.gestorRol.listarGrupo(rol);
    }
}