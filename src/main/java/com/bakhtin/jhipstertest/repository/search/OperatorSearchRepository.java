package com.bakhtin.jhipstertest.repository.search;

import com.bakhtin.jhipstertest.domain.Operator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Operator} entity.
 */
public interface OperatorSearchRepository extends ElasticsearchRepository<Operator, String> {
}
