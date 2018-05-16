package controlador;

import java.util.List;

import modelo.division.GestorArea;
import modelo.division.GestorDivision;
import modelo.division.IArea;
import vista.controladores.ControladorVista;

public class ControlDivision {

    private ControladorVista vista;

    private GestorArea gestorArea = new GestorArea();
    private GestorDivision gestorDivision = new GestorDivision();

    public ControlDivision(ControladorVista vista) {
        this.vista = vista;
    }

    public List<IArea> listarAreas(IArea area) {
        return this.gestorArea.listarArea(area);
    }
}
