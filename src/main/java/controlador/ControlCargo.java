package controlador;

import java.util.List;

import com.sun.tracing.dtrace.ProviderAttributes;

import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.ICargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.Cargo;

public class ControlCargo {
	
	private GestorCargo gestorCargo=new GestorCargo();
	
	  public EstadoOperacion nuevoCargo(ICargo cargo) {
		  return this.gestorCargo.nuevoCargo(cargo);
	    }
	  
	  public EstadoOperacion modificarCargo(ICargo cargo) {
	        return this.gestorCargo.modificarCargo(cargo);
	    }
	  
	  public EstadoOperacion eliminarCargo(ICargo cargo) {
	        return this.gestorCargo.eliminarCargo(cargo);
	    }

	  
	  public List<ICargo> listarCargo(ICargo cargo){
		  
		  return this.gestorCargo.listarCargo(cargo);
		  
	  }
}