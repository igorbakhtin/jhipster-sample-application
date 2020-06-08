package com.bakhtin.jhipstertest.repository.search;

import com.bakhtin.jhipstertest.domain.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Message} entity.
 */
public interface MessageSearchRepository extends ElasticsearchRepository<Message, String> {
}
