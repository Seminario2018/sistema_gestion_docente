package modelo.informe;

import java.util.List;

public interface ITipoInforme {
    public ITipoInforme clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public boolean isEditable();
    public void setEditable(boolean editable);
    public List<ColumnaInforme> getColumnas();
    public void setColumnas(List<ColumnaInforme> columnas);
    public String getFromString();
    public void setFromString(String from);
    public String getGroupByString();
    public void setGroupByString(String groupby);
    
    public String armarConsulta();
}