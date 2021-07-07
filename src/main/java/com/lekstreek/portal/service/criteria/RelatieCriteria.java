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

    private StringFilter voorNaam;

    private StringFilter achterNaam;

    private StringFilter initialen;

    private StringFilter weergaveNaam;

    private GeslachtFilter geslacht;

    private LocalDateFilter geboorteDatum;

    private RelatieTypeFilter relatieType;

    private InstantFilter inschrijvingsDatum;

    private StringFilter straatNaam;

    private IntegerFilter huisNummer;

    private StringFilter postCode;

    private StringFilter woonPlaats;

    private StringFilter land;

    private StringFilter email;

    private StringFilter email2;

    private IntegerFilter telefoonNummer;

    private IntegerFilter telefoonNummer2;

    private IntegerFilter telefoonNummer3;

    private StringFilter ibanCode;

    private LongFilter knsbRelatieNummer;

    private UUIDFilter rolId;

    public RelatieCriteria() {}

    public RelatieCriteria(RelatieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.voorNaam = other.voorNaam == null ? null : other.voorNaam.copy();
        this.achterNaam = other.achterNaam == null ? null : other.achterNaam.copy();
        this.initialen = other.initialen == null ? null : other.initialen.copy();
        this.weergaveNaam = other.weergaveNaam == null ? null : other.weergaveNaam.copy();
        this.geslacht = other.geslacht == null ? null : other.geslacht.copy();
        this.geboorteDatum = other.geboorteDatum == null ? null : other.geboorteDatum.copy();
        this.relatieType = other.relatieType == null ? null : other.relatieType.copy();
        this.inschrijvingsDatum = other.inschrijvingsDatum == null ? null : other.inschrijvingsDatum.copy();
        this.straatNaam = other.straatNaam == null ? null : other.straatNaam.copy();
        this.huisNummer = other.huisNummer == null ? null : other.huisNummer.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.woonPlaats = other.woonPlaats == null ? null : other.woonPlaats.copy();
        this.land = other.land == null ? null : other.land.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.email2 = other.email2 == null ? null : other.email2.copy();
        this.telefoonNummer = other.telefoonNummer == null ? null : other.telefoonNummer.copy();
        this.telefoonNummer2 = other.telefoonNummer2 == null ? null : other.telefoonNummer2.copy();
        this.telefoonNummer3 = other.telefoonNummer3 == null ? null : other.telefoonNummer3.copy();
        this.ibanCode = other.ibanCode == null ? null : other.ibanCode.copy();
        this.knsbRelatieNummer = other.knsbRelatieNummer == null ? null : other.knsbRelatieNummer.copy();
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

    public StringFilter getVoorNaam() {
        return voorNaam;
    }

    public StringFilter voorNaam() {
        if (voorNaam == null) {
            voorNaam = new StringFilter();
        }
        return voorNaam;
    }

    public void setVoorNaam(StringFilter voorNaam) {
        this.voorNaam = voorNaam;
    }

    public StringFilter getAchterNaam() {
        return achterNaam;
    }

    public StringFilter achterNaam() {
        if (achterNaam == null) {
            achterNaam = new StringFilter();
        }
        return achterNaam;
    }

    public void setAchterNaam(StringFilter achterNaam) {
        this.achterNaam = achterNaam;
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

    public StringFilter getWeergaveNaam() {
        return weergaveNaam;
    }

    public StringFilter weergaveNaam() {
        if (weergaveNaam == null) {
            weergaveNaam = new StringFilter();
        }
        return weergaveNaam;
    }

    public void setWeergaveNaam(StringFilter weergaveNaam) {
        this.weergaveNaam = weergaveNaam;
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

    public LocalDateFilter getGeboorteDatum() {
        return geboorteDatum;
    }

    public LocalDateFilter geboorteDatum() {
        if (geboorteDatum == null) {
            geboorteDatum = new LocalDateFilter();
        }
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDateFilter geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public RelatieTypeFilter getRelatieType() {
        return relatieType;
    }

    public RelatieTypeFilter relatieType() {
        if (relatieType == null) {
            relatieType = new RelatieTypeFilter();
        }
        return relatieType;
    }

    public void setRelatieType(RelatieTypeFilter relatieType) {
        this.relatieType = relatieType;
    }

    public InstantFilter getInschrijvingsDatum() {
        return inschrijvingsDatum;
    }

    public InstantFilter inschrijvingsDatum() {
        if (inschrijvingsDatum == null) {
            inschrijvingsDatum = new InstantFilter();
        }
        return inschrijvingsDatum;
    }

    public void setInschrijvingsDatum(InstantFilter inschrijvingsDatum) {
        this.inschrijvingsDatum = inschrijvingsDatum;
    }

    public StringFilter getStraatNaam() {
        return straatNaam;
    }

    public StringFilter straatNaam() {
        if (straatNaam == null) {
            straatNaam = new StringFilter();
        }
        return straatNaam;
    }

    public void setStraatNaam(StringFilter straatNaam) {
        this.straatNaam = straatNaam;
    }

    public IntegerFilter getHuisNummer() {
        return huisNummer;
    }

    public IntegerFilter huisNummer() {
        if (huisNummer == null) {
            huisNummer = new IntegerFilter();
        }
        return huisNummer;
    }

    public void setHuisNummer(IntegerFilter huisNummer) {
        this.huisNummer = huisNummer;
    }

    public StringFilter getPostCode() {
        return postCode;
    }

    public StringFilter postCode() {
        if (postCode == null) {
            postCode = new StringFilter();
        }
        return postCode;
    }

    public void setPostCode(StringFilter postCode) {
        this.postCode = postCode;
    }

    public StringFilter getWoonPlaats() {
        return woonPlaats;
    }

    public StringFilter woonPlaats() {
        if (woonPlaats == null) {
            woonPlaats = new StringFilter();
        }
        return woonPlaats;
    }

    public void setWoonPlaats(StringFilter woonPlaats) {
        this.woonPlaats = woonPlaats;
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

    public IntegerFilter getTelefoonNummer() {
        return telefoonNummer;
    }

    public IntegerFilter telefoonNummer() {
        if (telefoonNummer == null) {
            telefoonNummer = new IntegerFilter();
        }
        return telefoonNummer;
    }

    public void setTelefoonNummer(IntegerFilter telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public IntegerFilter getTelefoonNummer2() {
        return telefoonNummer2;
    }

    public IntegerFilter telefoonNummer2() {
        if (telefoonNummer2 == null) {
            telefoonNummer2 = new IntegerFilter();
        }
        return telefoonNummer2;
    }

    public void setTelefoonNummer2(IntegerFilter telefoonNummer2) {
        this.telefoonNummer2 = telefoonNummer2;
    }

    public IntegerFilter getTelefoonNummer3() {
        return telefoonNummer3;
    }

    public IntegerFilter telefoonNummer3() {
        if (telefoonNummer3 == null) {
            telefoonNummer3 = new IntegerFilter();
        }
        return telefoonNummer3;
    }

    public void setTelefoonNummer3(IntegerFilter telefoonNummer3) {
        this.telefoonNummer3 = telefoonNummer3;
    }

    public StringFilter getIbanCode() {
        return ibanCode;
    }

    public StringFilter ibanCode() {
        if (ibanCode == null) {
            ibanCode = new StringFilter();
        }
        return ibanCode;
    }

    public void setIbanCode(StringFilter ibanCode) {
        this.ibanCode = ibanCode;
    }

    public LongFilter getKnsbRelatieNummer() {
        return knsbRelatieNummer;
    }

    public LongFilter knsbRelatieNummer() {
        if (knsbRelatieNummer == null) {
            knsbRelatieNummer = new LongFilter();
        }
        return knsbRelatieNummer;
    }

    public void setKnsbRelatieNummer(LongFilter knsbRelatieNummer) {
        this.knsbRelatieNummer = knsbRelatieNummer;
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
            Objects.equals(voorNaam, that.voorNaam) &&
            Objects.equals(achterNaam, that.achterNaam) &&
            Objects.equals(initialen, that.initialen) &&
            Objects.equals(weergaveNaam, that.weergaveNaam) &&
            Objects.equals(geslacht, that.geslacht) &&
            Objects.equals(geboorteDatum, that.geboorteDatum) &&
            Objects.equals(relatieType, that.relatieType) &&
            Objects.equals(inschrijvingsDatum, that.inschrijvingsDatum) &&
            Objects.equals(straatNaam, that.straatNaam) &&
            Objects.equals(huisNummer, that.huisNummer) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(woonPlaats, that.woonPlaats) &&
            Objects.equals(land, that.land) &&
            Objects.equals(email, that.email) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(telefoonNummer, that.telefoonNummer) &&
            Objects.equals(telefoonNummer2, that.telefoonNummer2) &&
            Objects.equals(telefoonNummer3, that.telefoonNummer3) &&
            Objects.equals(ibanCode, that.ibanCode) &&
            Objects.equals(knsbRelatieNummer, that.knsbRelatieNummer) &&
            Objects.equals(rolId, that.rolId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            voorNaam,
            achterNaam,
            initialen,
            weergaveNaam,
            geslacht,
            geboorteDatum,
            relatieType,
            inschrijvingsDatum,
            straatNaam,
            huisNummer,
            postCode,
            woonPlaats,
            land,
            email,
            email2,
            telefoonNummer,
            telefoonNummer2,
            telefoonNummer3,
            ibanCode,
            knsbRelatieNummer,
            rolId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatieCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (voorNaam != null ? "voorNaam=" + voorNaam + ", " : "") +
            (achterNaam != null ? "achterNaam=" + achterNaam + ", " : "") +
            (initialen != null ? "initialen=" + initialen + ", " : "") +
            (weergaveNaam != null ? "weergaveNaam=" + weergaveNaam + ", " : "") +
            (geslacht != null ? "geslacht=" + geslacht + ", " : "") +
            (geboorteDatum != null ? "geboorteDatum=" + geboorteDatum + ", " : "") +
            (relatieType != null ? "relatieType=" + relatieType + ", " : "") +
            (inschrijvingsDatum != null ? "inschrijvingsDatum=" + inschrijvingsDatum + ", " : "") +
            (straatNaam != null ? "straatNaam=" + straatNaam + ", " : "") +
            (huisNummer != null ? "huisNummer=" + huisNummer + ", " : "") +
            (postCode != null ? "postCode=" + postCode + ", " : "") +
            (woonPlaats != null ? "woonPlaats=" + woonPlaats + ", " : "") +
            (land != null ? "land=" + land + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (email2 != null ? "email2=" + email2 + ", " : "") +
            (telefoonNummer != null ? "telefoonNummer=" + telefoonNummer + ", " : "") +
            (telefoonNummer2 != null ? "telefoonNummer2=" + telefoonNummer2 + ", " : "") +
            (telefoonNummer3 != null ? "telefoonNummer3=" + telefoonNummer3 + ", " : "") +
            (ibanCode != null ? "ibanCode=" + ibanCode + ", " : "") +
            (knsbRelatieNummer != null ? "knsbRelatieNummer=" + knsbRelatieNummer + ", " : "") +
            (rolId != null ? "rolId=" + rolId + ", " : "") +
            "}";
    }
}
