package com.lekstreek.portal.service.criteria;

import com.lekstreek.portal.domain.enumeration.Geslacht;
import com.lekstreek.portal.domain.enumeration.RelatieType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.lekstreek.portal.domain.Relatie} entity. This class is used
 * in {@link com.lekstreek.portal.web.rest.RelatieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /relaties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RelatieCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Geslacht
     */
    public static class GeslachtFilter extends Filter<Geslacht> {

        public GeslachtFilter() {}

        public GeslachtFilter(GeslachtFilter filter) {
            super(filter);
        }

        @Override
        public GeslachtFilter copy() {
            return new GeslachtFilter(this);
        }
    }

    /**
     * Class for filtering RelatieType
     */
    public static class RelatieTypeFilter extends Filter<RelatieType> {

        public RelatieTypeFilter() {}

        public RelatieTypeFilter(RelatieTypeFilter filter) {
            super(filter);
        }

        @Override
        public RelatieTypeFilter copy() {
            return new RelatieTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter voornaam;

    private StringFilter achternaam;

    private StringFilter initialen;

    private StringFilter weergavenaam;

    private GeslachtFilter geslacht;

    private LocalDateFilter geboortedatum;

    private RelatieTypeFilter relatietype;

    private InstantFilter inschrijvingsdatum;

    private StringFilter straatnaam;

    private IntegerFilter huisnummer;

    private StringFilter postcode;

    private StringFilter woonplaats;

    private StringFilter land;

    private StringFilter email;

    private StringFilter email2;

    private IntegerFilter telefoonnummer;

    private IntegerFilter telefoonnummer2;

    private IntegerFilter telefoonnummer3;

    private StringFilter ibancode;

    private LongFilter knsbRelatienummer;

    private UUIDFilter rolId;

    public RelatieCriteria() {}

    public RelatieCriteria(RelatieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.voornaam = other.voornaam == null ? null : other.voornaam.copy();
        this.achternaam = other.achternaam == null ? null : other.achternaam.copy();
        this.initialen = other.initialen == null ? null : other.initialen.copy();
        this.weergavenaam = other.weergavenaam == null ? null : other.weergavenaam.copy();
        this.geslacht = other.geslacht == null ? null : other.geslacht.copy();
        this.geboortedatum = other.geboortedatum == null ? null : other.geboortedatum.copy();
        this.relatietype = other.relatietype == null ? null : other.relatietype.copy();
        this.inschrijvingsdatum = other.inschrijvingsdatum == null ? null : other.inschrijvingsdatum.copy();
        this.straatnaam = other.straatnaam == null ? null : other.straatnaam.copy();
        this.huisnummer = other.huisnummer == null ? null : other.huisnummer.copy();
        this.postcode = other.postcode == null ? null : other.postcode.copy();
        this.woonplaats = other.woonplaats == null ? null : other.woonplaats.copy();
        this.land = other.land == null ? null : other.land.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.email2 = other.email2 == null ? null : other.email2.copy();
        this.telefoonnummer = other.telefoonnummer == null ? null : other.telefoonnummer.copy();
        this.telefoonnummer2 = other.telefoonnummer2 == null ? null : other.telefoonnummer2.copy();
        this.telefoonnummer3 = other.telefoonnummer3 == null ? null : other.telefoonnummer3.copy();
        this.ibancode = other.ibancode == null ? null : other.ibancode.copy();
        this.knsbRelatienummer = other.knsbRelatienummer == null ? null : other.knsbRelatienummer.copy();
        this.rolId = other.rolId == null ? null : other.rolId.copy();
    }

    @Override
    public RelatieCriteria copy() {
        return new RelatieCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public UUIDFilter id() {
        if (id == null) {
            id = new UUIDFilter();
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public StringFilter getVoornaam() {
        return voornaam;
    }

    public StringFilter voornaam() {
        if (voornaam == null) {
            voornaam = new StringFilter();
        }
        return voornaam;
    }

    public void setVoornaam(StringFilter voornaam) {
        this.voornaam = voornaam;
    }

    public StringFilter getAchternaam() {
        return achternaam;
    }

    public StringFilter achternaam() {
        if (achternaam == null) {
            achternaam = new StringFilter();
        }
        return achternaam;
    }

    public void setAchternaam(StringFilter achternaam) {
        this.achternaam = achternaam;
    }

    public StringFilter getInitialen() {
        return initialen;
    }

    public StringFilter initialen() {
        if (initialen == null) {
            initialen = new StringFilter();
        }
        return initialen;
    }

    public void setInitialen(StringFilter initialen) {
        this.initialen = initialen;
    }

    public StringFilter getWeergavenaam() {
        return weergavenaam;
    }

    public StringFilter weergavenaam() {
        if (weergavenaam == null) {
            weergavenaam = new StringFilter();
        }
        return weergavenaam;
    }

    public void setWeergavenaam(StringFilter weergavenaam) {
        this.weergavenaam = weergavenaam;
    }

    public GeslachtFilter getGeslacht() {
        return geslacht;
    }

    public GeslachtFilter geslacht() {
        if (geslacht == null) {
            geslacht = new GeslachtFilter();
        }
        return geslacht;
    }

    public void setGeslacht(GeslachtFilter geslacht) {
        this.geslacht = geslacht;
    }

    public LocalDateFilter getGeboortedatum() {
        return geboortedatum;
    }

    public LocalDateFilter geboortedatum() {
        if (geboortedatum == null) {
            geboortedatum = new LocalDateFilter();
        }
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDateFilter geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public RelatieTypeFilter getRelatietype() {
        return relatietype;
    }

    public RelatieTypeFilter relatietype() {
        if (relatietype == null) {
            relatietype = new RelatieTypeFilter();
        }
        return relatietype;
    }

    public void setRelatietype(RelatieTypeFilter relatietype) {
        this.relatietype = relatietype;
    }

    public InstantFilter getInschrijvingsdatum() {
        return inschrijvingsdatum;
    }

    public InstantFilter inschrijvingsdatum() {
        if (inschrijvingsdatum == null) {
            inschrijvingsdatum = new InstantFilter();
        }
        return inschrijvingsdatum;
    }

    public void setInschrijvingsdatum(InstantFilter inschrijvingsdatum) {
        this.inschrijvingsdatum = inschrijvingsdatum;
    }

    public StringFilter getStraatnaam() {
        return straatnaam;
    }

    public StringFilter straatnaam() {
        if (straatnaam == null) {
            straatnaam = new StringFilter();
        }
        return straatnaam;
    }

    public void setStraatnaam(StringFilter straatnaam) {
        this.straatnaam = straatnaam;
    }

    public IntegerFilter getHuisnummer() {
        return huisnummer;
    }

    public IntegerFilter huisnummer() {
        if (huisnummer == null) {
            huisnummer = new IntegerFilter();
        }
        return huisnummer;
    }

    public void setHuisnummer(IntegerFilter huisnummer) {
        this.huisnummer = huisnummer;
    }

    public StringFilter getPostcode() {
        return postcode;
    }

    public StringFilter postcode() {
        if (postcode == null) {
            postcode = new StringFilter();
        }
        return postcode;
    }

    public void setPostcode(StringFilter postcode) {
        this.postcode = postcode;
    }

    public StringFilter getWoonplaats() {
        return woonplaats;
    }

    public StringFilter woonplaats() {
        if (woonplaats == null) {
            woonplaats = new StringFilter();
        }
        return woonplaats;
    }

    public void setWoonplaats(StringFilter woonplaats) {
        this.woonplaats = woonplaats;
    }

    public StringFilter getLand() {
        return land;
    }

    public StringFilter land() {
        if (land == null) {
            land = new StringFilter();
        }
        return land;
    }

    public void setLand(StringFilter land) {
        this.land = land;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getEmail2() {
        return email2;
    }

    public StringFilter email2() {
        if (email2 == null) {
            email2 = new StringFilter();
        }
        return email2;
    }

    public void setEmail2(StringFilter email2) {
        this.email2 = email2;
    }

    public IntegerFilter getTelefoonnummer() {
        return telefoonnummer;
    }

    public IntegerFilter telefoonnummer() {
        if (telefoonnummer == null) {
            telefoonnummer = new IntegerFilter();
        }
        return telefoonnummer;
    }

    public void setTelefoonnummer(IntegerFilter telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public IntegerFilter getTelefoonnummer2() {
        return telefoonnummer2;
    }

    public IntegerFilter telefoonnummer2() {
        if (telefoonnummer2 == null) {
            telefoonnummer2 = new IntegerFilter();
        }
        return telefoonnummer2;
    }

    public void setTelefoonnummer2(IntegerFilter telefoonnummer2) {
        this.telefoonnummer2 = telefoonnummer2;
    }

    public IntegerFilter getTelefoonnummer3() {
        return telefoonnummer3;
    }

    public IntegerFilter telefoonnummer3() {
        if (telefoonnummer3 == null) {
            telefoonnummer3 = new IntegerFilter();
        }
        return telefoonnummer3;
    }

    public void setTelefoonnummer3(IntegerFilter telefoonnummer3) {
        this.telefoonnummer3 = telefoonnummer3;
    }

    public StringFilter getIbancode() {
        return ibancode;
    }

    public StringFilter ibancode() {
        if (ibancode == null) {
            ibancode = new StringFilter();
        }
        return ibancode;
    }

    public void setIbancode(StringFilter ibancode) {
        this.ibancode = ibancode;
    }

    public LongFilter getKnsbRelatienummer() {
        return knsbRelatienummer;
    }

    public LongFilter knsbRelatienummer() {
        if (knsbRelatienummer == null) {
            knsbRelatienummer = new LongFilter();
        }
        return knsbRelatienummer;
    }

    public void setKnsbRelatienummer(LongFilter knsbRelatienummer) {
        this.knsbRelatienummer = knsbRelatienummer;
    }

    public UUIDFilter getRolId() {
        return rolId;
    }

    public UUIDFilter rolId() {
        if (rolId == null) {
            rolId = new UUIDFilter();
        }
        return rolId;
    }

    public void setRolId(UUIDFilter rolId) {
        this.rolId = rolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RelatieCriteria that = (RelatieCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(voornaam, that.voornaam) &&
            Objects.equals(achternaam, that.achternaam) &&
            Objects.equals(initialen, that.initialen) &&
            Objects.equals(weergavenaam, that.weergavenaam) &&
            Objects.equals(geslacht, that.geslacht) &&
            Objects.equals(geboortedatum, that.geboortedatum) &&
            Objects.equals(relatietype, that.relatietype) &&
            Objects.equals(inschrijvingsdatum, that.inschrijvingsdatum) &&
            Objects.equals(straatnaam, that.straatnaam) &&
            Objects.equals(huisnummer, that.huisnummer) &&
            Objects.equals(postcode, that.postcode) &&
            Objects.equals(woonplaats, that.woonplaats) &&
            Objects.equals(land, that.land) &&
            Objects.equals(email, that.email) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(telefoonnummer, that.telefoonnummer) &&
            Objects.equals(telefoonnummer2, that.telefoonnummer2) &&
            Objects.equals(telefoonnummer3, that.telefoonnummer3) &&
            Objects.equals(ibancode, that.ibancode) &&
            Objects.equals(knsbRelatienummer, that.knsbRelatienummer) &&
            Objects.equals(rolId, that.rolId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            voornaam,
            achternaam,
            initialen,
            weergavenaam,
            geslacht,
            geboortedatum,
            relatietype,
            inschrijvingsdatum,
            straatnaam,
            huisnummer,
            postcode,
            woonplaats,
            land,
            email,
            email2,
            telefoonnummer,
            telefoonnummer2,
            telefoonnummer3,
            ibancode,
            knsbRelatienummer,
            rolId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatieCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (voornaam != null ? "voornaam=" + voornaam + ", " : "") +
            (achternaam != null ? "achternaam=" + achternaam + ", " : "") +
            (initialen != null ? "initialen=" + initialen + ", " : "") +
            (weergavenaam != null ? "weergavenaam=" + weergavenaam + ", " : "") +
            (geslacht != null ? "geslacht=" + geslacht + ", " : "") +
            (geboortedatum != null ? "geboortedatum=" + geboortedatum + ", " : "") +
            (relatietype != null ? "relatietype=" + relatietype + ", " : "") +
            (inschrijvingsdatum != null ? "inschrijvingsdatum=" + inschrijvingsdatum + ", " : "") +
            (straatnaam != null ? "straatnaam=" + straatnaam + ", " : "") +
            (huisnummer != null ? "huisnummer=" + huisnummer + ", " : "") +
            (postcode != null ? "postcode=" + postcode + ", " : "") +
            (woonplaats != null ? "woonplaats=" + woonplaats + ", " : "") +
            (land != null ? "land=" + land + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (email2 != null ? "email2=" + email2 + ", " : "") +
            (telefoonnummer != null ? "telefoonnummer=" + telefoonnummer + ", " : "") +
            (telefoonnummer2 != null ? "telefoonnummer2=" + telefoonnummer2 + ", " : "") +
            (telefoonnummer3 != null ? "telefoonnummer3=" + telefoonnummer3 + ", " : "") +
            (ibancode != null ? "ibancode=" + ibancode + ", " : "") +
            (knsbRelatienummer != null ? "knsbRelatienummer=" + knsbRelatienummer + ", " : "") +
            (rolId != null ? "rolId=" + rolId + ", " : "") +
            "}";
    }
}
