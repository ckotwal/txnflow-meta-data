package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.TransactionFlow;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransactionFlow entity.
 */
@SuppressWarnings("unused")
public interface TransactionFlowRepository extends JpaRepository<TransactionFlow,Long> {

}
