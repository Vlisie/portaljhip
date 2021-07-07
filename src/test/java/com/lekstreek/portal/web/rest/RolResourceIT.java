package com.lekstreek.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lekstreek.portal.IntegrationTest;
import com.lekstreek.portal.domain.Relatie;
import com.lekstreek.portal.domain.Rol;
import com.lekstreek.portal.repository.RolRepository;
import com.lekstreek.portal.service.criteria.RolCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link RolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolResourceIT {

    private static final String DEFAULT_ROL_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_ROL_NAAM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_JEUGDSCHAATSEN = false;
    private static final Boolean UPDATED_JEUGDSCHAATSEN = true;

    private static final Instant DEFAULT_START_DATUM_ROL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATUM_ROL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EIND_DATUM_ROL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EIND_DATUM_ROL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolMockMvc;

    private Rol rol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rol createEntity(EntityManager em) {
        Rol rol = new Rol()
            .rolNaam(DEFAULT_ROL_NAAM)
            .jeugdschaatsen(DEFAULT_JEUGDSCHAATSEN)
            .startDatumRol(DEFAULT_START_DATUM_ROL)
            .eindDatumRol(DEFAULT_EIND_DATUM_ROL);
        return rol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rol createUpdatedEntity(EntityManager em) {
        Rol rol = new Rol()
            .rolNaam(UPDATED_ROL_NAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startDatumRol(UPDATED_START_DATUM_ROL)
            .eindDatumRol(UPDATED_EIND_DATUM_ROL);
        return rol;
    }

    @BeforeEach
    public void initTest() {
        rol = createEntity(em);
    }

    @Test
    @Transactional
    void createRol() throws Exception {
        int databaseSizeBeforeCreate = rolRepository.findAll().size();
        // Create the Rol
        restRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isCreated());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate + 1);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getRolNaam()).isEqualTo(DEFAULT_ROL_NAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(DEFAULT_JEUGDSCHAATSEN);
        assertThat(testRol.getStartDatumRol()).isEqualTo(DEFAULT_START_DATUM_ROL);
        assertThat(testRol.getEindDatumRol()).isEqualTo(DEFAULT_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void createRolWithExistingId() throws Exception {
        // Create the Rol with an existing ID
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeCreate = rolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRols() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().toString())))
            .andExpect(jsonPath("$.[*].rolNaam").value(hasItem(DEFAULT_ROL_NAAM)))
            .andExpect(jsonPath("$.[*].jeugdschaatsen").value(hasItem(DEFAULT_JEUGDSCHAATSEN.booleanValue())))
            .andExpect(jsonPath("$.[*].startDatumRol").value(hasItem(DEFAULT_START_DATUM_ROL.toString())))
            .andExpect(jsonPath("$.[*].eindDatumRol").value(hasItem(DEFAULT_EIND_DATUM_ROL.toString())));
    }

    @Test
    @Transactional
    void getRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get the rol
        restRolMockMvc
            .perform(get(ENTITY_API_URL_ID, rol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rol.getId().toString()))
            .andExpect(jsonPath("$.rolNaam").value(DEFAULT_ROL_NAAM))
            .andExpect(jsonPath("$.jeugdschaatsen").value(DEFAULT_JEUGDSCHAATSEN.booleanValue()))
            .andExpect(jsonPath("$.startDatumRol").value(DEFAULT_START_DATUM_ROL.toString()))
            .andExpect(jsonPath("$.eindDatumRol").value(DEFAULT_EIND_DATUM_ROL.toString()));
    }

    @Test
    @Transactional
    void getRolsByIdFiltering() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        UUID id = rol.getId();

        defaultRolShouldBeFound("id.equals=" + id);
        defaultRolShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam equals to DEFAULT_ROL_NAAM
        defaultRolShouldBeFound("rolNaam.equals=" + DEFAULT_ROL_NAAM);

        // Get all the rolList where rolNaam equals to UPDATED_ROL_NAAM
        defaultRolShouldNotBeFound("rolNaam.equals=" + UPDATED_ROL_NAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam not equals to DEFAULT_ROL_NAAM
        defaultRolShouldNotBeFound("rolNaam.notEquals=" + DEFAULT_ROL_NAAM);

        // Get all the rolList where rolNaam not equals to UPDATED_ROL_NAAM
        defaultRolShouldBeFound("rolNaam.notEquals=" + UPDATED_ROL_NAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam in DEFAULT_ROL_NAAM or UPDATED_ROL_NAAM
        defaultRolShouldBeFound("rolNaam.in=" + DEFAULT_ROL_NAAM + "," + UPDATED_ROL_NAAM);

        // Get all the rolList where rolNaam equals to UPDATED_ROL_NAAM
        defaultRolShouldNotBeFound("rolNaam.in=" + UPDATED_ROL_NAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam is not null
        defaultRolShouldBeFound("rolNaam.specified=true");

        // Get all the rolList where rolNaam is null
        defaultRolShouldNotBeFound("rolNaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamContainsSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam contains DEFAULT_ROL_NAAM
        defaultRolShouldBeFound("rolNaam.contains=" + DEFAULT_ROL_NAAM);

        // Get all the rolList where rolNaam contains UPDATED_ROL_NAAM
        defaultRolShouldNotBeFound("rolNaam.contains=" + UPDATED_ROL_NAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolNaamNotContainsSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolNaam does not contain DEFAULT_ROL_NAAM
        defaultRolShouldNotBeFound("rolNaam.doesNotContain=" + DEFAULT_ROL_NAAM);

        // Get all the rolList where rolNaam does not contain UPDATED_ROL_NAAM
        defaultRolShouldBeFound("rolNaam.doesNotContain=" + UPDATED_ROL_NAAM);
    }

    @Test
    @Transactional
    void getAllRolsByJeugdschaatsenIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where jeugdschaatsen equals to DEFAULT_JEUGDSCHAATSEN
        defaultRolShouldBeFound("jeugdschaatsen.equals=" + DEFAULT_JEUGDSCHAATSEN);

        // Get all the rolList where jeugdschaatsen equals to UPDATED_JEUGDSCHAATSEN
        defaultRolShouldNotBeFound("jeugdschaatsen.equals=" + UPDATED_JEUGDSCHAATSEN);
    }

    @Test
    @Transactional
    void getAllRolsByJeugdschaatsenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where jeugdschaatsen not equals to DEFAULT_JEUGDSCHAATSEN
        defaultRolShouldNotBeFound("jeugdschaatsen.notEquals=" + DEFAULT_JEUGDSCHAATSEN);

        // Get all the rolList where jeugdschaatsen not equals to UPDATED_JEUGDSCHAATSEN
        defaultRolShouldBeFound("jeugdschaatsen.notEquals=" + UPDATED_JEUGDSCHAATSEN);
    }

    @Test
    @Transactional
    void getAllRolsByJeugdschaatsenIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where jeugdschaatsen in DEFAULT_JEUGDSCHAATSEN or UPDATED_JEUGDSCHAATSEN
        defaultRolShouldBeFound("jeugdschaatsen.in=" + DEFAULT_JEUGDSCHAATSEN + "," + UPDATED_JEUGDSCHAATSEN);

        // Get all the rolList where jeugdschaatsen equals to UPDATED_JEUGDSCHAATSEN
        defaultRolShouldNotBeFound("jeugdschaatsen.in=" + UPDATED_JEUGDSCHAATSEN);
    }

    @Test
    @Transactional
    void getAllRolsByJeugdschaatsenIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where jeugdschaatsen is not null
        defaultRolShouldBeFound("jeugdschaatsen.specified=true");

        // Get all the rolList where jeugdschaatsen is null
        defaultRolShouldNotBeFound("jeugdschaatsen.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByStartDatumRolIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startDatumRol equals to DEFAULT_START_DATUM_ROL
        defaultRolShouldBeFound("startDatumRol.equals=" + DEFAULT_START_DATUM_ROL);

        // Get all the rolList where startDatumRol equals to UPDATED_START_DATUM_ROL
        defaultRolShouldNotBeFound("startDatumRol.equals=" + UPDATED_START_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartDatumRolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startDatumRol not equals to DEFAULT_START_DATUM_ROL
        defaultRolShouldNotBeFound("startDatumRol.notEquals=" + DEFAULT_START_DATUM_ROL);

        // Get all the rolList where startDatumRol not equals to UPDATED_START_DATUM_ROL
        defaultRolShouldBeFound("startDatumRol.notEquals=" + UPDATED_START_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartDatumRolIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startDatumRol in DEFAULT_START_DATUM_ROL or UPDATED_START_DATUM_ROL
        defaultRolShouldBeFound("startDatumRol.in=" + DEFAULT_START_DATUM_ROL + "," + UPDATED_START_DATUM_ROL);

        // Get all the rolList where startDatumRol equals to UPDATED_START_DATUM_ROL
        defaultRolShouldNotBeFound("startDatumRol.in=" + UPDATED_START_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartDatumRolIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startDatumRol is not null
        defaultRolShouldBeFound("startDatumRol.specified=true");

        // Get all the rolList where startDatumRol is null
        defaultRolShouldNotBeFound("startDatumRol.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByEindDatumRolIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where eindDatumRol equals to DEFAULT_EIND_DATUM_ROL
        defaultRolShouldBeFound("eindDatumRol.equals=" + DEFAULT_EIND_DATUM_ROL);

        // Get all the rolList where eindDatumRol equals to UPDATED_EIND_DATUM_ROL
        defaultRolShouldNotBeFound("eindDatumRol.equals=" + UPDATED_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEindDatumRolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where eindDatumRol not equals to DEFAULT_EIND_DATUM_ROL
        defaultRolShouldNotBeFound("eindDatumRol.notEquals=" + DEFAULT_EIND_DATUM_ROL);

        // Get all the rolList where eindDatumRol not equals to UPDATED_EIND_DATUM_ROL
        defaultRolShouldBeFound("eindDatumRol.notEquals=" + UPDATED_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEindDatumRolIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where eindDatumRol in DEFAULT_EIND_DATUM_ROL or UPDATED_EIND_DATUM_ROL
        defaultRolShouldBeFound("eindDatumRol.in=" + DEFAULT_EIND_DATUM_ROL + "," + UPDATED_EIND_DATUM_ROL);

        // Get all the rolList where eindDatumRol equals to UPDATED_EIND_DATUM_ROL
        defaultRolShouldNotBeFound("eindDatumRol.in=" + UPDATED_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEindDatumRolIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where eindDatumRol is not null
        defaultRolShouldBeFound("eindDatumRol.specified=true");

        // Get all the rolList where eindDatumRol is null
        defaultRolShouldNotBeFound("eindDatumRol.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByRelatieIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);
        Relatie relatie = RelatieResourceIT.createEntity(em);
        em.persist(relatie);
        em.flush();
        rol.addRelatie(relatie);
        rolRepository.saveAndFlush(rol);
        UUID relatieId = relatie.getId();

        // Get all the rolList where relatie equals to relatieId
        defaultRolShouldBeFound("relatieId.equals=" + relatieId);

        // Get all the rolList where relatie equals to UUID.randomUUID()
        defaultRolShouldNotBeFound("relatieId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRolShouldBeFound(String filter) throws Exception {
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().toString())))
            .andExpect(jsonPath("$.[*].rolNaam").value(hasItem(DEFAULT_ROL_NAAM)))
            .andExpect(jsonPath("$.[*].jeugdschaatsen").value(hasItem(DEFAULT_JEUGDSCHAATSEN.booleanValue())))
            .andExpect(jsonPath("$.[*].startDatumRol").value(hasItem(DEFAULT_START_DATUM_ROL.toString())))
            .andExpect(jsonPath("$.[*].eindDatumRol").value(hasItem(DEFAULT_EIND_DATUM_ROL.toString())));

        // Check, that the count call also returns 1
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRolShouldNotBeFound(String filter) throws Exception {
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRol() throws Exception {
        // Get the rol
        restRolMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Update the rol
        Rol updatedRol = rolRepository.findById(rol.getId()).get();
        // Disconnect from session so that the updates on updatedRol are not directly saved in db
        em.detach(updatedRol);
        updatedRol
            .rolNaam(UPDATED_ROL_NAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startDatumRol(UPDATED_START_DATUM_ROL)
            .eindDatumRol(UPDATED_EIND_DATUM_ROL);

        restRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getRolNaam()).isEqualTo(UPDATED_ROL_NAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(UPDATED_JEUGDSCHAATSEN);
        assertThat(testRol.getStartDatumRol()).isEqualTo(UPDATED_START_DATUM_ROL);
        assertThat(testRol.getEindDatumRol()).isEqualTo(UPDATED_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void putNonExistingRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rol.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolWithPatch() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Update the rol using partial update
        Rol partialUpdatedRol = new Rol();
        partialUpdatedRol.setId(rol.getId());

        partialUpdatedRol.rolNaam(UPDATED_ROL_NAAM).startDatumRol(UPDATED_START_DATUM_ROL);

        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getRolNaam()).isEqualTo(UPDATED_ROL_NAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(DEFAULT_JEUGDSCHAATSEN);
        assertThat(testRol.getStartDatumRol()).isEqualTo(UPDATED_START_DATUM_ROL);
        assertThat(testRol.getEindDatumRol()).isEqualTo(DEFAULT_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void fullUpdateRolWithPatch() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Update the rol using partial update
        Rol partialUpdatedRol = new Rol();
        partialUpdatedRol.setId(rol.getId());

        partialUpdatedRol
            .rolNaam(UPDATED_ROL_NAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startDatumRol(UPDATED_START_DATUM_ROL)
            .eindDatumRol(UPDATED_EIND_DATUM_ROL);

        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getRolNaam()).isEqualTo(UPDATED_ROL_NAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(UPDATED_JEUGDSCHAATSEN);
        assertThat(testRol.getStartDatumRol()).isEqualTo(UPDATED_START_DATUM_ROL);
        assertThat(testRol.getEindDatumRol()).isEqualTo(UPDATED_EIND_DATUM_ROL);
    }

    @Test
    @Transactional
    void patchNonExistingRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();
        rol.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeDelete = rolRepository.findAll().size();

        // Delete the rol
        restRolMockMvc
            .perform(delete(ENTITY_API_URL_ID, rol.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
