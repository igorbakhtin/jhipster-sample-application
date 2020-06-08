package com.bakhtin.jhipstertest.web.rest;

import com.bakhtin.jhipstertest.JhipsterSampleApplicationApp;
import com.bakhtin.jhipstertest.domain.Operator;
import com.bakhtin.jhipstertest.repository.OperatorRepository;
import com.bakhtin.jhipstertest.repository.search.OperatorSearchRepository;
import com.bakhtin.jhipstertest.service.OperatorService;
import com.bakhtin.jhipstertest.service.dto.OperatorDTO;
import com.bakhtin.jhipstertest.service.mapper.OperatorMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OperatorResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OperatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private OperatorService operatorService;

    /**
     * This repository is mocked in the com.bakhtin.jhipstertest.repository.search test package.
     *
     * @see com.bakhtin.jhipstertest.repository.search.OperatorSearchRepositoryMockConfiguration
     */
    @Autowired
    private OperatorSearchRepository mockOperatorSearchRepository;

    @Autowired
    private MockMvc restOperatorMockMvc;

    private Operator operator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createEntity() {
        Operator operator = new Operator()
            .name(DEFAULT_NAME);
        return operator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createUpdatedEntity() {
        Operator operator = new Operator()
            .name(UPDATED_NAME);
        return operator;
    }

    @BeforeEach
    public void initTest() {
        operatorRepository.deleteAll();
        operator = createEntity();
    }

    @Test
    public void createOperator() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();
        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate + 1);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Operator in Elasticsearch
        verify(mockOperatorSearchRepository, times(1)).save(testOperator);
    }

    @Test
    public void createOperatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();

        // Create the Operator with an existing ID
        operator.setId("existing_id");
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Operator in Elasticsearch
        verify(mockOperatorSearchRepository, times(0)).save(operator);
    }


    @Test
    public void getAllOperators() throws Exception {
        // Initialize the database
        operatorRepository.save(operator);

        // Get all the operatorList
        restOperatorMockMvc.perform(get("/api/operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getOperator() throws Exception {
        // Initialize the database
        operatorRepository.save(operator);

        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operator.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    public void getNonExistingOperator() throws Exception {
        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOperator() throws Exception {
        // Initialize the database
        operatorRepository.save(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator
        Operator updatedOperator = operatorRepository.findById(operator.getId()).get();
        updatedOperator
            .name(UPDATED_NAME);
        OperatorDTO operatorDTO = operatorMapper.toDto(updatedOperator);

        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Operator in Elasticsearch
        verify(mockOperatorSearchRepository, times(1)).save(testOperator);
    }

    @Test
    public void updateNonExistingOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Operator in Elasticsearch
        verify(mockOperatorSearchRepository, times(0)).save(operator);
    }

    @Test
    public void deleteOperator() throws Exception {
        // Initialize the database
        operatorRepository.save(operator);

        int databaseSizeBeforeDelete = operatorRepository.findAll().size();

        // Delete the operator
        restOperatorMockMvc.perform(delete("/api/operators/{id}", operator.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Operator in Elasticsearch
        verify(mockOperatorSearchRepository, times(1)).deleteById(operator.getId());
    }

    @Test
    public void searchOperator() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        operatorRepository.save(operator);
        when(mockOperatorSearchRepository.search(queryStringQuery("id:" + operator.getId())))
            .thenReturn(Collections.singletonList(operator));

        // Search the operator
        restOperatorMockMvc.perform(get("/api/_search/operators?query=id:" + operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
