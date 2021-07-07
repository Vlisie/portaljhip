package com.lekstreek.portal.service;

import com.lekstreek.portal.domain.Rol;
import com.lekstreek.portal.repository.RolRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rol}.
 */
@Service
@Transactional
public class RolService {

    private final Logger log = LoggerFactory.getLogger(RolService.class);

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Save a rol.
     *
     * @param rol the entity to save.
     * @return the persisted entity.
     */
    public Rol save(Rol rol) {
        log.debug("Request to save Rol : {}", rol);
        return rolRepository.save(rol);
    }

    /**
     * Partially update a rol.
     *
     * @param rol the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Rol> partialUpdate(Rol rol) {
        log.debug("Request to partially update Rol : {}", rol);

        return rolRepository
            .findById(rol.getId())
            .map(
                existingRol -> {
                    if (rol.getRolNaam() != null) {
                        existingRol.setRolNaam(rol.getRolNaam());
                    }
                    if (rol.getJeugdschaatsen() != null) {
                        existingRol.setJeugdschaatsen(rol.getJeugdschaatsen());
                    }
                    if (rol.getStartDatumRol() != null) {
                        existingRol.setStartDatumRol(rol.getStartDatumRol());
                    }
                    if (rol.getEindDatumRol() != null) {
                        existingRol.setEindDatumRol(rol.getEindDatumRol());
                    }

                    return existingRol;
                }
            )
            .map(rolRepository::save);
    }

    /**
     * Get all the rols.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        log.debug("Request to get all Rols");
        return rolRepository.findAll();
    }

    /**
     * Get one rol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rol> findOne(UUID id) {
        log.debug("Request to get Rol : {}", id);
        return rolRepository.findById(id);
    }

    /**
     * Delete the rol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Rol : {}", id);
        rolRepository.deleteById(id);
    }
}
