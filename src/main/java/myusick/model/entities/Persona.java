package myusick.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Persona")
public class Persona implements Serializable{

    @Id
    @Column(name = "Publicante_UUID")
    private int publicante_uuid;

    @Transient
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Publicante publicante;

	@Column(name = "nombre", nullable = false, length = 20)
	private String nombre;

	@Column(name = "apellidos", nullable = true, length = 60)
	private String apellidos;

	@Column(name = "password", nullable = false, length = 20)
	private String password;
	
	@Column(name = "email", nullable = false, length = 60)
	private String email;

	@Column(name = "fechanacimiento", nullable = false)
	private long fechanacimiento;

	@Column(name = "ciudad", nullable = false, length = 45)
	private String ciudad;
	
	@Column(name = "pais", nullable = false, length = 45)
	private String pais;
	
	@Column(name = "telefono", nullable = true)
	private long telefono;
	
	/*------RELACIONES------*/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tiene_aptitud",
            joinColumns = {@JoinColumn(name = "UUID_P", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "idAptitud", nullable = false, updatable = false)})
    private Set<Aptitud> aptitudes = new HashSet<Aptitud>(0);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "es_integrante",
            joinColumns = {@JoinColumn(name = "UUID_P", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "UUID_G", nullable = false, updatable = false)})
    private Set<Grupo> grupos = new HashSet<Grupo>(0);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "persona_tiene_tag",
            joinColumns = {@JoinColumn(name = "UUID_P", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "idTag", nullable = false, updatable = false)})
    private Set<Tag> tagsPersona = new HashSet<Tag>(0);

    public Persona(String nombre, String apellidos,String password, String email, long fechanacimiento,
                   String ciudad, String pais, long telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.email = email;
        this.fechanacimiento = fechanacimiento;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
    }

    /*------GETTERS/SETTERS------*/

    public int getPublicante_uuid() {
        return publicante_uuid;
    }

    public void setPublicante_uuid(int publicante_uuid) {
        this.publicante_uuid = publicante_uuid;
    }

    public Publicante getPublicante() {
        return publicante;
    }

    public void setPublicante(Publicante publicante) {
        this.publicante = publicante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(long fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public Set<Aptitud> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(Set<Aptitud> aptitudes) {
        this.aptitudes = aptitudes;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Set<Tag> getTagsPersona() {
        return tagsPersona;
    }

    public void setTagsPersona(Set<Tag> tagsPersona) {
        this.tagsPersona = tagsPersona;
    }

   /*public boolean addGrupo(Grupo g){
        boolean exito = false;
        try{
            grupos.add(g); exito=true;
        }catch(Exception ex){*//*nada, exito permanece false*//* }

        return exito;
    }
    */
     public boolean anadirTag(Tag tag){
        try {
            this.tagsPersona.add(tag);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean anadirGrupo(Grupo grupo){
        try {
            this.grupos.add(grupo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean anadirAptitud(Aptitud ap){
        try{
            this.aptitudes.add(ap);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
