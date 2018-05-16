package modelo.investigacion;

import modelo.auxiliares.TipoDocumento;

public interface IIntegrante {
   public IIntegrante clone();
    //public String getApellidoNombre();
  //  public void setApellidoNombre(String apellidoNombre);
    public String getCargo();
    public void setCargo(String cargo);
    public String getInstitucion();
    public int getHorasSemanales();
    public void setHorasSemanales(int horasSemanales);
  String getApellido();
  String getNombre();
  void setNombre(String nombre);
  void setApellido(String apellido);
  void setId(int id);
  int getId();
  void  setTipoDocumento( TipoDocumento tipoDocumento);
   TipoDocumento getTipoDocumento();
  void setNroDocumento(int nroDocumento);
  int getNroDocumento();
}