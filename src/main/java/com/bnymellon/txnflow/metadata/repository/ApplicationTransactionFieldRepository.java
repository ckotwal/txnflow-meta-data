package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.ApplicationTransactionField;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApplicationTransactionField entity.
 */
@SuppressWarnings("unused")
public interface ApplicationTransactionFieldRepository extends JpaRepository<ApplicationTransactionField,Long> {

}
