package com.bnymellon.txnflow.metadata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnymellon.txnflow.metadata.domain.FlowApplicationSequence;

import com.bnymellon.txnflow.metadata.repository.FlowApplicationSequenceRepository;
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
 * REST controller for managing FlowApplicationSequence.
 */
@RestController
@RequestMapping("/api")
public class FlowApplicationSequenceResource {

    private final Logger log = LoggerFactory.getLogger(FlowApplicationSequenceResource.class);

    private static final String ENTITY_NAME = "flowApplicationSequence";
        
    private final FlowApplicationSequenceRepository flowApplicationSequenceRepository;

    public FlowApplicationSequenceResource(FlowApplicationSequenceRepository flowApplicationSequenceRepository) {
        this.flowApplicationSequenceRepository = flowApplicationSequenceRepository;
    }

    /**
     * POST  /flow-application-sequences : Create a new flowApplicationSequence.
     *
     * @param flowApplicationSequence the flowApplicationSequence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new flowApplicationSequence, or with status 400 (Bad Request) if the flowApplicationSequence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/flow-application-sequences")
    @Timed
    public ResponseEntity<FlowApplicationSequence> createFlowApplicationSequence(@RequestBody FlowApplicationSequence flowApplicationSequence) throws URISyntaxException {
        log.debug("REST request to save FlowApplicationSequence : {}", flowApplicationSequence);
        if (flowApplicationSequence.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new flowApplicationSequence cannot already have an ID")).body(null);
        }
        FlowApplicationSequence result = flowApplicationSequenceRepository.save(flowApplicationSequence);
        return ResponseEntity.created(new URI("/api/flow-application-sequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /flow-application-sequences : Updates an existing flowApplicationSequence.
     *
     * @param flowApplicationSequence the flowApplicationSequence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated flowApplicationSequence,
     * or with status 400 (Bad Request) if the flowApplicationSequence is not valid,
     * or with status 500 (Internal Server Error) if the flowApplicationSequence couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/flow-application-sequences")
    @Timed
    public ResponseEntity<FlowApplicationSequence> updateFlowApplicationSequence(@RequestBody FlowApplicationSequence flowApplicationSequence) throws URISyntaxException {
        log.debug("REST request to update FlowApplicationSequence : {}", flowApplicationSequence);
        if (flowApplicationSequence.getId() == null) {
            return createFlowApplicationSequence(flowApplicationSequence);
        }
        FlowApplicationSequence result = flowApplicationSequenceRepository.save(flowApplicationSequence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, flowApplicationSequence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /flow-application-sequences : get all the flowApplicationSequences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flowApplicationSequences in body
     */
    @GetMapping("/flow-application-sequences")
    @Timed
    public List<FlowApplicationSequence> getAllFlowApplicationSequences() {
        log.debug("REST request to get all FlowApplicationSequences");
        List<FlowApplicationSequence> flowApplicationSequences = flowApplicationSequenceRepository.findAll();
        return flowApplicationSequences;
    }

    /**
     * GET  /flow-application-sequences/:id : get the "id" flowApplicationSequence.
     *
     * @param id the id of the flowApplicationSequence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the flowApplicationSequence, or with status 404 (Not Found)
     */
    @GetMapping("/flow-application-sequences/{id}")
    @Timed
    public ResponseEntity<FlowApplicationSequence> getFlowApplicationSequence(@PathVariable Long id) {
        log.debug("REST request to get FlowApplicationSequence : {}", id);
        FlowApplicationSequence flowApplicationSequence = flowApplicationSequenceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(flowApplicationSequence));
    }

    /**
     * DELETE  /flow-application-sequences/:id : delete the "id" flowApplicationSequence.
     *
     * @param id the id of the flowApplicationSequence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/flow-application-sequences/{id}")
    @Timed
    public ResponseEntity<Void> deleteFlowApplicationSequence(@PathVariable Long id) {
        log.debug("REST request to delete FlowApplicationSequence : {}", id);
        flowApplicationSequenceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
