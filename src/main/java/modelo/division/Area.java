package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public class Area implements IArea {

    private int codigo;
    private String descripcion;
    private IDivision division;
    private IDocente responsable;
    private String disposicion;
    private LocalDate dispDesde;
    private LocalDate dispHasta;
    private IArea subAreaDe;

    @Override
    public IArea clone() {
        return (IArea) new Area(
            this.codigo,
            this.descripcion,
            this.division,
            this.responsable,
            this.disposicion,
            this.dispDesde,
            this.dispHasta,
            this.subAreaDe);
    }

    public Area(int codigo, String descripcion, IDivision division,
            IDocente responsable, String disposicion, LocalDate dispDesde,
            LocalDate dispHasta, IArea subAreaDe) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.division = division;
        this.responsable = responsable;
        this.disposicion = disposicion;
        this.dispDesde = dispDesde;
        this.dispHasta = dispHasta;
        this.subAreaDe = subAreaDe;
    }

    @Override
    public int getCodigo() {
        return this.codigo;
    }

    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public IDivision getDivision() {
        return this.division;
    }

    @Override
    public void setDivision(IDivision division) {
        this.division = division;
    }

    @Override
    public IDocente getDocenteResponsable() {
        return this.responsable;
    }

    @Override
    public void setDocenteResponsable(IDocente responsable) {
        this.responsable = responsable;
    }

    @Override
    public String getDisposicion() {
        return this.disposicion;
    }

    @Override
    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    @Override
    public LocalDate getDispDesde() {
        return this.dispDesde;
    }

    @Override
    public void setDispDesde(LocalDate dispDesde) {
        this.dispDesde = dispDesde;
    }

    @Override
    public LocalDate getDispHasta() {
        return this.dispHasta;
    }

    @Override
    public void setDispHasta(LocalDate dispHasta) {
        this.dispHasta = dispHasta;
    }

    @Override
    public IArea getAreaDe() {
        return this.subAreaDe;
    }

    @Override
    public void setAreaDe(IArea subAreaDe) {
        this.subAreaDe = subAreaDe;
    }

}