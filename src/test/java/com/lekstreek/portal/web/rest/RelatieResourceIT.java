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

    private static final String DEFAULT_VOORNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_INITIALEN = "AAAAAAAAAA";
    private static final String UPDATED_INITIALEN = "BBBBBBBBBB";

    private static final String DEFAULT_WEERGAVENAAM = "AAAAAAAAAA";
    private static final String UPDATED_WEERGAVENAAM = "BBBBBBBBBB";

    private static final Geslacht DEFAULT_GESLACHT = Geslacht.MAN;
    private static final Geslacht UPDATED_GESLACHT = Geslacht.VROUW;

    private static final LocalDate DEFAULT_GEBOORTEDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBOORTEDATUM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_GEBOORTEDATUM = LocalDate.ofEpochDay(-1L);

    private static final RelatieType DEFAULT_RELATIETYPE = RelatieType.LID;
    private static final RelatieType UPDATED_RELATIETYPE = RelatieType.JEUGDSCHAATSLID;

    private static final Instant DEFAULT_INSCHRIJVINGSDATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSCHRIJVINGSDATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STRAATNAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAATNAAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_HUISNUMMER = 1;
    private static final Integer UPDATED_HUISNUMMER = 2;
    private static final Integer SMALLER_HUISNUMMER = 1 - 1;

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WOONPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_LAND = "AAAAAAAAAA";
    private static final String UPDATED_LAND = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFOONNUMMER = 1;
    private static final Integer UPDATED_TELEFOONNUMMER = 2;
    private static final Integer SMALLER_TELEFOONNUMMER = 1 - 1;

    private static final Integer DEFAULT_TELEFOONNUMMER_2 = 1;
    private static final Integer UPDATED_TELEFOONNUMMER_2 = 2;
    private static final Integer SMALLER_TELEFOONNUMMER_2 = 1 - 1;

    private static final Integer DEFAULT_TELEFOONNUMMER_3 = 1;
    private static final Integer UPDATED_TELEFOONNUMMER_3 = 2;
    private static final Integer SMALLER_TELEFOONNUMMER_3 = 1 - 1;

    private static final String DEFAULT_IBANCODE = "AAAAAAAAAA";
    private static final String UPDATED_IBANCODE = "BBBBBBBBBB";

    private static final Long DEFAULT_KNSB_RELATIENUMMER = 1L;
    private static final Long UPDATED_KNSB_RELATIENUMMER = 2L;
    private static final Long SMALLER_KNSB_RELATIENUMMER = 1L - 1L;

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
            .voornaam(DEFAULT_VOORNAAM)
            .achternaam(DEFAULT_ACHTERNAAM)
            .initialen(DEFAULT_INITIALEN)
            .weergavenaam(DEFAULT_WEERGAVENAAM)
            .geslacht(DEFAULT_GESLACHT)
            .geboortedatum(DEFAULT_GEBOORTEDATUM)
            .relatietype(DEFAULT_RELATIETYPE)
            .inschrijvingsdatum(DEFAULT_INSCHRIJVINGSDATUM)
            .straatnaam(DEFAULT_STRAATNAAM)
            .huisnummer(DEFAULT_HUISNUMMER)
            .postcode(DEFAULT_POSTCODE)
            .woonplaats(DEFAULT_WOONPLAATS)
            .land(DEFAULT_LAND)
            .email(DEFAULT_EMAIL)
            .email2(DEFAULT_EMAIL_2)
            .telefoonnummer(DEFAULT_TELEFOONNUMMER)
            .telefoonnummer2(DEFAULT_TELEFOONNUMMER_2)
            .telefoonnummer3(DEFAULT_TELEFOONNUMMER_3)
            .ibancode(DEFAULT_IBANCODE)
            .knsbRelatienummer(DEFAULT_KNSB_RELATIENUMMER)
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
            .voornaam(UPDATED_VOORNAAM)
            .achternaam(UPDATED_ACHTERNAAM)
            .initialen(UPDATED_INITIALEN)
            .weergavenaam(UPDATED_WEERGAVENAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .relatietype(UPDATED_RELATIETYPE)
            .inschrijvingsdatum(UPDATED_INSCHRIJVINGSDATUM)
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .telefoonnummer2(UPDATED_TELEFOONNUMMER_2)
            .telefoonnummer3(UPDATED_TELEFOONNUMMER_3)
            .ibancode(UPDATED_IBANCODE)
            .knsbRelatienummer(UPDATED_KNSB_RELATIENUMMER)
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
        assertThat(testRelatie.getVoornaam()).isEqualTo(DEFAULT_VOORNAAM);
        assertThat(testRelatie.getAchternaam()).isEqualTo(DEFAULT_ACHTERNAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(DEFAULT_INITIALEN);
        assertThat(testRelatie.getWeergavenaam()).isEqualTo(DEFAULT_WEERGAVENAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(DEFAULT_GESLACHT);
        assertThat(testRelatie.getGeboortedatum()).isEqualTo(DEFAULT_GEBOORTEDATUM);
        assertThat(testRelatie.getRelatietype()).isEqualTo(DEFAULT_RELATIETYPE);
        assertThat(testRelatie.getInschrijvingsdatum()).isEqualTo(DEFAULT_INSCHRIJVINGSDATUM);
        assertThat(testRelatie.getStraatnaam()).isEqualTo(DEFAULT_STRAATNAAM);
        assertThat(testRelatie.getHuisnummer()).isEqualTo(DEFAULT_HUISNUMMER);
        assertThat(testRelatie.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testRelatie.getWoonplaats()).isEqualTo(DEFAULT_WOONPLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(DEFAULT_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testRelatie.getTelefoonnummer()).isEqualTo(DEFAULT_TELEFOONNUMMER);
        assertThat(testRelatie.getTelefoonnummer2()).isEqualTo(DEFAULT_TELEFOONNUMMER_2);
        assertThat(testRelatie.getTelefoonnummer3()).isEqualTo(DEFAULT_TELEFOONNUMMER_3);
        assertThat(testRelatie.getIbancode()).isEqualTo(DEFAULT_IBANCODE);
        assertThat(testRelatie.getKnsbRelatienummer()).isEqualTo(DEFAULT_KNSB_RELATIENUMMER);
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
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM)))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].initialen").value(hasItem(DEFAULT_INITIALEN)))
            .andExpect(jsonPath("$.[*].weergavenaam").value(hasItem(DEFAULT_WEERGAVENAAM)))
            .andExpect(jsonPath("$.[*].geslacht").value(hasItem(DEFAULT_GESLACHT.toString())))
            .andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
            .andExpect(jsonPath("$.[*].relatietype").value(hasItem(DEFAULT_RELATIETYPE.toString())))
            .andExpect(jsonPath("$.[*].inschrijvingsdatum").value(hasItem(DEFAULT_INSCHRIJVINGSDATUM.toString())))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)))
            .andExpect(jsonPath("$.[*].telefoonnummer2").value(hasItem(DEFAULT_TELEFOONNUMMER_2)))
            .andExpect(jsonPath("$.[*].telefoonnummer3").value(hasItem(DEFAULT_TELEFOONNUMMER_3)))
            .andExpect(jsonPath("$.[*].ibancode").value(hasItem(DEFAULT_IBANCODE)))
            .andExpect(jsonPath("$.[*].knsbRelatienummer").value(hasItem(DEFAULT_KNSB_RELATIENUMMER.intValue())))
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
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.initialen").value(DEFAULT_INITIALEN))
            .andExpect(jsonPath("$.weergavenaam").value(DEFAULT_WEERGAVENAAM))
            .andExpect(jsonPath("$.geslacht").value(DEFAULT_GESLACHT.toString()))
            .andExpect(jsonPath("$.geboortedatum").value(DEFAULT_GEBOORTEDATUM.toString()))
            .andExpect(jsonPath("$.relatietype").value(DEFAULT_RELATIETYPE.toString()))
            .andExpect(jsonPath("$.inschrijvingsdatum").value(DEFAULT_INSCHRIJVINGSDATUM.toString()))
            .andExpect(jsonPath("$.straatnaam").value(DEFAULT_STRAATNAAM))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.woonplaats").value(DEFAULT_WOONPLAATS))
            .andExpect(jsonPath("$.land").value(DEFAULT_LAND))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER))
            .andExpect(jsonPath("$.telefoonnummer2").value(DEFAULT_TELEFOONNUMMER_2))
            .andExpect(jsonPath("$.telefoonnummer3").value(DEFAULT_TELEFOONNUMMER_3))
            .andExpect(jsonPath("$.ibancode").value(DEFAULT_IBANCODE))
            .andExpect(jsonPath("$.knsbRelatienummer").value(DEFAULT_KNSB_RELATIENUMMER.intValue()))
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
    void getAllRelatiesByVoornaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam equals to DEFAULT_VOORNAAM
        defaultRelatieShouldBeFound("voornaam.equals=" + DEFAULT_VOORNAAM);

        // Get all the relatieList where voornaam equals to UPDATED_VOORNAAM
        defaultRelatieShouldNotBeFound("voornaam.equals=" + UPDATED_VOORNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoornaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam not equals to DEFAULT_VOORNAAM
        defaultRelatieShouldNotBeFound("voornaam.notEquals=" + DEFAULT_VOORNAAM);

        // Get all the relatieList where voornaam not equals to UPDATED_VOORNAAM
        defaultRelatieShouldBeFound("voornaam.notEquals=" + UPDATED_VOORNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoornaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam in DEFAULT_VOORNAAM or UPDATED_VOORNAAM
        defaultRelatieShouldBeFound("voornaam.in=" + DEFAULT_VOORNAAM + "," + UPDATED_VOORNAAM);

        // Get all the relatieList where voornaam equals to UPDATED_VOORNAAM
        defaultRelatieShouldNotBeFound("voornaam.in=" + UPDATED_VOORNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoornaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam is not null
        defaultRelatieShouldBeFound("voornaam.specified=true");

        // Get all the relatieList where voornaam is null
        defaultRelatieShouldNotBeFound("voornaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByVoornaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam contains DEFAULT_VOORNAAM
        defaultRelatieShouldBeFound("voornaam.contains=" + DEFAULT_VOORNAAM);

        // Get all the relatieList where voornaam contains UPDATED_VOORNAAM
        defaultRelatieShouldNotBeFound("voornaam.contains=" + UPDATED_VOORNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByVoornaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where voornaam does not contain DEFAULT_VOORNAAM
        defaultRelatieShouldNotBeFound("voornaam.doesNotContain=" + DEFAULT_VOORNAAM);

        // Get all the relatieList where voornaam does not contain UPDATED_VOORNAAM
        defaultRelatieShouldBeFound("voornaam.doesNotContain=" + UPDATED_VOORNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam equals to DEFAULT_ACHTERNAAM
        defaultRelatieShouldBeFound("achternaam.equals=" + DEFAULT_ACHTERNAAM);

        // Get all the relatieList where achternaam equals to UPDATED_ACHTERNAAM
        defaultRelatieShouldNotBeFound("achternaam.equals=" + UPDATED_ACHTERNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam not equals to DEFAULT_ACHTERNAAM
        defaultRelatieShouldNotBeFound("achternaam.notEquals=" + DEFAULT_ACHTERNAAM);

        // Get all the relatieList where achternaam not equals to UPDATED_ACHTERNAAM
        defaultRelatieShouldBeFound("achternaam.notEquals=" + UPDATED_ACHTERNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam in DEFAULT_ACHTERNAAM or UPDATED_ACHTERNAAM
        defaultRelatieShouldBeFound("achternaam.in=" + DEFAULT_ACHTERNAAM + "," + UPDATED_ACHTERNAAM);

        // Get all the relatieList where achternaam equals to UPDATED_ACHTERNAAM
        defaultRelatieShouldNotBeFound("achternaam.in=" + UPDATED_ACHTERNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam is not null
        defaultRelatieShouldBeFound("achternaam.specified=true");

        // Get all the relatieList where achternaam is null
        defaultRelatieShouldNotBeFound("achternaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam contains DEFAULT_ACHTERNAAM
        defaultRelatieShouldBeFound("achternaam.contains=" + DEFAULT_ACHTERNAAM);

        // Get all the relatieList where achternaam contains UPDATED_ACHTERNAAM
        defaultRelatieShouldNotBeFound("achternaam.contains=" + UPDATED_ACHTERNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByAchternaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where achternaam does not contain DEFAULT_ACHTERNAAM
        defaultRelatieShouldNotBeFound("achternaam.doesNotContain=" + DEFAULT_ACHTERNAAM);

        // Get all the relatieList where achternaam does not contain UPDATED_ACHTERNAAM
        defaultRelatieShouldBeFound("achternaam.doesNotContain=" + UPDATED_ACHTERNAAM);
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
    void getAllRelatiesByWeergavenaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam equals to DEFAULT_WEERGAVENAAM
        defaultRelatieShouldBeFound("weergavenaam.equals=" + DEFAULT_WEERGAVENAAM);

        // Get all the relatieList where weergavenaam equals to UPDATED_WEERGAVENAAM
        defaultRelatieShouldNotBeFound("weergavenaam.equals=" + UPDATED_WEERGAVENAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergavenaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam not equals to DEFAULT_WEERGAVENAAM
        defaultRelatieShouldNotBeFound("weergavenaam.notEquals=" + DEFAULT_WEERGAVENAAM);

        // Get all the relatieList where weergavenaam not equals to UPDATED_WEERGAVENAAM
        defaultRelatieShouldBeFound("weergavenaam.notEquals=" + UPDATED_WEERGAVENAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergavenaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam in DEFAULT_WEERGAVENAAM or UPDATED_WEERGAVENAAM
        defaultRelatieShouldBeFound("weergavenaam.in=" + DEFAULT_WEERGAVENAAM + "," + UPDATED_WEERGAVENAAM);

        // Get all the relatieList where weergavenaam equals to UPDATED_WEERGAVENAAM
        defaultRelatieShouldNotBeFound("weergavenaam.in=" + UPDATED_WEERGAVENAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergavenaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam is not null
        defaultRelatieShouldBeFound("weergavenaam.specified=true");

        // Get all the relatieList where weergavenaam is null
        defaultRelatieShouldNotBeFound("weergavenaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergavenaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam contains DEFAULT_WEERGAVENAAM
        defaultRelatieShouldBeFound("weergavenaam.contains=" + DEFAULT_WEERGAVENAAM);

        // Get all the relatieList where weergavenaam contains UPDATED_WEERGAVENAAM
        defaultRelatieShouldNotBeFound("weergavenaam.contains=" + UPDATED_WEERGAVENAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByWeergavenaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where weergavenaam does not contain DEFAULT_WEERGAVENAAM
        defaultRelatieShouldNotBeFound("weergavenaam.doesNotContain=" + DEFAULT_WEERGAVENAAM);

        // Get all the relatieList where weergavenaam does not contain UPDATED_WEERGAVENAAM
        defaultRelatieShouldBeFound("weergavenaam.doesNotContain=" + UPDATED_WEERGAVENAAM);
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
    void getAllRelatiesByGeboortedatumIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum equals to DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.equals=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum equals to UPDATED_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.equals=" + UPDATED_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum not equals to DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.notEquals=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum not equals to UPDATED_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.notEquals=" + UPDATED_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum in DEFAULT_GEBOORTEDATUM or UPDATED_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.in=" + DEFAULT_GEBOORTEDATUM + "," + UPDATED_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum equals to UPDATED_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.in=" + UPDATED_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum is not null
        defaultRelatieShouldBeFound("geboortedatum.specified=true");

        // Get all the relatieList where geboortedatum is null
        defaultRelatieShouldNotBeFound("geboortedatum.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum is greater than or equal to DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.greaterThanOrEqual=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum is greater than or equal to UPDATED_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.greaterThanOrEqual=" + UPDATED_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum is less than or equal to DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.lessThanOrEqual=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum is less than or equal to SMALLER_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.lessThanOrEqual=" + SMALLER_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum is less than DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.lessThan=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum is less than UPDATED_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.lessThan=" + UPDATED_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByGeboortedatumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where geboortedatum is greater than DEFAULT_GEBOORTEDATUM
        defaultRelatieShouldNotBeFound("geboortedatum.greaterThan=" + DEFAULT_GEBOORTEDATUM);

        // Get all the relatieList where geboortedatum is greater than SMALLER_GEBOORTEDATUM
        defaultRelatieShouldBeFound("geboortedatum.greaterThan=" + SMALLER_GEBOORTEDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatietypeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatietype equals to DEFAULT_RELATIETYPE
        defaultRelatieShouldBeFound("relatietype.equals=" + DEFAULT_RELATIETYPE);

        // Get all the relatieList where relatietype equals to UPDATED_RELATIETYPE
        defaultRelatieShouldNotBeFound("relatietype.equals=" + UPDATED_RELATIETYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatietypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatietype not equals to DEFAULT_RELATIETYPE
        defaultRelatieShouldNotBeFound("relatietype.notEquals=" + DEFAULT_RELATIETYPE);

        // Get all the relatieList where relatietype not equals to UPDATED_RELATIETYPE
        defaultRelatieShouldBeFound("relatietype.notEquals=" + UPDATED_RELATIETYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatietypeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatietype in DEFAULT_RELATIETYPE or UPDATED_RELATIETYPE
        defaultRelatieShouldBeFound("relatietype.in=" + DEFAULT_RELATIETYPE + "," + UPDATED_RELATIETYPE);

        // Get all the relatieList where relatietype equals to UPDATED_RELATIETYPE
        defaultRelatieShouldNotBeFound("relatietype.in=" + UPDATED_RELATIETYPE);
    }

    @Test
    @Transactional
    void getAllRelatiesByRelatietypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where relatietype is not null
        defaultRelatieShouldBeFound("relatietype.specified=true");

        // Get all the relatieList where relatietype is null
        defaultRelatieShouldNotBeFound("relatietype.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsdatumIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsdatum equals to DEFAULT_INSCHRIJVINGSDATUM
        defaultRelatieShouldBeFound("inschrijvingsdatum.equals=" + DEFAULT_INSCHRIJVINGSDATUM);

        // Get all the relatieList where inschrijvingsdatum equals to UPDATED_INSCHRIJVINGSDATUM
        defaultRelatieShouldNotBeFound("inschrijvingsdatum.equals=" + UPDATED_INSCHRIJVINGSDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsdatumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsdatum not equals to DEFAULT_INSCHRIJVINGSDATUM
        defaultRelatieShouldNotBeFound("inschrijvingsdatum.notEquals=" + DEFAULT_INSCHRIJVINGSDATUM);

        // Get all the relatieList where inschrijvingsdatum not equals to UPDATED_INSCHRIJVINGSDATUM
        defaultRelatieShouldBeFound("inschrijvingsdatum.notEquals=" + UPDATED_INSCHRIJVINGSDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsdatumIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsdatum in DEFAULT_INSCHRIJVINGSDATUM or UPDATED_INSCHRIJVINGSDATUM
        defaultRelatieShouldBeFound("inschrijvingsdatum.in=" + DEFAULT_INSCHRIJVINGSDATUM + "," + UPDATED_INSCHRIJVINGSDATUM);

        // Get all the relatieList where inschrijvingsdatum equals to UPDATED_INSCHRIJVINGSDATUM
        defaultRelatieShouldNotBeFound("inschrijvingsdatum.in=" + UPDATED_INSCHRIJVINGSDATUM);
    }

    @Test
    @Transactional
    void getAllRelatiesByInschrijvingsdatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where inschrijvingsdatum is not null
        defaultRelatieShouldBeFound("inschrijvingsdatum.specified=true");

        // Get all the relatieList where inschrijvingsdatum is null
        defaultRelatieShouldNotBeFound("inschrijvingsdatum.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam equals to DEFAULT_STRAATNAAM
        defaultRelatieShouldBeFound("straatnaam.equals=" + DEFAULT_STRAATNAAM);

        // Get all the relatieList where straatnaam equals to UPDATED_STRAATNAAM
        defaultRelatieShouldNotBeFound("straatnaam.equals=" + UPDATED_STRAATNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam not equals to DEFAULT_STRAATNAAM
        defaultRelatieShouldNotBeFound("straatnaam.notEquals=" + DEFAULT_STRAATNAAM);

        // Get all the relatieList where straatnaam not equals to UPDATED_STRAATNAAM
        defaultRelatieShouldBeFound("straatnaam.notEquals=" + UPDATED_STRAATNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam in DEFAULT_STRAATNAAM or UPDATED_STRAATNAAM
        defaultRelatieShouldBeFound("straatnaam.in=" + DEFAULT_STRAATNAAM + "," + UPDATED_STRAATNAAM);

        // Get all the relatieList where straatnaam equals to UPDATED_STRAATNAAM
        defaultRelatieShouldNotBeFound("straatnaam.in=" + UPDATED_STRAATNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam is not null
        defaultRelatieShouldBeFound("straatnaam.specified=true");

        // Get all the relatieList where straatnaam is null
        defaultRelatieShouldNotBeFound("straatnaam.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam contains DEFAULT_STRAATNAAM
        defaultRelatieShouldBeFound("straatnaam.contains=" + DEFAULT_STRAATNAAM);

        // Get all the relatieList where straatnaam contains UPDATED_STRAATNAAM
        defaultRelatieShouldNotBeFound("straatnaam.contains=" + UPDATED_STRAATNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByStraatnaamNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where straatnaam does not contain DEFAULT_STRAATNAAM
        defaultRelatieShouldNotBeFound("straatnaam.doesNotContain=" + DEFAULT_STRAATNAAM);

        // Get all the relatieList where straatnaam does not contain UPDATED_STRAATNAAM
        defaultRelatieShouldBeFound("straatnaam.doesNotContain=" + UPDATED_STRAATNAAM);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer equals to DEFAULT_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.equals=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer equals to UPDATED_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.equals=" + UPDATED_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer not equals to DEFAULT_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.notEquals=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer not equals to UPDATED_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.notEquals=" + UPDATED_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer in DEFAULT_HUISNUMMER or UPDATED_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.in=" + DEFAULT_HUISNUMMER + "," + UPDATED_HUISNUMMER);

        // Get all the relatieList where huisnummer equals to UPDATED_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.in=" + UPDATED_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer is not null
        defaultRelatieShouldBeFound("huisnummer.specified=true");

        // Get all the relatieList where huisnummer is null
        defaultRelatieShouldNotBeFound("huisnummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer is greater than or equal to DEFAULT_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.greaterThanOrEqual=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer is greater than or equal to UPDATED_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.greaterThanOrEqual=" + UPDATED_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer is less than or equal to DEFAULT_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.lessThanOrEqual=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer is less than or equal to SMALLER_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.lessThanOrEqual=" + SMALLER_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer is less than DEFAULT_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.lessThan=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer is less than UPDATED_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.lessThan=" + UPDATED_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByHuisnummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where huisnummer is greater than DEFAULT_HUISNUMMER
        defaultRelatieShouldNotBeFound("huisnummer.greaterThan=" + DEFAULT_HUISNUMMER);

        // Get all the relatieList where huisnummer is greater than SMALLER_HUISNUMMER
        defaultRelatieShouldBeFound("huisnummer.greaterThan=" + SMALLER_HUISNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode equals to DEFAULT_POSTCODE
        defaultRelatieShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the relatieList where postcode equals to UPDATED_POSTCODE
        defaultRelatieShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode not equals to DEFAULT_POSTCODE
        defaultRelatieShouldNotBeFound("postcode.notEquals=" + DEFAULT_POSTCODE);

        // Get all the relatieList where postcode not equals to UPDATED_POSTCODE
        defaultRelatieShouldBeFound("postcode.notEquals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultRelatieShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the relatieList where postcode equals to UPDATED_POSTCODE
        defaultRelatieShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode is not null
        defaultRelatieShouldBeFound("postcode.specified=true");

        // Get all the relatieList where postcode is null
        defaultRelatieShouldNotBeFound("postcode.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode contains DEFAULT_POSTCODE
        defaultRelatieShouldBeFound("postcode.contains=" + DEFAULT_POSTCODE);

        // Get all the relatieList where postcode contains UPDATED_POSTCODE
        defaultRelatieShouldNotBeFound("postcode.contains=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByPostcodeNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where postcode does not contain DEFAULT_POSTCODE
        defaultRelatieShouldNotBeFound("postcode.doesNotContain=" + DEFAULT_POSTCODE);

        // Get all the relatieList where postcode does not contain UPDATED_POSTCODE
        defaultRelatieShouldBeFound("postcode.doesNotContain=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats equals to DEFAULT_WOONPLAATS
        defaultRelatieShouldBeFound("woonplaats.equals=" + DEFAULT_WOONPLAATS);

        // Get all the relatieList where woonplaats equals to UPDATED_WOONPLAATS
        defaultRelatieShouldNotBeFound("woonplaats.equals=" + UPDATED_WOONPLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats not equals to DEFAULT_WOONPLAATS
        defaultRelatieShouldNotBeFound("woonplaats.notEquals=" + DEFAULT_WOONPLAATS);

        // Get all the relatieList where woonplaats not equals to UPDATED_WOONPLAATS
        defaultRelatieShouldBeFound("woonplaats.notEquals=" + UPDATED_WOONPLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats in DEFAULT_WOONPLAATS or UPDATED_WOONPLAATS
        defaultRelatieShouldBeFound("woonplaats.in=" + DEFAULT_WOONPLAATS + "," + UPDATED_WOONPLAATS);

        // Get all the relatieList where woonplaats equals to UPDATED_WOONPLAATS
        defaultRelatieShouldNotBeFound("woonplaats.in=" + UPDATED_WOONPLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats is not null
        defaultRelatieShouldBeFound("woonplaats.specified=true");

        // Get all the relatieList where woonplaats is null
        defaultRelatieShouldNotBeFound("woonplaats.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats contains DEFAULT_WOONPLAATS
        defaultRelatieShouldBeFound("woonplaats.contains=" + DEFAULT_WOONPLAATS);

        // Get all the relatieList where woonplaats contains UPDATED_WOONPLAATS
        defaultRelatieShouldNotBeFound("woonplaats.contains=" + UPDATED_WOONPLAATS);
    }

    @Test
    @Transactional
    void getAllRelatiesByWoonplaatsNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where woonplaats does not contain DEFAULT_WOONPLAATS
        defaultRelatieShouldNotBeFound("woonplaats.doesNotContain=" + DEFAULT_WOONPLAATS);

        // Get all the relatieList where woonplaats does not contain UPDATED_WOONPLAATS
        defaultRelatieShouldBeFound("woonplaats.doesNotContain=" + UPDATED_WOONPLAATS);
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
    void getAllRelatiesByTelefoonnummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer equals to DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.equals=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer equals to UPDATED_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.equals=" + UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer not equals to DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.notEquals=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer not equals to UPDATED_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.notEquals=" + UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer in DEFAULT_TELEFOONNUMMER or UPDATED_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.in=" + DEFAULT_TELEFOONNUMMER + "," + UPDATED_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer equals to UPDATED_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.in=" + UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer is not null
        defaultRelatieShouldBeFound("telefoonnummer.specified=true");

        // Get all the relatieList where telefoonnummer is null
        defaultRelatieShouldNotBeFound("telefoonnummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer is greater than or equal to DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.greaterThanOrEqual=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer is greater than or equal to UPDATED_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.greaterThanOrEqual=" + UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer is less than or equal to DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.lessThanOrEqual=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer is less than or equal to SMALLER_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.lessThanOrEqual=" + SMALLER_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer is less than DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.lessThan=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer is less than UPDATED_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.lessThan=" + UPDATED_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer is greater than DEFAULT_TELEFOONNUMMER
        defaultRelatieShouldNotBeFound("telefoonnummer.greaterThan=" + DEFAULT_TELEFOONNUMMER);

        // Get all the relatieList where telefoonnummer is greater than SMALLER_TELEFOONNUMMER
        defaultRelatieShouldBeFound("telefoonnummer.greaterThan=" + SMALLER_TELEFOONNUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 equals to DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.equals=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 equals to UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.equals=" + UPDATED_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 not equals to DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.notEquals=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 not equals to UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.notEquals=" + UPDATED_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 in DEFAULT_TELEFOONNUMMER_2 or UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.in=" + DEFAULT_TELEFOONNUMMER_2 + "," + UPDATED_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 equals to UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.in=" + UPDATED_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 is not null
        defaultRelatieShouldBeFound("telefoonnummer2.specified=true");

        // Get all the relatieList where telefoonnummer2 is null
        defaultRelatieShouldNotBeFound("telefoonnummer2.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 is greater than or equal to DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.greaterThanOrEqual=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 is greater than or equal to UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.greaterThanOrEqual=" + UPDATED_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 is less than or equal to DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.lessThanOrEqual=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 is less than or equal to SMALLER_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.lessThanOrEqual=" + SMALLER_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 is less than DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.lessThan=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 is less than UPDATED_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.lessThan=" + UPDATED_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer2 is greater than DEFAULT_TELEFOONNUMMER_2
        defaultRelatieShouldNotBeFound("telefoonnummer2.greaterThan=" + DEFAULT_TELEFOONNUMMER_2);

        // Get all the relatieList where telefoonnummer2 is greater than SMALLER_TELEFOONNUMMER_2
        defaultRelatieShouldBeFound("telefoonnummer2.greaterThan=" + SMALLER_TELEFOONNUMMER_2);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 equals to DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.equals=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 equals to UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.equals=" + UPDATED_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 not equals to DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.notEquals=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 not equals to UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.notEquals=" + UPDATED_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 in DEFAULT_TELEFOONNUMMER_3 or UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.in=" + DEFAULT_TELEFOONNUMMER_3 + "," + UPDATED_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 equals to UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.in=" + UPDATED_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 is not null
        defaultRelatieShouldBeFound("telefoonnummer3.specified=true");

        // Get all the relatieList where telefoonnummer3 is null
        defaultRelatieShouldNotBeFound("telefoonnummer3.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 is greater than or equal to DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.greaterThanOrEqual=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 is greater than or equal to UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.greaterThanOrEqual=" + UPDATED_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 is less than or equal to DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.lessThanOrEqual=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 is less than or equal to SMALLER_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.lessThanOrEqual=" + SMALLER_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 is less than DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.lessThan=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 is less than UPDATED_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.lessThan=" + UPDATED_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByTelefoonnummer3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where telefoonnummer3 is greater than DEFAULT_TELEFOONNUMMER_3
        defaultRelatieShouldNotBeFound("telefoonnummer3.greaterThan=" + DEFAULT_TELEFOONNUMMER_3);

        // Get all the relatieList where telefoonnummer3 is greater than SMALLER_TELEFOONNUMMER_3
        defaultRelatieShouldBeFound("telefoonnummer3.greaterThan=" + SMALLER_TELEFOONNUMMER_3);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode equals to DEFAULT_IBANCODE
        defaultRelatieShouldBeFound("ibancode.equals=" + DEFAULT_IBANCODE);

        // Get all the relatieList where ibancode equals to UPDATED_IBANCODE
        defaultRelatieShouldNotBeFound("ibancode.equals=" + UPDATED_IBANCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode not equals to DEFAULT_IBANCODE
        defaultRelatieShouldNotBeFound("ibancode.notEquals=" + DEFAULT_IBANCODE);

        // Get all the relatieList where ibancode not equals to UPDATED_IBANCODE
        defaultRelatieShouldBeFound("ibancode.notEquals=" + UPDATED_IBANCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode in DEFAULT_IBANCODE or UPDATED_IBANCODE
        defaultRelatieShouldBeFound("ibancode.in=" + DEFAULT_IBANCODE + "," + UPDATED_IBANCODE);

        // Get all the relatieList where ibancode equals to UPDATED_IBANCODE
        defaultRelatieShouldNotBeFound("ibancode.in=" + UPDATED_IBANCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode is not null
        defaultRelatieShouldBeFound("ibancode.specified=true");

        // Get all the relatieList where ibancode is null
        defaultRelatieShouldNotBeFound("ibancode.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode contains DEFAULT_IBANCODE
        defaultRelatieShouldBeFound("ibancode.contains=" + DEFAULT_IBANCODE);

        // Get all the relatieList where ibancode contains UPDATED_IBANCODE
        defaultRelatieShouldNotBeFound("ibancode.contains=" + UPDATED_IBANCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByIbancodeNotContainsSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where ibancode does not contain DEFAULT_IBANCODE
        defaultRelatieShouldNotBeFound("ibancode.doesNotContain=" + DEFAULT_IBANCODE);

        // Get all the relatieList where ibancode does not contain UPDATED_IBANCODE
        defaultRelatieShouldBeFound("ibancode.doesNotContain=" + UPDATED_IBANCODE);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer equals to DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.equals=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer equals to UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.equals=" + UPDATED_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer not equals to DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.notEquals=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer not equals to UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.notEquals=" + UPDATED_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsInShouldWork() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer in DEFAULT_KNSB_RELATIENUMMER or UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.in=" + DEFAULT_KNSB_RELATIENUMMER + "," + UPDATED_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer equals to UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.in=" + UPDATED_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer is not null
        defaultRelatieShouldBeFound("knsbRelatienummer.specified=true");

        // Get all the relatieList where knsbRelatienummer is null
        defaultRelatieShouldNotBeFound("knsbRelatienummer.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer is greater than or equal to DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.greaterThanOrEqual=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer is greater than or equal to UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.greaterThanOrEqual=" + UPDATED_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer is less than or equal to DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.lessThanOrEqual=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer is less than or equal to SMALLER_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.lessThanOrEqual=" + SMALLER_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsLessThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer is less than DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.lessThan=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer is less than UPDATED_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.lessThan=" + UPDATED_KNSB_RELATIENUMMER);
    }

    @Test
    @Transactional
    void getAllRelatiesByKnsbRelatienummerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList where knsbRelatienummer is greater than DEFAULT_KNSB_RELATIENUMMER
        defaultRelatieShouldNotBeFound("knsbRelatienummer.greaterThan=" + DEFAULT_KNSB_RELATIENUMMER);

        // Get all the relatieList where knsbRelatienummer is greater than SMALLER_KNSB_RELATIENUMMER
        defaultRelatieShouldBeFound("knsbRelatienummer.greaterThan=" + SMALLER_KNSB_RELATIENUMMER);
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
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM)))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].initialen").value(hasItem(DEFAULT_INITIALEN)))
            .andExpect(jsonPath("$.[*].weergavenaam").value(hasItem(DEFAULT_WEERGAVENAAM)))
            .andExpect(jsonPath("$.[*].geslacht").value(hasItem(DEFAULT_GESLACHT.toString())))
            .andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
            .andExpect(jsonPath("$.[*].relatietype").value(hasItem(DEFAULT_RELATIETYPE.toString())))
            .andExpect(jsonPath("$.[*].inschrijvingsdatum").value(hasItem(DEFAULT_INSCHRIJVINGSDATUM.toString())))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)))
            .andExpect(jsonPath("$.[*].telefoonnummer2").value(hasItem(DEFAULT_TELEFOONNUMMER_2)))
            .andExpect(jsonPath("$.[*].telefoonnummer3").value(hasItem(DEFAULT_TELEFOONNUMMER_3)))
            .andExpect(jsonPath("$.[*].ibancode").value(hasItem(DEFAULT_IBANCODE)))
            .andExpect(jsonPath("$.[*].knsbRelatienummer").value(hasItem(DEFAULT_KNSB_RELATIENUMMER.intValue())))
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
            .voornaam(UPDATED_VOORNAAM)
            .achternaam(UPDATED_ACHTERNAAM)
            .initialen(UPDATED_INITIALEN)
            .weergavenaam(UPDATED_WEERGAVENAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .relatietype(UPDATED_RELATIETYPE)
            .inschrijvingsdatum(UPDATED_INSCHRIJVINGSDATUM)
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .telefoonnummer2(UPDATED_TELEFOONNUMMER_2)
            .telefoonnummer3(UPDATED_TELEFOONNUMMER_3)
            .ibancode(UPDATED_IBANCODE)
            .knsbRelatienummer(UPDATED_KNSB_RELATIENUMMER)
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
        assertThat(testRelatie.getVoornaam()).isEqualTo(UPDATED_VOORNAAM);
        assertThat(testRelatie.getAchternaam()).isEqualTo(UPDATED_ACHTERNAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergavenaam()).isEqualTo(UPDATED_WEERGAVENAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboortedatum()).isEqualTo(UPDATED_GEBOORTEDATUM);
        assertThat(testRelatie.getRelatietype()).isEqualTo(UPDATED_RELATIETYPE);
        assertThat(testRelatie.getInschrijvingsdatum()).isEqualTo(UPDATED_INSCHRIJVINGSDATUM);
        assertThat(testRelatie.getStraatnaam()).isEqualTo(UPDATED_STRAATNAAM);
        assertThat(testRelatie.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testRelatie.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testRelatie.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonnummer()).isEqualTo(UPDATED_TELEFOONNUMMER);
        assertThat(testRelatie.getTelefoonnummer2()).isEqualTo(UPDATED_TELEFOONNUMMER_2);
        assertThat(testRelatie.getTelefoonnummer3()).isEqualTo(UPDATED_TELEFOONNUMMER_3);
        assertThat(testRelatie.getIbancode()).isEqualTo(UPDATED_IBANCODE);
        assertThat(testRelatie.getKnsbRelatienummer()).isEqualTo(UPDATED_KNSB_RELATIENUMMER);
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
            .voornaam(UPDATED_VOORNAAM)
            .achternaam(UPDATED_ACHTERNAAM)
            .initialen(UPDATED_INITIALEN)
            .geslacht(UPDATED_GESLACHT)
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND)
            .email2(UPDATED_EMAIL_2)
            .ibancode(UPDATED_IBANCODE)
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
        assertThat(testRelatie.getVoornaam()).isEqualTo(UPDATED_VOORNAAM);
        assertThat(testRelatie.getAchternaam()).isEqualTo(UPDATED_ACHTERNAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergavenaam()).isEqualTo(DEFAULT_WEERGAVENAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboortedatum()).isEqualTo(DEFAULT_GEBOORTEDATUM);
        assertThat(testRelatie.getRelatietype()).isEqualTo(DEFAULT_RELATIETYPE);
        assertThat(testRelatie.getInschrijvingsdatum()).isEqualTo(DEFAULT_INSCHRIJVINGSDATUM);
        assertThat(testRelatie.getStraatnaam()).isEqualTo(UPDATED_STRAATNAAM);
        assertThat(testRelatie.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testRelatie.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testRelatie.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonnummer()).isEqualTo(DEFAULT_TELEFOONNUMMER);
        assertThat(testRelatie.getTelefoonnummer2()).isEqualTo(DEFAULT_TELEFOONNUMMER_2);
        assertThat(testRelatie.getTelefoonnummer3()).isEqualTo(DEFAULT_TELEFOONNUMMER_3);
        assertThat(testRelatie.getIbancode()).isEqualTo(UPDATED_IBANCODE);
        assertThat(testRelatie.getKnsbRelatienummer()).isEqualTo(DEFAULT_KNSB_RELATIENUMMER);
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
            .voornaam(UPDATED_VOORNAAM)
            .achternaam(UPDATED_ACHTERNAAM)
            .initialen(UPDATED_INITIALEN)
            .weergavenaam(UPDATED_WEERGAVENAAM)
            .geslacht(UPDATED_GESLACHT)
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .relatietype(UPDATED_RELATIETYPE)
            .inschrijvingsdatum(UPDATED_INSCHRIJVINGSDATUM)
            .straatnaam(UPDATED_STRAATNAAM)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .woonplaats(UPDATED_WOONPLAATS)
            .land(UPDATED_LAND)
            .email(UPDATED_EMAIL)
            .email2(UPDATED_EMAIL_2)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .telefoonnummer2(UPDATED_TELEFOONNUMMER_2)
            .telefoonnummer3(UPDATED_TELEFOONNUMMER_3)
            .ibancode(UPDATED_IBANCODE)
            .knsbRelatienummer(UPDATED_KNSB_RELATIENUMMER)
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
        assertThat(testRelatie.getVoornaam()).isEqualTo(UPDATED_VOORNAAM);
        assertThat(testRelatie.getAchternaam()).isEqualTo(UPDATED_ACHTERNAAM);
        assertThat(testRelatie.getInitialen()).isEqualTo(UPDATED_INITIALEN);
        assertThat(testRelatie.getWeergavenaam()).isEqualTo(UPDATED_WEERGAVENAAM);
        assertThat(testRelatie.getGeslacht()).isEqualTo(UPDATED_GESLACHT);
        assertThat(testRelatie.getGeboortedatum()).isEqualTo(UPDATED_GEBOORTEDATUM);
        assertThat(testRelatie.getRelatietype()).isEqualTo(UPDATED_RELATIETYPE);
        assertThat(testRelatie.getInschrijvingsdatum()).isEqualTo(UPDATED_INSCHRIJVINGSDATUM);
        assertThat(testRelatie.getStraatnaam()).isEqualTo(UPDATED_STRAATNAAM);
        assertThat(testRelatie.getHuisnummer()).isEqualTo(UPDATED_HUISNUMMER);
        assertThat(testRelatie.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testRelatie.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
        assertThat(testRelatie.getLand()).isEqualTo(UPDATED_LAND);
        assertThat(testRelatie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRelatie.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testRelatie.getTelefoonnummer()).isEqualTo(UPDATED_TELEFOONNUMMER);
        assertThat(testRelatie.getTelefoonnummer2()).isEqualTo(UPDATED_TELEFOONNUMMER_2);
        assertThat(testRelatie.getTelefoonnummer3()).isEqualTo(UPDATED_TELEFOONNUMMER_3);
        assertThat(testRelatie.getIbancode()).isEqualTo(UPDATED_IBANCODE);
        assertThat(testRelatie.getKnsbRelatienummer()).isEqualTo(UPDATED_KNSB_RELATIENUMMER);
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
