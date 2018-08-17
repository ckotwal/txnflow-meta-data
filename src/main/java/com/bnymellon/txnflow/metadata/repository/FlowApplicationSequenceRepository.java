package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.FlowApplicationSequence;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FlowApplicationSequence entity.
 */
@SuppressWarnings("unused")
public interface FlowApplicationSequenceRepository extends JpaRepository<FlowApplicationSequence,Long> {

}
