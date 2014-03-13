package org.lateralthoughts.elasticsearch.mapping.comparator;


import java.util.regex.Pattern;

public class ElasticSearchMappingComparator {

    /**
     *
     * @param serverMapping
     * @param expectedMapping
     * @return comparison
     */
    public boolean areTheSame(String serverMapping, String expectedMapping) {
        String cleanedExpected = clean(expectedMapping);
        String cleanedServer = clean(serverMapping);
        return cleanedExpected.equals(cleanedServer);
    }

    private String clean(String content) {
        return content
                .replaceAll(",?\"omit_norms\":true,?", "")
                .replaceAll(",?\"index_options\":\"docs\",?", "");
    }
}
