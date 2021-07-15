package com.lekstreek.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lekstreek.portal.domain.enumeration.Geslacht;
import com.lekstreek.portal.domain.enumeration.RelatieType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Relatie.
 */
@Entity
@Table(name = "relatie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Relatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "initialen")
    private String initialen;

    @Column(name = "weergavenaam")
    private String weergavenaam;

    @Enumerated(EnumType.STRING)
    @Column(name = "geslacht")
    private Geslacht geslacht;

    @Column(name = "geboortedatum")
    private LocalDate geboortedatum;

    @Enumerated(EnumType.STRING)
    @Column(name = "relatietype")
    private RelatieType relatietype;

    @Column(name = "inschrijvingsdatum")
    private Instant inschrijvingsdatum;

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

    @Column(name = "email")
    private String email;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "telefoonnummer")
    private Integer telefoonnummer;

    @Column(name = "telefoonnummer_2")
    private Integer telefoonnummer2;

    @Column(name = "telefoonnummer_3")
    private Integer telefoonnummer3;

    @Column(name = "ibancode")
    private String ibancode;

    @Column(name = "knsb_relatienummer")
    private Long knsbRelatienummer;

    @Lob
    @Column(name = "pasfoto")
    private byte[] pasfoto;

    @Column(name = "pasfoto_content_type")
    private String pasfotoContentType;

    @Lob
    @Column(name = "privacy_verklaring")
    private byte[] privacyVerklaring;

    @Column(name = "privacy_verklaring_content_type")
    private String privacyVerklaringContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rel_relatie__rol", joinColumns = @JoinColumn(name = "relatie_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @JsonIgnoreProperties(value = { "relaties" }, allowSetters = true)
    private Set<Rol> rols = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Relatie id(UUID id) {
        this.id = id;
        return this;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public Relatie voornaam(String voornaam) {
        this.voornaam = voornaam;
        return this;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public Relatie achternaam(String achternaam) {
        this.achternaam = achternaam;
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getInitialen() {
        return this.initialen;
    }

    public Relatie initialen(String initialen) {
        this.initialen = initialen;
        return this;
    }

    public void setInitialen(String initialen) {
        this.initialen = initialen;
    }

    public String getWeergavenaam() {
        return this.weergavenaam;
    }

    public Relatie weergavenaam(String weergavenaam) {
        this.weergavenaam = weergavenaam;
        return this;
    }

    public void setWeergavenaam(String weergavenaam) {
        this.weergavenaam = weergavenaam;
    }

    public Geslacht getGeslacht() {
        return this.geslacht;
    }

    public Relatie geslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
        return this;
    }

    public void setGeslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
    }

    public LocalDate getGeboortedatum() {
        return this.geboortedatum;
    }

    public Relatie geboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
        return this;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public RelatieType getRelatietype() {
        return this.relatietype;
    }

    public Relatie relatietype(RelatieType relatietype) {
        this.relatietype = relatietype;
        return this;
    }

    public void setRelatietype(RelatieType relatietype) {
        this.relatietype = relatietype;
    }

    public Instant getInschrijvingsdatum() {
        return this.inschrijvingsdatum;
    }

    public Relatie inschrijvingsdatum(Instant inschrijvingsdatum) {
        this.inschrijvingsdatum = inschrijvingsdatum;
        return this;
    }

    public void setInschrijvingsdatum(Instant inschrijvingsdatum) {
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public Relatie straatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
        return this;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public Integer getHuisnummer() {
        return this.huisnummer;
    }

    public Relatie huisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
        return this;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Relatie postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public Relatie woonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
        return this;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getLand() {
        return this.land;
    }

    public Relatie land(String land) {
        this.land = land;
        return this;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getEmail() {
        return this.email;
    }

    public Relatie email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return this.email2;
    }

    public Relatie email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Integer getTelefoonnummer() {
        return this.telefoonnummer;
    }

    public Relatie telefoonnummer(Integer telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
        return this;
    }

    public void setTelefoonnummer(Integer telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public Integer getTelefoonnummer2() {
        return this.telefoonnummer2;
    }

    public Relatie telefoonnummer2(Integer telefoonnummer2) {
        this.telefoonnummer2 = telefoonnummer2;
        return this;
    }

    public void setTelefoonnummer2(Integer telefoonnummer2) {
        this.telefoonnummer2 = telefoonnummer2;
    }

    public Integer getTelefoonnummer3() {
        return this.telefoonnummer3;
    }

    public Relatie telefoonnummer3(Integer telefoonnummer3) {
        this.telefoonnummer3 = telefoonnummer3;
        return this;
    }

    public void setTelefoonnummer3(Integer telefoonnummer3) {
        this.telefoonnummer3 = telefoonnummer3;
    }

    public String getIbancode() {
        return this.ibancode;
    }

    public Relatie ibancode(String ibancode) {
        this.ibancode = ibancode;
        return this;
    }

    public void setIbancode(String ibancode) {
        this.ibancode = ibancode;
    }

    public Long getKnsbRelatienummer() {
        return this.knsbRelatienummer;
    }

    public Relatie knsbRelatienummer(Long knsbRelatienummer) {
        this.knsbRelatienummer = knsbRelatienummer;
        return this;
    }

    public void setKnsbRelatienummer(Long knsbRelatienummer) {
        this.knsbRelatienummer = knsbRelatienummer;
    }

    public byte[] getPasfoto() {
        return this.pasfoto;
    }

    public Relatie pasfoto(byte[] pasfoto) {
        this.pasfoto = pasfoto;
        return this;
    }

    public void setPasfoto(byte[] pasfoto) {
        this.pasfoto = pasfoto;
    }

    public String getPasfotoContentType() {
        return this.pasfotoContentType;
    }

    public Relatie pasfotoContentType(String pasfotoContentType) {
        this.pasfotoContentType = pasfotoContentType;
        return this;
    }

    public void setPasfotoContentType(String pasfotoContentType) {
        this.pasfotoContentType = pasfotoContentType;
    }

    public byte[] getPrivacyVerklaring() {
        return this.privacyVerklaring;
    }

    public Relatie privacyVerklaring(byte[] privacyVerklaring) {
        this.privacyVerklaring = privacyVerklaring;
        return this;
    }

    public void setPrivacyVerklaring(byte[] privacyVerklaring) {
        this.privacyVerklaring = privacyVerklaring;
    }

    public String getPrivacyVerklaringContentType() {
        return this.privacyVerklaringContentType;
    }

    public Relatie privacyVerklaringContentType(String privacyVerklaringContentType) {
        this.privacyVerklaringContentType = privacyVerklaringContentType;
        return this;
    }

    public void setPrivacyVerklaringContentType(String privacyVerklaringContentType) {
        this.privacyVerklaringContentType = privacyVerklaringContentType;
    }

    public Set<Rol> getRols() {
        return this.rols;
    }

    public Relatie rols(Set<Rol> rols) {
        this.setRols(rols);
        return this;
    }

    public Relatie addRol(Rol rol) {
        this.rols.add(rol);
        rol.getRelaties().add(this);
        return this;
    }

    public Relatie removeRol(Rol rol) {
        this.rols.remove(rol);
        rol.getRelaties().remove(this);
        return this;
    }

    public void setRols(Set<Rol> rols) {
        this.rols = rols;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relatie)) {
            return false;
        }
        return id != null && id.equals(((Relatie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relatie{" +
            "id=" + getId() +
            ", voornaam='" + getVoornaam() + "'" +
            ", achternaam='" + getAchternaam() + "'" +
            ", initialen='" + getInitialen() + "'" +
            ", weergavenaam='" + getWeergavenaam() + "'" +
            ", geslacht='" + getGeslacht() + "'" +
            ", geboortedatum='" + getGeboortedatum() + "'" +
            ", relatietype='" + getRelatietype() + "'" +
            ", inschrijvingsdatum='" + getInschrijvingsdatum() + "'" +
            ", straatnaam='" + getStraatnaam() + "'" +
            ", huisnummer=" + getHuisnummer() +
            ", postcode='" + getPostcode() + "'" +
            ", woonplaats='" + getWoonplaats() + "'" +
            ", land='" + getLand() + "'" +
            ", email='" + getEmail() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", telefoonnummer=" + getTelefoonnummer() +
            ", telefoonnummer2=" + getTelefoonnummer2() +
            ", telefoonnummer3=" + getTelefoonnummer3() +
            ", ibancode='" + getIbancode() + "'" +
            ", knsbRelatienummer=" + getKnsbRelatienummer() +
            ", pasfoto='" + getPasfoto() + "'" +
            ", pasfotoContentType='" + getPasfotoContentType() + "'" +
            ", privacyVerklaring='" + getPrivacyVerklaring() + "'" +
            ", privacyVerklaringContentType='" + getPrivacyVerklaringContentType() + "'" +
            "}";
    }
}
