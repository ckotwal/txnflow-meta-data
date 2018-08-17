package com.bnymellon.txnflow.metadata.web.rest;

import com.bnymellon.txnflow.metadata.TxnMetaDataApp;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransaction;
import com.bnymellon.txnflow.metadata.repository.ApplicationTransactionRepository;
import com.bnymellon.txnflow.metadata.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bnymellon.txnflow.metadata.domain.enumeration.EventRepositoryType;
/**
 * Test class for the ApplicationTransactionResource REST controller.
 *
 * @see ApplicationTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TxnMetaDataApp.class)
public class ApplicationTransactionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_EVENT_COUNT = 1L;
    private static final Long UPDATED_EVENT_COUNT = 2L;

    private static final EventRepositoryType DEFAULT_EVENT_REPOSITORY_TYPE = EventRepositoryType.DP;
    private static final EventRepositoryType UPDATED_EVENT_REPOSITORY_TYPE = EventRepositoryType.DP;

    private static final String DEFAULT_REPOSITORY_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORY_EVENT_NAME = "BBBBBBBBBB";

    @Autowired
    private ApplicationTransactionRepository applicationTransactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationTransactionMockMvc;

    private ApplicationTransaction applicationTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicationTransactionResource applicationTransactionResource = new ApplicationTransactionResource(applicationTransactionRepository);
        this.restApplicationTransactionMockMvc = MockMvcBuilders.standaloneSetup(applicationTransactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationTransaction createEntity(EntityManager em) {
        ApplicationTransaction applicationTransaction = new ApplicationTransaction()
            .name(DEFAULT_NAME)
            .eventCount(DEFAULT_EVENT_COUNT)
            .eventRepositoryType(DEFAULT_EVENT_REPOSITORY_TYPE)
            .repositoryEventName(DEFAULT_REPOSITORY_EVENT_NAME);
        return applicationTransaction;
    }

    @Before
    public void initTest() {
        applicationTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationTransaction() throws Exception {
        int databaseSizeBeforeCreate = applicationTransactionRepository.findAll().size();

        // Create the ApplicationTransaction
        restApplicationTransactionMockMvc.perform(post("/api/application-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTransaction)))
            .andExpect(status().isCreated());

        // Validate the ApplicationTransaction in the database
        List<ApplicationTransaction> applicationTransactionList = applicationTransactionRepository.findAll();
        assertThat(applicationTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationTransaction testApplicationTransaction = applicationTransactionList.get(applicationTransactionList.size() - 1);
        assertThat(testApplicationTransaction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationTransaction.getEventCount()).isEqualTo(DEFAULT_EVENT_COUNT);
        assertThat(testApplicationTransaction.getEventRepositoryType()).isEqualTo(DEFAULT_EVENT_REPOSITORY_TYPE);
        assertThat(testApplicationTransaction.getRepositoryEventName()).isEqualTo(DEFAULT_REPOSITORY_EVENT_NAME);
    }

    @Test
    @Transactional
    public void createApplicationTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationTransactionRepository.findAll().size();

        // Create the ApplicationTransaction with an existing ID
        applicationTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationTransactionMockMvc.perform(post("/api/application-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ApplicationTransaction> applicationTransactionList = applicationTransactionRepository.findAll();
        assertThat(applicationTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicationTransactions() throws Exception {
        // Initialize the database
        applicationTransactionRepository.saveAndFlush(applicationTransaction);

        // Get all the applicationTransactionList
        restApplicationTransactionMockMvc.perform(get("/api/application-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventCount").value(hasItem(DEFAULT_EVENT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].eventRepositoryType").value(hasItem(DEFAULT_EVENT_REPOSITORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].repositoryEventName").value(hasItem(DEFAULT_REPOSITORY_EVENT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getApplicationTransaction() throws Exception {
        // Initialize the database
        applicationTransactionRepository.saveAndFlush(applicationTransaction);

        // Get the applicationTransaction
        restApplicationTransactionMockMvc.perform(get("/api/application-transactions/{id}", applicationTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationTransaction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.eventCount").value(DEFAULT_EVENT_COUNT.intValue()))
            .andExpect(jsonPath("$.eventRepositoryType").value(DEFAULT_EVENT_REPOSITORY_TYPE.toString()))
            .andExpect(jsonPath("$.repositoryEventName").value(DEFAULT_REPOSITORY_EVENT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationTransaction() throws Exception {
        // Get the applicationTransaction
        restApplicationTransactionMockMvc.perform(get("/api/application-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationTransaction() throws Exception {
        // Initialize the database
        applicationTransactionRepository.saveAndFlush(applicationTransaction);
        int databaseSizeBeforeUpdate = applicationTransactionRepository.findAll().size();

        // Update the applicationTransaction
        ApplicationTransaction updatedApplicationTransaction = applicationTransactionRepository.findOne(applicationTransaction.getId());
        updatedApplicationTransaction
            .name(UPDATED_NAME)
            .eventCount(UPDATED_EVENT_COUNT)
            .eventRepositoryType(UPDATED_EVENT_REPOSITORY_TYPE)
            .repositoryEventName(UPDATED_REPOSITORY_EVENT_NAME);

        restApplicationTransactionMockMvc.perform(put("/api/application-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationTransaction)))
            .andExpect(status().isOk());

        // Validate the ApplicationTransaction in the database
        List<ApplicationTransaction> applicationTransactionList = applicationTransactionRepository.findAll();
        assertThat(applicationTransactionList).hasSize(databaseSizeBeforeUpdate);
        ApplicationTransaction testApplicationTransaction = applicationTransactionList.get(applicationTransactionList.size() - 1);
        assertThat(testApplicationTransaction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationTransaction.getEventCount()).isEqualTo(UPDATED_EVENT_COUNT);
        assertThat(testApplicationTransaction.getEventRepositoryType()).isEqualTo(UPDATED_EVENT_REPOSITORY_TYPE);
        assertThat(testApplicationTransaction.getRepositoryEventName()).isEqualTo(UPDATED_REPOSITORY_EVENT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationTransaction() throws Exception {
        int databaseSizeBeforeUpdate = applicationTransactionRepository.findAll().size();

        // Create the ApplicationTransaction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicationTransactionMockMvc.perform(put("/api/application-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTransaction)))
            .andExpect(status().isCreated());

        // Validate the ApplicationTransaction in the database
        List<ApplicationTransaction> applicationTransactionList = applicationTransactionRepository.findAll();
        assertThat(applicationTransactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicationTransaction() throws Exception {
        // Initialize the database
        applicationTransactionRepository.saveAndFlush(applicationTransaction);
        int databaseSizeBeforeDelete = applicationTransactionRepository.findAll().size();

        // Get the applicationTransaction
        restApplicationTransactionMockMvc.perform(delete("/api/application-transactions/{id}", applicationTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationTransaction> applicationTransactionList = applicationTransactionRepository.findAll();
        assertThat(applicationTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationTransaction.class);
    }
}
