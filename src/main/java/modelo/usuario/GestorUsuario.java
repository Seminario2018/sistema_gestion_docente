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

        	table = "RolesXUsuario";
        	campos = "`Usuario`, `Rol`";
        	for(IRol r : usuario.getRoles()) {
        		valores = "'" + usuario.getUser() + "', " + r.getId();
            	md.insertar(table, campos, valores);
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
            		campos += "`TipoDocumento` = '" + usuario.getPersona().getTipoDocumento().getId();
            	}
            	if (usuario.getPersona().getNroDocumento() != -1) {
            		if (!campos.equals("")) {
            			campos += ", ";
            		}
            		campos += "`NroDocumento`= '" + usuario.getPersona().getNroDocumento() + "'";
            	}
        	}
        	

        	md.update(tabla, campos, condicion);

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
    	List<Hashtable<String, String>> res = new ArrayList<Hashtable<String, String>>();
        List<IUsuario> usuarios = new ArrayList<IUsuario>();

        String tabla = "Usuarios INNER JOIN Personas ON Usuarios.TipoDocumentoPersona = Personas.TipoDocumento"
        		+ " AND Usuarios.NroDocumentoPerson = Personas.NroDocumento";
        String campos = "*";
        String condicion = this.armarCondicion(usuario);

    	try {
        	ManejoDatos md = new ManejoDatos();
        	res = md.select(tabla, campos, condicion);
        	for (Hashtable<String, String> reg : res) {
				IUsuario user = new Usuario(
					reg.get("Usuario").toString(),
					new HashSalt(reg.get("Hash").toString(), reg.get("Salt").toString()),
					reg.get("Descripcion").toString(),
					new ArrayList<IRol>()
				);

				GestorPersona gp = new GestorPersona();
				IPersona p = new Persona();
				p.setTipoDocumento(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
				p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
				p = (Persona) gp.listarPersonas(p).get(0);
				user.setPersona(p);
				this.setRoles(user);

				usuarios.add(user);

			}
        	return usuarios;

        } catch(Exception e) {
            e.printStackTrace();
        	return usuarios;
        }
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
    	ManejoDatos md = new ManejoDatos();
    	GestorRol gr = new GestorRol();
    	List<IRol> roles = gr.listarGrupo(rol);

    	if (roles.isEmpty()) {
    		gr.nuevoGrupo(rol);
    	}

    	String tabla = "RolesXUsuario";
    	String campos = "Usuario, Rol";
    	String valores = "'" + usuario.getUser() + "', " + rol.getId();

    	md.insertar(tabla, campos, valores);

    	if (md.isEstado()) {
    	    usuario.setGrupos(new ArrayList<IRol>());
    	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El grupo se agregó correctamente");
    	} else {
    	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "El grupo no se pudo agregar.");
    	}
    }

    public EstadoOperacion quitarGrupo(IUsuario usuario, IRol grupo) {
    	ManejoDatos md = new ManejoDatos();
    	String tabla = "RolesXUsuario";
    	String condicion = " Usuario = '" + usuario.getUser() + "', " + grupo.getId();

    	md.delete(tabla, condicion);

    	if (md.isEstado()) {
    	    usuario.setGrupos(new ArrayList<IRol>());
    	    return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El grupo se quitó correctamente");
    	} else {
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
    		if (usuario.getUser() != null) {
    			if (!condicion.equals("")) {
					condicion += " AND ";
				}
    			condicion += "Usuario = '" + usuario.getUser() + "'";
    		}

        }
    	return condicion;
    }

    private void  setRoles(IUsuario usuario) {
    	try {
    		String tabla = "RolesXUsuario";

        	ManejoDatos md = new ManejoDatos();
    		List<Hashtable<String, String>> res = md.select(tabla, "*", "Usuario = '" + usuario.getUser() + "'");
    		for (Hashtable<String, String> reg : res) {
				Rol r = new Rol(Integer.parseInt(reg.get("Rol")));
				GestorRol gr = new GestorRol();
				r = (Rol) gr.listarGrupo(r).get(0);
				usuario.agregarRol(r);
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}