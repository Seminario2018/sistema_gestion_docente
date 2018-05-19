package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoCargo {

    private int id;
    private String descripcion;

    public EstadoCargo(
        int id,
        String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return la lista de estados de los cargos de la BD
     */
    public static List<EstadoCargo> getLista() {
        List<EstadoCargo> listaEstados = new ArrayList<EstadoCargo>();

        ManejoDatos md = new ManejoDatos();
        List<Hashtable<String, String>> filas = md.select("estadocargo", "*");
        for (Hashtable<String, String> fila : filas) {
            listaEstados.add(new EstadoCargo(Integer.parseInt(fila.get("idestadocargo")), fila.get("Descripcion")));
        }
        return listaEstados;
    }

    @Override public String toString() {
        return this.descripcion;
    }
}
