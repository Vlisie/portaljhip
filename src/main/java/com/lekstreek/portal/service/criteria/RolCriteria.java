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

    private StringFilter rolNaam;

    private BooleanFilter jeugdschaatsen;

    private InstantFilter startDatumRol;

    private InstantFilter eindDatumRol;

    private UUIDFilter relatieId;

    public RolCriteria() {}

    public RolCriteria(RolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rolNaam = other.rolNaam == null ? null : other.rolNaam.copy();
        this.jeugdschaatsen = other.jeugdschaatsen == null ? null : other.jeugdschaatsen.copy();
        this.startDatumRol = other.startDatumRol == null ? null : other.startDatumRol.copy();
        this.eindDatumRol = other.eindDatumRol == null ? null : other.eindDatumRol.copy();
        this.relatieId = other.relatieId == null ? null : other.relatieId.copy();
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

    public StringFilter getRolNaam() {
        return rolNaam;
    }

    public StringFilter rolNaam() {
        if (rolNaam == null) {
            rolNaam = new StringFilter();
        }
        return rolNaam;
    }

    public void setRolNaam(StringFilter rolNaam) {
        this.rolNaam = rolNaam;
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

    public InstantFilter getStartDatumRol() {
        return startDatumRol;
    }

    public InstantFilter startDatumRol() {
        if (startDatumRol == null) {
            startDatumRol = new InstantFilter();
        }
        return startDatumRol;
    }

    public void setStartDatumRol(InstantFilter startDatumRol) {
        this.startDatumRol = startDatumRol;
    }

    public InstantFilter getEindDatumRol() {
        return eindDatumRol;
    }

    public InstantFilter eindDatumRol() {
        if (eindDatumRol == null) {
            eindDatumRol = new InstantFilter();
        }
        return eindDatumRol;
    }

    public void setEindDatumRol(InstantFilter eindDatumRol) {
        this.eindDatumRol = eindDatumRol;
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
            Objects.equals(rolNaam, that.rolNaam) &&
            Objects.equals(jeugdschaatsen, that.jeugdschaatsen) &&
            Objects.equals(startDatumRol, that.startDatumRol) &&
            Objects.equals(eindDatumRol, that.eindDatumRol) &&
            Objects.equals(relatieId, that.relatieId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rolNaam, jeugdschaatsen, startDatumRol, eindDatumRol, relatieId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rolNaam != null ? "rolNaam=" + rolNaam + ", " : "") +
            (jeugdschaatsen != null ? "jeugdschaatsen=" + jeugdschaatsen + ", " : "") +
            (startDatumRol != null ? "startDatumRol=" + startDatumRol + ", " : "") +
            (eindDatumRol != null ? "eindDatumRol=" + eindDatumRol + ", " : "") +
            (relatieId != null ? "relatieId=" + relatieId + ", " : "") +
            "}";
    }
}
