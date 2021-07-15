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

    @Column(name = "rolnaam")
    private String rolnaam;

    @Column(name = "jeugdschaatsen")
    private Boolean jeugdschaatsen;

    @Column(name = "startdatum_rol")
    private Instant startdatumRol;

    @Column(name = "einddatum_rol")
    private Instant einddatumRol;

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

    public String getRolnaam() {
        return this.rolnaam;
    }

    public Rol rolnaam(String rolnaam) {
        this.rolnaam = rolnaam;
        return this;
    }

    public void setRolnaam(String rolnaam) {
        this.rolnaam = rolnaam;
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

    public Instant getStartdatumRol() {
        return this.startdatumRol;
    }

    public Rol startdatumRol(Instant startdatumRol) {
        this.startdatumRol = startdatumRol;
        return this;
    }

    public void setStartdatumRol(Instant startdatumRol) {
        this.startdatumRol = startdatumRol;
    }

    public Instant getEinddatumRol() {
        return this.einddatumRol;
    }

    public Rol einddatumRol(Instant einddatumRol) {
        this.einddatumRol = einddatumRol;
        return this;
    }

    public void setEinddatumRol(Instant einddatumRol) {
        this.einddatumRol = einddatumRol;
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
            ", rolnaam='" + getRolnaam() + "'" +
            ", jeugdschaatsen='" + getJeugdschaatsen() + "'" +
            ", startdatumRol='" + getStartdatumRol() + "'" +
            ", einddatumRol='" + getEinddatumRol() + "'" +
            "}";
    }
}
