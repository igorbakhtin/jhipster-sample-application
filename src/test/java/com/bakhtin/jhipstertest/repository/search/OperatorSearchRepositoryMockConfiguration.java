package com.bakhtin.jhipstertest.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OperatorSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OperatorSearchRepositoryMockConfiguration {

    @MockBean
    private OperatorSearchRepository mockOperatorSearchRepository;

}
