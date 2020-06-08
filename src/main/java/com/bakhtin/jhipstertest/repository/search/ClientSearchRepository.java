package com.bakhtin.jhipstertest.repository.search;

import com.bakhtin.jhipstertest.domain.Client;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Client} entity.
 */
public interface ClientSearchRepository extends ElasticsearchRepository<Client, String> {
}
