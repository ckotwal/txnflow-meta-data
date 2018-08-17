package com.bnymellon.txnflow.metadata.web.rest;

import com.bnymellon.txnflow.metadata.TxnMetaDataApp;

import com.bnymellon.txnflow.metadata.domain.TransactionFlowGroup;
import com.bnymellon.txnflow.metadata.repository.TransactionFlowGroupRepository;
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

/**
 * Test class for the TransactionFlowGroupResource REST controller.
 *
 * @see TransactionFlowGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TxnMetaDataApp.class)
public class TransactionFlowGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TransactionFlowGroupRepository transactionFlowGroupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionFlowGroupMockMvc;

    private TransactionFlowGroup transactionFlowGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionFlowGroupResource transactionFlowGroupResource = new TransactionFlowGroupResource(transactionFlowGroupRepository);
        this.restTransactionFlowGroupMockMvc = MockMvcBuilders.standaloneSetup(transactionFlowGroupResource)
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
    public static TransactionFlowGroup createEntity(EntityManager em) {
        TransactionFlowGroup transactionFlowGroup = new TransactionFlowGroup()
            .name(DEFAULT_NAME);
        return transactionFlowGroup;
    }

    @Before
    public void initTest() {
        transactionFlowGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionFlowGroup() throws Exception {
        int databaseSizeBeforeCreate = transactionFlowGroupRepository.findAll().size();

        // Create the TransactionFlowGroup
        restTransactionFlowGroupMockMvc.perform(post("/api/transaction-flow-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlowGroup)))
            .andExpect(status().isCreated());

        // Validate the TransactionFlowGroup in the database
        List<TransactionFlowGroup> transactionFlowGroupList = transactionFlowGroupRepository.findAll();
        assertThat(transactionFlowGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionFlowGroup testTransactionFlowGroup = transactionFlowGroupList.get(transactionFlowGroupList.size() - 1);
        assertThat(testTransactionFlowGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransactionFlowGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionFlowGroupRepository.findAll().size();

        // Create the TransactionFlowGroup with an existing ID
        transactionFlowGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionFlowGroupMockMvc.perform(post("/api/transaction-flow-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlowGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransactionFlowGroup> transactionFlowGroupList = transactionFlowGroupRepository.findAll();
        assertThat(transactionFlowGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactionFlowGroups() throws Exception {
        // Initialize the database
        transactionFlowGroupRepository.saveAndFlush(transactionFlowGroup);

        // Get all the transactionFlowGroupList
        restTransactionFlowGroupMockMvc.perform(get("/api/transaction-flow-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionFlowGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTransactionFlowGroup() throws Exception {
        // Initialize the database
        transactionFlowGroupRepository.saveAndFlush(transactionFlowGroup);

        // Get the transactionFlowGroup
        restTransactionFlowGroupMockMvc.perform(get("/api/transaction-flow-groups/{id}", transactionFlowGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionFlowGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionFlowGroup() throws Exception {
        // Get the transactionFlowGroup
        restTransactionFlowGroupMockMvc.perform(get("/api/transaction-flow-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionFlowGroup() throws Exception {
        // Initialize the database
        transactionFlowGroupRepository.saveAndFlush(transactionFlowGroup);
        int databaseSizeBeforeUpdate = transactionFlowGroupRepository.findAll().size();

        // Update the transactionFlowGroup
        TransactionFlowGroup updatedTransactionFlowGroup = transactionFlowGroupRepository.findOne(transactionFlowGroup.getId());
        updatedTransactionFlowGroup
            .name(UPDATED_NAME);

        restTransactionFlowGroupMockMvc.perform(put("/api/transaction-flow-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionFlowGroup)))
            .andExpect(status().isOk());

        // Validate the TransactionFlowGroup in the database
        List<TransactionFlowGroup> transactionFlowGroupList = transactionFlowGroupRepository.findAll();
        assertThat(transactionFlowGroupList).hasSize(databaseSizeBeforeUpdate);
        TransactionFlowGroup testTransactionFlowGroup = transactionFlowGroupList.get(transactionFlowGroupList.size() - 1);
        assertThat(testTransactionFlowGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionFlowGroup() throws Exception {
        int databaseSizeBeforeUpdate = transactionFlowGroupRepository.findAll().size();

        // Create the TransactionFlowGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionFlowGroupMockMvc.perform(put("/api/transaction-flow-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlowGroup)))
            .andExpect(status().isCreated());

        // Validate the TransactionFlowGroup in the database
        List<TransactionFlowGroup> transactionFlowGroupList = transactionFlowGroupRepository.findAll();
        assertThat(transactionFlowGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionFlowGroup() throws Exception {
        // Initialize the database
        transactionFlowGroupRepository.saveAndFlush(transactionFlowGroup);
        int databaseSizeBeforeDelete = transactionFlowGroupRepository.findAll().size();

        // Get the transactionFlowGroup
        restTransactionFlowGroupMockMvc.perform(delete("/api/transaction-flow-groups/{id}", transactionFlowGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionFlowGroup> transactionFlowGroupList = transactionFlowGroupRepository.findAll();
        assertThat(transactionFlowGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionFlowGroup.class);
    }
}
