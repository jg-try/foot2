package com.everteam.web.rest;

import com.everteam.FootApp;

import com.everteam.domain.TeamC;
import com.everteam.repository.TeamCRepository;
import com.everteam.service.TeamCService;
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
 * Test class for the TeamCResource REST controller.
 *
 * @see TeamCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FootApp.class)
public class TeamCResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TeamCRepository teamCRepository;

    @Mock
    private TeamCRepository teamCRepositoryMock;

    @Mock
    private TeamCService teamCServiceMock;

    @Autowired
    private TeamCService teamCService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamCMockMvc;

    private TeamC teamC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamCResource teamCResource = new TeamCResource(teamCService);
        this.restTeamCMockMvc = MockMvcBuilders.standaloneSetup(teamCResource)
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
    public static TeamC createEntity(EntityManager em) {
        TeamC teamC = new TeamC()
            .name(DEFAULT_NAME);
        return teamC;
    }

    @Before
    public void initTest() {
        teamC = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamC() throws Exception {
        int databaseSizeBeforeCreate = teamCRepository.findAll().size();

        // Create the TeamC
        restTeamCMockMvc.perform(post("/api/team-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamC)))
            .andExpect(status().isCreated());

        // Validate the TeamC in the database
        List<TeamC> teamCList = teamCRepository.findAll();
        assertThat(teamCList).hasSize(databaseSizeBeforeCreate + 1);
        TeamC testTeamC = teamCList.get(teamCList.size() - 1);
        assertThat(testTeamC.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTeamCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamCRepository.findAll().size();

        // Create the TeamC with an existing ID
        teamC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamCMockMvc.perform(post("/api/team-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamC)))
            .andExpect(status().isBadRequest());

        // Validate the TeamC in the database
        List<TeamC> teamCList = teamCRepository.findAll();
        assertThat(teamCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeamCS() throws Exception {
        // Initialize the database
        teamCRepository.saveAndFlush(teamC);

        // Get all the teamCList
        restTeamCMockMvc.perform(get("/api/team-cs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamC.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTeamCSWithEagerRelationshipsIsEnabled() throws Exception {
        TeamCResource teamCResource = new TeamCResource(teamCServiceMock);
        when(teamCServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTeamCMockMvc = MockMvcBuilders.standaloneSetup(teamCResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeamCMockMvc.perform(get("/api/team-cs?eagerload=true"))
        .andExpect(status().isOk());

        verify(teamCServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTeamCSWithEagerRelationshipsIsNotEnabled() throws Exception {
        TeamCResource teamCResource = new TeamCResource(teamCServiceMock);
            when(teamCServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTeamCMockMvc = MockMvcBuilders.standaloneSetup(teamCResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeamCMockMvc.perform(get("/api/team-cs?eagerload=true"))
        .andExpect(status().isOk());

            verify(teamCServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTeamC() throws Exception {
        // Initialize the database
        teamCRepository.saveAndFlush(teamC);

        // Get the teamC
        restTeamCMockMvc.perform(get("/api/team-cs/{id}", teamC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamC.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeamC() throws Exception {
        // Get the teamC
        restTeamCMockMvc.perform(get("/api/team-cs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamC() throws Exception {
        // Initialize the database
        teamCService.save(teamC);

        int databaseSizeBeforeUpdate = teamCRepository.findAll().size();

        // Update the teamC
        TeamC updatedTeamC = teamCRepository.findById(teamC.getId()).get();
        // Disconnect from session so that the updates on updatedTeamC are not directly saved in db
        em.detach(updatedTeamC);
        updatedTeamC
            .name(UPDATED_NAME);

        restTeamCMockMvc.perform(put("/api/team-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamC)))
            .andExpect(status().isOk());

        // Validate the TeamC in the database
        List<TeamC> teamCList = teamCRepository.findAll();
        assertThat(teamCList).hasSize(databaseSizeBeforeUpdate);
        TeamC testTeamC = teamCList.get(teamCList.size() - 1);
        assertThat(testTeamC.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamC() throws Exception {
        int databaseSizeBeforeUpdate = teamCRepository.findAll().size();

        // Create the TeamC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamCMockMvc.perform(put("/api/team-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamC)))
            .andExpect(status().isBadRequest());

        // Validate the TeamC in the database
        List<TeamC> teamCList = teamCRepository.findAll();
        assertThat(teamCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeamC() throws Exception {
        // Initialize the database
        teamCService.save(teamC);

        int databaseSizeBeforeDelete = teamCRepository.findAll().size();

        // Get the teamC
        restTeamCMockMvc.perform(delete("/api/team-cs/{id}", teamC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamC> teamCList = teamCRepository.findAll();
        assertThat(teamCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamC.class);
        TeamC teamC1 = new TeamC();
        teamC1.setId(1L);
        TeamC teamC2 = new TeamC();
        teamC2.setId(teamC1.getId());
        assertThat(teamC1).isEqualTo(teamC2);
        teamC2.setId(2L);
        assertThat(teamC1).isNotEqualTo(teamC2);
        teamC1.setId(null);
        assertThat(teamC1).isNotEqualTo(teamC2);
    }
}
