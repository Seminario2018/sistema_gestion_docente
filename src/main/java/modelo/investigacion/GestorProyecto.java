
package modelo.investigacion;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorProyecto {

    public GestorProyecto() {}

    public EstadoOperacion nuevoProyecto(IProyecto proyecto, IPrograma programa) {
        try {

        	if (proyecto.getId() == -1) {
        		proyecto.setId(GestorProyecto.getMaxID("Proyectos", "id") + 1);
        	}

        	proyecto.getEstado().guardar();

            ManejoDatos e = new ManejoDatos();
            String table = "Proyectos";
            String campos = "`id`, `Nombre`, `FechaPresentacion`, `Director`,  `Estado`";
            String valores = proyecto.getId() + ", '" + proyecto.getNombre() + "', "
            		+ "'" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
            		+ "" + proyecto.getDirector().getLegajo() + ", " + proyecto.getEstado().getId();

            if (proyecto.getResumen() != null && !proyecto.getResumen().equals("")) {
            	campos += ", `Resumen`";
            	valores += ", '" + proyecto.getResumen() + "'";
            }
            if (proyecto.getFechaAprobacion() != null) {
            	campos += ", `FechaAprobacion`";
            	valores += ", '" + Date.valueOf(proyecto.getFechaAprobacion()) + "'";
            }
            if (proyecto.getDescripcion() != null && !proyecto.getDescripcion().equals("")) {
            	campos += ", `Descripcion`";
            	valores += ", '" + proyecto.getDescripcion() + "'";
            }
            if (proyecto.getCodirector() != null) {
            	campos += ", `Codirector`";
            	valores += ", " + proyecto.getCodirector().getLegajo();
            }
            if (proyecto.getFechaInicio() != null) {
            	campos += ", `FechaInicio`";
            	valores += ", '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
            }
            if (proyecto.getFechaFin() != null) {
            	campos += ", `Fecha_Fin`";
            	valores += ", '" + Date.valueOf(proyecto.getFechaFin()) + "'";
            }
            if (programa != null) {
            	campos += ", `Programa`";
            	valores += ", " + programa.getId();
            }


            for (IIntegrante i : proyecto.getIntegrantes()) {
				this.AgregarIntegrante(proyecto, i);
			}
            for (ISubsidio s: proyecto.getSubsidios()) {
				this.agregarSubsidio(proyecto, s);
			}
            for (IProrroga p : proyecto.getProrrogas()) {
				this.agregarProrroga(proyecto, p);
			}

            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        }
    }



	public EstadoOperacion modificarProyecto(IProyecto proyecto, IPrograma programa) {
        try {

        	proyecto.getEstado().guardar();

            ManejoDatos e = new ManejoDatos();
            String tabla = "Proyectos";
            String campos = "`Nombre` = '" + proyecto.getNombre() + "', `Director`= " + proyecto.getDirector().getLegajo() + ", "
            		+ "`Fecha_Presentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
            		+ "`Estado` = " + proyecto.getEstado().getId();


            if (proyecto.getResumen() != null && !proyecto.getResumen().equals("")) {
            	campos += ", `Resumen`= '" + proyecto.getResumen() + "'";
            }
            if (proyecto.getFechaAprobacion() != null) {
            	campos += ", `FechaAprobacion` = '" + Date.valueOf(proyecto.getFechaAprobacion()) + "'";
            }
            if (proyecto.getDescripcion() != null && !proyecto.getDescripcion().equals("")) {
            	campos += ", `Descripcion` = '" + proyecto.getDescripcion() + "'";
            }
            if (proyecto.getCodirector() != null) {
            	campos += ", `Codirector` = " + proyecto.getCodirector().getLegajo();
            }
            if (proyecto.getFechaInicio() != null) {
            	campos += ", `FechaInicio` = '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
            }
            if (proyecto.getFechaFin() != null) {
            	campos += ", `Fecha_Fin` = '" + Date.valueOf(proyecto.getFechaFin()) + "'";
            }
            if (programa != null) {
            	campos += ", `Programa` = " + programa.getId();
            }

            String condicion = "`Id` = " + proyecto.getId() + "'";
            e.update(tabla, campos, condicion);
            return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se modificó correctamente");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        }
    }

    public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
        try {
            ManejoDatos e = new ManejoDatos();
            e.delete("`Integrantes`", "Proyecto = " + proyecto.getId());
            e.delete("`Prorrogas", "Proyecto = " + proyecto.getId());
            e.delete("`Subcidios`", "Proyecto = " + proyecto.getId());
            e.delete("`Proyectos`", "id = " + proyecto.getId());
            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El proyecto se eliminó correctamente");
        } catch (Exception var3) {
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Proyecto");
        }
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto, IPrograma programa) {

        List<IProyecto> proyectos = new ArrayList<IProyecto>();
        String condicion = this.armarCondicion(proyecto, programa);
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "Proyectos";
            ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
            for (Hashtable<String, String> reg : res) {
                Proyecto p = new Proyecto();
                p.setId(Integer.parseInt(reg.get("Id")));
                p.setNombre(reg.get("Nombre"));

                String[] fechaPresentacion = reg.get("FechaPresentacion").split("-");
                LocalDate fechaPres = LocalDate.of(Integer.parseInt(fechaPresentacion[0]),
                		Integer.parseInt(fechaPresentacion[1]), Integer.parseInt(fechaPresentacion[2]));
                p.setFechaPresentacion(fechaPres);

                if (!reg.get("Resumen").equals("")) {
                	p.setResumen(reg.get("Resumen"));
                }

                if (!reg.get("FechaAprobacion").equals("")) {
                	String[] fechaAprobacion = reg.get("FechaAprobacion").split("-");
                    LocalDate fechaAprob = LocalDate.of(Integer.parseInt(fechaAprobacion[0]),
                    		Integer.parseInt(fechaAprobacion[1]), Integer.parseInt(fechaAprobacion[2]));
                	p.setFechaAprobacion(fechaAprob);
                }
                if (!reg.get("Descripcion").equals("")) {
                	p.setDescripcion(reg.get("Descripcion"));
                }
                
                if (!reg.get("FechaInicio").equals("")) {
                	String[] fechaInicio = reg.get("FechaAprobacion").split("-");
                    LocalDate fechaIni = LocalDate.of(Integer.parseInt(fechaInicio[0]),
                    		Integer.parseInt(fechaInicio[1]), Integer.parseInt(fechaInicio[2]));
                	p.setFechaInicio(fechaIni);
                }
                if (!reg.get("Fecha_Fin").equals("")) {
                	String[] fechaFin = reg.get("FechaAprobacion").split("-");
                    LocalDate fechaF = LocalDate.of(Integer.parseInt(fechaFin[0]),
                    		Integer.parseInt(fechaFin[1]), Integer.parseInt(fechaFin[2]));
                    p.setFechaFin(fechaF);
                }


                proyectos.add(p);
            }
        } catch (Exception e) {
            proyectos = new ArrayList<IProyecto>();
        }

        return proyectos;
    }

    public EstadoOperacion AgregarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        try {
        	if (integrante.getId() == -1) {
        		integrante.setId(GestorProyecto.getMaxID("Integrantes", "id"));
        	}
            ManejoDatos e = new ManejoDatos();
            String table = "Integrantes";
            String campos = "`id`, `Proyecto`";
            String valores = integrante.getId() + ", " + proyecto.getId();

            if (integrante.getLegajo() != -1) {
            	campos += ", Legajo";
            	valores += ", " + integrante.getLegajo();
            }
            if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
            	campos += ", Apellido";
            	valores += ", '" + integrante.getApellido() + "'";
            }
            if (integrante.getNombre() != null && !integrante.getApellido().equals("")) {
            	campos += ", Nombre";
            	valores += ", '" + integrante.getNombre() + "'";
            }
            if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
            	campos += ", Cargo";
            	valores += ", '" + integrante.getCargo() + "'";
            }
            if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
            	campos += ", Institucion";
            	valores += ", '" + integrante.getInstitucion() + "'";
            }
            if (integrante.getHorasSemanales() != -1) {
            	campos += ", HorasSemanales";
            	valores += ", " + integrante.getHorasSemanales();
            }
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El integrante se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el integrante");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        }
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        try {
            ManejoDatos e = new ManejoDatos();
            String tabla = "Integrante";
            e.delete(tabla, "id = " + integrante.getId() + "");
            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El integrante se eliminÃ³ correctamente");
        } catch (Exception var5) {
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el integrante");
        }
    }

    public List<IIntegrante> listarIntegrantes (IProyecto proyecto, IIntegrante integrante){
    	List<IIntegrante> integrantes = new ArrayList<IIntegrante>();
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Integrantes";
			String condicion = this.armarCondicion(proyecto, integrante);
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Integrante i = new Integrante();
				i.setId(Integer.parseInt(reg.get("id")));

				if (!reg.get("Legajo").equals("")) {
					i.setLegajo(Integer.parseInt(reg.get("Legajo")));
				}
				if (!reg.get("Apellido").equals("")) {
					i.setApellido(reg.get("Apellido"));
				}
				if (!reg.get("Nombre").equals("")) {
					i.setNombre(reg.get("Nombre"));
				}
				if (!reg.get("Cargo").equals("")) {
					i.setCargo(reg.get("Cargo"));
				}
				if (!reg.get("Institucion").equals("")) {
					i.setInstitucion(reg.get("Institucion"));
				}
				if (!reg.get("HorasSemanales").equals("")) {
					i.setHorasSemanales(Integer.parseInt(reg.get("HorasSemanales")));
				}
				integrantes.add(i);
			}
		} catch (NumberFormatException e) {
			integrantes = new ArrayList<IIntegrante>();
		}
    	return integrantes;
    }

    public IIntegrante getIIntegrante() {
        return new Integrante();
    }

    private String armarCondicion(IProyecto proyecto, IIntegrante integrante) {
    	String condicion = "TRUE";
    	if (integrante != null) {
    		if (integrante.getId() != -1) {
    			condicion += " AND id = " + integrante.getId();
    		}
    		if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
    			condicion += " AND Apellido = '" + integrante.getApellido() + "'";
    		}
    		if (integrante.getNombre() != null && !integrante.getNombre().equals("")) {
    			condicion += " AND Nombre = '" + integrante.getNombre() + "'";
    		}
    		if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
    			condicion += " AND Cargo = '" + integrante.getCargo() + "'";
    		}
    		if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
    			condicion += " AND Institucion = '" + integrante.getInstitucion() + "'";
    		}
    		if (integrante.getHorasSemanales() != -1) {
    			condicion += " AND HorasSemanales = " + integrante.getHorasSemanales();
    		}
    	}
    	if (proyecto != null) {
    		condicion += " AND Proyecto = " + proyecto.getId();
    	}

		return condicion;
	}

	public EstadoOperacion agregarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Subsidios";
            String campos = "`Proyecto`, `Year`, `Disposicion`, `MontoTotal`";
            String valores = proyecto.getId() + ", '" + subsidio.getFecha() + "', '" + subsidio.getDisposicion() + "', "
            		+ subsidio.getMontoTotal();

            if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
            	campos += ", Observaciones";
            	valores += ", '" + subsidio.getObservaciones() + "'";
            }

            for (IRendicion rendicion : subsidio.getRendiciones()) {
				this.agregarRendicion(rendicion, proyecto, subsidio);
			}

            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El subsidio se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el subsidio");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el subsidio");
        }
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`Subsidios`", "Proyecto = " + proyecto.getId() + ", Disposicion= '" + subsidio.getDisposicion() +  "'");
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitó correctamente");
    }

    public List<ISubsidio> listarSubsidios(IProyecto proyecto, ISubsidio subsidio){
    	List<ISubsidio> subsidios = new ArrayList<ISubsidio>();
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Subsidios";
			String condicion = this.armarCondicion(proyecto, subsidio);

			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Subsidio sub = new Subsidio();
				sub.setFecha(Year.parse(reg.get("Year")));
				sub.setDisposicion(reg.get("Disposicion"));
				sub.setMontoTotal(Float.parseFloat(reg.get("MontoTotal")));
				sub.setObservaciones(reg.get("Observaciones").equals("") ? null : reg.get("Observaciones"));

			}
		} catch (Exception e) {
			subsidios = new ArrayList<ISubsidio>();
		}
    	return subsidios;
    }


    private String armarCondicion(IProyecto proyecto, ISubsidio subsidio) {
		String condicion = "TRUE";
		if (subsidio != null) {
			if (subsidio.getFecha() != null) {
				condicion += " AND Year = " + subsidio.getFecha();
			}
			if (subsidio.getDisposicion() != null && !subsidio.getDisposicion().equals("")) {
				condicion += " AND Disposicion = '" + subsidio.getDisposicion() + "'";
			}
			if (subsidio.getMontoTotal() != -1) {
				condicion += " AND MontoTotal = " + subsidio.getMontoTotal();
			}
			if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
				condicion += " AND Observaciones = '" + subsidio.getObservaciones() + "'";
			}
		}
		if (proyecto != null) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		return condicion;
	}

	public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
        try {
            ManejoDatos e = new ManejoDatos();
            //`Fecha_inicio`, `Fecha_fin`
            String table = "Prorrogas";
            String campos = "`Proyecto`, `Disposicion`";
            String valores = proyecto.getId() + "', '" + prorroga.getDisposicion() + "'";

            if (prorroga.getFechaInicio() != null) {
            	campos += ", Fecha_inicio";
            	valores += ", '" + Date.valueOf(prorroga.getFechaInicio()) + "'";
            }
            if (prorroga.getFechaFin() != null) {
            	campos += ", Fecha_fin";
            	valores += ", '" + Date.valueOf(prorroga.getFechaFin()) + "'";
            }

            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El prorroga se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el prorroga");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el prorroga");
        }
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`Prorrogas`", "`Proyecto` = " + proyecto.getId() + ", Disposicion = '" + prorroga.getDisposicion() + "'");
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitó correctamente");
    }

    public List<IProrroga> listarProrrogas(IProyecto proyecto, IProrroga prorroga){
    	List<IProrroga> prorrogas = new ArrayList<IProrroga>();
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Prorrogas";
			String condicion = this.armarCondicion(proyecto, prorroga);

			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Prorroga p = new Prorroga();
				p.setDisposicion(reg.get("Disposicion"));

				if (!reg.get("Fecha_inicio").equals("")) {
					String[] fecha = reg.get("Fecha_inicio").split("-");
					LocalDate f = LocalDate.of(Integer.parseInt(fecha[0]),
							Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
					p.setFechaInicio(f);
				}
				if (!reg.get("Fecha_fin").equals("")) {
					String[] fecha = reg.get("Fecha_fin").split("-");
					LocalDate f = LocalDate.of(Integer.parseInt(fecha[0]),
							Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
					p.setFechaFin(f);
				}
				prorrogas.add(p);
			}
		} catch (Exception e) {
			prorrogas = new ArrayList<IProrroga>();
		}

    	return prorrogas;
    }


    private String armarCondicion(IProyecto proyecto, IProrroga prorroga) {
		String condicion = "TRUE";
		if (prorroga != null) {
			if (prorroga.getDisposicion() != null && !prorroga.getDisposicion().equals("")) {
				condicion += " AND Disposicion = '" + prorroga.getDisposicion() + "'";
			}
			if (prorroga.getFechaInicio() != null) {
				condicion += " AND Fecha_inicio = '" + Date.valueOf(prorroga.getFechaInicio()) + "'";
			}
			if (prorroga.getFechaFin() != null) {
				condicion += " AND Fecha_fin = '" + Date.valueOf(prorroga.getFechaFin()) + "'";
			}
		}
		if (proyecto != null) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		return condicion;
	}

	private String armarCondicion(IProyecto p, IPrograma programa) {

		IProyectog proyecto = (IProyectog) p;
		
        String condicion = "TRUE";
        if (proyecto != null) {
            if (proyecto.getId() != 0) {
                condicion += " AND `Id` = " + proyecto.getId();
            }
            if (proyecto.getNombre() != "") {
                condicion += " AND `Nombre` = '" + proyecto.getNombre() + "'";
            }
            if (proyecto.getDescripcion() != "") {
                condicion += " AND `Descripcion` = '" + proyecto.getDescripcion() + "'";
            }
            if (proyecto.getDirector2() != null) {
                condicion += " AND `Director` = " + proyecto.getDirector2().getLegajo();
            }
            if (proyecto.getFechaPresentacion() != null) {
                condicion += " AND `Fecha_Presentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()).toString() + "'";
            }
            if (proyecto.getFechaAprobacion() != null) {
                condicion += " AND `Fecha_Aprovacion` = " + Date.valueOf(proyecto.getFechaAprobacion()).toString() + "'";
            }
            if (proyecto.getFechaInicio() != null) {
                condicion += " AND `Fecha_Inicio` = '" + Date.valueOf(proyecto.getFechaInicio()).toString() + "'";
            }
            if (proyecto.getFechaFin() != null) {
                condicion += " AND `Fecha_Fin` = " + Date.valueOf(proyecto.getFechaFin()).toString() + "'";
            }
            if (proyecto.getEstado2() != null) {
                condicion += " AND `Estado`= " + proyecto.getEstado2();
            }
        }
        if (programa != null) {
        	if (programa.getId() != -1) {
        		 condicion += " AND `Programa` = " + programa.getId();
        	}
        }

        return condicion;
    }

    private static int getMaxID(String tabla, String campo) {
    	int maxid = 0;
    	try {
			ManejoDatos md = new ManejoDatos();
			String c = "MAX("+ campo + ")";
			ArrayList<Hashtable<String, String>> res = md.select(tabla, c);
			Hashtable<String, String> reg = res.get(0);
			maxid = Integer.parseInt(reg.get(c));
		} catch (NumberFormatException e) {
			maxid = 0;
		}catch (NullPointerException e2) {
			maxid = 0;
		}
    	return maxid;
	}

    public EstadoOperacion agregarRendicion(IRendicion rendicion, IProyecto proyecto, ISubsidio subsidio) {
    	try {
			rendicion.setId(GestorProyecto.getMaxID("Rendiciones", "id") + 1);
			ManejoDatos md = new ManejoDatos();
			String tabla = "Rendisiones";
			String campos = "`id`, `Proyecto`, `YearSubsidio`, `Fecha`, `Monto`";
			String valores = rendicion.getId() + ", " + proyecto.getId() + ", " + subsidio.getFecha()  + ", "
					+ "'" + Date.valueOf(rendicion.getFecha()) + "', " + rendicion.getMonto();

			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				campos += ", Observaciones";
				valores += ", '" + rendicion.getObservaciones() + "'";
			}

			md.insertar(tabla, campos, valores);
			return md.isEstado()
			        ? new EstadoOperacion(CodigoEstado.INSERT_OK, "La rendicion se agregó correctamente")
			        : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
		} catch (Exception e) {
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
		}
    }


    public EstadoOperacion eliminarRendicion(IRendicion rendicion) {
    	ManejoDatos md = new ManejoDatos();

    	md.delete("Rendiciones", "id = " + rendicion.getId());

    	return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.DELETE_OK, "La rendicion se elimino correctamente")
                : new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la rendicion");
    }


    public List<IRendicion> listarRendiciones(IRendicion rendicion){
    	List<IRendicion> rendiciones = new ArrayList<IRendicion>();
    	ManejoDatos md = new ManejoDatos();
    	String tabla = "Rendiciones";
    	String condicion = this.armarCondicion(rendicion);

    	ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
		for (Hashtable<String, String> reg : res) {
			Rendicion r = new Rendicion();
			r.setId(Integer.parseInt(reg.get("id")));

			String[] fecha = reg.get("Fecha").split("-");
			LocalDate f = LocalDate.of(Integer.parseInt(fecha[0]),
					Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
			r.setFecha(f);
			r.setMonto(Float.parseFloat(reg.get("Monto")));
			if (!reg.get("Observaciones").equals("")) {
				r.setObservaciones(reg.get("Observaciones"));
			}
			rendiciones.add(r);
		}

    	return rendiciones;
    }

    private String armarCondicion(IRendicion rendicion) {
		String condicion = "TRUE";
		if (rendicion != null) {
			if (rendicion.getId() != -1) {
				condicion += " AND id = " + rendicion.getId();
			}
			if (rendicion.getFecha() != null) {
				condicion += " AND Fecha = '" + Date.valueOf(rendicion.getFecha()) + "'";
			}
			if (rendicion.getMonto() != -1) {
				condicion += " AND Monto =" + rendicion.getMonto();
			}
			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				condicion += " AND Observaciones = '" + rendicion.getObservaciones() + "'";
			}
		}
		return condicion;
	}

	public IProyecto getIProyecto() {
        return new Proyecto();
    }


}
