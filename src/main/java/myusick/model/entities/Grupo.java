package myusick.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Grupo implements Serializable{
	//@EmbeddedId
    @Id
	@OneToOne(targetEntity = Publicante.class, cascade = CascadeType.ALL)
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
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="grupo_tiene_tag", 
			joinColumns={@JoinColumn(name="uuid_g", referencedColumnName="publicante_uuid")}, 
			inverseJoinColumns={@JoinColumn(name="idtag", referencedColumnName="idtag")})
	private Set<Tag> tags_grupo = new HashSet<Tag>();
		
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

    public boolean addMiembro(Persona p){
        boolean exito = false;
        try{
            miembros.add(p); exito=true;
        }catch(Exception ex){/*nada, exito permanece false*/ }

        return exito;
    }

    public boolean addTag(Tag t){
        boolean exito = false;
        try{
            tags_grupo.add(t); exito=true;
        }catch(Exception ex){/*nada, exito permanece false*/ }

        return exito;
    }
}
