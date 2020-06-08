package com.bakhtin.jhipstertest.repository;

import com.bakhtin.jhipstertest.domain.Operator;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Operator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatorRepository extends MongoRepository<Operator, String> {
}
