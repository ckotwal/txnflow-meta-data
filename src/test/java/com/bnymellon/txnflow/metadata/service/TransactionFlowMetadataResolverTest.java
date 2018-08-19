package com.bnymellon.txnflow.metadata.service;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransaction;
import com.bnymellon.txnflow.metadata.domain.ApplicationTransactionField;
import com.bnymellon.txnflow.metadata.domain.FlowApplicationSequence;
import com.bnymellon.txnflow.metadata.domain.TransactionFlow;
import com.bnymellon.txnflow.metadata.repository.TransactionFlowRepository;
import com.bnymellon.txnflow.metadata.service.dto.ApplicationMetadataDTO;
import com.bnymellon.txnflow.metadata.service.dto.TransactionFlowMetadataDTO;
import com.google.common.collect.Maps;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kotwal on 19-08-2018.
 */
public class TransactionFlowMetadataResolverTest {

    public static final int TIMEOUT = 5;
    public static final String FGTM_1 = "fgtm1";
    public static final String FGSP_1 = "fgsp1";
    public static final String FILTER_GSP = "$flow.GTM.1.fgtm1";
    @Mock
    private TransactionFlowRepository mockFlowRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void resolveMetdataFor() throws Exception {
        TransactionFlow transactionFlow1 = new TransactionFlow().name("flow1") ;
        buildApplicationTransactionFlowWithAppFields(transactionFlow1);
        TransactionFlow transactionFlow2 = new TransactionFlow().name("flow1");
        buildApplicationTransactionFlowWithOverrideFields(transactionFlow2);

        ApplicationMetadataDTO gtmapplicationMetadataDTO = new ApplicationMetadataDTO("GTM",1,
            "GTM_IDX", TIMEOUT);
        gtmapplicationMetadataDTO.setFields(Arrays.asList(FGTM_1));
        Map<String, String> filter = new HashMap<>();
        filter.put(FGTM_1, "12");
        gtmapplicationMetadataDTO.setFilters(filter);
        ApplicationMetadataDTO gspapplicationMetadataDTO = new ApplicationMetadataDTO("GSP",2,
            "GSP_IDX", TIMEOUT);
        gspapplicationMetadataDTO.setFields(Arrays.asList(FGSP_1));
        Map<String, String> gspfilter = new HashMap<>();
        gspfilter.put(FGSP_1, FILTER_GSP);
        gspapplicationMetadataDTO.setFilters(gspfilter);
        gspapplicationMetadataDTO.setPredecessorNodes(Arrays.asList("$flow.GTM.1"));

        TransactionFlowMetadataDTO transactionFlowMetadataDTO = new TransactionFlowMetadataDTO("flow1",
            Arrays.asList(gtmapplicationMetadataDTO, gspapplicationMetadataDTO));

        when(mockFlowRepo.findApplicationFields("flow1")).thenReturn(transactionFlow1);
        when(mockFlowRepo.findFlowOverrideFields("flow1")).thenReturn(transactionFlow2);

        TransactionFlowMetadataResolver transactionFlowMetadataResolver = new TransactionFlowMetadataResolver(mockFlowRepo);
        TransactionFlowMetadataDTO transactionFlowMetadataDTO1 = transactionFlowMetadataResolver
                                                                .resolveMetdataFor("flow1", "12");
        assertThat(transactionFlowMetadataDTO1.getApplications().size()).isEqualTo(2);

    }

    public void buildApplicationTransactionFlowWithAppFields(TransactionFlow flow) {
        ApplicationTransaction gspapplicationTransaction = new ApplicationTransaction().name("GSP").eventCount(1L)
            .repositoryEventName("IDX_GSP");
        ApplicationTransactionField applicationTransactionField1 = new ApplicationTransactionField().name("fgsp1")
            .isIdentifier(true).filterValue("$input");
        ApplicationTransactionField applicationTransactionField2 = new ApplicationTransactionField().name("fgsp2")
            .isIdentifier(false);
        gspapplicationTransaction.addField(applicationTransactionField1).addField(applicationTransactionField2);

        ApplicationTransaction gtmapplicationTransaction = new ApplicationTransaction().name("GTM").eventCount(1L)
            .repositoryEventName("IDX_GTM");
        ApplicationTransactionField gtmapplicationTransactionField = new ApplicationTransactionField().name(FGTM_1)
            .isIdentifier(true).filterValue("$input");
        gtmapplicationTransaction.addField(gtmapplicationTransactionField);

        FlowApplicationSequence flowApplicationSequence1 = new FlowApplicationSequence().appSequence(1);
        FlowApplicationSequence flowApplicationSequence2 = new FlowApplicationSequence().appSequence(2);
        flow.addApplication(flowApplicationSequence1).addApplication(flowApplicationSequence2);
        gspapplicationTransaction.addFlow(flowApplicationSequence2);
        gtmapplicationTransaction.addFlow(flowApplicationSequence1);


    }

    public void buildApplicationTransactionFlowWithOverrideFields(TransactionFlow flow) {
        ApplicationTransactionField overrideapplicationTransactionField1 = new ApplicationTransactionField().name(FGSP_1)
            .isIdentifier(true).filterValue(FILTER_GSP);
        FlowApplicationSequence flowApplicationSequence2 = new FlowApplicationSequence().appSequence(2)
            .addField(overrideapplicationTransactionField1);
        flow.addApplication(flowApplicationSequence2);
        ApplicationTransaction gspapplicationTransaction = new ApplicationTransaction().name("GSP").eventCount(1L).repositoryEventName("IDX_GSP");
        gspapplicationTransaction.addFlow(flowApplicationSequence2);


    }

}
