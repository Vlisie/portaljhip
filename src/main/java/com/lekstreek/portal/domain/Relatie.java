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

    @Column(name = "voor_naam")
    private String voorNaam;

    @Column(name = "achter_naam")
    private String achterNaam;

    @Column(name = "initialen")
    private String initialen;

    @Column(name = "weergave_naam")
    private String weergaveNaam;

    @Enumerated(EnumType.STRING)
    @Column(name = "geslacht")
    private Geslacht geslacht;

    @Column(name = "geboorte_datum")
    private LocalDate geboorteDatum;

    @Enumerated(EnumType.STRING)
    @Column(name = "relatie_type")
    private RelatieType relatieType;

    @Column(name = "inschrijvings_datum")
    private Instant inschrijvingsDatum;

    @Column(name = "straat_naam")
    private String straatNaam;

    @Column(name = "huis_nummer")
    private Integer huisNummer;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "woon_plaats")
    private String woonPlaats;

    @Column(name = "land")
    private String land;

    @Column(name = "email")
    private String email;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "telefoon_nummer")
    private Integer telefoonNummer;

    @Column(name = "telefoon_nummer_2")
    private Integer telefoonNummer2;

    @Column(name = "telefoon_nummer_3")
    private Integer telefoonNummer3;

    @Column(name = "iban_code")
    private String ibanCode;

    @Column(name = "knsb_relatie_nummer")
    private Long knsbRelatieNummer;

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

    public String getVoorNaam() {
        return this.voorNaam;
    }

    public Relatie voorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
        return this;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return this.achterNaam;
    }

    public Relatie achterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
        return this;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
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

    public String getWeergaveNaam() {
        return this.weergaveNaam;
    }

    public Relatie weergaveNaam(String weergaveNaam) {
        this.weergaveNaam = weergaveNaam;
        return this;
    }

    public void setWeergaveNaam(String weergaveNaam) {
        this.weergaveNaam = weergaveNaam;
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

    public LocalDate getGeboorteDatum() {
        return this.geboorteDatum;
    }

    public Relatie geboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
        return this;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public RelatieType getRelatieType() {
        return this.relatieType;
    }

    public Relatie relatieType(RelatieType relatieType) {
        this.relatieType = relatieType;
        return this;
    }

    public void setRelatieType(RelatieType relatieType) {
        this.relatieType = relatieType;
    }

    public Instant getInschrijvingsDatum() {
        return this.inschrijvingsDatum;
    }

    public Relatie inschrijvingsDatum(Instant inschrijvingsDatum) {
        this.inschrijvingsDatum = inschrijvingsDatum;
        return this;
    }

    public void setInschrijvingsDatum(Instant inschrijvingsDatum) {
        this.inschrijvingsDatum = inschrijvingsDatum;
    }

    public String getStraatNaam() {
        return this.straatNaam;
    }

    public Relatie straatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
        return this;
    }

    public void setStraatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
    }

    public Integer getHuisNummer() {
        return this.huisNummer;
    }

    public Relatie huisNummer(Integer huisNummer) {
        this.huisNummer = huisNummer;
        return this;
    }

    public void setHuisNummer(Integer huisNummer) {
        this.huisNummer = huisNummer;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public Relatie postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getWoonPlaats() {
        return this.woonPlaats;
    }

    public Relatie woonPlaats(String woonPlaats) {
        this.woonPlaats = woonPlaats;
        return this;
    }

    public void setWoonPlaats(String woonPlaats) {
        this.woonPlaats = woonPlaats;
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

    public Integer getTelefoonNummer() {
        return this.telefoonNummer;
    }

    public Relatie telefoonNummer(Integer telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
        return this;
    }

    public void setTelefoonNummer(Integer telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public Integer getTelefoonNummer2() {
        return this.telefoonNummer2;
    }

    public Relatie telefoonNummer2(Integer telefoonNummer2) {
        this.telefoonNummer2 = telefoonNummer2;
        return this;
    }

    public void setTelefoonNummer2(Integer telefoonNummer2) {
        this.telefoonNummer2 = telefoonNummer2;
    }

    public Integer getTelefoonNummer3() {
        return this.telefoonNummer3;
    }

    public Relatie telefoonNummer3(Integer telefoonNummer3) {
        this.telefoonNummer3 = telefoonNummer3;
        return this;
    }

    public void setTelefoonNummer3(Integer telefoonNummer3) {
        this.telefoonNummer3 = telefoonNummer3;
    }

    public String getIbanCode() {
        return this.ibanCode;
    }

    public Relatie ibanCode(String ibanCode) {
        this.ibanCode = ibanCode;
        return this;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

    public Long getKnsbRelatieNummer() {
        return this.knsbRelatieNummer;
    }

    public Relatie knsbRelatieNummer(Long knsbRelatieNummer) {
        this.knsbRelatieNummer = knsbRelatieNummer;
        return this;
    }

    public void setKnsbRelatieNummer(Long knsbRelatieNummer) {
        this.knsbRelatieNummer = knsbRelatieNummer;
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
            ", voorNaam='" + getVoorNaam() + "'" +
            ", achterNaam='" + getAchterNaam() + "'" +
            ", initialen='" + getInitialen() + "'" +
            ", weergaveNaam='" + getWeergaveNaam() + "'" +
            ", geslacht='" + getGeslacht() + "'" +
            ", geboorteDatum='" + getGeboorteDatum() + "'" +
            ", relatieType='" + getRelatieType() + "'" +
            ", inschrijvingsDatum='" + getInschrijvingsDatum() + "'" +
            ", straatNaam='" + getStraatNaam() + "'" +
            ", huisNummer=" + getHuisNummer() +
            ", postCode='" + getPostCode() + "'" +
            ", woonPlaats='" + getWoonPlaats() + "'" +
            ", land='" + getLand() + "'" +
            ", email='" + getEmail() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", telefoonNummer=" + getTelefoonNummer() +
            ", telefoonNummer2=" + getTelefoonNummer2() +
            ", telefoonNummer3=" + getTelefoonNummer3() +
            ", ibanCode='" + getIbanCode() + "'" +
            ", knsbRelatieNummer=" + getKnsbRelatieNummer() +
            ", pasfoto='" + getPasfoto() + "'" +
            ", pasfotoContentType='" + getPasfotoContentType() + "'" +
            ", privacyVerklaring='" + getPrivacyVerklaring() + "'" +
            ", privacyVerklaringContentType='" + getPrivacyVerklaringContentType() + "'" +
            "}";
    }
}
