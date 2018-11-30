package com.everteam.web.rest;

import com.everteam.FootApp;

import com.everteam.domain.Stadium;
import com.everteam.repository.StadiumRepository;
import com.everteam.service.StadiumService;
import com.everteam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.everteam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StadiumResource REST controller.
 *
 * @see StadiumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FootApp.class)
public class StadiumResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStadiumMockMvc;

    private Stadium stadium;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StadiumResource stadiumResource = new StadiumResource(stadiumService);
        this.restStadiumMockMvc = MockMvcBuilders.standaloneSetup(stadiumResource)
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
    public static Stadium createEntity(EntityManager em) {
        Stadium stadium = new Stadium()
            .name(DEFAULT_NAME);
        return stadium;
    }

    @Before
    public void initTest() {
        stadium = createEntity(em);
    }

    @Test
    @Transactional
    public void createStadium() throws Exception {
        int databaseSizeBeforeCreate = stadiumRepository.findAll().size();

        // Create the Stadium
        restStadiumMockMvc.perform(post("/api/stadiums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isCreated());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeCreate + 1);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStadiumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stadiumRepository.findAll().size();

        // Create the Stadium with an existing ID
        stadium.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStadiumMockMvc.perform(post("/api/stadiums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStadiums() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        // Get all the stadiumList
        restStadiumMockMvc.perform(get("/api/stadiums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stadium.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStadium() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        // Get the stadium
        restStadiumMockMvc.perform(get("/api/stadiums/{id}", stadium.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stadium.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStadium() throws Exception {
        // Get the stadium
        restStadiumMockMvc.perform(get("/api/stadiums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStadium() throws Exception {
        // Initialize the database
        stadiumService.save(stadium);

        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();

        // Update the stadium
        Stadium updatedStadium = stadiumRepository.findById(stadium.getId()).get();
        // Disconnect from session so that the updates on updatedStadium are not directly saved in db
        em.detach(updatedStadium);
        updatedStadium
            .name(UPDATED_NAME);

        restStadiumMockMvc.perform(put("/api/stadiums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStadium)))
            .andExpect(status().isOk());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();

        // Create the Stadium

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadiumMockMvc.perform(put("/api/stadiums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStadium() throws Exception {
        // Initialize the database
        stadiumService.save(stadium);

        int databaseSizeBeforeDelete = stadiumRepository.findAll().size();

        // Get the stadium
        restStadiumMockMvc.perform(delete("/api/stadiums/{id}", stadium.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stadium.class);
        Stadium stadium1 = new Stadium();
        stadium1.setId(1L);
        Stadium stadium2 = new Stadium();
        stadium2.setId(stadium1.getId());
        assertThat(stadium1).isEqualTo(stadium2);
        stadium2.setId(2L);
        assertThat(stadium1).isNotEqualTo(stadium2);
        stadium1.setId(null);
        assertThat(stadium1).isNotEqualTo(stadium2);
    }
}
