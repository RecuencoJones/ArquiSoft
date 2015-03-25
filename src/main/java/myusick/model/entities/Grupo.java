package myusick.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Grupo")
public class Grupo implements Serializable{

    @Id
    @Column(name = "Publicante_UUID")
    private int id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Publicante publicante;

	@Column(name = "anyofundacion", nullable = false)
	private long anyofundacion;
	
	@Column(name = "descripcion", nullable = true, length = 144)
	private String descripcion;

	/*------RELACIONES------*/
    @ManyToMany(mappedBy = "grupos")
    private Set<Persona> miembros;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "grupo_tiene_tag",
            joinColumns = {@JoinColumn(name = "UUID_G", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "idTag", nullable = false, updatable = false)})
    private Set<Tag> tagsGrupo = new HashSet<Tag>(0);

    public Grupo(String nombre, long anyofundacion, String descripcion) {
        this.nombre = nombre;
        this.anyofundacion = anyofundacion;
        this.descripcion = descripcion;
    }

	/*------GETTERS/SETTERS------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Publicante getPublicante() {
        return publicante;
    }

    public void setPublicante(Publicante publicante) {
        this.publicante = publicante;
    }

    public long getAnyofundacion() {
        return anyofundacion;
    }

    public void setAnyofundacion(long anyofundacion) {
        this.anyofundacion = anyofundacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Persona> getMiembros() {
        return miembros;
    }

    public void setMiembros(Set<Persona> miembros) {
        this.miembros = miembros;
    }

    public Set<Tag> getTagsBanda() {
        return tagsGrupo;
    }

    public void setTagsGrupo(Set<Tag> tagsBanda) {
        this.tagsGrupo = tagsBanda;
    }
	/*public boolean addMiembro(Persona p){
        boolean exito = false;
        try{
            miembros.add(p); exito=true;
        }catch(Exception ex){*//*nada, exito permanece false*//* }

        return exito;
    }
    */
     public boolean anadirTag(Tag tag){
        try {
            this.tagsGrupo.add(tag);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
