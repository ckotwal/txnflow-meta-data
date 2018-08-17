package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransaction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApplicationTransaction entity.
 */
@SuppressWarnings("unused")
public interface ApplicationTransactionRepository extends JpaRepository<ApplicationTransaction,Long> {

}
