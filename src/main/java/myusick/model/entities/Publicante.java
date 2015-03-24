package myusick.model.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Publicante")
public class Publicante {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "uuid", nullable = false)
	private int uuid;
	
	@Column (name = "tipoPublicante", nullable = false)
	private boolean tipoPublicante;

	/*------RELACIONES------*/
	/*@ManyToMany(cascade=CascadeType.ALL)
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
	private Set<Grupo> pub_grupo = new HashSet<Grupo>();*/
	
	@ElementCollection
	List<Publicacion> pubs;
		
	/*------GETTERS/SETTERS------*/
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
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
