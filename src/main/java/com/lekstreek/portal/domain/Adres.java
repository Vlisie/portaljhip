package com.lekstreek.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adres.
 */
@Entity
@Table(name = "adres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "straatnaam")
    private String straatnaam;

    @Column(name = "huisnummer")
    private Integer huisnummer;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "woonplaats")
    private String woonplaats;

    @Column(name = "land")
    private String land;

    @ManyToOne
    @JsonIgnoreProperties(value = { "adres", "rols" }, allowSetters = true)
    private Relatie relatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Adres id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public Adres straatnaam(String straatnaam) {
        this.setStraatnaam(straatnaam);
        return this;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public Integer getHuisnummer() {
        return this.huisnummer;
    }

    public Adres huisnummer(Integer huisnummer) {
        this.setHuisnummer(huisnummer);
        return this;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Adres postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public Adres woonplaats(String woonplaats) {
        this.setWoonplaats(woonplaats);
        return this;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getLand() {
        return this.land;
    }

    public Adres land(String land) {
        this.setLand(land);
        return this;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Relatie getRelatie() {
        return this.relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Adres relatie(Relatie relatie) {
        this.setRelatie(relatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adres)) {
            return false;
        }
        return id != null && id.equals(((Adres) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adres{" +
            "id=" + getId() +
            ", straatnaam='" + getStraatnaam() + "'" +
            ", huisnummer=" + getHuisnummer() +
            ", postcode='" + getPostcode() + "'" +
            ", woonplaats='" + getWoonplaats() + "'" +
            ", land='" + getLand() + "'" +
            "}";
    }
}
