package com.lekstreek.portal.service;

import com.lekstreek.portal.domain.Relatie;
import com.lekstreek.portal.repository.RelatieRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Relatie}.
 */
@Service
@Transactional
public class RelatieService {

    private final Logger log = LoggerFactory.getLogger(RelatieService.class);

    private final RelatieRepository relatieRepository;

    public RelatieService(RelatieRepository relatieRepository) {
        this.relatieRepository = relatieRepository;
    }

    /**
     * Save a relatie.
     *
     * @param relatie the entity to save.
     * @return the persisted entity.
     */
    public Relatie save(Relatie relatie) {
        log.debug("Request to save Relatie : {}", relatie);
        return relatieRepository.save(relatie);
    }

    /**
     * Partially update a relatie.
     *
     * @param relatie the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Relatie> partialUpdate(Relatie relatie) {
        log.debug("Request to partially update Relatie : {}", relatie);

        return relatieRepository
            .findById(relatie.getId())
            .map(
                existingRelatie -> {
                    if (relatie.getVoornaam() != null) {
                        existingRelatie.setVoornaam(relatie.getVoornaam());
                    }
                    if (relatie.getAchternaam() != null) {
                        existingRelatie.setAchternaam(relatie.getAchternaam());
                    }
                    if (relatie.getInitialen() != null) {
                        existingRelatie.setInitialen(relatie.getInitialen());
                    }
                    if (relatie.getWeergavenaam() != null) {
                        existingRelatie.setWeergavenaam(relatie.getWeergavenaam());
                    }
                    if (relatie.getGeslacht() != null) {
                        existingRelatie.setGeslacht(relatie.getGeslacht());
                    }
                    if (relatie.getGeboortedatum() != null) {
                        existingRelatie.setGeboortedatum(relatie.getGeboortedatum());
                    }
                    if (relatie.getRelatietype() != null) {
                        existingRelatie.setRelatietype(relatie.getRelatietype());
                    }
                    if (relatie.getInschrijvingsdatum() != null) {
                        existingRelatie.setInschrijvingsdatum(relatie.getInschrijvingsdatum());
                    }
                    if (relatie.getStraatnaam() != null) {
                        existingRelatie.setStraatnaam(relatie.getStraatnaam());
                    }
                    if (relatie.getHuisnummer() != null) {
                        existingRelatie.setHuisnummer(relatie.getHuisnummer());
                    }
                    if (relatie.getPostcode() != null) {
                        existingRelatie.setPostcode(relatie.getPostcode());
                    }
                    if (relatie.getWoonplaats() != null) {
                        existingRelatie.setWoonplaats(relatie.getWoonplaats());
                    }
                    if (relatie.getLand() != null) {
                        existingRelatie.setLand(relatie.getLand());
                    }
                    if (relatie.getEmail() != null) {
                        existingRelatie.setEmail(relatie.getEmail());
                    }
                    if (relatie.getEmail2() != null) {
                        existingRelatie.setEmail2(relatie.getEmail2());
                    }
                    if (relatie.getTelefoonnummer() != null) {
                        existingRelatie.setTelefoonnummer(relatie.getTelefoonnummer());
                    }
                    if (relatie.getTelefoonnummer2() != null) {
                        existingRelatie.setTelefoonnummer2(relatie.getTelefoonnummer2());
                    }
                    if (relatie.getTelefoonnummer3() != null) {
                        existingRelatie.setTelefoonnummer3(relatie.getTelefoonnummer3());
                    }
                    if (relatie.getIbancode() != null) {
                        existingRelatie.setIbancode(relatie.getIbancode());
                    }
                    if (relatie.getKnsbRelatienummer() != null) {
                        existingRelatie.setKnsbRelatienummer(relatie.getKnsbRelatienummer());
                    }
                    if (relatie.getPasfoto() != null) {
                        existingRelatie.setPasfoto(relatie.getPasfoto());
                    }
                    if (relatie.getPasfotoContentType() != null) {
                        existingRelatie.setPasfotoContentType(relatie.getPasfotoContentType());
                    }
                    if (relatie.getPrivacyVerklaring() != null) {
                        existingRelatie.setPrivacyVerklaring(relatie.getPrivacyVerklaring());
                    }
                    if (relatie.getPrivacyVerklaringContentType() != null) {
                        existingRelatie.setPrivacyVerklaringContentType(relatie.getPrivacyVerklaringContentType());
                    }

                    return existingRelatie;
                }
            )
            .map(relatieRepository::save);
    }

    /**
     * Get all the relaties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Relatie> findAll() {
        log.debug("Request to get all Relaties");
        return relatieRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the relaties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Relatie> findAllWithEagerRelationships(Pageable pageable) {
        return relatieRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one relatie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Relatie> findOne(UUID id) {
        log.debug("Request to get Relatie : {}", id);
        return relatieRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the relatie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Relatie : {}", id);
        relatieRepository.deleteById(id);
    }
}
