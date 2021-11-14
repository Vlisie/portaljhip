package com.lekstreek.portal.web.rest;

import com.lekstreek.portal.domain.Rol;
import com.lekstreek.portal.repository.RolRepository;
import com.lekstreek.portal.service.RolQueryService;
import com.lekstreek.portal.service.RolService;
import com.lekstreek.portal.service.criteria.RolCriteria;
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
 * REST controller for managing {@link com.lekstreek.portal.domain.Rol}.
 */
@RestController
@RequestMapping("/api")
public class RolResource {

    private final Logger log = LoggerFactory.getLogger(RolResource.class);

    private static final String ENTITY_NAME = "rol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolService rolService;

    private final RolRepository rolRepository;

    private final RolQueryService rolQueryService;

    public RolResource(RolService rolService, RolRepository rolRepository, RolQueryService rolQueryService) {
        this.rolService = rolService;
        this.rolRepository = rolRepository;
        this.rolQueryService = rolQueryService;
    }

    /**
     * {@code POST  /rols} : Create a new rol.
     *
     * @param rol the rol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rol, or with status {@code 400 (Bad Request)} if the rol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rols")
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) throws URISyntaxException {
        log.debug("REST request to save Rol : {}", rol);
        if (rol.getId() != null) {
            throw new BadRequestAlertException("A new rol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rol result = rolService.save(rol);
        return ResponseEntity
            .created(new URI("/api/rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rols/:id} : Updates an existing rol.
     *
     * @param id the id of the rol to save.
     * @param rol the rol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rol,
     * or with status {@code 400 (Bad Request)} if the rol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rols/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Rol rol)
        throws URISyntaxException {
        log.debug("REST request to update Rol : {}, {}", id, rol);
        if (rol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rol result = rolService.save(rol);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rol.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rols/:id} : Partial updates given fields of an existing rol, field will ignore if it is null
     *
     * @param id the id of the rol to save.
     * @param rol the rol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rol,
     * or with status {@code 400 (Bad Request)} if the rol is not valid,
     * or with status {@code 404 (Not Found)} if the rol is not found,
     * or with status {@code 500 (Internal Server Error)} if the rol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rols/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rol> partialUpdateRol(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Rol rol)
        throws URISyntaxException {
        log.debug("REST request to partial update Rol partially : {}, {}", id, rol);
        if (rol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rol> result = rolService.partialUpdate(rol);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rol.getId().toString())
        );
    }

    /**
     * {@code GET  /rols} : get all the rols.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rols in body.
     */
    @GetMapping("/rols")
    public ResponseEntity<List<Rol>> getAllRols(RolCriteria criteria) {
        log.debug("REST request to get Rols by criteria: {}", criteria);
        List<Rol> entityList = rolQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /rols/count} : count all the rols.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rols/count")
    public ResponseEntity<Long> countRols(RolCriteria criteria) {
        log.debug("REST request to count Rols by criteria: {}", criteria);
        return ResponseEntity.ok().body(rolQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rols/:id} : get the "id" rol.
     *
     * @param id the id of the rol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rols/{id}")
    public ResponseEntity<Rol> getRol(@PathVariable UUID id) {
        log.debug("REST request to get Rol : {}", id);
        Optional<Rol> rol = rolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rol);
    }

    /**
     * {@code DELETE  /rols/:id} : delete the "id" rol.
     *
     * @param id the id of the rol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rols/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable UUID id) {
        log.debug("REST request to delete Rol : {}", id);
        rolService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
