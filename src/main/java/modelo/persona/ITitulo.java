package modelo.persona;


public interface ITitulo {
    public ITitulo clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public boolean isEsMayor();
    public void setEsMayor(boolean esMayor);
}