package modelo.auxiliares;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class TipoCargo {

	private int id;
	private String descripcion;

	public TipoCargo(int id, String descripcion) {
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
     * @return la lista de estados del docente de la BD
     */
    public static List<TipoCargo> getLista() {
        return new ArrayList<TipoCargo>();
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
