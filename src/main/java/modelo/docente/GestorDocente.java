package modelo.docente;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import java.util.ArrayList;
import java.util.Hashtable;
import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.persona.IPersona;
import modelo.persona.ITitulo;
import modelo.usuario.GestorRol;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import persistencia.ManejoDatos;
import modelo.docente.*;

public class GestorDocente {

	public EstadoOperacion nuevoDocente(IDocente docente) {
		// TODO actualizar BD
		try {
			ManejoDatos md=new ManejoDatos();
			String table="docente";
			String campos="`Legajo`,`TipoDocumento`,`NroDocumento`,`Observaciones`,`CategoriaInvestigacion`,`Estado`";
			String valores="'"+docente.getLegajo()
							+"','"+docente.getPersona().getTipoDocumento()
							+"','"+docente.getPersona().getNroDocumento()
							+"','"+docente.getObservaciones()
							+"','"+docente.getCategoriaInvestigacion()
							+"','"+docente.getEstado()+"'";
			md.insertar(table, campos, valores);
			
			
			
			table="IncentivoxDocente";
			campos="`Docente`,`Persona`";
			for(IIncentivo i: docente.getIncentivos()) {
				valores = "'" + docente.getLegajo() + "', '" +i.getFecha() + "'";
            	md.insertar(table, campos, valores);
}
			
			
			if (md.isEstado())

				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,"El docente se creó correctamente");
				else {

					return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,"El docente no se creo");
					
				}
				 
		} catch (Exception e) {
					return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,"Error al crear al docente");
		
		}
	}

	public EstadoOperacion modificarDocente(IDocente docente) {
		// TODO actualizar BD
		  try {
	        	ManejoDatos md = new ManejoDatos();
	        	String tabla = "Docente";
	        	String campos = "`Legajo` = '"+ docente.getLegajo() 
	        			+"', `TipoDocumento` = '"+ docente.getPersona().getTipoDocumento() 
	        			+"', `NroDocumento`= '"+ docente.getPersona().getNroDocumento() 
	        			+"', `Observaciones`= '"+ docente.getObservaciones() 
	        			+"', `CategoriaInvestigacion` = '"+ docente.getCategoriaInvestigacion() 
	        			+"'";
	        	String condicion = "`Docente` = '" + docente.getLegajo() + "'";
	        	
	        	md.update(tabla, campos, condicion);
	        	
	            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
	                    "El Docente se modificó correctamente");
	        }catch(Exception e) {
	        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
	                    "No se pudo modificar el Docente");
	        }
	}

	public EstadoOperacion eliminarDocente(IDocente docente) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
				"El docente se eliminó correctamente");
	}

	public List<IDocente> listarDocente(IDocente docente) {
		
		
		
		if (docente != null) {
			// TODO Filtrar por los campos que ingresan
		}
		// TODO select BD
		return null;
	}

/**	public EstadoOperacion agregarTitulo(IDocente docente, ITitulo planta) {
		ERT_OManejoDatos md = new ManejoDatos();	
    	
                "El grupo se agregó correctamente");    }

    public EstadoOperacion quitarTitulo(IDocente docente, ITitulo planta) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El titulo se quitó correctamente");
    }  */

    public EstadoOperacion agregarIncentivo(IDocente docente, IIncentivo planta) {
        // TODO actualizar BD
              
            	ManejoDatos md=new ManejoDatos();

            	String tabla = "IncentivoXDocente";
            	String campos = "Docente, Incentivo";
            	String valores = "'" + docente.getLegajo() + "', '" + planta.getFecha()+ "'";  
            	
            	md.insertar(tabla, campos, valores);
            	
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                        "El incentivo se agregó correctamente");
    }

    public EstadoOperacion quitarIncentivo(IDocente docente, IIncentivo planta) {
        // TODO actualizar BD
    	ManejoDatos md = new ManejoDatos();
    	String tabla = "IncentivoXDocente";
    	String condicion = " Docente = '" + docente.getLegajo() + "', '" + planta.getFecha() + "'"; 
    	
    	md.delete(tabla, condicion);
    	
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El incentivo se quitó correctamente");
    }

	public EstadoOperacion agregarPlanta(IDocente docente, ICargo planta) {
		// TODO actualizar BD
    	ManejoDatos md = new ManejoDatos();	
    	GestorCargo cd = new GestorCargo();
    	ArrayList<ICargo> cargos = (ArrayList<ICargo>) cd.listarCargo(planta);
    	
    	if (cargos.isEmpty()) {
    		cd.nuevoCargo(planta);
    	}

    	String tabla = "cargosXdocente";
    	String campos = "docente, cargo";
    	String valores = "'" + docente.getLegajo() + "', '" + planta.getCodigo() + "'";  
    	
    	md.insertar(tabla, campos, valores);
    	
		
		
		
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
				"El cargo se agregó correctamente");
	}

	public EstadoOperacion quitarPlanta(IDocente docente, ICargo planta) {
		// TODO actualizar BD
		
		ManejoDatos md = new ManejoDatos();
    	String tabla = "cargosXdocente";
    	String condicion = " docente = '" + docente.getLegajo() + "', '" + planta.getCodigo() + "'"; 
    	
    	md.delete(tabla, condicion);
    	
		
		
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
				"El cargo se quitó correctamente");
	}
}