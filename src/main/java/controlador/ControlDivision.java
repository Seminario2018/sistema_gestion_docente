package controlador;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.division.GestorArea;
import modelo.division.GestorDivision;
import modelo.division.IArea;
import modelo.division.IDivision;
import vista.controladores.ControladorVista;

public class ControlDivision {

    private ControladorVista vista;

    private GestorArea gestorArea = new GestorArea();
    private GestorDivision gestorDivision = new GestorDivision();

    public ControlDivision(ControladorVista vista) {
        this.vista = vista;
    }

//  Áreas
    public EstadoOperacion nuevaArea(IArea area) {
        return this.gestorArea.nuevaArea(area);
    }

    public EstadoOperacion modificarArea(IArea area) {
        return this.gestorArea.modificarArea(area);
    }

    public EstadoOperacion eliminarArea(IArea area) {
        return this.gestorArea.eliminarArea(area);
    }

    public List<IArea> listarAreas(IArea area) {
        return this.gestorArea.listarAreas(area);
    }

//  Divisiones
    public EstadoOperacion nuevaDivision(IDivision division) {
        return this.gestorDivision.nuevaDivision(division);
    }

    public EstadoOperacion modificarDivision(IDivision division) {
        return this.gestorDivision.modificarDivision(division);
    }

    public EstadoOperacion eliminarDivision(IDivision division) {
        return this.gestorDivision.eliminarDivision(division);
    }

    public List<IDivision> listarDivisiones(IDivision division) {
        return this.gestorDivision.listarDivision(division);
    }
}
