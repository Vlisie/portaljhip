package com.lekstreek.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rol.
 */
@Entity
@Table(name = "rol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "rol_naam")
    private String rolNaam;

    @Column(name = "jeugdschaatsen")
    private Boolean jeugdschaatsen;

    @Column(name = "start_datum_rol")
    private Instant startDatumRol;

    @Column(name = "eind_datum_rol")
    private Instant eindDatumRol;

    @ManyToMany(mappedBy = "rols")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rols" }, allowSetters = true)
    private Set<Relatie> relaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Rol id(UUID id) {
        this.id = id;
        return this;
    }

    public String getRolNaam() {
        return this.rolNaam;
    }

    public Rol rolNaam(String rolNaam) {
        this.rolNaam = rolNaam;
        return this;
    }

    public void setRolNaam(String rolNaam) {
        this.rolNaam = rolNaam;
    }

    public Boolean getJeugdschaatsen() {
        return this.jeugdschaatsen;
    }

    public Rol jeugdschaatsen(Boolean jeugdschaatsen) {
        this.jeugdschaatsen = jeugdschaatsen;
        return this;
    }

    public void setJeugdschaatsen(Boolean jeugdschaatsen) {
        this.jeugdschaatsen = jeugdschaatsen;
    }

    public Instant getStartDatumRol() {
        return this.startDatumRol;
    }

    public Rol startDatumRol(Instant startDatumRol) {
        this.startDatumRol = startDatumRol;
        return this;
    }

    public void setStartDatumRol(Instant startDatumRol) {
        this.startDatumRol = startDatumRol;
    }

    public Instant getEindDatumRol() {
        return this.eindDatumRol;
    }

    public Rol eindDatumRol(Instant eindDatumRol) {
        this.eindDatumRol = eindDatumRol;
        return this;
    }

    public void setEindDatumRol(Instant eindDatumRol) {
        this.eindDatumRol = eindDatumRol;
    }

    public Set<Relatie> getRelaties() {
        return this.relaties;
    }

    public Rol relaties(Set<Relatie> relaties) {
        this.setRelaties(relaties);
        return this;
    }

    public Rol addRelatie(Relatie relatie) {
        this.relaties.add(relatie);
        relatie.getRols().add(this);
        return this;
    }

    public Rol removeRelatie(Relatie relatie) {
        this.relaties.remove(relatie);
        relatie.getRols().remove(this);
        return this;
    }

    public void setRelaties(Set<Relatie> relaties) {
        if (this.relaties != null) {
            this.relaties.forEach(i -> i.removeRol(this));
        }
        if (relaties != null) {
            relaties.forEach(i -> i.addRol(this));
        }
        this.relaties = relaties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rol)) {
            return false;
        }
        return id != null && id.equals(((Rol) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rol{" +
            "id=" + getId() +
            ", rolNaam='" + getRolNaam() + "'" +
            ", jeugdschaatsen='" + getJeugdschaatsen() + "'" +
            ", startDatumRol='" + getStartDatumRol() + "'" +
            ", eindDatumRol='" + getEindDatumRol() + "'" +
            "}";
    }
}
