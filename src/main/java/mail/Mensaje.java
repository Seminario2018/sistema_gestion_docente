package mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que reune los datos de un mensaje de actualización/es de
 * cargos docentes.
 * @author LeoAM
 *
 */
public class Mensaje {

    /**
     * Item de la actualización de un cargo docente dentro de un
     * mensaje.
     * @author LeoAM
     *
     */
    public class Item {
        private String legajo;
        private String cargo;
        private String estAnterior;
        private String estNuevo;

        public Item(
            String legajo,
            String cargo,
            String estAnterior,
            String estNuevo)
        {
            this.legajo = legajo;
            this.cargo = cargo;
            this.estAnterior = estAnterior;
            this.estNuevo = estNuevo;
        }

        public String getLegajo() { return this.legajo; }
        public String getCargo() { return this.cargo; }
        public String getEstadoAnterior() { return this.estAnterior; }
        public String getEstadoNuevo() { return this.estNuevo; }
    }

    private List<Item> items = new ArrayList<Item>();
    private String asunto = "";
    private String destino = "";
    private String emisor = "";
    private String mensaje = "";
    private String resultado = "";
    private String usuario = "";

    public String getAsunto() {
        return this.asunto;
    }
    public String getDestino() {
        return this.destino;
    }
    public String getEmisor() {
        return this.emisor;
    }
    public String getMensaje() {
        return this.mensaje;
    }
    public String getResultado() {
        return this.resultado;
    }
    public String getUsuario() {
        return this.usuario;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void addItem(
        String legajo,
        String cargo,
        String estAnterior,
        String estNuevo)
    {
        this.items.add(new Item(legajo, cargo, estAnterior, estNuevo));
    }

    public List<Item> getItems() {
        return this.items;
    }

}
