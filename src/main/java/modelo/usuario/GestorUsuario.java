package modelo.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoDocumento;
import modelo.auxiliares.hash.HashSalt;
import modelo.persona.Persona;
import persistencia.ManejoDatos;

public class GestorUsuario {

    public EstadoOperacion nuevoUsuario(IUsuario usuario) {
        
    	try {
    		ManejoDatos md = new ManejoDatos();
        	String table = "Usuario";
        	String campos = "`Usuario`, `Hash`, `Salt`, `TipoDocumentoPersona`, `NroDocumentoPerson`, `Descripcion`";
        	String valores = "'" + usuario.getUser() + "', '" +
        			usuario.getHash().getHash() + "', '" +
        			usuario.getHash().getSalt() + "', " +
        			usuario.getPersona().getTipoDocumento().ordinal() + ", '" +
        			String.valueOf(usuario.getPersona().getNroDocumento()) + "', '" +
        			usuario.getDescripcion() + "'";
        	

        	md.insertar(table, campos, valores);
        	
        	table = "RolesXUsuario";
        	campos = "`Usuario`, `Rol`";
        	for(IRol r : usuario.getGrupos()) {
        		valores = "'" + usuario.getUser() + "', '" + r.getNombre() + "'";
            	md.insertar(table, campos, valores);
        	}
        	
            if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
						"El usuario se creó correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el usuario");
			}
    	}catch (Exception e) {
    		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el usuario");
    		
    	}
    	
    }

    public EstadoOperacion modificarUsuario(IUsuario usuario) {
        try {
        	ManejoDatos md = new ManejoDatos();
        	String tabla = "Usuario";
        	String campos = "`Usuario` = '"+ usuario.getUser() +
        			"', `Hash` = '"+ usuario.getHash().getHash() +"', `Salt`= '"+ usuario.getHash().getSalt() +
        			"', `TipoDocumentoPersona`= '"+ usuario.getPersona().getTipoDocumento() +
        			"', `NroDocumentoPerson` = '"+ usuario.getPersona().getTipoDocumento() +"'";
        	String condicion = "`Usuario` = '" + usuario.getUser() + "'";
        	
        	md.update(tabla, campos, condicion);
        	
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                    "El usuario se modificó correctamente");
        }catch(Exception e) {
        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
                    "No se pudo modificar el usuario");
        }
    	
    }

    public EstadoOperacion eliminarUsuario(IUsuario usuario) {
    	try {
        	ManejoDatos md = new ManejoDatos();
        	
        	md.delete("`RolesXUsuario`", "Usuario = " + usuario.getUser());
        	md.delete("`Usuario`", "Usuario = " + usuario.getUser());
        	
        	
            return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                    "El usuario se eliminó correctamente");
        }catch(Exception e) {
        	return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
                    "No se pudo eliminar el usuario");
        }
    }

    public List<IUsuario> listarUsuario(IUsuario usuario) {
    	ArrayList<Hashtable<String, String>> res = new ArrayList<Hashtable<String, String>>();
        ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
    	
        String tabla = "Usuario INNER JOIN Persona ON Usuario.TipoDocumentoPersona = Persona.TipoDocumento"
        		+ " AND Usuario.NroDocumentoPerson = Persona.NroDocumento";
        String campos = "*";
        String condicion = this.armarCondicion(usuario);
    	
    	
    	
    	try {
        	ManejoDatos md = new ManejoDatos();	
        	res = md.select(tabla, campos, condicion);
        	for (Hashtable<String, String> reg : res) {
				Usuario user = new Usuario(
							reg.get("Usuario").toString(),
							new HashSalt(reg.get("Hash").toString(), reg.get("Salt").toString()),
							reg.get("Descripcion").toString(),
							new ArrayList<IRol>()
						);
				String[] fnac = reg.get("FechaNacimiento").split("-");
				
				
				Persona p = new Persona(reg.get("Apellido").toString(),
						reg.get("Nombre").toString(),
						LocalDate.of(Integer.parseInt(fnac[0]), Integer.parseInt(fnac[1]), Integer.parseInt(fnac[2])),
						TipoDocumento.values()[Integer.parseInt(reg.get("TipoDocumento").toString())],
						Integer.parseInt(reg.get("NroDocumento").toString()), null, null, null, null);
				user.setPersona(p);
				this.setRoles(user);
				
				usuarios.add(user);				
				
			}
        	return usuarios;
        }catch(Exception e) {
        	return usuarios;
        }
        
        
    }

    public EstadoOperacion agregarGrupo(IUsuario usuario, IRol grupo) {
    	ManejoDatos md = new ManejoDatos();	
    	GestorRol gr = new GestorRol();
    	ArrayList<IRol> roles = (ArrayList<IRol>) gr.listarGrupo(grupo);
    	
    	if (roles.isEmpty()) {
    		gr.nuevoGrupo(grupo);
    	}

    	String tabla = "RolesXUsuario";
    	String campos = "Usuario, Rol";
    	String valores = "'" + usuario.getUser() + "', '" + grupo.getNombre() + "'";  
    	
    	md.insertar(tabla, campos, valores);
    	
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El grupo se agregó correctamente");
    }

    public EstadoOperacion quitarGrupo(IUsuario usuario, IRol grupo) {
    	
    	ManejoDatos md = new ManejoDatos();
    	String tabla = "RolesXUsuario";
    	String condicion = " Usuario = '" + usuario.getUser() + "', '" + grupo.getNombre() + "'"; 
    	
    	md.delete(tabla, condicion);
    	
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El grupo se quitó correctamente");
    }
    
    private String armarCondicion(IUsuario usuario) {
    	String condicion = "TRUE";
    	if (usuario != null) {
    		condicion = "";
    		//usuario
    		if (usuario.getPersona() != null) {
    			if (usuario.getPersona().getTipoDocumento() != null) {
    				condicion += "TipoDocumentoPersona = '" + usuario.getPersona().getTipoDocumento() + "'";
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
    		ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", "Usuario = '" + usuario.getUser() + "'");
    		for (Hashtable<String, String> reg : res) {
				Rol r = new Rol(reg.get("Rol"));
				GestorRol gr = new GestorRol();
				r = (Rol) gr.listarGrupo(r).get(0);
				usuario.agregarGrupo(r);
			}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	
		
    	
    	
    }
    
}