package com.bakhtin.jhipstertest.web.rest;

import com.bakhtin.jhipstertest.service.OperatorService;
import com.bakhtin.jhipstertest.web.rest.errors.BadRequestAlertException;
import com.bakhtin.jhipstertest.service.dto.OperatorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.bakhtin.jhipstertest.domain.Operator}.
 */
@RestController
@RequestMapping("/api")
public class OperatorResource {

    private final Logger log = LoggerFactory.getLogger(OperatorResource.class);

    private static final String ENTITY_NAME = "operator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperatorService operatorService;

    public OperatorResource(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /**
     * {@code POST  /operators} : Create a new operator.
     *
     * @param operatorDTO the operatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operatorDTO, or with status {@code 400 (Bad Request)} if the operator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operators")
    public ResponseEntity<OperatorDTO> createOperator(@RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to save Operator : {}", operatorDTO);
        if (operatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new operator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperatorDTO result = operatorService.save(operatorDTO);
        return ResponseEntity.created(new URI("/api/operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /operators} : Updates an existing operator.
     *
     * @param operatorDTO the operatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorDTO,
     * or with status {@code 400 (Bad Request)} if the operatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operators")
    public ResponseEntity<OperatorDTO> updateOperator(@RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to update Operator : {}", operatorDTO);
        if (operatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperatorDTO result = operatorService.save(operatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operatorDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /operators} : get all the operators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operators in body.
     */
    @GetMapping("/operators")
    public List<OperatorDTO> getAllOperators() {
        log.debug("REST request to get all Operators");
        return operatorService.findAll();
    }

    /**
     * {@code GET  /operators/:id} : get the "id" operator.
     *
     * @param id the id of the operatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operators/{id}")
    public ResponseEntity<OperatorDTO> getOperator(@PathVariable String id) {
        log.debug("REST request to get Operator : {}", id);
        Optional<OperatorDTO> operatorDTO = operatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operatorDTO);
    }

    /**
     * {@code DELETE  /operators/:id} : delete the "id" operator.
     *
     * @param id the id of the operatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operators/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable String id) {
        log.debug("REST request to delete Operator : {}", id);
        operatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/operators?query=:query} : search for the operator corresponding
     * to the query.
     *
     * @param query the query of the operator search.
     * @return the result of the search.
     */
    @GetMapping("/_search/operators")
    public List<OperatorDTO> searchOperators(@RequestParam String query) {
        log.debug("REST request to search Operators for query {}", query);
        return operatorService.search(query);
    }
}
