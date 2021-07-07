package com.lekstreek.portal.service;

import com.lekstreek.portal.domain.*; // for static metamodels
import com.lekstreek.portal.domain.Relatie;
import com.lekstreek.portal.repository.RelatieRepository;
import com.lekstreek.portal.service.criteria.RelatieCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Relatie} entities in the database.
 * The main input is a {@link RelatieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Relatie} or a {@link Page} of {@link Relatie} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RelatieQueryService extends QueryService<Relatie> {

    private final Logger log = LoggerFactory.getLogger(RelatieQueryService.class);

    private final RelatieRepository relatieRepository;

    public RelatieQueryService(RelatieRepository relatieRepository) {
        this.relatieRepository = relatieRepository;
    }

    /**
     * Return a {@link List} of {@link Relatie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Relatie> findByCriteria(RelatieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Relatie> specification = createSpecification(criteria);
        return relatieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Relatie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Relatie> findByCriteria(RelatieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Relatie> specification = createSpecification(criteria);
        return relatieRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RelatieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Relatie> specification = createSpecification(criteria);
        return relatieRepository.count(specification);
    }

    /**
     * Function to convert {@link RelatieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Relatie> createSpecification(RelatieCriteria criteria) {
        Specification<Relatie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Relatie_.id));
            }
            if (criteria.getVoorNaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoorNaam(), Relatie_.voorNaam));
            }
            if (criteria.getAchterNaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAchterNaam(), Relatie_.achterNaam));
            }
            if (criteria.getInitialen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInitialen(), Relatie_.initialen));
            }
            if (criteria.getWeergaveNaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeergaveNaam(), Relatie_.weergaveNaam));
            }
            if (criteria.getGeslacht() != null) {
                specification = specification.and(buildSpecification(criteria.getGeslacht(), Relatie_.geslacht));
            }
            if (criteria.getGeboorteDatum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeboorteDatum(), Relatie_.geboorteDatum));
            }
            if (criteria.getRelatieType() != null) {
                specification = specification.and(buildSpecification(criteria.getRelatieType(), Relatie_.relatieType));
            }
            if (criteria.getInschrijvingsDatum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInschrijvingsDatum(), Relatie_.inschrijvingsDatum));
            }
            if (criteria.getStraatNaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStraatNaam(), Relatie_.straatNaam));
            }
            if (criteria.getHuisNummer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHuisNummer(), Relatie_.huisNummer));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostCode(), Relatie_.postCode));
            }
            if (criteria.getWoonPlaats() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWoonPlaats(), Relatie_.woonPlaats));
            }
            if (criteria.getLand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLand(), Relatie_.land));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Relatie_.email));
            }
            if (criteria.getEmail2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail2(), Relatie_.email2));
            }
            if (criteria.getTelefoonNummer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonNummer(), Relatie_.telefoonNummer));
            }
            if (criteria.getTelefoonNummer2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonNummer2(), Relatie_.telefoonNummer2));
            }
            if (criteria.getTelefoonNummer3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonNummer3(), Relatie_.telefoonNummer3));
            }
            if (criteria.getIbanCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIbanCode(), Relatie_.ibanCode));
            }
            if (criteria.getKnsbRelatieNummer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKnsbRelatieNummer(), Relatie_.knsbRelatieNummer));
            }
            if (criteria.getRolId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRolId(), root -> root.join(Relatie_.rols, JoinType.LEFT).get(Rol_.id))
                    );
            }
        }
        return specification;
    }
}
