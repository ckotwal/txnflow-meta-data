package com.bnymellon.txnflow.metadata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnymellon.txnflow.metadata.domain.TransactionFlow;

import com.bnymellon.txnflow.metadata.repository.TransactionFlowRepository;
import com.bnymellon.txnflow.metadata.web.rest.util.HeaderUtil;
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
public class TransactionFlowResource {

    private final Logger log = LoggerFactory.getLogger(TransactionFlowResource.class);

    private static final String ENTITY_NAME = "transactionFlow";
        
    private final TransactionFlowRepository transactionFlowRepository;

    public TransactionFlowResource(TransactionFlowRepository transactionFlowRepository) {
        this.transactionFlowRepository = transactionFlowRepository;
    }

    /**
     * POST  /transaction-flows : Create a new transactionFlow.
     *
     * @param transactionFlow the transactionFlow to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionFlow, or with status 400 (Bad Request) if the transactionFlow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-flows")
    @Timed
    public ResponseEntity<TransactionFlow> createTransactionFlow(@RequestBody TransactionFlow transactionFlow) throws URISyntaxException {
        log.debug("REST request to save TransactionFlow : {}", transactionFlow);
        if (transactionFlow.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transactionFlow cannot already have an ID")).body(null);
        }
        TransactionFlow result = transactionFlowRepository.save(transactionFlow);
        return ResponseEntity.created(new URI("/api/transaction-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-flows : Updates an existing transactionFlow.
     *
     * @param transactionFlow the transactionFlow to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionFlow,
     * or with status 400 (Bad Request) if the transactionFlow is not valid,
     * or with status 500 (Internal Server Error) if the transactionFlow couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-flows")
    @Timed
    public ResponseEntity<TransactionFlow> updateTransactionFlow(@RequestBody TransactionFlow transactionFlow) throws URISyntaxException {
        log.debug("REST request to update TransactionFlow : {}", transactionFlow);
        if (transactionFlow.getId() == null) {
            return createTransactionFlow(transactionFlow);
        }
        TransactionFlow result = transactionFlowRepository.save(transactionFlow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionFlow.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-flows : get all the transactionFlows.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionFlows in body
     */
    @GetMapping("/transaction-flows")
    @Timed
    public List<TransactionFlow> getAllTransactionFlows() {
        log.debug("REST request to get all TransactionFlows");
        List<TransactionFlow> transactionFlows = transactionFlowRepository.findAll();
        return transactionFlows;
    }

    /**
     * GET  /transaction-flows/:id : get the "id" transactionFlow.
     *
     * @param id the id of the transactionFlow to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionFlow, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-flows/{id}")
    @Timed
    public ResponseEntity<TransactionFlow> getTransactionFlow(@PathVariable Long id) {
        log.debug("REST request to get TransactionFlow : {}", id);
        TransactionFlow transactionFlow = transactionFlowRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionFlow));
    }

    /**
     * DELETE  /transaction-flows/:id : delete the "id" transactionFlow.
     *
     * @param id the id of the transactionFlow to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-flows/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionFlow(@PathVariable Long id) {
        log.debug("REST request to delete TransactionFlow : {}", id);
        transactionFlowRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
