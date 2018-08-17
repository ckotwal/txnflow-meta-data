package com.bnymellon.txnflow.metadata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnymellon.txnflow.metadata.domain.ApplicationTransactionField;

import com.bnymellon.txnflow.metadata.repository.ApplicationTransactionFieldRepository;
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
 * REST controller for managing ApplicationTransactionField.
 */
@RestController
@RequestMapping("/api")
public class ApplicationTransactionFieldResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationTransactionFieldResource.class);

    private static final String ENTITY_NAME = "applicationTransactionField";
        
    private final ApplicationTransactionFieldRepository applicationTransactionFieldRepository;

    public ApplicationTransactionFieldResource(ApplicationTransactionFieldRepository applicationTransactionFieldRepository) {
        this.applicationTransactionFieldRepository = applicationTransactionFieldRepository;
    }

    /**
     * POST  /application-transaction-fields : Create a new applicationTransactionField.
     *
     * @param applicationTransactionField the applicationTransactionField to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationTransactionField, or with status 400 (Bad Request) if the applicationTransactionField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-transaction-fields")
    @Timed
    public ResponseEntity<ApplicationTransactionField> createApplicationTransactionField(@RequestBody ApplicationTransactionField applicationTransactionField) throws URISyntaxException {
        log.debug("REST request to save ApplicationTransactionField : {}", applicationTransactionField);
        if (applicationTransactionField.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new applicationTransactionField cannot already have an ID")).body(null);
        }
        ApplicationTransactionField result = applicationTransactionFieldRepository.save(applicationTransactionField);
        return ResponseEntity.created(new URI("/api/application-transaction-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-transaction-fields : Updates an existing applicationTransactionField.
     *
     * @param applicationTransactionField the applicationTransactionField to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationTransactionField,
     * or with status 400 (Bad Request) if the applicationTransactionField is not valid,
     * or with status 500 (Internal Server Error) if the applicationTransactionField couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-transaction-fields")
    @Timed
    public ResponseEntity<ApplicationTransactionField> updateApplicationTransactionField(@RequestBody ApplicationTransactionField applicationTransactionField) throws URISyntaxException {
        log.debug("REST request to update ApplicationTransactionField : {}", applicationTransactionField);
        if (applicationTransactionField.getId() == null) {
            return createApplicationTransactionField(applicationTransactionField);
        }
        ApplicationTransactionField result = applicationTransactionFieldRepository.save(applicationTransactionField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationTransactionField.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-transaction-fields : get all the applicationTransactionFields.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicationTransactionFields in body
     */
    @GetMapping("/application-transaction-fields")
    @Timed
    public List<ApplicationTransactionField> getAllApplicationTransactionFields() {
        log.debug("REST request to get all ApplicationTransactionFields");
        List<ApplicationTransactionField> applicationTransactionFields = applicationTransactionFieldRepository.findAll();
        return applicationTransactionFields;
    }

    /**
     * GET  /application-transaction-fields/:id : get the "id" applicationTransactionField.
     *
     * @param id the id of the applicationTransactionField to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationTransactionField, or with status 404 (Not Found)
     */
    @GetMapping("/application-transaction-fields/{id}")
    @Timed
    public ResponseEntity<ApplicationTransactionField> getApplicationTransactionField(@PathVariable Long id) {
        log.debug("REST request to get ApplicationTransactionField : {}", id);
        ApplicationTransactionField applicationTransactionField = applicationTransactionFieldRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicationTransactionField));
    }

    /**
     * DELETE  /application-transaction-fields/:id : delete the "id" applicationTransactionField.
     *
     * @param id the id of the applicationTransactionField to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-transaction-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationTransactionField(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationTransactionField : {}", id);
        applicationTransactionFieldRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
