package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.TransactionFlow;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the TransactionFlow entity.
 */
@SuppressWarnings("unused")
public interface TransactionFlowRepository extends JpaRepository<TransactionFlow,Long> {

    @Query("SELECT tf FROM TransactionFlow tf" +
        " join fetch tf.applications fas" +
       // " join fetch fas.fields fo" +
        " join fetch fas.application app" +
        " join fetch app.fields f" +
        " WHERE LOWER(tf.name) = LOWER(:flowName)")
    public Set<TransactionFlow> findApplicationFields(@Param("flowName") String flowName);

    @Query("SELECT tf FROM TransactionFlow tf" +
        " join fetch tf.applications fas" +
        " join fetch fas.fields f" +
        " WHERE LOWER(tf.name) = LOWER(:flowName)")
    public Set<TransactionFlow> findFlowOverrideFields(@Param("flowName") String flowName);

}
