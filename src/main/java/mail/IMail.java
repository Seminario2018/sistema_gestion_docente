package mail;

public interface IMail {
    public boolean enviarEmail(String mailDesde, String mailHasta, String asunto,
                            String mensaje, String contrasena);
}
