package com.lekstreek.portal.web.rest;

import com.lekstreek.portal.domain.Relatie;
import com.lekstreek.portal.repository.RelatieRepository;
import com.lekstreek.portal.service.RelatieQueryService;
import com.lekstreek.portal.service.RelatieService;
import com.lekstreek.portal.service.criteria.RelatieCriteria;
import com.lekstreek.portal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lekstreek.portal.domain.Relatie}.
 */
@RestController
@RequestMapping("/api")
public class RelatieResource {

    private final Logger log = LoggerFactory.getLogger(RelatieResource.class);

    private static final String ENTITY_NAME = "relatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatieService relatieService;

    private final RelatieRepository relatieRepository;

    private final RelatieQueryService relatieQueryService;

    public RelatieResource(RelatieService relatieService, RelatieRepository relatieRepository, RelatieQueryService relatieQueryService) {
        this.relatieService = relatieService;
        this.relatieRepository = relatieRepository;
        this.relatieQueryService = relatieQueryService;
    }

    /**
     * {@code POST  /relaties} : Create a new relatie.
     *
     * @param relatie the relatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatie, or with status {@code 400 (Bad Request)} if the relatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relaties")
    public ResponseEntity<Relatie> createRelatie(@RequestBody Relatie relatie) throws URISyntaxException {
        log.debug("REST request to save Relatie : {}", relatie);
        if (relatie.getId() != null) {
            throw new BadRequestAlertException("A new relatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relatie result = relatieService.save(relatie);
        return ResponseEntity
            .created(new URI("/api/relaties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relaties/:id} : Updates an existing relatie.
     *
     * @param id the id of the relatie to save.
     * @param relatie the relatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatie,
     * or with status {@code 400 (Bad Request)} if the relatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relaties/{id}")
    public ResponseEntity<Relatie> updateRelatie(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Relatie relatie)
        throws URISyntaxException {
        log.debug("REST request to update Relatie : {}, {}", id, relatie);
        if (relatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Relatie result = relatieService.save(relatie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relaties/:id} : Partial updates given fields of an existing relatie, field will ignore if it is null
     *
     * @param id the id of the relatie to save.
     * @param relatie the relatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatie,
     * or with status {@code 400 (Bad Request)} if the relatie is not valid,
     * or with status {@code 404 (Not Found)} if the relatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relaties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Relatie> partialUpdateRelatie(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody Relatie relatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Relatie partially : {}, {}", id, relatie);
        if (relatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Relatie> result = relatieService.partialUpdate(relatie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatie.getId().toString())
        );
    }

    /**
     * {@code GET  /relaties} : get all the relaties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relaties in body.
     */
    @GetMapping("/relaties")
    public ResponseEntity<List<Relatie>> getAllRelaties(RelatieCriteria criteria) {
        log.debug("REST request to get Relaties by criteria: {}", criteria);
        List<Relatie> entityList = relatieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /relaties/count} : count all the relaties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/relaties/count")
    public ResponseEntity<Long> countRelaties(RelatieCriteria criteria) {
        log.debug("REST request to count Relaties by criteria: {}", criteria);
        return ResponseEntity.ok().body(relatieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /relaties/:id} : get the "id" relatie.
     *
     * @param id the id of the relatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relaties/{id}")
    public ResponseEntity<Relatie> getRelatie(@PathVariable UUID id) {
        log.debug("REST request to get Relatie : {}", id);
        Optional<Relatie> relatie = relatieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relatie);
    }

    /**
     * {@code DELETE  /relaties/:id} : delete the "id" relatie.
     *
     * @param id the id of the relatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relaties/{id}")
    public ResponseEntity<Void> deleteRelatie(@PathVariable UUID id) {
        log.debug("REST request to delete Relatie : {}", id);
        relatieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
