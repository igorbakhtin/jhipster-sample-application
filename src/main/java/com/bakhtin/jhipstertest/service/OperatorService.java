package com.bakhtin.jhipstertest.service;

import com.bakhtin.jhipstertest.service.dto.OperatorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bakhtin.jhipstertest.domain.Operator}.
 */
public interface OperatorService {

    /**
     * Save a operator.
     *
     * @param operatorDTO the entity to save.
     * @return the persisted entity.
     */
    OperatorDTO save(OperatorDTO operatorDTO);

    /**
     * Get all the operators.
     *
     * @return the list of entities.
     */
    List<OperatorDTO> findAll();


    /**
     * Get the "id" operator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperatorDTO> findOne(String id);

    /**
     * Delete the "id" operator.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the operator corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<OperatorDTO> search(String query);
}
