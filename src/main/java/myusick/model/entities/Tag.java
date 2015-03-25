package myusick.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {

	@Id
	@Column(name = "idtag", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idtag;

	@Column(name = "nombretag", nullable = false, length = 60)
	private String nombretag;

    /*------RELACIONES------*/
    @ManyToMany(mappedBy = "tagsGrupo")
    private Set<Grupo> gruposConTag;

    @ManyToMany(mappedBy = "tagsPersona")
    private Set<Persona> personasConTag;

    public Tag(String nombretag) {
        this.nombretag = nombretag;
    }

    /*------GETTERS/SETTERS------*/
	public int getIdtag() {
		return idtag;
	}

	public void setIdtag(int idtag) {
		this.idtag = idtag;
	}

	public String getNombreTag() {
		return nombretag;
	}

	public void setNombreTag(String nombre) {
		this.nombretag = nombre;
	}

    public Set<Grupo> getGruposConTag() {
        return gruposConTag;
    }

    public void setGruposConTag(Set<Grupo> gruposConTag) {
        this.gruposConTag = gruposConTag;
    }

    public Set<Persona> getPersonasConTag() {
        return personasConTag;
    }

    public void setPersonasConTag(Set<Persona> personasConTag) {
        this.personasConTag = personasConTag;
    }
}
