package com.lekstreek.portal.service;

import com.lekstreek.portal.domain.*; // for static metamodels
import com.lekstreek.portal.domain.Rol;
import com.lekstreek.portal.repository.RolRepository;
import com.lekstreek.portal.service.criteria.RolCriteria;
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
 * Service for executing complex queries for {@link Rol} entities in the database.
 * The main input is a {@link RolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Rol} or a {@link Page} of {@link Rol} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RolQueryService extends QueryService<Rol> {

    private final Logger log = LoggerFactory.getLogger(RolQueryService.class);

    private final RolRepository rolRepository;

    public RolQueryService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Return a {@link List} of {@link Rol} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Rol> findByCriteria(RolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rol> specification = createSpecification(criteria);
        return rolRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Rol} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Rol> findByCriteria(RolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rol> specification = createSpecification(criteria);
        return rolRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rol> specification = createSpecification(criteria);
        return rolRepository.count(specification);
    }

    /**
     * Function to convert {@link RolCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rol> createSpecification(RolCriteria criteria) {
        Specification<Rol> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Rol_.id));
            }
            if (criteria.getRolnaam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRolnaam(), Rol_.rolnaam));
            }
            if (criteria.getJeugdschaatsen() != null) {
                specification = specification.and(buildSpecification(criteria.getJeugdschaatsen(), Rol_.jeugdschaatsen));
            }
            if (criteria.getStartdatumRol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdatumRol(), Rol_.startdatumRol));
            }
            if (criteria.getEinddatumRol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEinddatumRol(), Rol_.einddatumRol));
            }
            if (criteria.getRelatieId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRelatieId(), root -> root.join(Rol_.relaties, JoinType.LEFT).get(Relatie_.id))
                    );
            }
        }
        return specification;
    }
}
