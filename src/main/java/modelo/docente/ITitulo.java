package modelo.docente;


public interface ITitulo {
    public ITitulo clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
}