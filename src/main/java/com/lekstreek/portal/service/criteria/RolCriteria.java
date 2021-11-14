package com.lekstreek.portal.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.lekstreek.portal.domain.Rol} entity. This class is used
 * in {@link com.lekstreek.portal.web.rest.RolResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rols?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RolCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private UUIDFilter relatie;

    private StringFilter rolnaam;

    private BooleanFilter jeugdschaatsen;

    private InstantFilter startdatumRol;

    private InstantFilter einddatumRol;

    private UUIDFilter relatieId;

    private Boolean distinct;

    public RolCriteria() {}

    public RolCriteria(RolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.relatie = other.relatie == null ? null : other.relatie.copy();
        this.rolnaam = other.rolnaam == null ? null : other.rolnaam.copy();
        this.jeugdschaatsen = other.jeugdschaatsen == null ? null : other.jeugdschaatsen.copy();
        this.startdatumRol = other.startdatumRol == null ? null : other.startdatumRol.copy();
        this.einddatumRol = other.einddatumRol == null ? null : other.einddatumRol.copy();
        this.relatieId = other.relatieId == null ? null : other.relatieId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RolCriteria copy() {
        return new RolCriteria(this);
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

    public UUIDFilter getRelatie() {
        return relatie;
    }

    public UUIDFilter relatie() {
        if (relatie == null) {
            relatie = new UUIDFilter();
        }
        return relatie;
    }

    public void setRelatie(UUIDFilter relatie) {
        this.relatie = relatie;
    }

    public StringFilter getRolnaam() {
        return rolnaam;
    }

    public StringFilter rolnaam() {
        if (rolnaam == null) {
            rolnaam = new StringFilter();
        }
        return rolnaam;
    }

    public void setRolnaam(StringFilter rolnaam) {
        this.rolnaam = rolnaam;
    }

    public BooleanFilter getJeugdschaatsen() {
        return jeugdschaatsen;
    }

    public BooleanFilter jeugdschaatsen() {
        if (jeugdschaatsen == null) {
            jeugdschaatsen = new BooleanFilter();
        }
        return jeugdschaatsen;
    }

    public void setJeugdschaatsen(BooleanFilter jeugdschaatsen) {
        this.jeugdschaatsen = jeugdschaatsen;
    }

    public InstantFilter getStartdatumRol() {
        return startdatumRol;
    }

    public InstantFilter startdatumRol() {
        if (startdatumRol == null) {
            startdatumRol = new InstantFilter();
        }
        return startdatumRol;
    }

    public void setStartdatumRol(InstantFilter startdatumRol) {
        this.startdatumRol = startdatumRol;
    }

    public InstantFilter getEinddatumRol() {
        return einddatumRol;
    }

    public InstantFilter einddatumRol() {
        if (einddatumRol == null) {
            einddatumRol = new InstantFilter();
        }
        return einddatumRol;
    }

    public void setEinddatumRol(InstantFilter einddatumRol) {
        this.einddatumRol = einddatumRol;
    }

    public UUIDFilter getRelatieId() {
        return relatieId;
    }

    public UUIDFilter relatieId() {
        if (relatieId == null) {
            relatieId = new UUIDFilter();
        }
        return relatieId;
    }

    public void setRelatieId(UUIDFilter relatieId) {
        this.relatieId = relatieId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RolCriteria that = (RolCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(relatie, that.relatie) &&
            Objects.equals(rolnaam, that.rolnaam) &&
            Objects.equals(jeugdschaatsen, that.jeugdschaatsen) &&
            Objects.equals(startdatumRol, that.startdatumRol) &&
            Objects.equals(einddatumRol, that.einddatumRol) &&
            Objects.equals(relatieId, that.relatieId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relatie, rolnaam, jeugdschaatsen, startdatumRol, einddatumRol, relatieId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (relatie != null ? "relatie=" + relatie + ", " : "") +
            (rolnaam != null ? "rolnaam=" + rolnaam + ", " : "") +
            (jeugdschaatsen != null ? "jeugdschaatsen=" + jeugdschaatsen + ", " : "") +
            (startdatumRol != null ? "startdatumRol=" + startdatumRol + ", " : "") +
            (einddatumRol != null ? "einddatumRol=" + einddatumRol + ", " : "") +
            (relatieId != null ? "relatieId=" + relatieId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
