package modelo.investigacion;

import java.time.LocalDate;

public class Prorroga implements IProrroga {
    private String disposicion;
    private LocalDate fechaInicio;
    
    
    
    


    private LocalDate fechaFin;

    public Prorroga(String disposicion,
            LocalDate fechaFin) {
        this.disposicion = disposicion;
        this.fechaFin = fechaFin;
    }

    public Prorroga() {
        this.disposicion = null;
        this.fechaFin = null;
    }

    @Override
    public IProrroga clone() {
        return (IProrroga) new Prorroga(
            this.disposicion,
            this.fechaFin
            );
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
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    
    @Override
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    
    @Override
    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    @Override
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

}