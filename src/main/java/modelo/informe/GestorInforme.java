package modelo.informe;


import java.util.ArrayList;
import java.util.Hashtable;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;


public class GestorInforme {

	public EstadoOperacion nuevoInforme(TipoInforme informe) {
		try {
			ManejoDatos e = new ManejoDatos();
			String table = "TiposInformes";

			int editable = informe.isEditable() ? 1 : 0;
			String campos = "`id`, `Nombre`, `Descripcion`, `Editable`, `FromString`, `GroupByString`";
			String valores = informe.getId() + ", '" + informe.getNombre()+ "', "
					+ "'" + informe.getDescripcion() + "', "
					+ editable + ", "
					+ "'" + informe.getFromString() + "', "
					+ "'" + informe.getGroupByString() + "'";
			e.insertar(table, campos, valores);
			
			for (ColumnaInforme col : informe.getColumnas()) {
				this.agregarColumna(informe, col);
			}
			
			return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Informe se creó correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		} catch (Exception var6) {
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Informe");
		}
	}


	public void agregarColumna(TipoInforme informe, ColumnaInforme col) throws Exception {
		//`TipoInforme`, `Visible`, `Nombre`, `Atributo`, `Filtros`, `Calculo`, `Ordenar`, `Posicion`
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Columnas";
			String campos = "`TipoInforme`, `Visible`, `Nombre`, `Atributo`, `Filtros`, `Calculo`, "
					+ "`Ordenar`, `Posicion`";
			int visible = col.isVisible() ? 1 : 0;
			String valores = informe.getId() + ", " + visible + ", '" + col.getNombre() + "', "
					+ "'" + col.getAtributo() + "', '" + col.getFiltros().toString() + "', "
					+ "'" + col.getCalculo() + "', " + col.getOrdenar() + ", " + col.getPosicion();
			md.insertar(table, campos, valores);
			
			
		} catch (Exception var6) {
			throw new Exception(); 
		}
	}
	
	public void eliminarComuna(TipoInforme informe, ColumnaInforme col) {
		ManejoDatos md = new ManejoDatos();
		md.delete("Columnas", "TipoInforme = " + informe.getId() + " AND Atributo = '" + col.getAtributo() + "'");
	}


	public EstadoOperacion modificarInforme(TipoInforme informe) {
		try {
			ManejoDatos e = new ManejoDatos();
			String tabla = "TiposInformes";
			int editable = informe.isEditable() ? 1 : 0;
			String campos = "Nombre = '" + informe.getNombre() + "', "
					+ "Descripcion = '" + informe.getDescripcion() + "', "
					+ "Editable = " + editable + ", "
					+ "FromString = '" +informe.getFromString() + "', "
					+ "GroupByString = '" + informe.getGroupByString() + "'";

			String condicion = "`id` = '" + informe.getId() + "'";
			e.update(tabla, campos, condicion);
			return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El informe se modificó correctamente");
		} catch (Exception var6) {
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el informe");
		}
	}


	public EstadoOperacion eliminarInforme(TipoInforme informe) {
		try {
			ManejoDatos e = new ManejoDatos();

			e.delete("`TiposInformes`", "id = " + informe.getId());
			return new EstadoOperacion(CodigoEstado.DELETE_OK, "El informe se eliminó correctamente");
		} catch (Exception var3) {
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el informe");
		}
	}




	public ArrayList<ITipoInforme> listarProyecto(ITipoInforme informe) {

		ArrayList<ITipoInforme> informes = new ArrayList<ITipoInforme>();
		String condicion = "TRUE";
		condicion += this.armarCondicion(informe);
		//`id`, `Nombre`, `Descripcion`, `Editable`, `FromString`, `GroupByString`
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposInformes";
			ArrayList<Hashtable<String,String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				TipoInforme t=new TipoInforme();
				t.setId(Integer.parseInt(reg.get("id")));
				t.setNombre(reg.get("Nombre"));
				t.setDescripcion(reg.get("Descripcion"));
				t.setEditable((reg.get("Editable") == "1" ? true : false));
				t.setFromString(reg.get("FromString"));
				t.setGroupByString(reg.get("GroupByString"));
				informes.add(t);	
			}

		}catch (Exception e) {
			informes = new ArrayList<ITipoInforme>();
		}


		return informes;
	}




	private String armarCondicion(ITipoInforme informe) {

		String condicion = "TRUE";
		if (informe != null) {
			condicion = "";

			if (informe.getId() != 0) {
				condicion += " `id` = " + informe.getId();
			}
			if (!informe.getNombre().equals("") ){
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Nombre` = '" + informe.getNombre() + "'";
			}
			if (!informe.getDescripcion().equals("")) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " Descripcion = '" + informe.getDescripcion() + "'";
			}
			if (informe.isEditable()) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " Editable = " + 1;
			}else {
				if (!condicion.equals("") ) {
					condicion += " AND ";
				}
				condicion += " Editable = " + 0;
			}
			if (!informe.getFromString().equals("") ) {
				if (!condicion.equals("") ) {
					condicion += " AND ";
				}
				condicion += " `from` = '" + informe.getFromString() + "'";
			}

			if (informe.getColumnas()!= null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `TipoInformecol` = '" +informe.getColumnas() + "'";
			}
		}
		return condicion;
	}


}