package mail;

public interface IMail {
    /**
     * Envía un mensaje de correo electrónico
     * @param destino Dirección de correo electrónico de destino
     * @param asunto Asunto del mensaje
     * @param mensaje Contenido del mensaje
     * @return TODO
     */
    public boolean enviarEmail(String destino, String asunto, String mensaje);

    /**
     * Regresa la dirección de mail que utiliza esta instancia de Mail.
     * @return El usuario
     */
    public String getUsuario();
}
