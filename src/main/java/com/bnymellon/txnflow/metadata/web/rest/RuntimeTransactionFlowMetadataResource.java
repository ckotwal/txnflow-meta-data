package com.bnymellon.txnflow.metadata.web.rest;

import com.bnymellon.txnflow.metadata.domain.TransactionFlow;
import com.bnymellon.txnflow.metadata.repository.TransactionFlowRepository;
import com.bnymellon.txnflow.metadata.service.TransactionFlowMetadataResolver;
import com.bnymellon.txnflow.metadata.service.dto.RuntimeTransactionFlowMetadataRequest;
import com.bnymellon.txnflow.metadata.service.dto.TransactionFlowMetadataDTO;
import com.bnymellon.txnflow.metadata.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TransactionFlow.
 */
@RestController
@RequestMapping("/api")
public class RuntimeTransactionFlowMetadataResource {

    private final Logger log = LoggerFactory.getLogger(RuntimeTransactionFlowMetadataResource.class);

    private static final String ENTITY_NAME = "transactionFlow";
    private TransactionFlowMetadataResolver transactionFlowMetadataResolver;

    public RuntimeTransactionFlowMetadataResource(TransactionFlowMetadataResolver transactionFlowMetadataResolver) {
        this.transactionFlowMetadataResolver = transactionFlowMetadataResolver
    }

    /**
     * POST  /runtime-transaction-flows : Create a new runtime transactionFlow.
     *
     * @param request the transactionFlow to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionFlow, or with status 400 (Bad Request) if the transactionFlow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/runtime-transaction-flows")
    @Timed
    public ResponseEntity<TransactionFlowMetadataDTO> createTransactionFlow(@RequestBody RuntimeTransactionFlowMetadataRequest request) throws URISyntaxException {
        log.debug("REST request to create a new TransactionFlowMetadata : {}", request);
        TransactionFlowMetadataDTO result = transactionFlowMetadataResolver.resolveMetdataFor(request.getTxnFlowName(), request.getTxnId());
        return ResponseEntity.created(new URI("/api//runtime-transaction-flows/"
            + request.getTxnFlowName()+"-" + request.getTxnId()))
            .body(result);
    }



}
