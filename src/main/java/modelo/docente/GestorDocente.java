package modelo.docente;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorDocente {

    public EstadoOperacion nuevoDocente(IDocente doc) {
        try {
        	IDocenteg docente = (IDocenteg) doc;

            ManejoDatos md = new ManejoDatos();
            docente.getEstado2().guardar();

            String table = "Docentes";
            String campos = "`Legajo`, `TipoDocumento`, `NroDocumento`,  `Estado`";
            String valores = docente.getLegajo() + ", " + docente.getPersona2().getTipoDocumento().getId() + ", "
                    + "'" + docente.getPersona2().getNroDocumento() + "', " + docente.getEstado2().getId();

            if (docente.getObservaciones() != null && !docente.getObservaciones().equals("")) {
                campos += ", Observaciones";
                valores += ", '" + docente.getObservaciones() + "'";
            }

            if (docente.getCategoriaInvestigacion2() != null) {
                docente.getCategoriaInvestigacion2().guardar();
                campos += ", CategoriaInvestigacion";
                valores += ", " + docente.getCategoriaInvestigacion2().getId();
            }

            md.insertar(table, campos, valores);

            if (docente.getIncentivos2() != null) {
                for (IIncentivo incentivo : docente.getIncentivos2()) {
                    this.agregarIncentivo((IDocente) docente, incentivo);
                }
            }

            if (docente.getCargosDocentes2() != null) {
                for (ICargoDocente cargoDocente : docente.getCargosDocentes2()) {
                    this.agregarCargoDocente(docente, cargoDocente);
                }
            }

            if (md.isEstado()) {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El docente se creó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El docente no se creo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "Error al crear al docente");
        }
    }

    public EstadoOperacion modificarDocente(IDocente docente) {
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "Docentes";
            String campos = "";

            if (docente.getPersona() != null) {
            	if (docente.getPersona().getTipoDocumento() != null) {
            		campos += "`TipoDocumento` = " + docente.getPersona().getTipoDocumento().getId();
            	}
            	if (docente.getPersona().getNroDocumento() != -1) {
            		if (!campos.equals("")) {
            			campos += ", ";
            		}
            		campos += "`NroDocumento`= '" + docente.getPersona().getNroDocumento() + "'";
            	}
            }
            if (docente.getEstado() != null) {
            	if (!campos.equals("")) {
        			campos += ", ";
        		}
        		campos += "Estado = " + docente.getEstado().getId();
            }


            if (docente.getObservaciones() != null && !docente.getObservaciones().equals("")) {
            	if (!campos.equals("")) {
        			campos += ", ";
        		}
                campos += "`Observaciones`= '" + docente.getObservaciones() + "'";
            }

            if (docente.getCategoriaInvestigacion() != null) {
            	if (!campos.equals("")) {
        			campos += ", ";
        		}
                campos += "`CategoriaInvestigacion` = " + docente.getCategoriaInvestigacion().getId();
            }

            String condicion = "`Legajo` = " + docente.getLegajo();

            md.update(tabla, campos, condicion);

            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El Docente se modificó correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Docente");
        }
    }

    public EstadoOperacion eliminarDocente(IDocente docente) {
        try {
            ManejoDatos md = new ManejoDatos();
            md.delete("Docentes", "Legajo = " + docente.getLegajo());

            if (md.isEstado()) {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,"El docente se eliminó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se elimino el docente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se elimino el docente");
        }

    }

    public List<IDocente> listarDocentes(IDocente docente) {
        String tabla = "Docentes";
        String condicion = "TRUE";

        if (docente != null) {
        	IDocenteg doc = (IDocenteg) docente;
            condicion = this.armarCondicionDocente(doc);
        }

        List<IDocente> docentes = new ArrayList<IDocente>();
        try {
            ManejoDatos md = new ManejoDatos();
            List<Hashtable<String, String>> res = md.select(tabla, "Legajo, Observaciones", condicion);
            for (Hashtable<String, String> reg : res) {
                Docente doc = new Docente();

                doc.setLegajo(Integer.parseInt(reg.get("Legajo")));

                if (!reg.get("Observaciones").equals("")) {
                    doc.setObservaciones(reg.get("Observaciones"));
                }

                docentes.add(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return docentes;
    }

    private String armarCondicionDocente(IDocenteg docente) {
        String condicion = "";

        if (docente.getLegajo() > 0) {
            condicion += "`Legajo` = " + docente.getLegajo();
        }
        if (docente.getPersona2() != null) {
            if (docente.getPersona2().getTipoDocumento() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`TipoDocumento` = " + docente.getPersona2().getTipoDocumento().getId();
            }
            if (docente.getPersona2().getNroDocumento() > 0) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`NroDocumento` = '" + docente.getPersona2().getNroDocumento() + "'";
            }
        }
        if (docente.getObservaciones() != null) {
            if (!condicion.equals("")) {
                condicion += " AND ";
            }
            condicion += "`Observaciones` = '" + docente.getObservaciones() + "'";
        }
        if (docente.getCategoriaInvestigacion2() != null) {
            if (!condicion.equals("")) {
                condicion += " AND ";
            }
            condicion += "`CategoriaInvestigacion` = " + docente.getCategoriaInvestigacion2().getId();
        }
        if (docente.getEstado2() != null) {
            if (!condicion.equals("")) {
                condicion += " AND ";
            }
            condicion += "`Estado` = " + docente.getEstado2().getId();
        }

        return condicion;
    }

    public EstadoOperacion agregarIncentivo(IDocente docente, IIncentivo incentivo) {
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "Incentivos";
            String campos = "`Fecha`, `Legajo`";
            String valores = "'" + incentivo.getFecha().toString() + "', " + docente.getLegajo();
            md.insertar(tabla, campos, valores);
            if (md.isEstado()) {
                docente.setIncentivos(new ArrayList<IIncentivo>());
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El incentivo se creó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El incentivo no se creo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El incentivo no se creo");
        }
    }

    public EstadoOperacion quitarIncentivo(IDocente docente, IIncentivo incentivo) {
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "Incentivos";
            String condicion =
                "Legajo = " + docente.getLegajo() + " AND " +
                "Fecha = '" + incentivo.getFecha() + "'";

            md.delete(tabla, condicion);
            if (md.isEstado()) {
                docente.setIncentivos(new ArrayList<IIncentivo>());
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El incentivo se quitó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "El incentivo no se quitó");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "El incentivo no se quitó");
        }
    }

    public List<IIncentivo> listarIncentivos(IDocente docente, IIncentivo incentivo) {
        List<IIncentivo> incentivos = new ArrayList<IIncentivo>();
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "Incentivos";
            String condicion = this.armarCondicionIncentivo(docente, incentivo);
            List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
            for (Hashtable<String, String> reg : res) {
                incentivos.add(new Incentivo(Year.parse(reg.get("Fecha"))));
            }
            return incentivos;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<IIncentivo>();
        }
    }

    private String armarCondicionIncentivo(IDocente docente, IIncentivo incentivo) {
        List<String> condiciones = new ArrayList<String>();

        // Docente:
        if ((docente != null) && (docente.getLegajo() != 0)) {
            condiciones.add("Legajo = " + docente.getLegajo());
        }

        // Incentivo:
        if ((incentivo != null) && (incentivo.getFecha() != null)) {
            condiciones.add("Fecha = '" + incentivo.getFecha().toString() + "'");
        }

        if (condiciones.isEmpty()) {
            return "TRUE";
        } else {
            return String.join(" AND ", condiciones);
        }

    }

    public EstadoOperacion agregarCargoDocente(IDocenteg docente, ICargoDocente cd) {
        try {
        	ICargoDocenteg cargoDocente = (ICargoDocenteg) cd;
            cargoDocente.getEstado2().guardar();
            cargoDocente.getTipoCargo2().guardar();

            if (cargoDocente.getId() == -1) {
                cargoDocente.setId(this.getCodigoMax() + 1);
            }

            ManejoDatos md = new ManejoDatos();
            String campos = "`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `EstadoCargo`";

            //`UltimoCosto`, `FechaUltimoCosto`
            String valores = cargoDocente.getId()
                + ", " + docente.getLegajo()
                + ", '" + cargoDocente.getArea2().getCodigo()
                + "', " + cargoDocente.getCargo2().getCodigo()
                + ", " + cargoDocente.getTipoCargo2().getId()
                + ", " + cargoDocente.getEstado2().getId();

            if (cargoDocente.getDisposicion() != null && !cargoDocente.getDisposicion().equals("")) {
                campos += ", Disposicion";
                valores += ", '" + cargoDocente.getDisposicion() + "'";
            }
            if (cargoDocente.getDispDesde() != null) {
                campos += ", DispDesde";
                valores += ", '" + Date.valueOf(cargoDocente.getDispDesde()) + "'";
            }
            if (cargoDocente.getDispHasta() != null) {
                campos += ", DispHasta";
                valores += ", '" + Date.valueOf(cargoDocente.getDispHasta()) + "'";
            }
            if (cargoDocente.getResolucion() != null && !cargoDocente.getResolucion().equals("")) {
                campos += ", Resolucion";
                valores += ", '" + cargoDocente.getResolucion() + "'";
            }
            if (cargoDocente.getResDesde() != null) {
                campos += ", ResDesde";
                valores += ", '" + Date.valueOf(cargoDocente.getResDesde()) + "'";
            }
            if (cargoDocente.getResHasta() != null) {
                campos += ", ResHasta";
                valores += ", '" + Date.valueOf(cargoDocente.getResHasta()) + "'";
            }
            md.insertar("CargosDocentes", campos, valores);

            this.guardarCostos(cargoDocente);

            if (md.isEstado()) {
                ((IDocente) docente).setCargosDocentes(new ArrayList<ICargoDocente>());
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El Cargo Docente se agregó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El Cargo Docente no se agregó");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "Error al agregar el Cargo Docente");
        }
    }



	private void guardarCostos(ICargoDocenteg cargoDocente) {
		for(Costo c : cargoDocente.getCostos()) {
			c.guardar(cargoDocente);
		}

	}

	public EstadoOperacion modificarCargoDocente(IDocente docente, ICargoDocente cd) {
        try {

        	ICargoDocenteg cargoDocente = (ICargoDocenteg) cd;

            String campos = "";


            if (docente != null && docente.getLegajo() != -1) {
            	campos += "`Legajo` = " + docente.getLegajo();
            }
            if (cd.getArea() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
            	campos += "`Area` = '" + cargoDocente.getArea2().getCodigo() + "'";
            }
            if (cd.getCargo() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
            	campos += "`Cargo` = " + cargoDocente.getCargo2().getCodigo();
            }
            if (cd.getTipoCargo() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
            	campos += "`TipoCargo` = " + cargoDocente.getTipoCargo2().getId();
            }
            if (cd.getEstado() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
            	campos += "`EstadoCargo` = " + cargoDocente.getEstado2().getId();
            }
            if (cargoDocente.getDisposicion() != null && !cargoDocente.getDisposicion().equals("")) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "Disposicion = '" + cargoDocente.getDisposicion() + "'";
            }
            if (cargoDocente.getDispDesde() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "DispDesde = '" + Date.valueOf(cargoDocente.getDispDesde()) + "'";
            }
            if (cargoDocente.getDispHasta() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "DispHasta = '" + Date.valueOf(cargoDocente.getDispHasta()) + "'";
            }
            if (cargoDocente.getResolucion() != null && !cargoDocente.getResolucion().equals("")) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "Resolucion = '" + cargoDocente.getResolucion() + "'";
            }
            if (cargoDocente.getResDesde() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "ResDesde = '" + Date.valueOf(cargoDocente.getResDesde()) + "'";
            }
            if (cargoDocente.getResHasta() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "ResHasta = '" + Date.valueOf(cargoDocente.getResHasta()) + "'";
            }

            String condicion = String.format("Codigo = %d", cargoDocente.getId());

            ManejoDatos md = new ManejoDatos();
            md.update("CargosDocentes", campos, condicion);

            this.guardarCostos(cargoDocente);

            if (md.isEstado()) {
            	if (docente != null) {
                    ((IDocente) docente).setCargosDocentes(new ArrayList<ICargoDocente>());
                }
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El CargoDocente se modificó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "El CargoDocente no se modificó");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "Error al modificar el CargoDocente");
        }
    }

    public EstadoOperacion quitarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        try {
            ManejoDatos md = new ManejoDatos();
            String condicion = "Codigo = " + cargoDocente.getId();
            md.delete("CargosDocentes", condicion);

            if (md.isEstado()) {
                ((IDocente) docente).setCargosDocentes(new ArrayList<ICargoDocente>());
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El CargoDocente se quitó correctamente");
            } else {
                return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "El CargoDocente no se quitó");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "Error al quitar el CargoDocente");
        }
    }

    public List<ICargoDocente> listarCargo(IDocente docente, ICargoDocente cargo) {
        List<ICargoDocente> cargos = new ArrayList<ICargoDocente>();
        String condicion = this.armarCondicionCargo(cargo, docente);
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "CargosDocentes";
            String campos = "Codigo, Disposicion, DispDesde, DispHasta, "
                + "Resolucion, ResDesde, ResHasta, "
                + "UltimoCosto, FechaUltimoCosto";
            List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
            for (Hashtable<String, String> reg : res) {
                CargoDocente c = new CargoDocente();

                c.setId(Integer.parseInt(reg.get("Codigo")));

                if (!reg.get("Disposicion").equals("")) {

                    c.setDisposicion(reg.get("Disposicion"));
                }

                if (!reg.get("DispDesde").equals("")) {
                    c.setDispDesde(Date.valueOf(reg.get("DispDesde")).toLocalDate());
                }
                if (!reg.get("DispHasta").equals("")) {
                    c.setDispHasta(Date.valueOf(reg.get("DispHasta")).toLocalDate());
                }
                if (!reg.get("Resolucion").equals("")) {
                    c.setResolucion(reg.get("Resolucion"));
                }
                if (!reg.get("ResDesde").equals("")) {
                    c.setResDesde(Date.valueOf(reg.get("ResDesde")).toLocalDate());
                }
                if (!reg.get("ResHasta").equals("")) {
                    c.setResHasta(Date.valueOf(reg.get("ResHasta")).toLocalDate());
                }
                if (!reg.get("UltimoCosto").equals("")) {
                    c.setUltimoCosto(Float.parseFloat(reg.get("UltimoCosto")));
                }
                if (!reg.get("FechaUltimoCosto").equals("")) {
                    c.setFechaUltCost(Date.valueOf(reg.get("FechaUltimoCosto")).toLocalDate());
                }

                cargos.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
            cargos = new ArrayList<ICargoDocente>();
        }
        return cargos;
    }

    private String armarCondicionCargo(ICargoDocente car, IDocente docente) {
        String condicion = "TRUE";
        ICargoDocenteg cargo = (ICargoDocenteg) car;


        if (cargo != null) {
            if (cargo.getId() != -1) {
                condicion += " AND `Codigo` = " + cargo.getId();
            }
            if (cargo.getArea2() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`Area` = '" + cargo.getArea2().getCodigo() + "'";
            }
            if (cargo.getCargo2() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`Cargo` = " + cargo.getCargo2().getCodigo();
            }
            if (cargo.getTipoCargo2() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`TipoCargo` = " + cargo.getTipoCargo2().getId();
            }
            if (cargo.getEstado2() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += "`EstadoCargo` = " + cargo.getEstado2().getId();
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
        }
        if (docente != null) {
            if (!condicion.equals("")) {
                condicion += " AND ";
            }
            condicion += "`Legajo` = " + docente.getLegajo();
        }
        return condicion;
    }

    private int getCodigoMax() {
        int cod = 0;
        try {
            ManejoDatos md = new ManejoDatos();
            List<Hashtable<String, String>> res = md.select("CargosDocentes", "MAX(Codigo)");
            for (Hashtable<String, String> reg : res) {
            	if (!reg.get("MAX(Codigo)").equals("")) {
                    cod = Integer.parseInt(reg.get("MAX(Codigo)"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cod = 0;
        }
        return cod;
    }

    public static boolean existeDocente(IDocente docente) {
        String tabla = "Docentes";
        if (docente == null || docente.getLegajo() == -1) {
            return false;
        }
        String condicion = "Legajo = " + docente.getLegajo();
        try {
            ManejoDatos md = new ManejoDatos();
            List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
            return !(res.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<ProyectoDocente> listarProyectosDocente(IDocente docente){
    	List<ProyectoDocente> proyectos;
		try {
			proyectos = new ArrayList<ProyectoDocente>();
			ManejoDatos md = new ManejoDatos();
			String tabla = "viewproyectosdocentes";
			String condicion = "TRUE";
			if (docente != null && docente.getLegajo() != -1) {
				condicion = "Legajo = " + docente.getLegajo();
			}

			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				ProyectoDocente pd = new ProyectoDocente();
				pd.setNombreProyecto(reg.get("Nombre"));
				pd.setArea(reg.get("Area"));
				pd.setCargo(reg.get("Cargo"));
				pd.setCodigoCargo(Integer.parseInt(reg.get("CodigoCargo")));
				pd.setHorasDedicadas(Integer.parseInt(reg.get("HorasSemanales")));
				proyectos.add(pd);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			proyectos = new ArrayList<ProyectoDocente>();
		}

    	return proyectos;

    }


    // Plantillas vacías ======================================================
    /**
     * @return Una plantilla ICargoDocente vacía
     */
    public ICargoDocente getICargoDocente() {
        return new CargoDocente();
    }

    /**
     * @return Una plantilla IDocente vacía
     */
    public IDocente getIDocente() {
        return new Docente();
    }


    /**
     * @return Una plantilla IIncentivo vacía
     */
    public IIncentivo getIIncentivo() {
        return new Incentivo();
    }
}
