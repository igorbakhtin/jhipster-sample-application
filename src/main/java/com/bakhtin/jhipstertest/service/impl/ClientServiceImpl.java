package com.bakhtin.jhipstertest.service.impl;

import com.bakhtin.jhipstertest.service.ClientService;
import com.bakhtin.jhipstertest.domain.Client;
import com.bakhtin.jhipstertest.repository.ClientRepository;
import com.bakhtin.jhipstertest.repository.search.ClientSearchRepository;
import com.bakhtin.jhipstertest.service.dto.ClientDTO;
import com.bakhtin.jhipstertest.service.mapper.ClientMapper;
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
 * Service Implementation for managing {@link Client}.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final ClientSearchRepository clientSearchRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, ClientSearchRepository clientSearchRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.clientSearchRepository = clientSearchRepository;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        ClientDTO result = clientMapper.toDto(client);
        clientSearchRepository.save(client);
        return result;
    }

    /**
     * Get all the clients.
     *
     * @return the list of entities.
     */
    @Override
    public List<ClientDTO> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll().stream()
            .map(clientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ClientDTO> findOne(String id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id)
            .map(clientMapper::toDto);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
        clientSearchRepository.deleteById(id);
    }

    /**
     * Search for the client corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<ClientDTO> search(String query) {
        log.debug("Request to search Clients for query {}", query);
        return StreamSupport
            .stream(clientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(clientMapper::toDto)
        .collect(Collectors.toList());
    }
}
