package com.bnymellon.txnflow.metadata.service;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransactionField;
import com.bnymellon.txnflow.metadata.domain.FlowApplicationSequence;
import com.bnymellon.txnflow.metadata.domain.TransactionFlow;
import com.bnymellon.txnflow.metadata.repository.TransactionFlowRepository;
import com.bnymellon.txnflow.metadata.service.dto.ApplicationMetadataDTO;
import com.bnymellon.txnflow.metadata.service.dto.TransactionFlowMetadataDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kotwal on 19-08-2018.
 */
@Service
public class TransactionFlowMetadataResolver {
    private TransactionFlowRepository transactionFlowRepository;

    public TransactionFlowMetadataResolver(TransactionFlowRepository transactionFlowRepository) {
        this.transactionFlowRepository = transactionFlowRepository;
    }

    public TransactionFlowMetadataDTO resolveMetdataFor(String flowName,
                                                 String transactionFlowId ) {
        TransactionFlow flowWithAppFields = transactionFlowRepository.findApplicationFields(flowName);
        TransactionFlow flowWithOverrideFields = transactionFlowRepository.findFlowOverrideFields(flowName);
        if(flowWithAppFields == null || flowWithOverrideFields == null || !flowWithAppFields.getName().equals(
            flowWithOverrideFields.getName())) {
            throw new IllegalArgumentException("No flow name:"+ flowName );
        }

        Map<String, FlowApplicationSequence> nodeToFields = flowWithAppFields.getApplications().stream()
            .collect(
            Collectors.toMap(FlowApplicationSequence::getKey, fas -> fas));

        Map<String, FlowApplicationSequence> nodeToOverriddenFields  = flowWithOverrideFields.getApplications().stream()
            .collect(
            Collectors.toMap(FlowApplicationSequence::getKey, fas -> fas));

        nodeToFields.putAll(nodeToOverriddenFields);
        return new TransactionFlowMetadataDTO(flowName,buildApplicationDTO(transactionFlowId, nodeToFields) );

    }

    private List<ApplicationMetadataDTO> buildApplicationDTO(String transactionFlowId, Map<String, FlowApplicationSequence> nodeToFields) {
        return nodeToFields.values().stream()
            .map(fas -> fas.toDTO(transactionFlowId, 36000))
            .collect(Collectors.toList());
    }
}
