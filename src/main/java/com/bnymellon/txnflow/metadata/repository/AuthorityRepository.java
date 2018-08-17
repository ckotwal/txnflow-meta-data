package com.bnymellon.txnflow.metadata.repository;

import com.bnymellon.txnflow.metadata.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
