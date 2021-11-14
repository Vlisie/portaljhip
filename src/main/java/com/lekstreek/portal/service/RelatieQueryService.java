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
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Relatie_.id));
            }
            if (criteria.getRol() != null) {
                specification = specification.and(buildSpecification(criteria.getRol(), Relatie_.rol));
            }
            if (criteria.getVoornaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoornaam(), Relatie_.voornaam));
            }
            if (criteria.getAchternaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAchternaam(), Relatie_.achternaam));
            }
            if (criteria.getInitialen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInitialen(), Relatie_.initialen));
            }
            if (criteria.getWeergavenaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeergavenaam(), Relatie_.weergavenaam));
            }
            if (criteria.getGeslacht() != null) {
                specification = specification.and(buildSpecification(criteria.getGeslacht(), Relatie_.geslacht));
            }
            if (criteria.getGeboortedatum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeboortedatum(), Relatie_.geboortedatum));
            }
            if (criteria.getRelatietype() != null) {
                specification = specification.and(buildSpecification(criteria.getRelatietype(), Relatie_.relatietype));
            }
            if (criteria.getInschrijvingsdatum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInschrijvingsdatum(), Relatie_.inschrijvingsdatum));
            }
            if (criteria.getAdres() != null) {
                specification = specification.and(buildSpecification(criteria.getAdres(), Relatie_.adres));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Relatie_.email));
            }
            if (criteria.getEmail2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail2(), Relatie_.email2));
            }
            if (criteria.getTelefoonnummer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonnummer(), Relatie_.telefoonnummer));
            }
            if (criteria.getTelefoonnummer2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonnummer2(), Relatie_.telefoonnummer2));
            }
            if (criteria.getTelefoonnummer3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefoonnummer3(), Relatie_.telefoonnummer3));
            }
            if (criteria.getIbancode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIbancode(), Relatie_.ibancode));
            }
            if (criteria.getKnsbRelatienummer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKnsbRelatienummer(), Relatie_.knsbRelatienummer));
            }
            if (criteria.getAdresId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAdresId(), root -> root.join(Relatie_.adres, JoinType.LEFT).get(Adres_.id))
                    );
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
