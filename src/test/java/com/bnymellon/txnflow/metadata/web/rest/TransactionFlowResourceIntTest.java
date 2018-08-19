package com.bnymellon.txnflow.metadata.web.rest;

import com.bnymellon.txnflow.metadata.TxnMetaDataApp;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransaction;
import com.bnymellon.txnflow.metadata.domain.ApplicationTransactionField;
import com.bnymellon.txnflow.metadata.domain.FlowApplicationSequence;
import com.bnymellon.txnflow.metadata.domain.TransactionFlow;
import com.bnymellon.txnflow.metadata.domain.enumeration.EventRepositoryType;
import com.bnymellon.txnflow.metadata.repository.ApplicationTransactionFieldRepository;
import com.bnymellon.txnflow.metadata.repository.ApplicationTransactionRepository;
import com.bnymellon.txnflow.metadata.repository.FlowApplicationSequenceRepository;
import com.bnymellon.txnflow.metadata.repository.TransactionFlowRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransactionFlowResource REST controller.
 *
 * @see TransactionFlowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TxnMetaDataApp.class)
public class TransactionFlowResourceIntTest {

    private static final String DEFAULT_L_OB = "AAAAAAAAAA";
    private static final String UPDATED_L_OB = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLOW_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_CORRELATION_ID = "BBBBBBBBBB";
    public static final String STIF_CASH_LEG = "STIF Cash Leg";
    public static final String GSP = "GSP";
    public static final String FIELD_1 = "field1";
    public static final String FIELD_2 = "field2";
    public static final String FIELD_3 = "field3";
    public static final String FIELD_4 = "field4";

    @Autowired
    private TransactionFlowRepository transactionFlowRepository;

    @Autowired
    private ApplicationTransactionRepository applicationTransactionRepository;

    @Autowired
    private ApplicationTransactionFieldRepository applicationTransactionFieldRepository;

    @Autowired
    private FlowApplicationSequenceRepository flowApplicationSequenceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionFlowMockMvc;

    private TransactionFlow transactionFlow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionFlowResource transactionFlowResource = new TransactionFlowResource(transactionFlowRepository);
        this.restTransactionFlowMockMvc = MockMvcBuilders.standaloneSetup(transactionFlowResource)
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
    public static TransactionFlow createEntity(EntityManager em) {
        TransactionFlow transactionFlow = new TransactionFlow()
            .lOB(DEFAULT_L_OB)
            .name(DEFAULT_NAME)
            .flowCorrelationId(DEFAULT_FLOW_CORRELATION_ID);
        return transactionFlow;
    }

    @Before
    public void initTest() {
        transactionFlow = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionFlow() throws Exception {
        int databaseSizeBeforeCreate = transactionFlowRepository.findAll().size();

        // Create the TransactionFlow
        restTransactionFlowMockMvc.perform(post("/api/transaction-flows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlow)))
            .andExpect(status().isCreated());

        // Validate the TransactionFlow in the database
        List<TransactionFlow> transactionFlowList = transactionFlowRepository.findAll();
        assertThat(transactionFlowList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionFlow testTransactionFlow = transactionFlowList.get(transactionFlowList.size() - 1);
        assertThat(testTransactionFlow.getlOB()).isEqualTo(DEFAULT_L_OB);
        assertThat(testTransactionFlow.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransactionFlow.getFlowCorrelationId()).isEqualTo(DEFAULT_FLOW_CORRELATION_ID);
    }

    @Test
    @Transactional
    public void createTransactionFlowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionFlowRepository.findAll().size();

        // Create the TransactionFlow with an existing ID
        transactionFlow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionFlowMockMvc.perform(post("/api/transaction-flows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlow)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransactionFlow> transactionFlowList = transactionFlowRepository.findAll();
        assertThat(transactionFlowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactionFlows() throws Exception {
        // Initialize the database
        transactionFlowRepository.saveAndFlush(transactionFlow);

        // Get all the transactionFlowList
        restTransactionFlowMockMvc.perform(get("/api/transaction-flows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionFlow.getId().intValue())))
            .andExpect(jsonPath("$.[*].lOB").value(hasItem(DEFAULT_L_OB.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].flowCorrelationId").value(hasItem(DEFAULT_FLOW_CORRELATION_ID.toString())));
    }

    @Test
    @Transactional
    public void getTransactionFlow() throws Exception {
        // Initialize the database
        transactionFlowRepository.saveAndFlush(transactionFlow);

        // Get the transactionFlow
        restTransactionFlowMockMvc.perform(get("/api/transaction-flows/{id}", transactionFlow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionFlow.getId().intValue()))
            .andExpect(jsonPath("$.lOB").value(DEFAULT_L_OB.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.flowCorrelationId").value(DEFAULT_FLOW_CORRELATION_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionFlow() throws Exception {
        // Get the transactionFlow
        restTransactionFlowMockMvc.perform(get("/api/transaction-flows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionFlow() throws Exception {
        // Initialize the database
        transactionFlowRepository.saveAndFlush(transactionFlow);
        int databaseSizeBeforeUpdate = transactionFlowRepository.findAll().size();

        // Update the transactionFlow
        TransactionFlow updatedTransactionFlow = transactionFlowRepository.findOne(transactionFlow.getId());
        updatedTransactionFlow
            .lOB(UPDATED_L_OB)
            .name(UPDATED_NAME)
            .flowCorrelationId(UPDATED_FLOW_CORRELATION_ID);

        restTransactionFlowMockMvc.perform(put("/api/transaction-flows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionFlow)))
            .andExpect(status().isOk());

        // Validate the TransactionFlow in the database
        List<TransactionFlow> transactionFlowList = transactionFlowRepository.findAll();
        assertThat(transactionFlowList).hasSize(databaseSizeBeforeUpdate);
        TransactionFlow testTransactionFlow = transactionFlowList.get(transactionFlowList.size() - 1);
        assertThat(testTransactionFlow.getlOB()).isEqualTo(UPDATED_L_OB);
        assertThat(testTransactionFlow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransactionFlow.getFlowCorrelationId()).isEqualTo(UPDATED_FLOW_CORRELATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionFlow() throws Exception {
        int databaseSizeBeforeUpdate = transactionFlowRepository.findAll().size();

        // Create the TransactionFlow

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionFlowMockMvc.perform(put("/api/transaction-flows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionFlow)))
            .andExpect(status().isCreated());

        // Validate the TransactionFlow in the database
        List<TransactionFlow> transactionFlowList = transactionFlowRepository.findAll();
        assertThat(transactionFlowList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionFlow() throws Exception {
        // Initialize the database
        transactionFlowRepository.saveAndFlush(transactionFlow);
        int databaseSizeBeforeDelete = transactionFlowRepository.findAll().size();

        // Get the transactionFlow
        restTransactionFlowMockMvc.perform(delete("/api/transaction-flows/{id}", transactionFlow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionFlow> transactionFlowList = transactionFlowRepository.findAll();
        assertThat(transactionFlowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void retrieveRegularApplicationFields() {
        setupTransactionFlowFields();
        Set<TransactionFlow> applicationFields = transactionFlowRepository.findApplicationFields(STIF_CASH_LEG);
        TransactionFlow transactionFlow = applicationFields.iterator().next();
        FlowApplicationSequence flowSequence = transactionFlow.getApplications().iterator().next();
        ApplicationTransaction application = flowSequence.getApplication();
        assertThat(application.getName()).isEqualTo(GSP);
        assertThat(application.getFields().stream()
            .map(f -> f.getName())
            .collect(Collectors.toList()))
            .containsExactlyInAnyOrder(
                FIELD_1,
                FIELD_2);
    }

    private void setupTransactionFlowFields() {
        ApplicationTransactionField applicationTransactionField1 = new ApplicationTransactionField()
                                                                    .name(FIELD_1).filterValue("$input");
        ApplicationTransactionField applicationTransactionField2 = new ApplicationTransactionField()
            .name(FIELD_2).filterValue("1234");


        ApplicationTransaction applicationTransaction = new ApplicationTransaction()
            .name(GSP).eventRepositoryType(EventRepositoryType.DP).eventCount(1L).
            addField(applicationTransactionField1).addField(applicationTransactionField2);
        applicationTransactionRepository.save(applicationTransaction);

        applicationTransactionFieldRepository.save(applicationTransactionField1);
        applicationTransactionFieldRepository.save(applicationTransactionField2);
        TransactionFlow transactionFlow = new TransactionFlow().name(STIF_CASH_LEG);


        FlowApplicationSequence flowApplicationSequence = new FlowApplicationSequence().appSequence(1);
        transactionFlow.addApplication(flowApplicationSequence);
        applicationTransaction.addFlow(flowApplicationSequence);
        transactionFlowRepository.saveAndFlush(transactionFlow);
        flowApplicationSequenceRepository.saveAndFlush(flowApplicationSequence);

    }

    @Test
    @Transactional
    public void retrieveOverrideApplicationFields() {
        setupTransactionFlowOverrideFields();
        Set<TransactionFlow> transactionFlows = transactionFlowRepository.findFlowOverrideFields(STIF_CASH_LEG);
        TransactionFlow transactionFlow = transactionFlows.iterator().next();
        FlowApplicationSequence flowSequence = transactionFlow.getApplications().iterator().next();
        ApplicationTransaction application = flowSequence.getApplication();
        assertThat(application.getName()).isEqualTo(GSP);
        assertThat(flowSequence.getFields().stream()
            .map(f -> f.getName())
            .collect(Collectors.toList()))
            .containsExactlyInAnyOrder(
                FIELD_3,
                FIELD_4);
    }

    private void setupTransactionFlowOverrideFields() {
        ApplicationTransactionField applicationTransactionField1 = new ApplicationTransactionField()
            .name(FIELD_3).filterValue("$flow.GTM.2.corrId");
        ApplicationTransactionField applicationTransactionField2 = new ApplicationTransactionField()
            .name(FIELD_4).filterValue("$input");


        ApplicationTransaction applicationTransaction = new ApplicationTransaction()
            .name(GSP).eventRepositoryType(EventRepositoryType.DP).eventCount(1L);

        applicationTransactionFieldRepository.saveAndFlush(applicationTransactionField1);
        applicationTransactionFieldRepository.saveAndFlush(applicationTransactionField2);
        applicationTransactionRepository.save(applicationTransaction);

        TransactionFlow transactionFlow = new TransactionFlow().name(STIF_CASH_LEG);

       transactionFlowRepository.saveAndFlush(transactionFlow);
        FlowApplicationSequence flowApplicationSequence = new FlowApplicationSequence().appSequence(1)
            .addField(applicationTransactionField1).addField(applicationTransactionField2);
        flowApplicationSequenceRepository.saveAndFlush(flowApplicationSequence);

        transactionFlow.addApplication(flowApplicationSequence);
        applicationTransaction.addFlow(flowApplicationSequence);
        //transactionFlowRepository.saveAndFlush(transactionFlow);


    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionFlow.class);
    }
}
