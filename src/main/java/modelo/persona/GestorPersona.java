package modelo.persona;

import java.util.ArrayList;
import java.util.Hashtable;
import java.sql.Date;

import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorPersona {

	public EstadoOperacion nuevoUsuario(IPersona persona) {

		try {
			ManejoDatos md = new ManejoDatos();
			String table = "EstadoPersona";
			String campos = "";
			String valores = persona.getEstado().ordinal() + ", '" + persona.getEstado().name() + "'";
			String condicion = "descripcion = '" + persona.getEstado().name() + "'";
			
			if (md.select(table, "*", condicion).isEmpty()) {
				md.insertar(table, "idEstado, Descripcion", valores);
			}
			
			ArrayList<Hashtable<String, String>> res = md.select(table, "*", condicion);
			String estado = res.get(0).get("idEstado");
			
			table = "tipos_documentos";
			campos = "idTipo, Descripcion";
			valores = persona.getTipoDocumento().ordinal() + ", '" + persona.getTipoDocumento().name() + "'";
			condicion = "Descripcion = '" + persona.getTipoDocumento().name() + "'";
			
			if (md.select(table, "*", condicion).isEmpty()) {
				md.insertar(table, campos, valores);
			}
			
			
			table = "tipos_documentos";
			campos = "idTipo, Descripcion";
			valores = persona.getTipoDocumento().ordinal() + ", '" + persona.getTipoDocumento().name() + "'";
			
			res = md.select(table, "*", condicion);
			String tipoDoc = res.get(0).get("idTipo");
			String nroDoc = String.valueOf(persona.getNroDocumento());
			String fechaNac = Date.valueOf(persona.getFechaNacimiento()).toString();

			table = "Persona";
			campos = "`TipoDocumento`, `NroDocumento`, `Apellido`, `Nombre`, `FechaNacimiento`, `Estado`";
			valores = tipoDoc + ", '" + nroDoc + "'," + " '" + persona.getApellido() + "', "
					+ "'" + persona.getNombre() + "',"
					+ " '" + fechaNac + "', " + estado;
			
			md.insertar(table, campos, valores);
			
			
			if (persona.getContactos() != null) {
				if(!persona.getContactos().isEmpty()) {
					this.insertarContactos(persona);
				}
			}
			
			if (persona.getDomicilios() != null) {
				if (!persona.getDomicilios().isEmpty()) {
					this.insetarDomicilios(persona);
				}
			}
			
			if (persona.getTitulos() != null) {
				if (!persona.getTitulos().isEmpty()) {
					this.insertarTitulos(persona);
				}
			}
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
						"La persona se creo correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,
						"No se pudo crear la persona");
			}
			
			
		}catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,
					"No se pudo crear la persona");

		}

	}

	private void insertarTitulos(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (ITitulo titulo : persona.getTitulos()) {
			int esMayor = titulo.isEsMayor() ? 1 : 0;
			String table = "titulos";
			String campos = "`idtitulos`, `TipoDocumento`, `NroDocumento`, `Nombre`, `EsMayor`";
			String valores = titulo.getId() + ", " + persona.getTipoDocumento().ordinal() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + titulo.getNombre() + "', " + esMayor ;
			
			md.insertar(table, campos, valores);
		}
		
		
		
	}

	private void insetarDomicilios(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (IDomicilio domicilio : persona.getDomicilios()) {
			String table = "domicilios";
			String campos = "`iddomicilios`, `TipoDocumento`, `NroDocumento`, `Provincia`, `Ciudad`,"
					+ "`CodigoPostal`, `Domicilio`";
			String valores = domicilio.getId() + ", " + persona.getTipoDocumento().ordinal() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + domicilio.getProvincia() + "', "
					+ "'" + domicilio.getCiudad() + "', '" + domicilio.getCodigoPostal() + "', "
					+ "'" + domicilio.getDireccion() + "'";
			
			md.insertar(table, campos, valores);
		}
		
	}

	private void insertarContactos(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (IContacto contacto : persona.getContactos()) {
			String table = "contacto";
			String campos = "`idcontacto`, `TipoDocumento`, `NroDocumento`, `Nombre`, `Tipo`, `Valor`";
			String valores = contacto.getId() + ", " + persona.getTipoDocumento().ordinal() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + contacto.getNombre() + "', "
					+ "'" + contacto.getTipo() + "', '" + contacto.getValor() + "'";
			
			md.insertar(table, campos, valores);
		}
		
	}

}
