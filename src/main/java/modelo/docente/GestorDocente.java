package modelo.docente;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoDocumento;
import modelo.cargo.Cargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.IArea;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.persona.Persona;
import persistencia.ManejoDatos;

public class GestorDocente {

	public EstadoOperacion nuevoDocente(IDocente docente) {
		try {
			ManejoDatos md=new ManejoDatos();
			String table="docentes";
			String campos="`Legajo`,`TipoDocumento`,`NroDocumento`,`Observaciones`,`CategoriaInvestigacion`,`Estado`";
			String valores="'"+docente.getLegajo()
							+"','"+docente.getPersona().getTipoDocumento()
							+"','"+docente.getPersona().getNroDocumento()
							+"','"+docente.getObservaciones()
							+"','"+docente.getCategoriaInvestigacion()
							+"','"+docente.getEstado()+"'";
			md.insertar(table, campos, valores);
			
			for (IIncentivo incentivo : docente.getIncentivos()) {
				this.agregarIncentivo(docente, incentivo);
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

	public EstadoOperacion modificarDocente(IDocente docente) {
		  try {
	        	ManejoDatos md = new ManejoDatos();
	        	String tabla = "docentes";
	        	String campos = "`Legajo` = '"+ docente.getLegajo()
	        			+"', `TipoDocumento` = '"+ docente.getPersona().getTipoDocumento()
	        			+"', `NroDocumento`= '"+ docente.getPersona().getNroDocumento()
	        			+"', `Observaciones`= '"+ docente.getObservaciones()
	        			+"', `CategoriaInvestigacion` = '"+ docente.getCategoriaInvestigacion()
	        			+"'";
	        	String condicion = "`Legajo` = '" + docente.getLegajo() + "'";

	        	md.update(tabla, campos, condicion);

	            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
	                    "El Docente se modificó correctamente");
	        }catch(Exception e) {
	        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
	                    "No se pudo modificar el Docente");
	        }
	}

	public EstadoOperacion eliminarDocente(IDocente docente) {
		try {
			ManejoDatos md = new ManejoDatos();
			md.delete("docentes", "Legajo = " + docente.getLegajo());
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
						"El docente se eliminó correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
						"No se elimino el docente");
			}
			
		}catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
					"No se elimino el docente");
		}
		
	}

	public List<IDocente> listarDocente(IDocente docente) {
		String tabla = "docentes";
		String condicion = "TRUE";
		if (docente != null) {
			condicion =  this.armarCondicionDocente(docente);
		}
		ArrayList<IDocente> docentes = new ArrayList<IDocente>();
		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				//`Observaciones`, `CategoriaInvestigacion`, `Estado`
				GestorPersona gp = new GestorPersona();
				Persona p = new Persona();
				TipoDocumento td = new TipoDocumento();
				td.setId(Integer.parseInt(reg.get("TipoDocumento")));
				p.setTipoDocumento(TipoDocumento.getTipo(td));
				p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
				CategoriaInvestigacion cat = new CategoriaInvestigacion();
				cat.setId(Integer.parseInt(reg.get("CategoriaInvestigacion")));
				EstadoDocente estado = new EstadoDocente();
				estado.setId(Integer.parseInt(reg.get("Estado")));
				Docente doc = new Docente(gp.listarPersonas(p).get(0), 
						Integer.parseInt(reg.get("Legajo")), reg.get("Observacionse"), CategoriaInvestigacion.getCategoria(cat),
						EstadoDocente.getEstado(estado), null, null);
				this.cargarCargos(doc);
				this.cargarIncentivos(doc);
				docentes.add(doc);
			}
			
		}catch (Exception e) {
			
		}
		
		return docentes;
	}

    private void cargarIncentivos(Docente doc) {
		// TODO Auto-generated method stub
		
	}

	private void cargarCargos(Docente doc) {
		// TODO Auto-generated method stub
		
	}

	private String armarCondicionDocente(IDocente docente) {
		String condicion = "";
		if (docente.getLegajo() > 0) {
			condicion += "`Legajo` = " + docente.getLegajo();
		}
		if (docente.getPersona() != null) {
			if (docente.getPersona().getTipoDocumento() != null) {
				if(!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += "`TipoDocumento` = " + docente.getPersona().getTipoDocumento().getId(); 
			}
			if (docente.getPersona().getNroDocumento() > 0) {
				if(!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += "`NroDocumento` = '" +  docente.getPersona().getNroDocumento() + "'";
			}
		}
		if (docente.getObservaciones() != null) {
			if(!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Observaciones` = '" + docente.getObservaciones() + "'";
		}
		if (docente.getCategoriaInvestigacion() != null) {
			if(!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`CategoriaInvestigacion` = " + docente.getCategoriaInvestigacion().getId();
		}
		if (docente.getEstado() != null) {
			if(!condicion.equals("")) {
				condicion += " AND ";
			}
			condicion += "`Estado` = " + docente.getEstado().getId();
		}
		
		return condicion;
	}

	public EstadoOperacion agregarIncentivo(IDocente docente, IIncentivo incentivo) {
    	ManejoDatos md=new ManejoDatos();
    	
    	String tabla = "incentivos";
    	String campos = "`Fecha`, `Legajo`";
		String valores = "'" + incentivo.getFecha().toString() + "', " + docente.getLegajo(); 
    	md.insertar(tabla, campos, valores);
    	if (md.isEstado()) {
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,"El incentivo se creó correctamente");
        } else {

			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,"El incentivo no se creo");

		}
    }

    public EstadoOperacion quitarIncentivo(IDocente docente, IIncentivo planta) {
    	ManejoDatos md = new ManejoDatos();
    	String tabla = "incentivos";
    	String condicion = " Legajo = " + docente.getLegajo() + ", Fecha = '" + planta.getFecha() + "'";

    	md.delete(tabla, condicion);

        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El incentivo se quitó correctamente");
    }

	public EstadoOperacion agregarPlanta(IDocente docente, ICargo planta) {
    	ManejoDatos md = new ManejoDatos();
    	GestorCargo cd = new GestorCargo();
    	ArrayList<ICargo> cargos = (ArrayList<ICargo>) cd.listarCargo(planta);

    	if (cargos.isEmpty()) {
    		cd.nuevoCargo(planta);
    	}

    	String tabla = "";
    	String campos = "docente, cargo";
    	String valores = "'" + docente.getLegajo() + "', '" + planta.getCodigo() + "'";

    	md.insertar(tabla, campos, valores);




		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
				"El cargo se agregó correctamente");
	}

	public EstadoOperacion quitarPlanta(IDocente docente, ICargo planta) {
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
	
	public ArrayList<ICargoDocente> listarCargo(IDocente docente, ICargoDocente cargo){
		ArrayList<ICargoDocente> cargos = new ArrayList<ICargoDocente>();
		String condicion = "TRUE";
		condicion += this.armarCondicionCargo(cargo, docente);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "planta";
			ArrayList<Hashtable<String,String>> res = md.select(tabla, "*", condicion);
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
			cargos = new ArrayList<ICargoDocente>();
		}
		
		
		return cargos;
	}
	
	
	private String armarCondicionCargo(ICargoDocente cargo,IDocente docente) {
		String condicion = "";
		if (cargo != null) {
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