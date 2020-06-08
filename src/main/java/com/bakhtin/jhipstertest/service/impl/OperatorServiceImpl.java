package com.bakhtin.jhipstertest.service.impl;

import com.bakhtin.jhipstertest.service.OperatorService;
import com.bakhtin.jhipstertest.domain.Operator;
import com.bakhtin.jhipstertest.repository.OperatorRepository;
import com.bakhtin.jhipstertest.repository.search.OperatorSearchRepository;
import com.bakhtin.jhipstertest.service.dto.OperatorDTO;
import com.bakhtin.jhipstertest.service.mapper.OperatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Operator}.
 */
@Service
public class OperatorServiceImpl implements OperatorService {

    private final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);

    private final OperatorRepository operatorRepository;

    private final OperatorMapper operatorMapper;

    private final OperatorSearchRepository operatorSearchRepository;

    public OperatorServiceImpl(OperatorRepository operatorRepository, OperatorMapper operatorMapper, OperatorSearchRepository operatorSearchRepository) {
        this.operatorRepository = operatorRepository;
        this.operatorMapper = operatorMapper;
        this.operatorSearchRepository = operatorSearchRepository;
    }

    /**
     * Save a operator.
     *
     * @param operatorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OperatorDTO save(OperatorDTO operatorDTO) {
        log.debug("Request to save Operator : {}", operatorDTO);
        Operator operator = operatorMapper.toEntity(operatorDTO);
        operator = operatorRepository.save(operator);
        OperatorDTO result = operatorMapper.toDto(operator);
        operatorSearchRepository.save(operator);
        return result;
    }

    /**
     * Get all the operators.
     *
     * @return the list of entities.
     */
    @Override
    public List<OperatorDTO> findAll() {
        log.debug("Request to get all Operators");
        return operatorRepository.findAll().stream()
            .map(operatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one operator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<OperatorDTO> findOne(String id) {
        log.debug("Request to get Operator : {}", id);
        return operatorRepository.findById(id)
            .map(operatorMapper::toDto);
    }

    /**
     * Delete the operator by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Operator : {}", id);
        operatorRepository.deleteById(id);
        operatorSearchRepository.deleteById(id);
    }

    /**
     * Search for the operator corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<OperatorDTO> search(String query) {
        log.debug("Request to search Operators for query {}", query);
        return StreamSupport
            .stream(operatorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(operatorMapper::toDto)
        .collect(Collectors.toList());
    }
}
