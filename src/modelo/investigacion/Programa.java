package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;

import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;

public class Programa {
	private int id;
	private String nombre;
	private String disposicion;
	private IDocente director;
	private IDocente codirector;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private EstadoPrograma estado;
	
	private ArrayList<IProyecto> proyectos;
}