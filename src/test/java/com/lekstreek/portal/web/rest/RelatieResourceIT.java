package com.lekstreek.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lekstreek.portal.IntegrationTest;
import com.lekstreek.portal.domain.Relatie;
import com.lekstreek.portal.domain.Rol;
import com.lekstreek.portal.domain.enumeration.Geslacht;
import com.lekstreek.portal.domain.enumeration.RelatieType;
import com.lekstreek.portal.repository.RelatieRepository;
import com.lekstreek.portal.service.RelatieService;
import com.lekstreek.portal.service.criteria.RelatieCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RelatieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RelatieResourceIT {

    private static final String DEFAULT_VOOR_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOOR_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ACHTER_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTER_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_INITIALEN = "AAAAAAAAAA";
    private static final String UPDATED_INITIALEN = "BBBBBBBBBB";

    private static final String DEFAULT_WEERGAVE_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_WEERGAVE_NAAM = "BBBBBBBBBB";

    private static final Geslacht DEFAULT_GESLACHT = Geslacht.MAN;
    private static final Geslacht UPDATED_GESLACHT = Geslacht.VROUW;

    private static final LocalDate DEFAULT_GEBOORTE_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBOORTE_DATUM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_GEBOORTE_DATUM = LocalDate.ofEpochDay(-1L);

    private static final RelatieType DEFAULT_RELATIE_TYPE = RelatieType.LID;
    private static final RelatieType UPDATED_RELATIE_TYPE = RelatieType.JEUGDSCHAATSLID;

    private static final Instant DEFAULT_INSCHRIJVINGS_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSCHRIJVINGS_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STRAAT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAAT_NAAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_HUIS_NUMMER = 1;
    private static final Integer UPDATED_HUIS_NUMMER = 2;
    private static final Integer SMALLER_HUIS_NUMMER = 1 - 1;

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WOON_PLAATS = "AAAAAAAAAA";
    private static final String UPDATED_WOON_PLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_LAND = "AAAAAAAAAA";
    private static final String UPDATED_LAND = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFOON_NUMMER = 1;
    private static final Integer UPDATED_TELEFOON_NUMMER = 2;
    private static final Integer SMALLER_TELEFOON_NUMMER = 1 - 1;

    private static final Integer DEFAULT_TELEFOON_NUMMER_2 = 1;
    private static final Integer UPDATED_TELEFOON_NUMMER_2 = 2;
    private static final Integer SMALLER_TELEFOON_NUMMER_2 = 1 - 1;

    private static final Integer DEFAULT_TELEFOON_NUMMER_3 = 1;
    private static final Integer UPDATED_TELEFOON_NUMMER_3 = 2;
    private static final Integer SMALLER_TELEFOON_NUMMER_3 = 1 - 1;

    private static final String DEFAULT_IBAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IBAN_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_KNSB_RELATIE_NUMMER = 1L;
    private static final Long UPDATED_KNSB_RELATIE_NUMMER = 2L;
    private static final Long SMALLER_KNSB_RELATIE_NUMMER = 1L - 1L;

    private static final byte[] DEFAULT_PASFOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PASFOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PASFOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PASFOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PRIVACY_VERKLARING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRIVACY_VERKLARING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/relaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RelatieRepository relatieRepository;

    @Mock
    private RelatieRepository relatieRepositoryMock;

    @Mock
    private RelatieService relatieServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatieMockMvc;

    private Relatie relatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatie createEntity(EntityManager em) {
        Relatie relatie = new Relatie()
            .voorNaam(DEFAULT_VOOR_NAAM)
            .achterNaam(DEFAULT_ACHTER_NAAM)
            .initialen(DEFAULT_INITIALEN)
            .weergaveNaam(DEFAULT_WEERGAVE_NAAM)
            .geslacht(DEFAULT_GESLACHT)
            .geboorteDatum(DEFAULT_GEBOORTE_DATUM)
            .relatieType(DEFAULT_RELATIE_TYPE)
            .inschrijvingsDatum(DEFAULT_INSCHRIJVINGS_DATUM)
            .straatNaam(DEFAULT_STRAAT_NAAM)
            .huisNummer(DEFAULT_HUIS_NUMMER)
            .postCode(DEFAULT_POST_CODE)
            .woonPlaats(DEFAULT_WOON_PLAATS)
            .land(DEFAULT_LAND)
            .email(DEFAULT_EMAIL)
            .email2(DEFAULT_EMAIL_2)
            .telefoonNummer(DEFAULT_TELEFOON_NUMMER)
            .telefoonNummer2(DEFAULT_TELEFOON_NUMMER_2)
            .telefoonNummer3(DEFAULT_TELEFOON_NUMMER_3)
            .ibanCode(DEFAULT_IBAN_CODE)
            .knsbRelatieNummer(DEFAULT_KNSB_RELATIE_NUMMER)
            .pasfoto(DEFAULT_PASFOTO)
            .pasfotoContentType(DEFAULT_PASFOTO_CONTENT_TYPE)
            .privacyVerklaring(DEFAULT_PRIVACY_VERKLARING)
            .privacyVerklaringContentType(DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE);
        return relatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatie createUpdatedEntity(EntityManager em) {
        Relatie relatie = new Relatie()
            .voorNaam(UPDATED_VOOR_NAAM)
            .achterNaam(UPDATED_ACHTER_NAAM)
            .initialen(UPDATED_INITIALEN)
            .weergaveNaam(UPDATED_WEERGAVE_NAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboorteDatum(UPDATED_GEBOORTE_DATUM)
            .relatieType(UPDATED_RELATIE_TYPE)
            .inschrijvingsDatum(UPDATED_INSCHRIJVINGS_DATUM)
            .straatNaam(UPDATED_STRAAT_NAAM)
            .huisNummer(UPDATED_HUIS_NUMMER)
            .postCode(UPDATED_POST_CODE)
            .woonPlaats(UPDATED_WOON_PLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonNummer(UPDATED_TELEFOON_NUMMER)
            .telefoonNummer2(UPDATED_TELEFOON_NUMMER_2)
            .telefoonNummer3(UPDATED_TELEFOON_NUMMER_3)
            .ibanCode(UPDATED_IBAN_CODE)
            .knsbRelatieNummer(UPDATED_KNSB_RELATIE_NUMMER)
            .pasfoto(UPDATED_PASFOTO)
            .pasfotoContentType(UPDATED_PASFOTO_CONTENT_TYPE)
            .privacyVerklaring(UPDATED_PRIVACY_VERKLARING)
            .privacyVerklaringContentType(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);
        return relatie;
    }

    @BeforeEach
    public void initTest() {
        relatie = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatie() throws Exception {
        int databaseSizeBeforeCreate = relatieRepository.findAll().size();
        // Create the Relatie
        restRelatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatie)))
            .andExpect(status().isCreated());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeCreate + 1);
        Relatie testRelatie = relatieList.get(relatieList.size() - 1);
        assertThat(testRelatie.getVoorNaam()).isEqualTo(DEFAULT_VOOR_NAAM);
        assertThat(testRelatie.getAchterNaam()).isEqualTo(DEFAULT_ACHTER_NAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(DEFAULT_INITIALEN);
        assertThat(testRelatie.getWeergaveNaam()).isEqualTo(DEFAULT_WEERGAVE_NAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(DEFAULT_GESLACHT);
        assertThat(testRelatie.getGeboorteDatum()).isEqualTo(DEFAULT_GEBOORTE_DATUM);
        assertThat(testRelatie.getRelatieType()).isEqualTo(DEFAULT_RELATIE_TYPE);
        assertThat(testRelatie.getInschrijvingsDatum()).isEqualTo(DEFAULT_INSCHRIJVINGS_DATUM);
        assertThat(testRelatie.getStraatNaam()).isEqualTo(DEFAULT_STRAAT_NAAM);
        assertThat(testRelatie.getHuisNummer()).isEqualTo(DEFAULT_HUIS_NUMMER);
        assertThat(testRelatie.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testRelatie.getWoonPlaats()).isEqualTo(DEFAULT_WOON_PLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(DEFAULT_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testRelatie.getTelefoonNummer()).isEqualTo(DEFAULT_TELEFOON_NUMMER);
        assertThat(testRelatie.getTelefoonNummer2()).isEqualTo(DEFAULT_TELEFOON_NUMMER_2);
        assertThat(testRelatie.getTelefoonNummer3()).isEqualTo(DEFAULT_TELEFOON_NUMMER_3);
        assertThat(testRelatie.getIbanCode()).isEqualTo(DEFAULT_IBAN_CODE);
        assertThat(testRelatie.getKnsbRelatieNummer()).isEqualTo(DEFAULT_KNSB_RELATIE_NUMMER);
        assertThat(testRelatie.getPasfoto()).isEqualTo(DEFAULT_PASFOTO);
        assertThat(testRelatie.getPasfotoContentType()).isEqualTo(DEFAULT_PASFOTO_CONTENT_TYPE);
        assertThat(testRelatie.getPrivacyVerklaring()).isEqualTo(DEFAULT_PRIVACY_VERKLARING);
        assertThat(testRelatie.getPrivacyVerklaringContentType()).isEqualTo(DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createRelatieWithExistingId() throws Exception {
        // Create the Relatie with an existing ID
        relatieRepository.saveAndFlush(relatie);

        int databaseSizeBeforeCreate = relatieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatie)))
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelaties() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatie.getId().toString())))
            .andExpect(jsonPath("$.[*].voorNaam").value(hasItem(DEFAULT_VOOR_NAAM)))
            .andExpect(jsonPath("$.[*].achterNaam").value(hasItem(DEFAULT_ACHTER_NAAM)))
            .andExpect(jsonPath("$.[*].initialen").value(hasItem(DEFAULT_INITIALEN)))
            .andExpect(jsonPath("$.[*].weergaveNaam").value(hasItem(DEFAULT_WEERGAVE_NAAM)))
            .andExpect(jsonPath("$.[*].geslacht").value(hasItem(DEFAULT_GESLACHT.toString())))
            .andExpect(jsonPath("$.[*].geboorteDatum").value(hasItem(DEFAULT_GEBOORTE_DATUM.toString())))
            .andExpect(jsonPath("$.[*].relatieType").value(hasItem(DEFAULT_RELATIE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inschrijvingsDatum").value(hasItem(DEFAULT_INSCHRIJVINGS_DATUM.toString())))
            .andExpect(jsonPath("$.[*].straatNaam").value(hasItem(DEFAULT_STRAAT_NAAM)))
            .andExpect(jsonPath("$.[*].huisNummer").value(hasItem(DEFAULT_HUIS_NUMMER)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].woonPlaats").value(hasItem(DEFAULT_WOON_PLAATS)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].telefoonNummer").value(hasItem(DEFAULT_TELEFOON_NUMMER)))
            .andExpect(jsonPath("$.[*].telefoonNummer2").value(hasItem(DEFAULT_TELEFOON_NUMMER_2)))
            .andExpect(jsonPath("$.[*].telefoonNummer3").value(hasItem(DEFAULT_TELEFOON_NUMMER_3)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].knsbRelatieNummer").value(hasItem(DEFAULT_KNSB_RELATIE_NUMMER.intValue())))
            .andExpect(jsonPath("$.[*].pasfotoContentType").value(hasItem(DEFAULT_PASFOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pasfoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASFOTO))))
            .andExpect(jsonPath("$.[*].privacyVerklaringContentType").value(hasItem(DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].privacyVerklaring").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVACY_VERKLARING))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(relatieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(relatieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(relatieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(relatieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get the relatie
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL_ID, relatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatie.getId().toString()))
            .andExpect(jsonPath("$.voorNaam").value(DEFAULT_VOOR_NAAM))
            .andExpect(jsonPath("$.achterNaam").value(DEFAULT_ACHTER_NAAM))
            .andExpect(jsonPath("$.initialen").value(DEFAULT_INITIALEN))
            .andExpect(jsonPath("$.weergaveNaam").value(DEFAULT_WEERGAVE_NAAM))
            .andExpect(jsonPath("$.geslacht").value(DEFAULT_GESLACHT.toString()))
            .andExpect(jsonPath("$.geboorteDatum").value(DEFAULT_GEBOORTE_DATUM.toString()))
            .andExpect(jsonPath("$.relatieType").value(DEFAULT_RELATIE_TYPE.toString()))
            .andExpect(jsonPath("$.inschrijvingsDatum").value(DEFAULT_INSCHRIJVINGS_DATUM.toString()))
            .andExpect(jsonPath("$.straatNaam").value(DEFAULT_STRAAT_NAAM))
            .andExpect(jsonPath("$.huisNummer").value(DEFAULT_HUIS_NUMMER))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.woonPlaats").value(DEFAULT_WOON_PLAATS))
            .andExpect(jsonPath("$.land").value(DEFAULT_LAND))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.telefoonNummer").value(DEFAULT_TELEFOON_NUMMER))
            .andExpect(jsonPath("$.telefoonNummer2").value(DEFAULT_TELEFOON_NUMMER_2))
            .andExpect(jsonPath("$.telefoonNummer3").value(DEFAULT_TELEFOON_NUMMER_3))
            .andExpect(jsonPath("$.ibanCode").value(DEFAULT_IBAN_CODE))
            .andExpect(jsonPath("$.knsbRelatieNummer").value(DEFAULT_KNSB_RELATIE_NUMMER.intValue()))
            .andExpect(jsonPath("$.pasfotoContentType").value(DEFAULT_PASFOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.pasfoto").value(Base64Utils.encodeToString(DEFAULT_PASFOTO)))
            .andExpect(jsonPath("$.privacyVerklaringContentType").value(DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE))
            .andExpect(jsonPath("$.privacyVerklaring").value(Base64Utils.encodeToString(DEFAULT_PRIVACY_VERKLARING)));
    }

    @Test
    @Transactional
    void getRelatiesByIdFiltering() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        UUID id = relatie.getId();

        defaultRelatieShouldBeFound("id.equals=" + id);
        defaultRelatieShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam equals to DEFAULT_VOOR_NAAM
        defaultRelatieShouldBeFound("voorNaam.equals=" + DEFAULT_VOOR_NAAM);

        // Get all the relatieList where voorNaam equals to UPDATED_VOOR_NAAM
        defaultRelatieShouldNotBeFound("voorNaam.equals=" + UPDATED_VOOR_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam not equals to DEFAULT_VOOR_NAAM
        defaultRelatieShouldNotBeFound("voorNaam.notEquals=" + DEFAULT_VOOR_NAAM);

        // Get all the relatieList where voorNaam not equals to UPDATED_VOOR_NAAM
        defaultRelatieShouldBeFound("voorNaam.notEquals=" + UPDATED_VOOR_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam in DEFAULT_VOOR_NAAM or UPDATED_VOOR_NAAM
        defaultRelatieShouldBeFound("voorNaam.in=" + DEFAULT_VOOR_NAAM + "," + UPDATED_VOOR_NAAM);

        // Get all the relatieList where voorNaam equals to UPDATED_VOOR_NAAM
        defaultRelatieShouldNotBeFound("voorNaam.in=" + UPDATED_VOOR_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam is not null
        defaultRelatieShouldBeFound("voorNaam.specified=true");

        // Get all the relatieList where voorNaam is null
        defaultRelatieShouldNotBeFound("voorNaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam contains DEFAULT_VOOR_NAAM
        defaultRelatieShouldBeFound("voorNaam.contains=" + DEFAULT_VOOR_NAAM);

        // Get all the relatieList where voorNaam contains UPDATED_VOOR_NAAM
        defaultRelatieShouldNotBeFound("voorNaam.contains=" + UPDATED_VOOR_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoorNaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voorNaam does not contain DEFAULT_VOOR_NAAM
        defaultRelatieShouldNotBeFound("voorNaam.doesNotContain=" + DEFAULT_VOOR_NAAM);

        // Get all the relatieList where voorNaam does not contain UPDATED_VOOR_NAAM
        defaultRelatieShouldBeFound("voorNaam.doesNotContain=" + UPDATED_VOOR_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam equals to DEFAULT_ACHTER_NAAM
        defaultRelatieShouldBeFound("achterNaam.equals=" + DEFAULT_ACHTER_NAAM);

        // Get all the relatieList where achterNaam equals to UPDATED_ACHTER_NAAM
        defaultRelatieShouldNotBeFound("achterNaam.equals=" + UPDATED_ACHTER_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam not equals to DEFAULT_ACHTER_NAAM
        defaultRelatieShouldNotBeFound("achterNaam.notEquals=" + DEFAULT_ACHTER_NAAM);

        // Get all the relatieList where achterNaam not equals to UPDATED_ACHTER_NAAM
        defaultRelatieShouldBeFound("achterNaam.notEquals=" + UPDATED_ACHTER_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam in DEFAULT_ACHTER_NAAM or UPDATED_ACHTER_NAAM
        defaultRelatieShouldBeFound("achterNaam.in=" + DEFAULT_ACHTER_NAAM + "," + UPDATED_ACHTER_NAAM);

        // Get all the relatieList where achterNaam equals to UPDATED_ACHTER_NAAM
        defaultRelatieShouldNotBeFound("achterNaam.in=" + UPDATED_ACHTER_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam is not null
        defaultRelatieShouldBeFound("achterNaam.specified=true");

        // Get all the relatieList where achterNaam is null
        defaultRelatieShouldNotBeFound("achterNaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam contains DEFAULT_ACHTER_NAAM
        defaultRelatieShouldBeFound("achterNaam.contains=" + DEFAULT_ACHTER_NAAM);

        // Get all the relatieList where achterNaam contains UPDATED_ACHTER_NAAM
        defaultRelatieShouldNotBeFound("achterNaam.contains=" + UPDATED_ACHTER_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchterNaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achterNaam does not contain DEFAULT_ACHTER_NAAM
        defaultRelatieShouldNotBeFound("achterNaam.doesNotContain=" + DEFAULT_ACHTER_NAAM);

        // Get all the relatieList where achterNaam does not contain UPDATED_ACHTER_NAAM
        defaultRelatieShouldBeFound("achterNaam.doesNotContain=" + UPDATED_ACHTER_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen equals to DEFAULT_INITIALEN
        defaultRelatieShouldBeFound("initialen.equals=" + DEFAULT_INITIALEN);

        // Get all the relatieList where initialen equals to UPDATED_INITIALEN
        defaultRelatieShouldNotBeFound("initialen.equals=" + UPDATED_INITIALEN);
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen not equals to DEFAULT_INITIALEN
        defaultRelatieShouldNotBeFound("initialen.notEquals=" + DEFAULT_INITIALEN);

        // Get all the relatieList where initialen not equals to UPDATED_INITIALEN
        defaultRelatieShouldBeFound("initialen.notEquals=" + UPDATED_INITIALEN);
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen in DEFAULT_INITIALEN or UPDATED_INITIALEN
        defaultRelatieShouldBeFound("initialen.in=" + DEFAULT_INITIALEN + "," + UPDATED_INITIALEN);

        // Get all the relatieList where initialen equals to UPDATED_INITIALEN
        defaultRelatieShouldNotBeFound("initialen.in=" + UPDATED_INITIALEN);
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen is not null
        defaultRelatieShouldBeFound("initialen.specified=true");

        // Get all the relatieList where initialen is null
        defaultRelatieShouldNotBeFound("initialen.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen contains DEFAULT_INITIALEN
        defaultRelatieShouldBeFound("initialen.contains=" + DEFAULT_INITIALEN);

        // Get all the relatieList where initialen contains UPDATED_INITIALEN
        defaultRelatieShouldNotBeFound("initialen.contains=" + UPDATED_INITIALEN);
    }

    @Test
    @Transactional
    void getAllRelatiesByInitialenNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where initialen does not contain DEFAULT_INITIALEN
        defaultRelatieShouldNotBeFound("initialen.doesNotContain=" + DEFAULT_INITIALEN);

        // Get all the relatieList where initialen does not contain UPDATED_INITIALEN
        defaultRelatieShouldBeFound("initialen.doesNotContain=" + UPDATED_INITIALEN);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam equals to DEFAULT_WEERGAVE_NAAM
        defaultRelatieShouldBeFound("weergaveNaam.equals=" + DEFAULT_WEERGAVE_NAAM);

        // Get all the relatieList where weergaveNaam equals to UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldNotBeFound("weergaveNaam.equals=" + UPDATED_WEERGAVE_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam not equals to DEFAULT_WEERGAVE_NAAM
        defaultRelatieShouldNotBeFound("weergaveNaam.notEquals=" + DEFAULT_WEERGAVE_NAAM);

        // Get all the relatieList where weergaveNaam not equals to UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldBeFound("weergaveNaam.notEquals=" + UPDATED_WEERGAVE_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam in DEFAULT_WEERGAVE_NAAM or UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldBeFound("weergaveNaam.in=" + DEFAULT_WEERGAVE_NAAM + "," + UPDATED_WEERGAVE_NAAM);

        // Get all the relatieList where weergaveNaam equals to UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldNotBeFound("weergaveNaam.in=" + UPDATED_WEERGAVE_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam is not null
        defaultRelatieShouldBeFound("weergaveNaam.specified=true");

        // Get all the relatieList where weergaveNaam is null
        defaultRelatieShouldNotBeFound("weergaveNaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam contains DEFAULT_WEERGAVE_NAAM
        defaultRelatieShouldBeFound("weergaveNaam.contains=" + DEFAULT_WEERGAVE_NAAM);

        // Get all the relatieList where weergaveNaam contains UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldNotBeFound("weergaveNaam.contains=" + UPDATED_WEERGAVE_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergaveNaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergaveNaam does not contain DEFAULT_WEERGAVE_NAAM
        defaultRelatieShouldNotBeFound("weergaveNaam.doesNotContain=" + DEFAULT_WEERGAVE_NAAM);

        // Get all the relatieList where weergaveNaam does not contain UPDATED_WEERGAVE_NAAM
        defaultRelatieShouldBeFound("weergaveNaam.doesNotContain=" + UPDATED_WEERGAVE_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeslachtIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geslacht equals to DEFAULT_GESLACHT
        defaultRelatieShouldBeFound("geslacht.equals=" + DEFAULT_GESLACHT);

        // Get all the relatieList where geslacht equals to UPDATED_GESLACHT
        defaultRelatieShouldNotBeFound("geslacht.equals=" + UPDATED_GESLACHT);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeslachtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geslacht not equals to DEFAULT_GESLACHT
        defaultRelatieShouldNotBeFound("geslacht.notEquals=" + DEFAULT_GESLACHT);

        // Get all the relatieList where geslacht not equals to UPDATED_GESLACHT
        defaultRelatieShouldBeFound("geslacht.notEquals=" + UPDATED_GESLACHT);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeslachtIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geslacht in DEFAULT_GESLACHT or UPDATED_GESLACHT
        defaultRelatieShouldBeFound("geslacht.in=" + DEFAULT_GESLACHT + "," + UPDATED_GESLACHT);

        // Get all the relatieList where geslacht equals to UPDATED_GESLACHT
        defaultRelatieShouldNotBeFound("geslacht.in=" + UPDATED_GESLACHT);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeslachtIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geslacht is not null
        defaultRelatieShouldBeFound("geslacht.specified=true");

        // Get all the relatieList where geslacht is null
        defaultRelatieShouldNotBeFound("geslacht.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum equals to DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.equals=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum equals to UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.equals=" + UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum not equals to DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.notEquals=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum not equals to UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.notEquals=" + UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum in DEFAULT_GEBOORTE_DATUM or UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.in=" + DEFAULT_GEBOORTE_DATUM + "," + UPDATED_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum equals to UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.in=" + UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum is not null
        defaultRelatieShouldBeFound("geboorteDatum.specified=true");

        // Get all the relatieList where geboorteDatum is null
        defaultRelatieShouldNotBeFound("geboorteDatum.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum is greater than or equal to DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.greaterThanOrEqual=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum is greater than or equal to UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.greaterThanOrEqual=" + UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum is less than or equal to DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.lessThanOrEqual=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum is less than or equal to SMALLER_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.lessThanOrEqual=" + SMALLER_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum is less than DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.lessThan=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum is less than UPDATED_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.lessThan=" + UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboorteDatumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboorteDatum is greater than DEFAULT_GEBOORTE_DATUM
        defaultRelatieShouldNotBeFound("geboorteDatum.greaterThan=" + DEFAULT_GEBOORTE_DATUM);

        // Get all the relatieList where geboorteDatum is greater than SMALLER_GEBOORTE_DATUM
        defaultRelatieShouldBeFound("geboorteDatum.greaterThan=" + SMALLER_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatieTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatieType equals to DEFAULT_RELATIE_TYPE
        defaultRelatieShouldBeFound("relatieType.equals=" + DEFAULT_RELATIE_TYPE);

        // Get all the relatieList where relatieType equals to UPDATED_RELATIE_TYPE
        defaultRelatieShouldNotBeFound("relatieType.equals=" + UPDATED_RELATIE_TYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatieTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatieType not equals to DEFAULT_RELATIE_TYPE
        defaultRelatieShouldNotBeFound("relatieType.notEquals=" + DEFAULT_RELATIE_TYPE);

        // Get all the relatieList where relatieType not equals to UPDATED_RELATIE_TYPE
        defaultRelatieShouldBeFound("relatieType.notEquals=" + UPDATED_RELATIE_TYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatieTypeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatieType in DEFAULT_RELATIE_TYPE or UPDATED_RELATIE_TYPE
        defaultRelatieShouldBeFound("relatieType.in=" + DEFAULT_RELATIE_TYPE + "," + UPDATED_RELATIE_TYPE);

        // Get all the relatieList where relatieType equals to UPDATED_RELATIE_TYPE
        defaultRelatieShouldNotBeFound("relatieType.in=" + UPDATED_RELATIE_TYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatieTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatieType is not null
        defaultRelatieShouldBeFound("relatieType.specified=true");

        // Get all the relatieList where relatieType is null
        defaultRelatieShouldNotBeFound("relatieType.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsDatumIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsDatum equals to DEFAULT_INSCHRIJVINGS_DATUM
        defaultRelatieShouldBeFound("inschrijvingsDatum.equals=" + DEFAULT_INSCHRIJVINGS_DATUM);

        // Get all the relatieList where inschrijvingsDatum equals to UPDATED_INSCHRIJVINGS_DATUM
        defaultRelatieShouldNotBeFound("inschrijvingsDatum.equals=" + UPDATED_INSCHRIJVINGS_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsDatumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsDatum not equals to DEFAULT_INSCHRIJVINGS_DATUM
        defaultRelatieShouldNotBeFound("inschrijvingsDatum.notEquals=" + DEFAULT_INSCHRIJVINGS_DATUM);

        // Get all the relatieList where inschrijvingsDatum not equals to UPDATED_INSCHRIJVINGS_DATUM
        defaultRelatieShouldBeFound("inschrijvingsDatum.notEquals=" + UPDATED_INSCHRIJVINGS_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsDatumIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsDatum in DEFAULT_INSCHRIJVINGS_DATUM or UPDATED_INSCHRIJVINGS_DATUM
        defaultRelatieShouldBeFound("inschrijvingsDatum.in=" + DEFAULT_INSCHRIJVINGS_DATUM + "," + UPDATED_INSCHRIJVINGS_DATUM);

        // Get all the relatieList where inschrijvingsDatum equals to UPDATED_INSCHRIJVINGS_DATUM
        defaultRelatieShouldNotBeFound("inschrijvingsDatum.in=" + UPDATED_INSCHRIJVINGS_DATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsDatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsDatum is not null
        defaultRelatieShouldBeFound("inschrijvingsDatum.specified=true");

        // Get all the relatieList where inschrijvingsDatum is null
        defaultRelatieShouldNotBeFound("inschrijvingsDatum.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam equals to DEFAULT_STRAAT_NAAM
        defaultRelatieShouldBeFound("straatNaam.equals=" + DEFAULT_STRAAT_NAAM);

        // Get all the relatieList where straatNaam equals to UPDATED_STRAAT_NAAM
        defaultRelatieShouldNotBeFound("straatNaam.equals=" + UPDATED_STRAAT_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam not equals to DEFAULT_STRAAT_NAAM
        defaultRelatieShouldNotBeFound("straatNaam.notEquals=" + DEFAULT_STRAAT_NAAM);

        // Get all the relatieList where straatNaam not equals to UPDATED_STRAAT_NAAM
        defaultRelatieShouldBeFound("straatNaam.notEquals=" + UPDATED_STRAAT_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam in DEFAULT_STRAAT_NAAM or UPDATED_STRAAT_NAAM
        defaultRelatieShouldBeFound("straatNaam.in=" + DEFAULT_STRAAT_NAAM + "," + UPDATED_STRAAT_NAAM);

        // Get all the relatieList where straatNaam equals to UPDATED_STRAAT_NAAM
        defaultRelatieShouldNotBeFound("straatNaam.in=" + UPDATED_STRAAT_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam is not null
        defaultRelatieShouldBeFound("straatNaam.specified=true");

        // Get all the relatieList where straatNaam is null
        defaultRelatieShouldNotBeFound("straatNaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam contains DEFAULT_STRAAT_NAAM
        defaultRelatieShouldBeFound("straatNaam.contains=" + DEFAULT_STRAAT_NAAM);

        // Get all the relatieList where straatNaam contains UPDATED_STRAAT_NAAM
        defaultRelatieShouldNotBeFound("straatNaam.contains=" + UPDATED_STRAAT_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatNaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatNaam does not contain DEFAULT_STRAAT_NAAM
        defaultRelatieShouldNotBeFound("straatNaam.doesNotContain=" + DEFAULT_STRAAT_NAAM);

        // Get all the relatieList where straatNaam does not contain UPDATED_STRAAT_NAAM
        defaultRelatieShouldBeFound("straatNaam.doesNotContain=" + UPDATED_STRAAT_NAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer equals to DEFAULT_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.equals=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer equals to UPDATED_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.equals=" + UPDATED_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer not equals to DEFAULT_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.notEquals=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer not equals to UPDATED_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.notEquals=" + UPDATED_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer in DEFAULT_HUIS_NUMMER or UPDATED_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.in=" + DEFAULT_HUIS_NUMMER + "," + UPDATED_HUIS_NUMMER);

        // Get all the relatieList where huisNummer equals to UPDATED_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.in=" + UPDATED_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer is not null
        defaultRelatieShouldBeFound("huisNummer.specified=true");

        // Get all the relatieList where huisNummer is null
        defaultRelatieShouldNotBeFound("huisNummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer is greater than or equal to DEFAULT_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.greaterThanOrEqual=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer is greater than or equal to UPDATED_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.greaterThanOrEqual=" + UPDATED_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer is less than or equal to DEFAULT_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.lessThanOrEqual=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer is less than or equal to SMALLER_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.lessThanOrEqual=" + SMALLER_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer is less than DEFAULT_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.lessThan=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer is less than UPDATED_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.lessThan=" + UPDATED_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisNummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisNummer is greater than DEFAULT_HUIS_NUMMER
        defaultRelatieShouldNotBeFound("huisNummer.greaterThan=" + DEFAULT_HUIS_NUMMER);

        // Get all the relatieList where huisNummer is greater than SMALLER_HUIS_NUMMER
        defaultRelatieShouldBeFound("huisNummer.greaterThan=" + SMALLER_HUIS_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode equals to DEFAULT_POST_CODE
        defaultRelatieShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the relatieList where postCode equals to UPDATED_POST_CODE
        defaultRelatieShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode not equals to DEFAULT_POST_CODE
        defaultRelatieShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the relatieList where postCode not equals to UPDATED_POST_CODE
        defaultRelatieShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultRelatieShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the relatieList where postCode equals to UPDATED_POST_CODE
        defaultRelatieShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode is not null
        defaultRelatieShouldBeFound("postCode.specified=true");

        // Get all the relatieList where postCode is null
        defaultRelatieShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode contains DEFAULT_POST_CODE
        defaultRelatieShouldBeFound("postCode.contains=" + DEFAULT_POST_CODE);

        // Get all the relatieList where postCode contains UPDATED_POST_CODE
        defaultRelatieShouldNotBeFound("postCode.contains=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostCodeNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postCode does not contain DEFAULT_POST_CODE
        defaultRelatieShouldNotBeFound("postCode.doesNotContain=" + DEFAULT_POST_CODE);

        // Get all the relatieList where postCode does not contain UPDATED_POST_CODE
        defaultRelatieShouldBeFound("postCode.doesNotContain=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats equals to DEFAULT_WOON_PLAATS
        defaultRelatieShouldBeFound("woonPlaats.equals=" + DEFAULT_WOON_PLAATS);

        // Get all the relatieList where woonPlaats equals to UPDATED_WOON_PLAATS
        defaultRelatieShouldNotBeFound("woonPlaats.equals=" + UPDATED_WOON_PLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats not equals to DEFAULT_WOON_PLAATS
        defaultRelatieShouldNotBeFound("woonPlaats.notEquals=" + DEFAULT_WOON_PLAATS);

        // Get all the relatieList where woonPlaats not equals to UPDATED_WOON_PLAATS
        defaultRelatieShouldBeFound("woonPlaats.notEquals=" + UPDATED_WOON_PLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats in DEFAULT_WOON_PLAATS or UPDATED_WOON_PLAATS
        defaultRelatieShouldBeFound("woonPlaats.in=" + DEFAULT_WOON_PLAATS + "," + UPDATED_WOON_PLAATS);

        // Get all the relatieList where woonPlaats equals to UPDATED_WOON_PLAATS
        defaultRelatieShouldNotBeFound("woonPlaats.in=" + UPDATED_WOON_PLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats is not null
        defaultRelatieShouldBeFound("woonPlaats.specified=true");

        // Get all the relatieList where woonPlaats is null
        defaultRelatieShouldNotBeFound("woonPlaats.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats contains DEFAULT_WOON_PLAATS
        defaultRelatieShouldBeFound("woonPlaats.contains=" + DEFAULT_WOON_PLAATS);

        // Get all the relatieList where woonPlaats contains UPDATED_WOON_PLAATS
        defaultRelatieShouldNotBeFound("woonPlaats.contains=" + UPDATED_WOON_PLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonPlaatsNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonPlaats does not contain DEFAULT_WOON_PLAATS
        defaultRelatieShouldNotBeFound("woonPlaats.doesNotContain=" + DEFAULT_WOON_PLAATS);

        // Get all the relatieList where woonPlaats does not contain UPDATED_WOON_PLAATS
        defaultRelatieShouldBeFound("woonPlaats.doesNotContain=" + UPDATED_WOON_PLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByLandIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land equals to DEFAULT_LAND
        defaultRelatieShouldBeFound("land.equals=" + DEFAULT_LAND);

        // Get all the relatieList where land equals to UPDATED_LAND
        defaultRelatieShouldNotBeFound("land.equals=" + UPDATED_LAND);
    }

    @Test
    @Transactional
    void getAllRelatiesByLandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land not equals to DEFAULT_LAND
        defaultRelatieShouldNotBeFound("land.notEquals=" + DEFAULT_LAND);

        // Get all the relatieList where land not equals to UPDATED_LAND
        defaultRelatieShouldBeFound("land.notEquals=" + UPDATED_LAND);
    }

    @Test
    @Transactional
    void getAllRelatiesByLandIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land in DEFAULT_LAND or UPDATED_LAND
        defaultRelatieShouldBeFound("land.in=" + DEFAULT_LAND + "," + UPDATED_LAND);

        // Get all the relatieList where land equals to UPDATED_LAND
        defaultRelatieShouldNotBeFound("land.in=" + UPDATED_LAND);
    }

    @Test
    @Transactional
    void getAllRelatiesByLandIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land is not null
        defaultRelatieShouldBeFound("land.specified=true");

        // Get all the relatieList where land is null
        defaultRelatieShouldNotBeFound("land.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByLandContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land contains DEFAULT_LAND
        defaultRelatieShouldBeFound("land.contains=" + DEFAULT_LAND);

        // Get all the relatieList where land contains UPDATED_LAND
        defaultRelatieShouldNotBeFound("land.contains=" + UPDATED_LAND);
    }

    @Test
    @Transactional
    void getAllRelatiesByLandNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where land does not contain DEFAULT_LAND
        defaultRelatieShouldNotBeFound("land.doesNotContain=" + DEFAULT_LAND);

        // Get all the relatieList where land does not contain UPDATED_LAND
        defaultRelatieShouldBeFound("land.doesNotContain=" + UPDATED_LAND);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email equals to DEFAULT_EMAIL
        defaultRelatieShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the relatieList where email equals to UPDATED_EMAIL
        defaultRelatieShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email not equals to DEFAULT_EMAIL
        defaultRelatieShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the relatieList where email not equals to UPDATED_EMAIL
        defaultRelatieShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultRelatieShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the relatieList where email equals to UPDATED_EMAIL
        defaultRelatieShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email is not null
        defaultRelatieShouldBeFound("email.specified=true");

        // Get all the relatieList where email is null
        defaultRelatieShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email contains DEFAULT_EMAIL
        defaultRelatieShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the relatieList where email contains UPDATED_EMAIL
        defaultRelatieShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email does not contain DEFAULT_EMAIL
        defaultRelatieShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the relatieList where email does not contain UPDATED_EMAIL
        defaultRelatieShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2IsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 equals to DEFAULT_EMAIL_2
        defaultRelatieShouldBeFound("email2.equals=" + DEFAULT_EMAIL_2);

        // Get all the relatieList where email2 equals to UPDATED_EMAIL_2
        defaultRelatieShouldNotBeFound("email2.equals=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 not equals to DEFAULT_EMAIL_2
        defaultRelatieShouldNotBeFound("email2.notEquals=" + DEFAULT_EMAIL_2);

        // Get all the relatieList where email2 not equals to UPDATED_EMAIL_2
        defaultRelatieShouldBeFound("email2.notEquals=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2IsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 in DEFAULT_EMAIL_2 or UPDATED_EMAIL_2
        defaultRelatieShouldBeFound("email2.in=" + DEFAULT_EMAIL_2 + "," + UPDATED_EMAIL_2);

        // Get all the relatieList where email2 equals to UPDATED_EMAIL_2
        defaultRelatieShouldNotBeFound("email2.in=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2IsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 is not null
        defaultRelatieShouldBeFound("email2.specified=true");

        // Get all the relatieList where email2 is null
        defaultRelatieShouldNotBeFound("email2.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2ContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 contains DEFAULT_EMAIL_2
        defaultRelatieShouldBeFound("email2.contains=" + DEFAULT_EMAIL_2);

        // Get all the relatieList where email2 contains UPDATED_EMAIL_2
        defaultRelatieShouldNotBeFound("email2.contains=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByEmail2NotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where email2 does not contain DEFAULT_EMAIL_2
        defaultRelatieShouldNotBeFound("email2.doesNotContain=" + DEFAULT_EMAIL_2);

        // Get all the relatieList where email2 does not contain UPDATED_EMAIL_2
        defaultRelatieShouldBeFound("email2.doesNotContain=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer equals to DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.equals=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer equals to UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.equals=" + UPDATED_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer not equals to DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.notEquals=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer not equals to UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.notEquals=" + UPDATED_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer in DEFAULT_TELEFOON_NUMMER or UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.in=" + DEFAULT_TELEFOON_NUMMER + "," + UPDATED_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer equals to UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.in=" + UPDATED_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer is not null
        defaultRelatieShouldBeFound("telefoonNummer.specified=true");

        // Get all the relatieList where telefoonNummer is null
        defaultRelatieShouldNotBeFound("telefoonNummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer is greater than or equal to DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.greaterThanOrEqual=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer is greater than or equal to UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.greaterThanOrEqual=" + UPDATED_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer is less than or equal to DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.lessThanOrEqual=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer is less than or equal to SMALLER_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.lessThanOrEqual=" + SMALLER_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer is less than DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.lessThan=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer is less than UPDATED_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.lessThan=" + UPDATED_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer is greater than DEFAULT_TELEFOON_NUMMER
        defaultRelatieShouldNotBeFound("telefoonNummer.greaterThan=" + DEFAULT_TELEFOON_NUMMER);

        // Get all the relatieList where telefoonNummer is greater than SMALLER_TELEFOON_NUMMER
        defaultRelatieShouldBeFound("telefoonNummer.greaterThan=" + SMALLER_TELEFOON_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 equals to DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.equals=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 equals to UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.equals=" + UPDATED_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 not equals to DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.notEquals=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 not equals to UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.notEquals=" + UPDATED_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 in DEFAULT_TELEFOON_NUMMER_2 or UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.in=" + DEFAULT_TELEFOON_NUMMER_2 + "," + UPDATED_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 equals to UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.in=" + UPDATED_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 is not null
        defaultRelatieShouldBeFound("telefoonNummer2.specified=true");

        // Get all the relatieList where telefoonNummer2 is null
        defaultRelatieShouldNotBeFound("telefoonNummer2.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 is greater than or equal to DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.greaterThanOrEqual=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 is greater than or equal to UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.greaterThanOrEqual=" + UPDATED_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 is less than or equal to DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.lessThanOrEqual=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 is less than or equal to SMALLER_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.lessThanOrEqual=" + SMALLER_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 is less than DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.lessThan=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 is less than UPDATED_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.lessThan=" + UPDATED_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer2 is greater than DEFAULT_TELEFOON_NUMMER_2
        defaultRelatieShouldNotBeFound("telefoonNummer2.greaterThan=" + DEFAULT_TELEFOON_NUMMER_2);

        // Get all the relatieList where telefoonNummer2 is greater than SMALLER_TELEFOON_NUMMER_2
        defaultRelatieShouldBeFound("telefoonNummer2.greaterThan=" + SMALLER_TELEFOON_NUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 equals to DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.equals=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 equals to UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.equals=" + UPDATED_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 not equals to DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.notEquals=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 not equals to UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.notEquals=" + UPDATED_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 in DEFAULT_TELEFOON_NUMMER_3 or UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.in=" + DEFAULT_TELEFOON_NUMMER_3 + "," + UPDATED_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 equals to UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.in=" + UPDATED_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 is not null
        defaultRelatieShouldBeFound("telefoonNummer3.specified=true");

        // Get all the relatieList where telefoonNummer3 is null
        defaultRelatieShouldNotBeFound("telefoonNummer3.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 is greater than or equal to DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.greaterThanOrEqual=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 is greater than or equal to UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.greaterThanOrEqual=" + UPDATED_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 is less than or equal to DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.lessThanOrEqual=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 is less than or equal to SMALLER_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.lessThanOrEqual=" + SMALLER_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 is less than DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.lessThan=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 is less than UPDATED_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.lessThan=" + UPDATED_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonNummer3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonNummer3 is greater than DEFAULT_TELEFOON_NUMMER_3
        defaultRelatieShouldNotBeFound("telefoonNummer3.greaterThan=" + DEFAULT_TELEFOON_NUMMER_3);

        // Get all the relatieList where telefoonNummer3 is greater than SMALLER_TELEFOON_NUMMER_3
        defaultRelatieShouldBeFound("telefoonNummer3.greaterThan=" + SMALLER_TELEFOON_NUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode equals to DEFAULT_IBAN_CODE
        defaultRelatieShouldBeFound("ibanCode.equals=" + DEFAULT_IBAN_CODE);

        // Get all the relatieList where ibanCode equals to UPDATED_IBAN_CODE
        defaultRelatieShouldNotBeFound("ibanCode.equals=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode not equals to DEFAULT_IBAN_CODE
        defaultRelatieShouldNotBeFound("ibanCode.notEquals=" + DEFAULT_IBAN_CODE);

        // Get all the relatieList where ibanCode not equals to UPDATED_IBAN_CODE
        defaultRelatieShouldBeFound("ibanCode.notEquals=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode in DEFAULT_IBAN_CODE or UPDATED_IBAN_CODE
        defaultRelatieShouldBeFound("ibanCode.in=" + DEFAULT_IBAN_CODE + "," + UPDATED_IBAN_CODE);

        // Get all the relatieList where ibanCode equals to UPDATED_IBAN_CODE
        defaultRelatieShouldNotBeFound("ibanCode.in=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode is not null
        defaultRelatieShouldBeFound("ibanCode.specified=true");

        // Get all the relatieList where ibanCode is null
        defaultRelatieShouldNotBeFound("ibanCode.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode contains DEFAULT_IBAN_CODE
        defaultRelatieShouldBeFound("ibanCode.contains=" + DEFAULT_IBAN_CODE);

        // Get all the relatieList where ibanCode contains UPDATED_IBAN_CODE
        defaultRelatieShouldNotBeFound("ibanCode.contains=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbanCodeNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibanCode does not contain DEFAULT_IBAN_CODE
        defaultRelatieShouldNotBeFound("ibanCode.doesNotContain=" + DEFAULT_IBAN_CODE);

        // Get all the relatieList where ibanCode does not contain UPDATED_IBAN_CODE
        defaultRelatieShouldBeFound("ibanCode.doesNotContain=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer equals to DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.equals=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer equals to UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.equals=" + UPDATED_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer not equals to DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.notEquals=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer not equals to UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.notEquals=" + UPDATED_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer in DEFAULT_KNSB_RELATIE_NUMMER or UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.in=" + DEFAULT_KNSB_RELATIE_NUMMER + "," + UPDATED_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer equals to UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.in=" + UPDATED_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer is not null
        defaultRelatieShouldBeFound("knsbRelatieNummer.specified=true");

        // Get all the relatieList where knsbRelatieNummer is null
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer is greater than or equal to DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.greaterThanOrEqual=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer is greater than or equal to UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.greaterThanOrEqual=" + UPDATED_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer is less than or equal to DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.lessThanOrEqual=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer is less than or equal to SMALLER_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.lessThanOrEqual=" + SMALLER_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer is less than DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.lessThan=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer is less than UPDATED_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.lessThan=" + UPDATED_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatieNummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatieNummer is greater than DEFAULT_KNSB_RELATIE_NUMMER
        defaultRelatieShouldNotBeFound("knsbRelatieNummer.greaterThan=" + DEFAULT_KNSB_RELATIE_NUMMER);

        // Get all the relatieList where knsbRelatieNummer is greater than SMALLER_KNSB_RELATIE_NUMMER
        defaultRelatieShouldBeFound("knsbRelatieNummer.greaterThan=" + SMALLER_KNSB_RELATIE_NUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByRolIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);
        Rol rol = RolResourceIT.createEntity(em);
        em.persist(rol);
        em.flush();
        relatie.addRol(rol);
        relatieRepository.saveAndFlush(relatie);
        UUID rolId = rol.getId();

        // Get all the relatieList where rol equals to rolId
        defaultRelatieShouldBeFound("rolId.equals=" + rolId);

        // Get all the relatieList where rol equals to UUID.randomUUID()
        defaultRelatieShouldNotBeFound("rolId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRelatieShouldBeFound(String filter) throws Exception {
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatie.getId().toString())))
            .andExpect(jsonPath("$.[*].voorNaam").value(hasItem(DEFAULT_VOOR_NAAM)))
            .andExpect(jsonPath("$.[*].achterNaam").value(hasItem(DEFAULT_ACHTER_NAAM)))
            .andExpect(jsonPath("$.[*].initialen").value(hasItem(DEFAULT_INITIALEN)))
            .andExpect(jsonPath("$.[*].weergaveNaam").value(hasItem(DEFAULT_WEERGAVE_NAAM)))
            .andExpect(jsonPath("$.[*].geslacht").value(hasItem(DEFAULT_GESLACHT.toString())))
            .andExpect(jsonPath("$.[*].geboorteDatum").value(hasItem(DEFAULT_GEBOORTE_DATUM.toString())))
            .andExpect(jsonPath("$.[*].relatieType").value(hasItem(DEFAULT_RELATIE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inschrijvingsDatum").value(hasItem(DEFAULT_INSCHRIJVINGS_DATUM.toString())))
            .andExpect(jsonPath("$.[*].straatNaam").value(hasItem(DEFAULT_STRAAT_NAAM)))
            .andExpect(jsonPath("$.[*].huisNummer").value(hasItem(DEFAULT_HUIS_NUMMER)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].woonPlaats").value(hasItem(DEFAULT_WOON_PLAATS)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].telefoonNummer").value(hasItem(DEFAULT_TELEFOON_NUMMER)))
            .andExpect(jsonPath("$.[*].telefoonNummer2").value(hasItem(DEFAULT_TELEFOON_NUMMER_2)))
            .andExpect(jsonPath("$.[*].telefoonNummer3").value(hasItem(DEFAULT_TELEFOON_NUMMER_3)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].knsbRelatieNummer").value(hasItem(DEFAULT_KNSB_RELATIE_NUMMER.intValue())))
            .andExpect(jsonPath("$.[*].pasfotoContentType").value(hasItem(DEFAULT_PASFOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pasfoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASFOTO))))
            .andExpect(jsonPath("$.[*].privacyVerklaringContentType").value(hasItem(DEFAULT_PRIVACY_VERKLARING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].privacyVerklaring").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVACY_VERKLARING))));

        // Check, that the count call also returns 1
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRelatieShouldNotBeFound(String filter) throws Exception {
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRelatie() throws Exception {
        // Get the relatie
        restRelatieMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();

        // Update the relatie
        Relatie updatedRelatie = relatieRepository.findById(relatie.getId()).get();
        // Disconnect from session so that the updates on updatedRelatie are not directly saved in db
        em.detach(updatedRelatie);
        updatedRelatie
            .voorNaam(UPDATED_VOOR_NAAM)
            .achterNaam(UPDATED_ACHTER_NAAM)
            .initialen(UPDATED_INITIALEN)
            .weergaveNaam(UPDATED_WEERGAVE_NAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboorteDatum(UPDATED_GEBOORTE_DATUM)
            .relatieType(UPDATED_RELATIE_TYPE)
            .inschrijvingsDatum(UPDATED_INSCHRIJVINGS_DATUM)
            .straatNaam(UPDATED_STRAAT_NAAM)
            .huisNummer(UPDATED_HUIS_NUMMER)
            .postCode(UPDATED_POST_CODE)
            .woonPlaats(UPDATED_WOON_PLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonNummer(UPDATED_TELEFOON_NUMMER)
            .telefoonNummer2(UPDATED_TELEFOON_NUMMER_2)
            .telefoonNummer3(UPDATED_TELEFOON_NUMMER_3)
            .ibanCode(UPDATED_IBAN_CODE)
            .knsbRelatieNummer(UPDATED_KNSB_RELATIE_NUMMER)
            .pasfoto(UPDATED_PASFOTO)
            .pasfotoContentType(UPDATED_PASFOTO_CONTENT_TYPE)
            .privacyVerklaring(UPDATED_PRIVACY_VERKLARING)
            .privacyVerklaringContentType(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);

        restRelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
        Relatie testRelatie = relatieList.get(relatieList.size() - 1);
        assertThat(testRelatie.getVoorNaam()).isEqualTo(UPDATED_VOOR_NAAM);
        assertThat(testRelatie.getAchterNaam()).isEqualTo(UPDATED_ACHTER_NAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergaveNaam()).isEqualTo(UPDATED_WEERGAVE_NAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
        assertThat(testRelatie.getRelatieType()).isEqualTo(UPDATED_RELATIE_TYPE);
        assertThat(testRelatie.getInschrijvingsDatum()).isEqualTo(UPDATED_INSCHRIJVINGS_DATUM);
        assertThat(testRelatie.getStraatNaam()).isEqualTo(UPDATED_STRAAT_NAAM);
        assertThat(testRelatie.getHuisNummer()).isEqualTo(UPDATED_HUIS_NUMMER);
        assertThat(testRelatie.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testRelatie.getWoonPlaats()).isEqualTo(UPDATED_WOON_PLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonNummer()).isEqualTo(UPDATED_TELEFOON_NUMMER);
        assertThat(testRelatie.getTelefoonNummer2()).isEqualTo(UPDATED_TELEFOON_NUMMER_2);
        assertThat(testRelatie.getTelefoonNummer3()).isEqualTo(UPDATED_TELEFOON_NUMMER_3);
        assertThat(testRelatie.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testRelatie.getKnsbRelatieNummer()).isEqualTo(UPDATED_KNSB_RELATIE_NUMMER);
        assertThat(testRelatie.getPasfoto()).isEqualTo(UPDATED_PASFOTO);
        assertThat(testRelatie.getPasfotoContentType()).isEqualTo(UPDATED_PASFOTO_CONTENT_TYPE);
        assertThat(testRelatie.getPrivacyVerklaring()).isEqualTo(UPDATED_PRIVACY_VERKLARING);
        assertThat(testRelatie.getPrivacyVerklaringContentType()).isEqualTo(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatieWithPatch() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();

        // Update the relatie using partial update
        Relatie partialUpdatedRelatie = new Relatie();
        partialUpdatedRelatie.setId(relatie.getId());

        partialUpdatedRelatie
            .voorNaam(UPDATED_VOOR_NAAM)
            .achterNaam(UPDATED_ACHTER_NAAM)
            .initialen(UPDATED_INITIALEN)
            .geslacht(UPDATED_GESLACHT)
            .straatNaam(UPDATED_STRAAT_NAAM)
            .huisNummer(UPDATED_HUIS_NUMMER)
            .woonPlaats(UPDATED_WOON_PLAATS)
            .land(UPDATED_LAND)
            .email2(UPDATED_EMAIL_2)
            .ibanCode(UPDATED_IBAN_CODE)
            .privacyVerklaring(UPDATED_PRIVACY_VERKLARING)
            .privacyVerklaringContentType(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);

        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
        Relatie testRelatie = relatieList.get(relatieList.size() - 1);
        assertThat(testRelatie.getVoorNaam()).isEqualTo(UPDATED_VOOR_NAAM);
        assertThat(testRelatie.getAchterNaam()).isEqualTo(UPDATED_ACHTER_NAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergaveNaam()).isEqualTo(DEFAULT_WEERGAVE_NAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboorteDatum()).isEqualTo(DEFAULT_GEBOORTE_DATUM);
        assertThat(testRelatie.getRelatieType()).isEqualTo(DEFAULT_RELATIE_TYPE);
        assertThat(testRelatie.getInschrijvingsDatum()).isEqualTo(DEFAULT_INSCHRIJVINGS_DATUM);
        assertThat(testRelatie.getStraatNaam()).isEqualTo(UPDATED_STRAAT_NAAM);
        assertThat(testRelatie.getHuisNummer()).isEqualTo(UPDATED_HUIS_NUMMER);
        assertThat(testRelatie.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testRelatie.getWoonPlaats()).isEqualTo(UPDATED_WOON_PLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonNummer()).isEqualTo(DEFAULT_TELEFOON_NUMMER);
        assertThat(testRelatie.getTelefoonNummer2()).isEqualTo(DEFAULT_TELEFOON_NUMMER_2);
        assertThat(testRelatie.getTelefoonNummer3()).isEqualTo(DEFAULT_TELEFOON_NUMMER_3);
        assertThat(testRelatie.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testRelatie.getKnsbRelatieNummer()).isEqualTo(DEFAULT_KNSB_RELATIE_NUMMER);
        assertThat(testRelatie.getPasfoto()).isEqualTo(DEFAULT_PASFOTO);
        assertThat(testRelatie.getPasfotoContentType()).isEqualTo(DEFAULT_PASFOTO_CONTENT_TYPE);
        assertThat(testRelatie.getPrivacyVerklaring()).isEqualTo(UPDATED_PRIVACY_VERKLARING);
        assertThat(testRelatie.getPrivacyVerklaringContentType()).isEqualTo(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRelatieWithPatch() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();

        // Update the relatie using partial update
        Relatie partialUpdatedRelatie = new Relatie();
        partialUpdatedRelatie.setId(relatie.getId());

        partialUpdatedRelatie
            .voorNaam(UPDATED_VOOR_NAAM)
            .achterNaam(UPDATED_ACHTER_NAAM)
            .initialen(UPDATED_INITIALEN)
            .weergaveNaam(UPDATED_WEERGAVE_NAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboorteDatum(UPDATED_GEBOORTE_DATUM)
            .relatieType(UPDATED_RELATIE_TYPE)
            .inschrijvingsDatum(UPDATED_INSCHRIJVINGS_DATUM)
            .straatNaam(UPDATED_STRAAT_NAAM)
            .huisNummer(UPDATED_HUIS_NUMMER)
            .postCode(UPDATED_POST_CODE)
            .woonPlaats(UPDATED_WOON_PLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonNummer(UPDATED_TELEFOON_NUMMER)
            .telefoonNummer2(UPDATED_TELEFOON_NUMMER_2)
            .telefoonNummer3(UPDATED_TELEFOON_NUMMER_3)
            .ibanCode(UPDATED_IBAN_CODE)
            .knsbRelatieNummer(UPDATED_KNSB_RELATIE_NUMMER)
            .pasfoto(UPDATED_PASFOTO)
            .pasfotoContentType(UPDATED_PASFOTO_CONTENT_TYPE)
            .privacyVerklaring(UPDATED_PRIVACY_VERKLARING)
            .privacyVerklaringContentType(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);

        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
        Relatie testRelatie = relatieList.get(relatieList.size() - 1);
        assertThat(testRelatie.getVoorNaam()).isEqualTo(UPDATED_VOOR_NAAM);
        assertThat(testRelatie.getAchterNaam()).isEqualTo(UPDATED_ACHTER_NAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergaveNaam()).isEqualTo(UPDATED_WEERGAVE_NAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
        assertThat(testRelatie.getRelatieType()).isEqualTo(UPDATED_RELATIE_TYPE);
        assertThat(testRelatie.getInschrijvingsDatum()).isEqualTo(UPDATED_INSCHRIJVINGS_DATUM);
        assertThat(testRelatie.getStraatNaam()).isEqualTo(UPDATED_STRAAT_NAAM);
        assertThat(testRelatie.getHuisNummer()).isEqualTo(UPDATED_HUIS_NUMMER);
        assertThat(testRelatie.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testRelatie.getWoonPlaats()).isEqualTo(UPDATED_WOON_PLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonNummer()).isEqualTo(UPDATED_TELEFOON_NUMMER);
        assertThat(testRelatie.getTelefoonNummer2()).isEqualTo(UPDATED_TELEFOON_NUMMER_2);
        assertThat(testRelatie.getTelefoonNummer3()).isEqualTo(UPDATED_TELEFOON_NUMMER_3);
        assertThat(testRelatie.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testRelatie.getKnsbRelatieNummer()).isEqualTo(UPDATED_KNSB_RELATIE_NUMMER);
        assertThat(testRelatie.getPasfoto()).isEqualTo(UPDATED_PASFOTO);
        assertThat(testRelatie.getPasfotoContentType()).isEqualTo(UPDATED_PASFOTO_CONTENT_TYPE);
        assertThat(testRelatie.getPrivacyVerklaring()).isEqualTo(UPDATED_PRIVACY_VERKLARING);
        assertThat(testRelatie.getPrivacyVerklaringContentType()).isEqualTo(UPDATED_PRIVACY_VERKLARING_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatie() throws Exception {
        int databaseSizeBeforeUpdate = relatieRepository.findAll().size();
        relatie.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(relatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relatie in the database
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        int databaseSizeBeforeDelete = relatieRepository.findAll().size();

        // Delete the relatie
        restRelatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatie.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relatie> relatieList = relatieRepository.findAll();
        assertThat(relatieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
