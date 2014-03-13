package org.lateralthoughts.elasticsearch.mapping.comparator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ogirardot on 13/03/2014.
 */
public class ElasticSearchMappingComparatorTest {

    private final ElasticSearchMappingComparator comparator = new ElasticSearchMappingComparator();

    @Test
    public void should_handle_exact_match() {
        assertThat(comparator.areTheSame("", "")).isTrue();
        assertThat(comparator.areTheSame(
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}",
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}")
        ).isTrue();
    }

    @Test
    public void should_handle_default_values_for_fields() {
        assertThat(comparator.areTheSame(
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}",
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"omit_norms\":true,\"index_options\":\"docs\"}}}}")
        ).isTrue();

        assertThat(comparator.areTheSame(
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}",
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"omit_norms\":true}}}}")
        ).isTrue();

        assertThat(comparator.areTheSame(
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}",
                "{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"index_options\":\"docs\"}}}}")
        ).isTrue();
    }
}
