package modelo.investigacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorPrograma {
	
	public EstadoOperacion nuevoPrograma (IPrograma programa) {
		try {
			if (programa.getId() == -1) {
				programa.setId(this.getMaxID() + 1);
			}
			
			programa.getEstado().guardar();
			
			ManejoDatos md = new ManejoDatos();
			//nullables, ,
			String table = "ProgramasInvestigacion";
			String campos = "`id`, `Nombre`, `Director`, `Estado`";
			String valores = programa.getId() + ", '" + programa.getNombre() + "', "
					+ "" + programa.getDirector().getLegajo() + ", " + programa.getEstado().getId();
			if (programa.getCodirector() != null) {
				campos += ", `Codirector`";
				valores += ", " + programa.getCodirector().getLegajo();
			}
			
			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				campos += ", `Disposicion`";
				valores += ", '" + programa.getDisposicion() + "'";
			}
			
			if (programa.getFechaInicio() != null) {
				campos += ", `Desde`";
				valores += ", '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}
			
			if (programa.getFechaFin() != null) {
				campos += ", `Hasta`";
				valores += ", '" + Date.valueOf(programa.getFechaFin()) + "'";
			}
			
			md.insertar(table, campos, valores);
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, 
						"El programa se guardo correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, 
						"No se pudo guardar el programa");
			}
		}catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, 
					"No se pudo guardar el programa");
		}
	}
	
	
	

	private int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "MAX(id)";
			int maxID = 0;
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			maxID = Integer.parseInt(res.get(0).get(campos));
			
			
			return maxID;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
