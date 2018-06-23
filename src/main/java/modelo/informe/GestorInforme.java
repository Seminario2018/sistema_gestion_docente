package modelo.informe;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.Calculo;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.informe.ColumnaInforme.TipoColumna;
import persistencia.Conexion;
import persistencia.ManejoDatos;
import utilidades.LogQuery;
import utilidades.Utilidades;


public class GestorInforme {
	
	private ITipoInforme informeActual;
	
	public ITipoInforme getInformeActual() {
		return informeActual.clone();
	}
	public void setInformeActual(ITipoInforme informeActual) {
		this.informeActual = informeActual.clone();
	}
	
	/**
	 * Almacena el {@code informeActual} en la Base de Datos, junto a sus columnas.
	 * @return EstadoOperacion UPDATE or INSERT
	 */
	public EstadoOperacion guardar() {
		// Recuperar el anterior para hacer rollback
		ITipoInforme informeSelect = new TipoInforme();
		informeSelect.setId(this.informeActual.getId());
		ITipoInforme informeAnterior = null;
		List<ITipoInforme> informeList = listarInforme(informeSelect);
		if (informeList != null && !informeList.isEmpty() && informeSelect.getId() > -1)
			informeAnterior = informeList.get(0);
		
		EstadoOperacion eo = new EstadoOperacion(CodigoEstado.UPDATE_ERROR,
				"Ocurrió un error al almacenar el Informe.");
		try {
			if (informeAnterior != null) {
				// Modificar el anterior
				eo = modificarInforme(this.informeActual);
				// Rollback si falló en modificar
				if (eo.getEstado() != CodigoEstado.UPDATE_OK)
					modificarInforme(informeAnterior);
			} else {
				// Crear uno
				this.informeActual.setEditable(true);
				eo = nuevoInforme(this.informeActual);
				// Rollback si falló en agregar
				if (eo.getEstado() != CodigoEstado.INSERT_OK) 
					eliminarInforme(this.informeActual);
			} 
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return eo;
	}

	/**
	 * Actualiza la columna del informe.
	 * @param columna La columna a actualizar. Se basa en su Posicion.
	 * @return EstadoOperacion UPDATE
	 */
	public EstadoOperacion actualizarColumna(ColumnaInforme columna) {
		if (columna != null) {
			ColumnaInforme col = this.informeActual.getColumnas()
					.get(columna.getPosicion());
			if (col.getAtributo().equals(columna.getAtributo())) {
				this.informeActual.getColumnas().set(columna.getPosicion(), columna);
				return new EstadoOperacion(CodigoEstado.UPDATE_OK,
						"La columna se actualizó exitosamente.");
			}
		}
		
		return new EstadoOperacion(CodigoEstado.UPDATE_ERROR,
				"No se pudo actualizar la columna.");
	}
	
	/**
	 * Intercambia el orden de dos columnas.
	 * @param a La primer columna 
	 * @param b La segunda columna
	 * @return 
	 */
	public EstadoOperacion swapColumna(int a, int b) {
		if (this.informeActual == null) 
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR,
					"No hay un informe seleccionado.");
		try {
			int posA = this.informeActual.getColumnas().get(a).getPosicion();
			int posB = this.informeActual.getColumnas().get(b).getPosicion();
			this.informeActual.getColumnas().get(a).setPosicion(posB);
			this.informeActual.getColumnas().get(b).setPosicion(posA);
			Collections.swap(this.informeActual.getColumnas(), a, b);
			
			return new EstadoOperacion(CodigoEstado.UPDATE_OK,
					"Se intercambiaron las columnas " + a + " y " + b + ".");
		} catch (Exception e) {
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR,
					"No se pudieron intercambiar las columnas.");
		}
	}
	
	public List<List<String>> vistaPrevia() {
		return vistaPrevia(this.informeActual);
	}
	
	/**
	 * Devuelve la vista previa del informe. También se usa para exportar a Excel
	 * @param informe El TipoInforme a importar
	 * @return Una grilla con los valores del informe
	 */
	public List<List<String>> vistaPrevia(ITipoInforme informe) {
		List<List<String>> grilla = new ArrayList<List<String>>();
		if (informe == null) return grilla;
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

			informe.setId(GestorInforme.getMaxID(table, "id") + 1);

			int editable = informe.isEditable() ? 1 : 0;
			//
			String campos = "`id`, `Nombre`, `Editable`";
			String valores = informe.getId() + ", '" + informe.getNombre()+ "', " + editable;

			if (informe.getDescripcion() != null && !informe.getDescripcion().equals("")) {
				campos += ", `Descripcion`";
				valores += ", '" + informe.getDescripcion() + "'";
			}
			if (informe.getFromString() != null && !informe.getFromString().equals("")) {
				campos += ", `FromString`";
				valores += ", '" + informe.getFromString() + "'";
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
		        new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Informe");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Informe");
		}
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
			
			for (ColumnaInforme col : informe.getColumnas()) {
				this.modificarColumna(informe, col);
			}
			
			return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El Informe se modificó correctamente");
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Informe");
		}
	}


	public EstadoOperacion eliminarInforme(ITipoInforme informe) {
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

				t.setEditable((reg.get("Editable").equals("1") ? true : false));

				if (!reg.get("FromString").equals("")) {
					t.setFromString(reg.get("FromString"));
				}
				if (!reg.get("GroupByString").equals("")) {
					t.setGroupByString(reg.get("GroupByString"));
				}

				t.setColumnas(this.listarColumnas(t));

				informes.add(t);
			}

		} catch (Exception e) {
			informes = new ArrayList<ITipoInforme>();
		}
		return informes;
	}

	
	
	public void agregarColumna(ITipoInforme informe, ColumnaInforme col) throws Exception {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Columnas";
			
			int visible = col.isVisible() == null || !col.isVisible() ? 0 : 1;
			
			String campos = "`TipoInforme`, `Visible`, `Atributo`";
			String valores = informe.getId() + ", " + visible + ", '" + col.getAtributo() + "'";
			
			if (col.getNombre() != null && !col.getNombre().equals("")) {
				campos += ", `Nombre`";
				valores += ", '" + col.getNombre() + "'";
			}
			if (col.getFiltro() != null) {
				campos += ", `Filtros`";
				valores += ", '" + col.getFiltro().toString() + "'";
			}
			if (col.getCalculo() != null && !col.getCalculo().equals("")) {
				campos += ", `Calculo`";
				valores += ", '" + col.getCalculo().getCalculo() + "'";
			}
			
			if (col.getOrdenar() > -1) {
				campos += ", `Ordenar`";
				valores += ", " + col.getOrdenar();
			}
			
			if (col.getPosicion() > -1) {
				campos += ", `Posicion`";
				valores += ", " + col.getPosicion();
			}
			
			if (col.getTipo() != null && !"".equals(col.getTipo())) {
				campos += ", `Tipo`";
				valores += ", '" + col.getTipo() + "'";
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
			if (col.getTipo() != null && !col.getTipo().equals(""))
				campos.add("Tipo = '" + col.getTipo() + "'");
			if (col.getFiltro() != null) campos.add("Filtros = '" + col.getFiltro().toString(col.getTipo()) + "'");
			if (col.getCalculo() != null && !col.getCalculo().equals(""))
				campos.add("Calculo = '" + col.getCalculo().getCalculo() + "'");
			if (col.getOrdenar() > -1) campos.add("Ordenar = " + col.getOrdenar());
			if (col.getPosicion() > -1) campos.add("Posicion = " + col.getPosicion());
			
			md.update(tabla, Utilidades.joinString(campos, ", "), condicion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public EstadoOperacion eliminarColumna(ITipoInforme informe, ColumnaInforme col) {
		try {
			String condicion = "TRUE";
			if (informe != null && informe.getId() > -1)
				condicion += " AND TipoInforme = " + informe.getId();
			if (col != null) {
				String atributo = col.getAtributo();
				if (atributo != null && !"".equals(atributo))
					condicion += " AND Atributo = '" + atributo + "'"; 
			}
			// No eliminar todo
			if (condicion.equals("TRUE")) 
				throw new Exception("No está permitido eliminar todo.");
			
			ManejoDatos md = new ManejoDatos();
			md.delete("Columnas", condicion);
			return new EstadoOperacion(CodigoEstado.DELETE_OK,
					"La columna se eliminó con éxito.");
		} catch (Exception e) {
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR,
					"No se pudo eliminar la columna.");
		}
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
				if (!reg.get("Calculo").equals("")) {
					c.setCalculo(Calculo.getEnum(reg.get("Calculo")));
				}
				if (reg.containsKey("Tipo")) c.setTipo(TipoColumna.valueOf(reg.get("Tipo")));

				if (!reg.get("Filtros").equals("")) {
					c.setFiltro(new FiltroColumna(reg.get("Filtros"), c.getTipo()));
				}
				
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
				c.setFiltro(new FiltroColumna(reg.get("Filtros")));
			}
			if (!reg.get("Calculo").equals("")) {
				switch (reg.get("Calculo")) {
				case "SUM":
					c.setCalculo(Calculo.SUM);
					break;
				case "COUNT":
					c.setCalculo(Calculo.COUNT);
					break;
				case "MIN":
					c.setCalculo(Calculo.MIN);
					break;
				case "MAX":
					c.setCalculo(Calculo.MAX);
					break;
				default:
					break;
				}
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
			if (columna.getFiltro() != null) {
				condicion += " AND Filtros = '" + columna.getFiltro().toString() + "'";
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