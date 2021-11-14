package com.lekstreek.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
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
    @Column(name = "id")
    private UUID id;

    @Column(name = "relatie")
    private UUID relatie;

    @Column(name = "rolnaam")
    private String rolnaam;

    @Column(name = "jeugdschaatsen")
    private Boolean jeugdschaatsen;

    @Column(name = "startdatum_rol")
    private Instant startdatumRol;

    @Column(name = "einddatum_rol")
    private Instant einddatumRol;

    @ManyToOne
    @JsonIgnoreProperties(value = { "adres", "rols" }, allowSetters = true)
    private Relatie relatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Rol id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRelatie() {
        return this.relatie;
    }

    public Rol relatie(UUID relatie) {
        this.setRelatie(relatie);
        return this;
    }

    public void setRelatie(UUID relatie) {
        this.relatie = relatie;
    }

    public String getRolnaam() {
        return this.rolnaam;
    }

    public Rol rolnaam(String rolnaam) {
        this.setRolnaam(rolnaam);
        return this;
    }

    public void setRolnaam(String rolnaam) {
        this.rolnaam = rolnaam;
    }

    public Boolean getJeugdschaatsen() {
        return this.jeugdschaatsen;
    }

    public Rol jeugdschaatsen(Boolean jeugdschaatsen) {
        this.setJeugdschaatsen(jeugdschaatsen);
        return this;
    }

    public void setJeugdschaatsen(Boolean jeugdschaatsen) {
        this.jeugdschaatsen = jeugdschaatsen;
    }

    public Instant getStartdatumRol() {
        return this.startdatumRol;
    }

    public Rol startdatumRol(Instant startdatumRol) {
        this.setStartdatumRol(startdatumRol);
        return this;
    }

    public void setStartdatumRol(Instant startdatumRol) {
        this.startdatumRol = startdatumRol;
    }

    public Instant getEinddatumRol() {
        return this.einddatumRol;
    }

    public Rol einddatumRol(Instant einddatumRol) {
        this.setEinddatumRol(einddatumRol);
        return this;
    }

    public void setEinddatumRol(Instant einddatumRol) {
        this.einddatumRol = einddatumRol;
    }

    public Relatie getRelatie() {
        return this.relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Rol relatie(Relatie relatie) {
        this.setRelatie(relatie);
        return this;
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
            ", relatie='" + getRelatie() + "'" +
            ", rolnaam='" + getRolnaam() + "'" +
            ", jeugdschaatsen='" + getJeugdschaatsen() + "'" +
            ", startdatumRol='" + getStartdatumRol() + "'" +
            ", einddatumRol='" + getEinddatumRol() + "'" +
            "}";
    }
}
