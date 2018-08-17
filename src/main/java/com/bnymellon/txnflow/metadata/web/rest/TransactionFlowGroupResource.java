package com.bnymellon.txnflow.metadata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnymellon.txnflow.metadata.domain.TransactionFlowGroup;

import com.bnymellon.txnflow.metadata.repository.TransactionFlowGroupRepository;
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
 * REST controller for managing TransactionFlowGroup.
 */
@RestController
@RequestMapping("/api")
public class TransactionFlowGroupResource {

    private final Logger log = LoggerFactory.getLogger(TransactionFlowGroupResource.class);

    private static final String ENTITY_NAME = "transactionFlowGroup";
        
    private final TransactionFlowGroupRepository transactionFlowGroupRepository;

    public TransactionFlowGroupResource(TransactionFlowGroupRepository transactionFlowGroupRepository) {
        this.transactionFlowGroupRepository = transactionFlowGroupRepository;
    }

    /**
     * POST  /transaction-flow-groups : Create a new transactionFlowGroup.
     *
     * @param transactionFlowGroup the transactionFlowGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionFlowGroup, or with status 400 (Bad Request) if the transactionFlowGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-flow-groups")
    @Timed
    public ResponseEntity<TransactionFlowGroup> createTransactionFlowGroup(@RequestBody TransactionFlowGroup transactionFlowGroup) throws URISyntaxException {
        log.debug("REST request to save TransactionFlowGroup : {}", transactionFlowGroup);
        if (transactionFlowGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transactionFlowGroup cannot already have an ID")).body(null);
        }
        TransactionFlowGroup result = transactionFlowGroupRepository.save(transactionFlowGroup);
        return ResponseEntity.created(new URI("/api/transaction-flow-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-flow-groups : Updates an existing transactionFlowGroup.
     *
     * @param transactionFlowGroup the transactionFlowGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionFlowGroup,
     * or with status 400 (Bad Request) if the transactionFlowGroup is not valid,
     * or with status 500 (Internal Server Error) if the transactionFlowGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-flow-groups")
    @Timed
    public ResponseEntity<TransactionFlowGroup> updateTransactionFlowGroup(@RequestBody TransactionFlowGroup transactionFlowGroup) throws URISyntaxException {
        log.debug("REST request to update TransactionFlowGroup : {}", transactionFlowGroup);
        if (transactionFlowGroup.getId() == null) {
            return createTransactionFlowGroup(transactionFlowGroup);
        }
        TransactionFlowGroup result = transactionFlowGroupRepository.save(transactionFlowGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionFlowGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-flow-groups : get all the transactionFlowGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionFlowGroups in body
     */
    @GetMapping("/transaction-flow-groups")
    @Timed
    public List<TransactionFlowGroup> getAllTransactionFlowGroups() {
        log.debug("REST request to get all TransactionFlowGroups");
        List<TransactionFlowGroup> transactionFlowGroups = transactionFlowGroupRepository.findAll();
        return transactionFlowGroups;
    }

    /**
     * GET  /transaction-flow-groups/:id : get the "id" transactionFlowGroup.
     *
     * @param id the id of the transactionFlowGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionFlowGroup, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-flow-groups/{id}")
    @Timed
    public ResponseEntity<TransactionFlowGroup> getTransactionFlowGroup(@PathVariable Long id) {
        log.debug("REST request to get TransactionFlowGroup : {}", id);
        TransactionFlowGroup transactionFlowGroup = transactionFlowGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionFlowGroup));
    }

    /**
     * DELETE  /transaction-flow-groups/:id : delete the "id" transactionFlowGroup.
     *
     * @param id the id of the transactionFlowGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-flow-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionFlowGroup(@PathVariable Long id) {
        log.debug("REST request to delete TransactionFlowGroup : {}", id);
        transactionFlowGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
