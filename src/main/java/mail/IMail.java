package mail;

public interface IMail {
    public void enviarEmail(String mailDesde, String mailHasta, String asunto,
                            String mensaje, String contrasena);
}
