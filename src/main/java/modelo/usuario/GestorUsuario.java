package modelo.usuario;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoDocumento;
import modelo.auxiliares.hash.HashSalt;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.persona.Persona;
import persistencia.ManejoDatos;

public class GestorUsuario {

    public EstadoOperacion nuevoUsuario(IUsuario usuario) {
    	try {
    		ManejoDatos md = new ManejoDatos();
        	String table = "Usuarios";
        	String campos = "`Usuario`, `Hash`, `Salt`, `TipoDocumentoPersona`, `NroDocumentoPerson`, `Descripcion`";
        	String valores = "'" + usuario.getUser() + "', '" +
    			usuario.getHash().getHash() + "', '" +
    			usuario.getHash().getSalt() + "', " +
    			usuario.getPersona().getTipoDocumento().getId() + ", '" +
    			String.valueOf(usuario.getPersona().getNroDocumento()) + "', '" +
    			usuario.getDescripcion() + "'";

        	md.insertar(table, campos, valores);

        	for (IRol rol : usuario.getRoles()) {
            	md.insertar(
            	    "RolesXUsuario",
            	    "`Usuario`, `Rol`",
            	    "'" + usuario.getUser() + "', " + rol.getId());
        	}

        	return md.isEstado() ?
        	    new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El usuario se creó correctamente") :
    	        new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el usuario");

    	} catch (Exception e) {
    	    e.printStackTrace();
    		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el usuario");
    	}

    }

    public EstadoOperacion modificarUsuario(IUsuario usuario) {
        try {
        	ManejoDatos md = new ManejoDatos();
        	String tabla = "Usuarios";
        	String campos = "";
        	String condicion = "`Usuario` = '" + usuario.getUser() + "'";

        	if (usuario.getUser() != null && !usuario.getUser().equals("")) {
        		campos += "`Usuario` = '"+ usuario.getUser() + "'";
        	}

        	if (usuario.getHash() != null) {
        		if (!campos.equals("")) {
        			campos += ", ";
        		}
        		campos += "`Hash` = '"+ usuario.getHash().getHash() +"', "
        				+ "`Salt`= '"+ usuario.getHash().getSalt() + "'";
        	}

        	if (usuario.getPersona() != null) {
        		if (usuario.getPersona().getTipoDocumento() != null) {
        			if (!campos.equals("")) {
            			campos += ", ";
            		}
            		campos += "`TipoDocumentoPersona` = " + usuario.getPersona().getTipoDocumento().getId();
            	}
            	if (usuario.getPersona().getNroDocumento() != -1) {
            		if (!campos.equals("")) {
            			campos += ", ";
            		}
            		campos += "`NroDocumentoPerson`= '" + usuario.getPersona().getNroDocumento() + "'";
            	}
        	}

        	if (usuario.getDescripcion() != null && !usuario.getDescripcion().equals("")) {
        	    if (!campos.equals("")) {
                    campos += ", ";
                }
                campos += "`Descripcion`= '" + usuario.getDescripcion() + "'";
        	}

        	md.update(tabla, campos, condicion);

        	// Actualizar roles de usuario:
        	md.delete("RolesXUsuario", "Usuario = '" + usuario.getUser() + "'");
        	for (IRol rol : usuario.getRoles()) {
                md.insertar(
                    "RolesXUsuario",
                    "`Usuario`, `Rol`",
                    "'" + usuario.getUser() + "', " + rol.getId());
            }

        	return md.isEstado() ?
        	    new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El usuario se modificó correctamente") :
    	        new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el usuario");

        } catch(Exception e) {
            e.printStackTrace();
        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el usuario");
        }

    }

    public EstadoOperacion eliminarUsuario(IUsuario usuario) {
    	try {
        	ManejoDatos md = new ManejoDatos();

        	md.delete("`RolesXUsuario`", "Usuario = '" + usuario.getUser() + "'");
        	md.delete("`Usuarios`", "Usuario = '" + usuario.getUser() + "'");

        	return md.isEstado() ?
        	    new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El usuario se eliminó correctamente") :
        	    new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el usuario");

        } catch(Exception e) {
            e.printStackTrace();
        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el usuario");
        }
    }

    public List<IUsuario> listarUsuario(IUsuario usuario) {
        /* listarUsuario original */
        GestorPersona gp = new GestorPersona();

        List<IUsuario> usuarios = new ArrayList<IUsuario>();

        String tabla = "Usuarios INNER JOIN Personas ON Usuarios.TipoDocumentoPersona = Personas.TipoDocumento"
        		+ " AND Usuarios.NroDocumentoPerson = Personas.NroDocumento";
        String campos = "*";
        String condicion = this.armarCondicion(usuario);

    	try {
        	ManejoDatos md = new ManejoDatos();
        	List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
        	for (Hashtable<String, String> reg : res) {
				IUsuario user = new Usuario(
					reg.get("Usuario").toString(),
					new HashSalt(reg.get("Hash").toString(), reg.get("Salt").toString()),
					reg.get("Descripcion").toString(),
					new ArrayList<IRol>()
				);

				IPersona personaBusqueda = new Persona();
				personaBusqueda.setTipoDocumento(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
				personaBusqueda.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
				IPersona persona = gp.listarPersonas(personaBusqueda).get(0);
				user.setPersona(persona);
				this.setRoles(user);

				usuarios.add(user);
			}
        	return usuarios;

        } catch(Exception e) {
            e.printStackTrace();
        	return new ArrayList<IUsuario>();
        }
    	//*/
    }

    public static boolean existeUsuario(IUsuario usuario) {
        String tabla = "usuarios";
        if (usuario == null || usuario.getUser().equals("")) {
            return false;
        }
        String condicion = "Usuario = '" + usuario.getUser() + "'";
        try {
            ManejoDatos md = new ManejoDatos();
            List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
            return !(res.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public IUsuario getIUsuario() {
        return new Usuario();
    }

    public EstadoOperacion agregarRol(IUsuario usuario, IRol rol) {
        try {
        	GestorRol gr = new GestorRol();
        	List<IRol> roles = gr.listarGrupo(rol);

        	if (roles.isEmpty()) {
        		gr.nuevoGrupo(rol);
        	}

        	String tabla = "RolesXUsuario";
        	String campos = "Usuario, Rol";
        	String valores = "'" + usuario.getUser() + "', " + rol.getId();

        	ManejoDatos md = new ManejoDatos();
        	md.insertar(tabla, campos, valores);

        	if (md.isEstado()) {
        	    usuario.setGrupos(new ArrayList<IRol>());
        	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El grupo se agregó correctamente");
        	} else {
        	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El grupo no se pudo agregar.");
        	}

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El grupo no se pudo agregar.");
        }
    }

    public EstadoOperacion quitarGrupo(IUsuario usuario, IRol grupo) {
        try {
        	ManejoDatos md = new ManejoDatos();
        	String tabla = "`rolesXusuario`";
        	String condicion =
        	    "Usuario = '" + usuario.getUser() + "' AND " +
        	    "Rol = " + grupo.getId();

        	md.delete(tabla, condicion);

        	if (md.isEstado()) {
        	    usuario.setGrupos(new ArrayList<IRol>());
        	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El grupo se quitó correctamente");
        	} else {
        	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "El grupo no se pudo quitar");
        	}

        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "El grupo no se pudo quitar");
        }
    }

    private String armarCondicion(IUsuario usuario) {
    	String condicion = "TRUE";
    	if (usuario != null) {
    		condicion = "";
    		//usuario
    		if (usuario.getPersona() != null) {
    			if (usuario.getPersona().getTipoDocumento() != null) {
    				condicion += "TipoDocumentoPersona = " + usuario.getPersona().getTipoDocumento().getId();
    			}
    			if (usuario.getPersona().getNroDocumento() != 0) {
    				if (!condicion.equals("")) {
    					condicion += " AND ";
    				}
    				condicion += "NroDocumentoPerson = '" + usuario.getPersona().getNroDocumento() + "'";
    			}
    		}
    		//private String user;
    		if (usuario.getUser() != null && !usuario.getUser().equals("")) {
    			if (!condicion.equals("")) {
					condicion += " AND ";
				}
    			condicion += "Usuario = '" + usuario.getUser() + "'";
    		}

    		HashSalt hash = usuario.getHash();
    		if (hash != null) {
    		    if (!condicion.equals("")) {
                    condicion += " AND ";
                }
    		    condicion +=
    		        "`Hash` = '" + hash.getHash() + "' AND " +
    		        "`Salt` = '" + hash.getSalt() + "'";
    		}

        }
    	return condicion;
    }

    private void setRoles(IUsuario usuario) {
    	try {
    	    GestorRol gr = new GestorRol();

        	ManejoDatos md = new ManejoDatos();
    		List<Hashtable<String, String>> res =
    		    md.select(
    		        "RolesXUsuario",
    		        "*",
    		        "Usuario = '" + usuario.getUser() + "'");
    		for (Hashtable<String, String> reg : res) {
				IRol r = new Rol(Integer.parseInt(reg.get("Rol")));
				r = gr.listarGrupo(r).get(0);
				usuario.agregarRol(r);
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}