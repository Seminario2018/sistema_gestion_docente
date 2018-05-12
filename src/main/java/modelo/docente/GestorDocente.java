package modelo.docente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.Cargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.IArea;
import modelo.persona.IPersona;
import persistencia.ManejoDatos;

public class GestorDocente {

	public EstadoOperacion nuevoDocente(IPersona persona, IDocente docente) {
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


			if (md.isEstado()) {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,"El docente se creó correctamente");
            } else {

					return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,"El docente no se creo");

				}

		} catch (Exception e) {
					return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,"Error al crear al docente");

		}
	}

	public EstadoOperacion modificarDocente(IPersona persona, IDocente docente) {
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

	public List<IDocente> listarDocente(IPersona persona, IDocente docente) {

		if (docente != null) {
			// TODO Filtrar por los campos que ingresan
		}
		// TODO select BD
		return null;
	}

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

	public IDocente getIDocente() {
		return (IDocente) new Docente();
	}

	public ICargoDocente getCargoDocente() {
		return (ICargoDocente) new CargoDocente();
	}

	public EstadoOperacion agregarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
	    // TODO Revisar
	    try {
    	    ManejoDatos md = new ManejoDatos();
    	    String campos = "Codigo, Legajo, Area, Cargo, Tipo_Cargo, Estado_Cargo, Disposicion, Resolucion, CostoActual";
    	    if (cargoDocente.getId() == -1) {
          	  cargoDocente.setId(this.getCodigoMax() + 1);
            }
    	    String valores = String.format("'%d', '%d', '%s', '%d', '%s', '%d', '%s', '%s', '%d'",
    	            cargoDocente.getId(),
    	            docente.getLegajo(),
    	            cargoDocente.getArea().getCodigo(),
    	            cargoDocente.getCargo().getCodigo(),
    	            cargoDocente.getTipoCargo().getDescripcion(),
    	            cargoDocente.getEstado().getId(),
    	            cargoDocente.getDisposicion(),
    	            cargoDocente.getResolucion(),
    	            0
            );
    	    md.insertar("planta", campos, valores);
    	    if (md.isEstado()) {
    	        return new EstadoOperacion(
    	                EstadoOperacion.CodigoEstado.INSERT_OK,
    	                "El CargoDocente se agregó correctamente");
    	    } else {
    	        return new EstadoOperacion(
    	                EstadoOperacion.CodigoEstado.INSERT_ERROR,
    	                "El CargoDocente no se agregó");
    	    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new EstadoOperacion(
	                EstadoOperacion.CodigoEstado.INSERT_ERROR,
	                "Error al agregar el CargoDocente");
	    }
	}

	public EstadoOperacion modificarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
	    // TODO Revisar
	    try {
	        StringBuilder campos = new StringBuilder();	        
	        campos.append(String.format(
	                "Legajo='%d', Area='%s', Cargo='%d', Tipo_Cargo='%s', Estado_Cargo='%s', Disposicion='%s', Resolucion='%s', CostoActual='%d'",
	                docente.getLegajo(),
	                cargoDocente.getArea().getCodigo(),
                    cargoDocente.getCargo().getCodigo(),
                    cargoDocente.getTipoCargo().getDescripcion(),
                    cargoDocente.getEstado().getDescripcion(),
                    cargoDocente.getDisposicion(),
                    cargoDocente.getResolucion(),
                    0
            ));


	        String condicion = String.format("Codigo='%d'", cargoDocente.getId());

	        ManejoDatos md = new ManejoDatos();
	        md.update("planta", campos.toString(), condicion);
	        if (md.isEstado()) {
                return new EstadoOperacion(
                        EstadoOperacion.CodigoEstado.UPDATE_OK,
                        "El CargoDocente se modificó correctamente");
            } else {
                return new EstadoOperacion(
                        EstadoOperacion.CodigoEstado.UPDATE_ERROR,
                        "El CargoDocente no se modificó");
            }
	    } catch (Exception e) {
	        return new EstadoOperacion(
                    EstadoOperacion.CodigoEstado.UPDATE_ERROR,
                    "Error al modificar el CargoDocente");
	    }
	}

	public EstadoOperacion quitarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
	 // TODO Revisar
        try {
            ManejoDatos md = new ManejoDatos();
            String condicion = String.format("Codigo = '%d'", cargoDocente.getId());
            md.delete("planta", condicion);

            if (md.isEstado()) {
                return new EstadoOperacion(
                        EstadoOperacion.CodigoEstado.DELETE_OK,
                        "El CargoDocente se quitó correctamente");
            } else {
                return new EstadoOperacion(
                        EstadoOperacion.CodigoEstado.DELETE_ERROR,
                        "El CargoDocente no se quitó");
            }
        } catch (Exception e) {
            return new EstadoOperacion(
                    EstadoOperacion.CodigoEstado.DELETE_ERROR,
                    "Error al quitar el CargoDocente");
        }
    }
	
	public ArrayList<ICargo> listarCargo(IDocente docente, ICargoDocente cargo){
		ArrayList<ICargoDocente> cargos = new ArrayList<ICargoDocente>();
		String condicion = "TRUE";
		condicion += this.armarCondicionCargo(cargo, docente);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "planta";
			ArrayList<Hashtable<String,String>> res = md.select(tabla, "*", condicion);
			/*
			 *`Area`, `Cargo`, `Tipo_Cargo`, `Estado_Cargo`, `CostoActual`
			 * 
			 *IArea area, ICargo cargo, TipoCargo tipoCargo,
	        String disposicion, LocalDate dispDesde, LocalDate dispHasta,
	        float ultimoCosto, LocalDate fechaUltCost, String resolucion,
	        LocalDate resDesde, LocalDate resHasta, EstadoCargo estado
			 */
			for (Hashtable<String, String> reg : res) {
				CargoDocente cargoN = new CargoDocente();
				cargoN.setId(Integer.parseInt(reg.get("Codigo")));
				cargoN.setArea(new Area(reg.get("Area"), null, null, null, null, null, null, null));
				cargoN.setCargo(new Cargo(Integer.parseInt(reg.get("Cargo")), null, -1));
				cargoN.setTipoCargo(new TipoCargo(0, reg.get("Tipo_Cargo")));
				cargoN.setDisposicion(reg.get("Disposicion"));
				cargoN.setResolucion(reg.get("Resolucion"));
				cargoN.setUltimoCosto(Float.parseFloat(reg.get("CostoActual")));
				cargos.add(cargoN);
				
			}
			
		}catch (Exception e) {
			cargos = new ArrayList<ICargo>();
		}
		
		
		return cargos;
	}
	
	
	private String armarCondicionCargo(ICargoDocente cargo,IDocente docente) {
		String condicion = "";
		if (cargo.getId() != -1) {
			condicion += "`Codigo` = " + cargo.getId();
		}
		if (docente != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Legajo` = " + docente.getLegajo();
		}
		if (cargo.getArea() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Area` = " + cargo.getArea().getCodigo();
		}
		if (cargo.getCargo() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Cargo` = " + cargo.getCargo().getCodigo();
		}
		if (cargo.getTipoCargo() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Tipo_Cargo` = '" + cargo.getTipoCargo().getDescripcion() + "'"; 
		}
		if (cargo.getEstado() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Estado_Cargo` = '" + cargo.getEstado().getDescripcion() + "'";
		}
		if (cargo.getDisposicion() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Disposicion` = '" + cargo.getDisposicion() + "'";
		}
		if (cargo.getResolucion() != null) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Resolucion` = '" + cargo.getResolucion() + "'";
		}
		if (cargo.getUltimoCosto() > 0) {
			if (!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`CostoActual` = " + cargo.getUltimoCosto(); 
		}
		
		return condicion;
	}

	
	
	private int getCodigoMax() {
    	int cod = 1;
    	try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select("planta", "MAX(Codigo");
    		for (Hashtable<String, String> reg : res) {
				cod = Integer.parseInt(reg.get("MAX(codigo"));
			}
    	}catch(Exception e){
    		cod = 1;
    	}
    	return cod;
    }
}