package modelo.informe;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.Conexion;
import persistencia.ManejoDatos;
import utilidades.LogQuery;
import utilidades.Utilidades;


public class GestorInforme {
	
	ITipoInforme informeActual;
	
	
	/**
	 * Devuelve la vista previa del informe. También se usa para exportar a Excel
	 * @param informe El TipoInforme a importar
	 * @return Una grilla con los valores del informe
	 */
	public List<List<String>> vistaPrevia(ITipoInforme informe) {
		List<List<String>> grilla = new ArrayList<List<String>>();
		Conexion conexion = new Conexion();
		try {
			String query = informe.armarConsulta();
			LogQuery.log(query);
			
			Connection con = conexion.conectar();
			
			ResultSet resultSet = con.createStatement().executeQuery(query);
		    while (resultSet.next()) {
		    	List<String> fila = new ArrayList<String>();
		    	for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
		    		fila.add(resultSet.getString(i) == null ? "" : resultSet.getString(i));
		    	grilla.add(fila);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.desconectar();
		}
		
		return grilla;
	}
	
// ------------------------------ Persistencia ------------------------------ //  
	
	public EstadoOperacion nuevoInforme(ITipoInforme informe) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "TiposInformes";

			informe.setId(GestorInforme.getMaxID(table, "id"));

			int editable = informe.isEditable() ? 1 : 0;
			//
			String campos = "`id`, `Nombre`, `Editable`";
			String valores = informe.getId() + ", '" + informe.getNombre()+ "', " + editable;

			if (informe.getDescripcion() != null && !informe.getDescripcion().equals("")) {
				campos += ", `Descripcion`";
				valores += "'" + informe.getDescripcion() + "'";
			}
			if (informe.getFromString() != null && !informe.getFromString().equals("")) {
				campos += ", `FromString`";
				valores += ", " + informe.getFromString() + "'";
			}
			if (informe.getGroupByString() != null && !informe.getFromString().equals("")) {
				campos += ", `GroupByString`";
				valores += ", '" + informe.getGroupByString() + "'";

			}

			md.insertar(table, campos, valores);

			for (ColumnaInforme col : informe.getColumnas()) {
				this.agregarColumna(informe, col);
			}

			return md.isEstado() ?
			    new EstadoOperacion(CodigoEstado.INSERT_OK, "El Informe se creó correctamente") :
		        new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Informe");
		}
	}

	public void agregarColumna(ITipoInforme informe, ColumnaInforme col) throws Exception {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Columnas";
			String campos = "`TipoInforme`, `Visible`, `Atributo`, `Ordenar`, `Posicion`, `Tipo`";
			int visible = col.isVisible() ? 1 : 0;
			String valores = informe.getId() + ", " + visible + ", '" + col.getAtributo() + "', " + col.getOrdenar() + ", " + col.getPosicion() + ", '" + col.getTipo() + "'";

			if (col.getNombre() != null && !col.getNombre().equals("")) {
				campos += ", `Nombre`";
				valores += ", '" + col.getNombre() + "'";
			}
			if (col.getFiltros() != null) {
				campos += ", `Filtros`";
				valores += ", '" + col.getFiltros() + "'";
			}
			if (col.getCalculo() != null && !col.getCalculo().equals("")) {
				campos += ", `Calculo`";
				valores += ", '" + col.getCalculo() + "'";
			}

			md.insertar(table, campos, valores);


		} catch (Exception var6) {
			throw new Exception();
		}
	}
	
	public void modificarColumna(ITipoInforme informe, ColumnaInforme col) {
		try {
			if (informe == null || informe.getId() < 0) throw new Exception("Falta el ID de Informe");
			if (col == null || col.getAtributo() == null || col.getAtributo().equals("")) throw new Exception ("Falta el atributo de la columna");
			
			ManejoDatos md = new ManejoDatos();
			String tabla = "Columnas";
			String condicion = "`TipoInforme` = " + informe.getId() + " AND `Atributo` = " + col.getAtributo();
			
			List<String> campos = new ArrayList<String>();
			if (col.isVisible() != null) campos.add("Visible = " + (col.isVisible() ? 1 : 0));
			if (col.getNombre() != null) campos.add("Nombre = '" + col.getNombre() + "'");
			if (col.getFiltros() != null && !col.getFiltros().isEmpty())
				campos.add("Filtros = '" + col.getFiltros().get(0) + "'");
			if (col.getCalculo() != null && !col.getCalculo().equals(""))
				campos.add("Calculo = '" + col.getCalculo() + "'");
			if (col.getOrdenar() > -1) campos.add("Ordenar = " + col.getOrdenar());
			if (col.getPosicion() > -1) campos.add("Posicion = " + col.getPosicion());
			if (col.getTipo() != null && !col.getTipo().equals(""))
				campos.add("Tipo = '" + col.getTipo() + "'");
			
			md.update(tabla, Utilidades.joinString(campos, ", "), condicion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

	public void eliminarColumna(ITipoInforme informe, ColumnaInforme col) {
		ManejoDatos md = new ManejoDatos();
		md.delete("Columnas", "TipoInforme = " + informe.getId() + " AND Atributo = '" + col.getAtributo() + "'");
	}


	public EstadoOperacion modificarInforme(ITipoInforme informe) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposInformes";
			int editable = informe.isEditable() ? 1 : 0;
			String campos = "Nombre = '" + informe.getNombre() + "', "
					+ "Editable = " + editable;

			if (informe.getDescripcion() != null && !informe.getDescripcion().equals("")) {
				campos += ", Descripcion = '" + informe.getDescripcion() + "'";
			}

			if (informe.getFromString() != null && !informe.getFromString().equals("")) {
				campos += ", FromString = '" + informe.getFromString() + "'";
			}

			if (informe.getGroupByString() != null && !informe.getGroupByString().equals("")) {
				campos += ", GroupByString = '" + informe.getGroupByString() + "'";
			}
			String condicion = "`id` = '" + informe.getId() + "'";
			md.update(tabla, campos, condicion);
			return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El informe se modificó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el informe");
		}
	}


	public EstadoOperacion eliminarInforme(TipoInforme informe) {
		try {
			ManejoDatos md = new ManejoDatos();

			md.delete("`TiposInformes`", "id = " + informe.getId());
			return new EstadoOperacion(CodigoEstado.DELETE_OK, "El informe se eliminó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el informe");
		}
	}




	public ArrayList<ITipoInforme> listarInforme(ITipoInforme informe) {

		ArrayList<ITipoInforme> informes = new ArrayList<ITipoInforme>();
		String condicion = this.armarCondicion(informe);
		//`id`, `Nombre`, `Descripcion`, `Editable`, `FromString`, `GroupByString`
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposInformes";
			ArrayList<Hashtable<String,String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				TipoInforme t = new TipoInforme();
				t.setId(Integer.parseInt(reg.get("id")));
				t.setNombre(reg.get("Nombre"));

				if (!reg.get("Descripcion").equals("")) {
					t.setDescripcion(reg.get("Descripcion"));
				}

				t.setEditable((reg.get("Editable") == "1" ? true : false));

				if (!reg.get("FromString").equals("")) {
					t.setFromString(reg.get("FromString"));
				}
				if (!reg.get("GroupByString").equals("")) {
					t.setGroupByString(reg.get("GroupByString"));
				}

				t.setColumnas(this.listarColumnas(t));

				informes.add(t);
			}

		}catch (Exception e) {
			informes = new ArrayList<ITipoInforme>();
		}


		return informes;
	}


	public List<ColumnaInforme> listarColumnas(ITipoInforme informe) {
		List<ColumnaInforme> columnas = new ArrayList<ColumnaInforme>(); 
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Columnas";
			String condicion = "TipoInforme = " + informe.getId() + " ORDER BY Columnas.Posicion";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				ColumnaInforme c = new ColumnaInforme();
				
				c.setAtributo(reg.get("Atributo"));
				c.setVisible("1".equals(reg.get("Visible")) ? true : false);
				if (!reg.get("Nombre").equals("")) {
					c.setNombre(reg.get("Nombre"));
				}
				if (!reg.get("Filtros").equals("")) {
					c.setFiltros(Arrays.asList(reg.get("Filtros").split("AND")));
				}
				if (!reg.get("Calculo").equals("")) {
					c.setCalculo(reg.get("Calculo"));
				}
				if (reg.containsKey("Tipo")) c.setTipo(reg.get("Tipo"));
				
				c.setOrdenar(Integer.parseInt(reg.get("Ordenar")));
				c.setPosicion(Integer.parseInt(reg.get("Posicion")));
				columnas.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnas;
	}


	public List<ColumnaInforme> listarColumnas(ITipoInforme informe, ColumnaInforme columna) {
		List<ColumnaInforme> columnas = new ArrayList<ColumnaInforme>();
		ManejoDatos md = new ManejoDatos();
		String tabla = "Columnas";
		String condicion = this.armarCondicion(informe, columna);

		ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
		for (Hashtable<String, String> reg : res) {
			ColumnaInforme c = new ColumnaInforme();
			//`Visible`, `Nombre`, `Atributo`, `Filtros`, `Calculo`, `Ordenar`, `Posicion`
			c.setVisible(reg.get("Visible") == "1" ? true : false);

			if (!reg.get("Visible").equals("")) {
				c.setNombre(reg.get("Nombre"));
			}
			c.setAtributo(reg.get("Atributo"));
			if (!reg.get("Filtros").equals("")) {
				c.setFiltros(Arrays.asList(reg.get("Filtros").split(",")));
			}
			if (!reg.get("Calculo").equals("")) {
				c.setCalculo(reg.get("Calculo"));
			}
			c.setOrdenar(Integer.parseInt(reg.get("Ordenar")));
			c.setPosicion(Integer.parseInt(reg.get("Posicion")));
			columnas.add(c);
		}

		return columnas;
	}





	private String armarCondicion(ITipoInforme informe, ColumnaInforme columna) {
		String condicion = "TRUE";
		if (informe != null) {
			condicion += " AND TipoInforme = " + informe.getId();
		}
		if (columna !=null) {
			if (columna.getNombre() != null && !columna.getNombre().equals("")) {
				condicion += " AND Nombre = '" + columna.getNombre() + "'";
			}
			if (columna.getAtributo() != null && ! columna.getAtributo().equals("")) {
				condicion += " AND Atributo = '" + columna.getAtributo() + "'";
			}
			if (columna.getFiltros() != null) {
				condicion += " AND Filtros = '" + columna.getFiltros().toString() + "'";
			}
			if (columna.getOrdenar() != -1) {
				condicion += " AND Ordenar = " + columna.getOrdenar();
			}
			if (columna.getPosicion() != -1) {
				condicion += " AND Posicion = " + columna.getPosicion();
			}
		}

		return condicion;
	}





	private String armarCondicion(ITipoInforme informe) {
		String condicion = "TRUE";
		if (informe != null) {
			if (informe.getId() > -1)
				condicion += " AND id = " + informe.getId();
			
			String nombre = informe.getNombre();
			if (nombre != null && !"".equals(nombre))
				condicion += " AND Nombre = '" + nombre + "'";
			
			String descripcion = informe.getDescripcion();
			if (descripcion != null && !"".equals(descripcion))
				condicion += " AND Descripcion = '" + descripcion + "'";
			
			if (informe.isEditable() != null)
				condicion += " AND Editable = " + (informe.isEditable() ? 1 : 0);
			/*	
			String from = informe.getFromString();
			if (from != null && !"".equals(from)) 
				condicion += " AND FromString = '" + from + "'";
			*/
		}
		return condicion;
	}

	private static int getMaxID(String tabla, String string) {
		int maxID = 0;
		try {
			ManejoDatos md = new ManejoDatos();
			String campo = "MAX(" + string + ")";
			maxID = Integer.parseInt(md.select(tabla, campo).get(0).get(campo));
		} catch (Exception e) {
			maxID = 0;
		}

		return maxID;
	}

	public ITipoInforme getITipoInforme() {
		return new TipoInforme();
	}

}