package com.lekstreek.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lekstreek.portal.IntegrationTest;
import com.lekstreek.portal.domain.Adres;
import com.lekstreek.portal.repository.AdresRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresResourceIT {

    private static final String DEFAULT_STRAATNAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAATNAAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_HUISNUMMER = 1;
    private static final Integer UPDATED_HUISNUMMER = 2;

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WOONPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_LAND = "AAAAAAAAAA";
    private static final String UPDATED_LAND = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AdresRepository adresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresMockMvc;

    private Adres adres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adres createEntity(EntityManager em) {
        Adres adres = new Adres()
            .straatnaam(DEFAULT_STRAATNAAM)
            .huisnummer(DEFAULT_HUISNUMMER)
            .postcode(DEFAULT_POSTCODE)
            .woonplaats(DEFAULT_WOONPLAATS)
            .land(DEFAULT_LAND);
        return adres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adres createUpdatedEntity(EntityManager em) {
        Adres adres = new Adres()
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND);
        return adres;
    }

    @BeforeEach
    public void initTest() {
        adres = createEntity(em);
    }

    @Test
    @Transactional
    void createAdres() throws Exception {
        int databaseSizeBeforeCreate = adresRepository.findAll().size();
        // Create the Adres
        restAdresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isCreated());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate + 1);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getStraatnaam()).isEqualTo(DEFAULT_STRAATNAAM);
        assertThat(testAdres.getHuisnummer()).isEqualTo(DEFAULT_HUISNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAdres.getWoonplaats()).isEqualTo(DEFAULT_WOONPLAATS);
        assertThat(testAdres.getLand()).isEqualTo(DEFAULT_LAND);
    }

    @Test
    @Transactional
    void createAdresWithExistingId() throws Exception {
        // Create the Adres with an existing ID
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeCreate = adresRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get all the adresList
        restAdresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adres.getId().toString())))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)));
    }

    @Test
    @Transactional
    void getAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        // Get the adres
        restAdresMockMvc
            .perform(get(ENTITY_API_URL_ID, adres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adres.getId().toString()))
            .andExpect(jsonPath("$.straatnaam").value(DEFAULT_STRAATNAAM))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.woonplaats").value(DEFAULT_WOONPLAATS))
            .andExpect(jsonPath("$.land").value(DEFAULT_LAND));
    }

    @Test
    @Transactional
    void getNonExistingAdres() throws Exception {
        // Get the adres
        restAdresMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres
        Adres updatedAdres = adresRepository.findById(adres.getId()).get();
        // Disconnect from session so that the updates on updatedAdres are not directly saved in db
        em.detach(updatedAdres);
        updatedAdres
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND);

        restAdresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdres))
            )
            .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getStraatnaam()).isEqualTo(UPDATED_STRAATNAAM);
        assertThat(testAdres.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAdres.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testAdres.getLand()).isEqualTo(UPDATED_LAND);
    }

    @Test
    @Transactional
    void putNonExistingAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresWithPatch() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres using partial update
        Adres partialUpdatedAdres = new Adres();
        partialUpdatedAdres.setId(adres.getId());

        partialUpdatedAdres.postcode(UPDATED_POSTCODE).woonplaats(UPDATED_WOONPLAATS).land(UPDATED_LAND);

        restAdresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdres.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdres))
            )
            .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getStraatnaam()).isEqualTo(DEFAULT_STRAATNAAM);
        assertThat(testAdres.getHuisnummer()).isEqualTo(DEFAULT_HUISNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAdres.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testAdres.getLand()).isEqualTo(UPDATED_LAND);
    }

    @Test
    @Transactional
    void fullUpdateAdresWithPatch() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeUpdate = adresRepository.findAll().size();

        // Update the adres using partial update
        Adres partialUpdatedAdres = new Adres();
        partialUpdatedAdres.setId(adres.getId());

        partialUpdatedAdres
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND);

        restAdresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdres.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdres))
            )
            .andExpect(status().isOk());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
        Adres testAdres = adresList.get(adresList.size() - 1);
        assertThat(testAdres.getStraatnaam()).isEqualTo(UPDATED_STRAATNAAM);
        assertThat(testAdres.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testAdres.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAdres.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testAdres.getLand()).isEqualTo(UPDATED_LAND);
    }

    @Test
    @Transactional
    void patchNonExistingAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adres.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdres() throws Exception {
        int databaseSizeBeforeUpdate = adresRepository.findAll().size();
        adres.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adres in the database
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdres() throws Exception {
        // Initialize the database
        adresRepository.saveAndFlush(adres);

        int databaseSizeBeforeDelete = adresRepository.findAll().size();

        // Delete the adres
        restAdresMockMvc
            .perform(delete(ENTITY_API_URL_ID, adres.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adres> adresList = adresRepository.findAll();
        assertThat(adresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
