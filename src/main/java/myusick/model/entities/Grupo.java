package myusick.model.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Grupo {
	@Id
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publicante_uuid")
	private int publicante_uuid;

	@Column(name = "nombre", nullable = false, length = 45)
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "anyofundacion", nullable = false)
	private Date anyofundacion;
	
	@Column(name = "descripcion", nullable = true, length = 144)
	private String descripcion;
	
	/*------RELACIONES------*/
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="es_integrante", 
			joinColumns={@JoinColumn(name="uuid_g", referencedColumnName="publicante_uuid")}, 
			inverseJoinColumns={@JoinColumn(name="uuid_p", referencedColumnName="publicante_uuid")})
	private Set<Persona> miembros = new HashSet<Persona>();
		
	/*------GETTERS/SETTERS------*/
	
	public int getPublicante_uuid() {
		return publicante_uuid;
	}

	public void setPublicante_uuid(int publicante_uuid) {
		this.publicante_uuid = publicante_uuid;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getAnyo() {
		return anyofundacion;
	}

	public void setAnyo(Date anyo) {
		this.anyofundacion = anyo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
