package modelo.docente;

import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
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
}