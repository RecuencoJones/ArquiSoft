package myusick.model.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public class Publicante {

	@Id
	@Column(name = "uuid", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int uuid;
	
	@Column (name = "email", nullable = false, length = 60)
	private String email;
	
	@Column (name = "tipoPublicante", nullable = false)
	private boolean tipoPublicante;

	/*------RELACIONES------*/
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="publicante", 
			joinColumns={@JoinColumn(name="uuid", referencedColumnName="uuid")}, 
			inverseJoinColumns={@JoinColumn(name="publicante_uuid", referencedColumnName="publicante_uuid")})
	private Set<Persona> pub_pers = new HashSet<Persona>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="publicante", 
			joinColumns={@JoinColumn(name="uuid", referencedColumnName="uuid")}, 
			inverseJoinColumns={@JoinColumn(name="publicante_uuid", referencedColumnName="publicante_uuid")})
	private Set<Grupo> pub_grupo = new HashSet<Grupo>();
	
	@ElementCollection
	List<Publicacion> pubs;
		
	/*------GETTERS/SETTERS------*/
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTipoPublicante() {
		return tipoPublicante;
	}

	public void setTipoPublicante(boolean tipoPublicante) {
		this.tipoPublicante = tipoPublicante;
	}
	
	public boolean addPublicacion(Publicacion p){
		pubs.add(p);
		return true;
	}
	
}
