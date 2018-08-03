package mail;

public interface IMail {
    /**
     * Envía un mensaje de correo electrónico
     * @param destino Dirección de correo electrónico de destino
     * @param asunto Asunto del mensaje
     * @param mensaje Contenido del mensaje
     */
    public void enviarEmail(String destino, String asunto, String mensaje);
}
