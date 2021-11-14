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

    private static final UUID DEFAULT_RELATIE = UUID.randomUUID();
    private static final UUID UPDATED_RELATIE = UUID.randomUUID();

    private static final String DEFAULT_ROLNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ROLNAAM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_JEUGDSCHAATSEN = false;
    private static final Boolean UPDATED_JEUGDSCHAATSEN = true;

    private static final Instant DEFAULT_STARTDATUM_ROL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATUM_ROL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EINDDATUM_ROL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EINDDATUM_ROL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
            .relatie(DEFAULT_RELATIE)
            .rolnaam(DEFAULT_ROLNAAM)
            .jeugdschaatsen(DEFAULT_JEUGDSCHAATSEN)
            .startdatumRol(DEFAULT_STARTDATUM_ROL)
            .einddatumRol(DEFAULT_EINDDATUM_ROL);
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
            .relatie(UPDATED_RELATIE)
            .rolnaam(UPDATED_ROLNAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startdatumRol(UPDATED_STARTDATUM_ROL)
            .einddatumRol(UPDATED_EINDDATUM_ROL);
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
        assertThat(testRol.getRelatie()).isEqualTo(DEFAULT_RELATIE);
        assertThat(testRol.getRolnaam()).isEqualTo(DEFAULT_ROLNAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(DEFAULT_JEUGDSCHAATSEN);
        assertThat(testRol.getStartdatumRol()).isEqualTo(DEFAULT_STARTDATUM_ROL);
        assertThat(testRol.getEinddatumRol()).isEqualTo(DEFAULT_EINDDATUM_ROL);
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
            .andExpect(jsonPath("$.[*].relatie").value(hasItem(DEFAULT_RELATIE.toString())))
            .andExpect(jsonPath("$.[*].rolnaam").value(hasItem(DEFAULT_ROLNAAM)))
            .andExpect(jsonPath("$.[*].jeugdschaatsen").value(hasItem(DEFAULT_JEUGDSCHAATSEN.booleanValue())))
            .andExpect(jsonPath("$.[*].startdatumRol").value(hasItem(DEFAULT_STARTDATUM_ROL.toString())))
            .andExpect(jsonPath("$.[*].einddatumRol").value(hasItem(DEFAULT_EINDDATUM_ROL.toString())));
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
            .andExpect(jsonPath("$.relatie").value(DEFAULT_RELATIE.toString()))
            .andExpect(jsonPath("$.rolnaam").value(DEFAULT_ROLNAAM))
            .andExpect(jsonPath("$.jeugdschaatsen").value(DEFAULT_JEUGDSCHAATSEN.booleanValue()))
            .andExpect(jsonPath("$.startdatumRol").value(DEFAULT_STARTDATUM_ROL.toString()))
            .andExpect(jsonPath("$.einddatumRol").value(DEFAULT_EINDDATUM_ROL.toString()));
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
    void getAllRolsByRelatieIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where relatie equals to DEFAULT_RELATIE
        defaultRolShouldBeFound("relatie.equals=" + DEFAULT_RELATIE);

        // Get all the rolList where relatie equals to UPDATED_RELATIE
        defaultRolShouldNotBeFound("relatie.equals=" + UPDATED_RELATIE);
    }

    @Test
    @Transactional
    void getAllRolsByRelatieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where relatie not equals to DEFAULT_RELATIE
        defaultRolShouldNotBeFound("relatie.notEquals=" + DEFAULT_RELATIE);

        // Get all the rolList where relatie not equals to UPDATED_RELATIE
        defaultRolShouldBeFound("relatie.notEquals=" + UPDATED_RELATIE);
    }

    @Test
    @Transactional
    void getAllRolsByRelatieIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where relatie in DEFAULT_RELATIE or UPDATED_RELATIE
        defaultRolShouldBeFound("relatie.in=" + DEFAULT_RELATIE + "," + UPDATED_RELATIE);

        // Get all the rolList where relatie equals to UPDATED_RELATIE
        defaultRolShouldNotBeFound("relatie.in=" + UPDATED_RELATIE);
    }

    @Test
    @Transactional
    void getAllRolsByRelatieIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where relatie is not null
        defaultRolShouldBeFound("relatie.specified=true");

        // Get all the rolList where relatie is null
        defaultRolShouldNotBeFound("relatie.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam equals to DEFAULT_ROLNAAM
        defaultRolShouldBeFound("rolnaam.equals=" + DEFAULT_ROLNAAM);

        // Get all the rolList where rolnaam equals to UPDATED_ROLNAAM
        defaultRolShouldNotBeFound("rolnaam.equals=" + UPDATED_ROLNAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam not equals to DEFAULT_ROLNAAM
        defaultRolShouldNotBeFound("rolnaam.notEquals=" + DEFAULT_ROLNAAM);

        // Get all the rolList where rolnaam not equals to UPDATED_ROLNAAM
        defaultRolShouldBeFound("rolnaam.notEquals=" + UPDATED_ROLNAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam in DEFAULT_ROLNAAM or UPDATED_ROLNAAM
        defaultRolShouldBeFound("rolnaam.in=" + DEFAULT_ROLNAAM + "," + UPDATED_ROLNAAM);

        // Get all the rolList where rolnaam equals to UPDATED_ROLNAAM
        defaultRolShouldNotBeFound("rolnaam.in=" + UPDATED_ROLNAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam is not null
        defaultRolShouldBeFound("rolnaam.specified=true");

        // Get all the rolList where rolnaam is null
        defaultRolShouldNotBeFound("rolnaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamContainsSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam contains DEFAULT_ROLNAAM
        defaultRolShouldBeFound("rolnaam.contains=" + DEFAULT_ROLNAAM);

        // Get all the rolList where rolnaam contains UPDATED_ROLNAAM
        defaultRolShouldNotBeFound("rolnaam.contains=" + UPDATED_ROLNAAM);
    }

    @Test
    @Transactional
    void getAllRolsByRolnaamNotContainsSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where rolnaam does not contain DEFAULT_ROLNAAM
        defaultRolShouldNotBeFound("rolnaam.doesNotContain=" + DEFAULT_ROLNAAM);

        // Get all the rolList where rolnaam does not contain UPDATED_ROLNAAM
        defaultRolShouldBeFound("rolnaam.doesNotContain=" + UPDATED_ROLNAAM);
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
    void getAllRolsByStartdatumRolIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startdatumRol equals to DEFAULT_STARTDATUM_ROL
        defaultRolShouldBeFound("startdatumRol.equals=" + DEFAULT_STARTDATUM_ROL);

        // Get all the rolList where startdatumRol equals to UPDATED_STARTDATUM_ROL
        defaultRolShouldNotBeFound("startdatumRol.equals=" + UPDATED_STARTDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartdatumRolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startdatumRol not equals to DEFAULT_STARTDATUM_ROL
        defaultRolShouldNotBeFound("startdatumRol.notEquals=" + DEFAULT_STARTDATUM_ROL);

        // Get all the rolList where startdatumRol not equals to UPDATED_STARTDATUM_ROL
        defaultRolShouldBeFound("startdatumRol.notEquals=" + UPDATED_STARTDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartdatumRolIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startdatumRol in DEFAULT_STARTDATUM_ROL or UPDATED_STARTDATUM_ROL
        defaultRolShouldBeFound("startdatumRol.in=" + DEFAULT_STARTDATUM_ROL + "," + UPDATED_STARTDATUM_ROL);

        // Get all the rolList where startdatumRol equals to UPDATED_STARTDATUM_ROL
        defaultRolShouldNotBeFound("startdatumRol.in=" + UPDATED_STARTDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByStartdatumRolIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where startdatumRol is not null
        defaultRolShouldBeFound("startdatumRol.specified=true");

        // Get all the rolList where startdatumRol is null
        defaultRolShouldNotBeFound("startdatumRol.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByEinddatumRolIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where einddatumRol equals to DEFAULT_EINDDATUM_ROL
        defaultRolShouldBeFound("einddatumRol.equals=" + DEFAULT_EINDDATUM_ROL);

        // Get all the rolList where einddatumRol equals to UPDATED_EINDDATUM_ROL
        defaultRolShouldNotBeFound("einddatumRol.equals=" + UPDATED_EINDDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEinddatumRolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where einddatumRol not equals to DEFAULT_EINDDATUM_ROL
        defaultRolShouldNotBeFound("einddatumRol.notEquals=" + DEFAULT_EINDDATUM_ROL);

        // Get all the rolList where einddatumRol not equals to UPDATED_EINDDATUM_ROL
        defaultRolShouldBeFound("einddatumRol.notEquals=" + UPDATED_EINDDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEinddatumRolIsInShouldWork() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where einddatumRol in DEFAULT_EINDDATUM_ROL or UPDATED_EINDDATUM_ROL
        defaultRolShouldBeFound("einddatumRol.in=" + DEFAULT_EINDDATUM_ROL + "," + UPDATED_EINDDATUM_ROL);

        // Get all the rolList where einddatumRol equals to UPDATED_EINDDATUM_ROL
        defaultRolShouldNotBeFound("einddatumRol.in=" + UPDATED_EINDDATUM_ROL);
    }

    @Test
    @Transactional
    void getAllRolsByEinddatumRolIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList where einddatumRol is not null
        defaultRolShouldBeFound("einddatumRol.specified=true");

        // Get all the rolList where einddatumRol is null
        defaultRolShouldNotBeFound("einddatumRol.specified=false");
    }

    @Test
    @Transactional
    void getAllRolsByRelatieIsEqualToSomething() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);
        Relatie relatie;
        if (TestUtil.findAll(em, Relatie.class).isEmpty()) {
            relatie = RelatieResourceIT.createEntity(em);
            em.persist(relatie);
            em.flush();
        } else {
            relatie = TestUtil.findAll(em, Relatie.class).get(0);
        }
        em.persist(relatie);
        em.flush();
        rol.setRelatie(relatie);
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
            .andExpect(jsonPath("$.[*].relatie").value(hasItem(DEFAULT_RELATIE.toString())))
            .andExpect(jsonPath("$.[*].rolnaam").value(hasItem(DEFAULT_ROLNAAM)))
            .andExpect(jsonPath("$.[*].jeugdschaatsen").value(hasItem(DEFAULT_JEUGDSCHAATSEN.booleanValue())))
            .andExpect(jsonPath("$.[*].startdatumRol").value(hasItem(DEFAULT_STARTDATUM_ROL.toString())))
            .andExpect(jsonPath("$.[*].einddatumRol").value(hasItem(DEFAULT_EINDDATUM_ROL.toString())));

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
            .relatie(UPDATED_RELATIE)
            .rolnaam(UPDATED_ROLNAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startdatumRol(UPDATED_STARTDATUM_ROL)
            .einddatumRol(UPDATED_EINDDATUM_ROL);

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
        assertThat(testRol.getRelatie()).isEqualTo(UPDATED_RELATIE);
        assertThat(testRol.getRolnaam()).isEqualTo(UPDATED_ROLNAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(UPDATED_JEUGDSCHAATSEN);
        assertThat(testRol.getStartdatumRol()).isEqualTo(UPDATED_STARTDATUM_ROL);
        assertThat(testRol.getEinddatumRol()).isEqualTo(UPDATED_EINDDATUM_ROL);
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

        partialUpdatedRol.relatie(UPDATED_RELATIE).jeugdschaatsen(UPDATED_JEUGDSCHAATSEN).einddatumRol(UPDATED_EINDDATUM_ROL);

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
        assertThat(testRol.getRelatie()).isEqualTo(UPDATED_RELATIE);
        assertThat(testRol.getRolnaam()).isEqualTo(DEFAULT_ROLNAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(UPDATED_JEUGDSCHAATSEN);
        assertThat(testRol.getStartdatumRol()).isEqualTo(DEFAULT_STARTDATUM_ROL);
        assertThat(testRol.getEinddatumRol()).isEqualTo(UPDATED_EINDDATUM_ROL);
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
            .relatie(UPDATED_RELATIE)
            .rolnaam(UPDATED_ROLNAAM)
            .jeugdschaatsen(UPDATED_JEUGDSCHAATSEN)
            .startdatumRol(UPDATED_STARTDATUM_ROL)
            .einddatumRol(UPDATED_EINDDATUM_ROL);

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
        assertThat(testRol.getRelatie()).isEqualTo(UPDATED_RELATIE);
        assertThat(testRol.getRolnaam()).isEqualTo(UPDATED_ROLNAAM);
        assertThat(testRol.getJeugdschaatsen()).isEqualTo(UPDATED_JEUGDSCHAATSEN);
        assertThat(testRol.getStartdatumRol()).isEqualTo(UPDATED_STARTDATUM_ROL);
        assertThat(testRol.getEinddatumRol()).isEqualTo(UPDATED_EINDDATUM_ROL);
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
