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

    @OneToMany(mappedBy = "publicante")
    private Set<Publicacion> publicaciones;

    public Publicante(boolean tipoPublicante) {
        this.tipoPublicante = tipoPublicante;
    }

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

    public Set<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Set<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
    //	public boolean addPublicacion(Publicacion p){
//		pubs.add(p);
//		return true;
//	}
	
}
