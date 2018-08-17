package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.TransactionFlowGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransactionFlowGroup entity.
 */
@SuppressWarnings("unused")
public interface TransactionFlowGroupRepository extends JpaRepository<TransactionFlowGroup,Long> {

}
