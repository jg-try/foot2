package com.everteam.web.rest;

import com.everteam.FootApp;

import com.everteam.domain.TeamS;
import com.everteam.repository.TeamSRepository;
import com.everteam.service.TeamSService;
import com.everteam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.everteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamSResource REST controller.
 *
 * @see TeamSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FootApp.class)
public class TeamSResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TeamSRepository teamSRepository;

    @Mock
    private TeamSRepository teamSRepositoryMock;

    @Mock
    private TeamSService teamSServiceMock;

    @Autowired
    private TeamSService teamSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamSMockMvc;

    private TeamS teamS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamSResource teamSResource = new TeamSResource(teamSService);
        this.restTeamSMockMvc = MockMvcBuilders.standaloneSetup(teamSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamS createEntity(EntityManager em) {
        TeamS teamS = new TeamS()
            .name(DEFAULT_NAME);
        return teamS;
    }

    @Before
    public void initTest() {
        teamS = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamS() throws Exception {
        int databaseSizeBeforeCreate = teamSRepository.findAll().size();

        // Create the TeamS
        restTeamSMockMvc.perform(post("/api/team-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamS)))
            .andExpect(status().isCreated());

        // Validate the TeamS in the database
        List<TeamS> teamSList = teamSRepository.findAll();
        assertThat(teamSList).hasSize(databaseSizeBeforeCreate + 1);
        TeamS testTeamS = teamSList.get(teamSList.size() - 1);
        assertThat(testTeamS.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTeamSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamSRepository.findAll().size();

        // Create the TeamS with an existing ID
        teamS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamSMockMvc.perform(post("/api/team-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamS)))
            .andExpect(status().isBadRequest());

        // Validate the TeamS in the database
        List<TeamS> teamSList = teamSRepository.findAll();
        assertThat(teamSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeamS() throws Exception {
        // Initialize the database
        teamSRepository.saveAndFlush(teamS);

        // Get all the teamSList
        restTeamSMockMvc.perform(get("/api/team-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamS.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTeamSWithEagerRelationshipsIsEnabled() throws Exception {
        TeamSResource teamSResource = new TeamSResource(teamSServiceMock);
        when(teamSServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTeamSMockMvc = MockMvcBuilders.standaloneSetup(teamSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeamSMockMvc.perform(get("/api/team-s?eagerload=true"))
        .andExpect(status().isOk());

        verify(teamSServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTeamSWithEagerRelationshipsIsNotEnabled() throws Exception {
        TeamSResource teamSResource = new TeamSResource(teamSServiceMock);
            when(teamSServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTeamSMockMvc = MockMvcBuilders.standaloneSetup(teamSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeamSMockMvc.perform(get("/api/team-s?eagerload=true"))
        .andExpect(status().isOk());

            verify(teamSServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTeamS() throws Exception {
        // Initialize the database
        teamSRepository.saveAndFlush(teamS);

        // Get the teamS
        restTeamSMockMvc.perform(get("/api/team-s/{id}", teamS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamS.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeamS() throws Exception {
        // Get the teamS
        restTeamSMockMvc.perform(get("/api/team-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamS() throws Exception {
        // Initialize the database
        teamSService.save(teamS);

        int databaseSizeBeforeUpdate = teamSRepository.findAll().size();

        // Update the teamS
        TeamS updatedTeamS = teamSRepository.findById(teamS.getId()).get();
        // Disconnect from session so that the updates on updatedTeamS are not directly saved in db
        em.detach(updatedTeamS);
        updatedTeamS
            .name(UPDATED_NAME);

        restTeamSMockMvc.perform(put("/api/team-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamS)))
            .andExpect(status().isOk());

        // Validate the TeamS in the database
        List<TeamS> teamSList = teamSRepository.findAll();
        assertThat(teamSList).hasSize(databaseSizeBeforeUpdate);
        TeamS testTeamS = teamSList.get(teamSList.size() - 1);
        assertThat(testTeamS.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamS() throws Exception {
        int databaseSizeBeforeUpdate = teamSRepository.findAll().size();

        // Create the TeamS

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamSMockMvc.perform(put("/api/team-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamS)))
            .andExpect(status().isBadRequest());

        // Validate the TeamS in the database
        List<TeamS> teamSList = teamSRepository.findAll();
        assertThat(teamSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeamS() throws Exception {
        // Initialize the database
        teamSService.save(teamS);

        int databaseSizeBeforeDelete = teamSRepository.findAll().size();

        // Get the teamS
        restTeamSMockMvc.perform(delete("/api/team-s/{id}", teamS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamS> teamSList = teamSRepository.findAll();
        assertThat(teamSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamS.class);
        TeamS teamS1 = new TeamS();
        teamS1.setId(1L);
        TeamS teamS2 = new TeamS();
        teamS2.setId(teamS1.getId());
        assertThat(teamS1).isEqualTo(teamS2);
        teamS2.setId(2L);
        assertThat(teamS1).isNotEqualTo(teamS2);
        teamS1.setId(null);
        assertThat(teamS1).isNotEqualTo(teamS2);
    }
}
