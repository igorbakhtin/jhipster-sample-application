package com.bakhtin.jhipstertest.repository.search;

import com.bakhtin.jhipstertest.domain.Chat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Chat} entity.
 */
public interface ChatSearchRepository extends ElasticsearchRepository<Chat, String> {
}
