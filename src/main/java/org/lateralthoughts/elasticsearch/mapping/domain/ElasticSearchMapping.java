package org.lateralthoughts.elasticsearch.mapping.domain;

import java.util.ArrayList;
import java.util.List;

public class ElasticSearchMapping {
    public static final String NOT_ANALYZED = "not_analyzed";
    private String baseUrl;
    private String index;
    private String type;
    private List<MappingElement> properties = new ArrayList<MappingElement>();

    public ElasticSearchMapping(String baseUrl, String index, String type) {
        this.baseUrl = baseUrl;
        this.index = index;
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MappingElement> getProperties() {
        return properties;
    }

    public void addField(String fieldName, String type) {
        properties.add(new MappingElement(fieldName, type));
    }

    public void addField(String fieldName, String type, String analyzer) {
        properties.add(new MappingElement(fieldName, type, analyzer));
    }

    public void addField(String fieldName,
                         String type,
                         String analyzer,
                         Boolean isToStore,
                         Boolean isOmittingNorm,
                         String indexOptions) {
        properties.add(new MappingElement(fieldName, type, isToStore, analyzer, isOmittingNorm, indexOptions));
    }

    public void addField(String fieldName, String type, boolean isToStore) {
        properties.add(new MappingElement(fieldName, type, isToStore));
    }

    public String getMappingUrl() {
        return baseUrl + "/" + index + "/" + type + "/_mapping";
    }

    public static class MappingElement {
        private final String fieldName;
        private final String type;
        private final Boolean isToStore;
        private final Boolean isOmittingNorm;

        private final String indexOptions;

        private final String analyzer;
        private MappingElement(String fieldName, String type) {
            this(fieldName, type, (Boolean) null);
        }

        private MappingElement(String fieldName, String type, Boolean isToStore) {
            this(fieldName, type, isToStore, null);
        }

        private MappingElement(String fieldName, String type, String analyzer) {
            this(fieldName, type, null, analyzer);
        }

        public MappingElement(String fieldName, String type, Boolean isToStore, String analyzer) {
            this(fieldName, type, isToStore, analyzer, null, null);
        }

        public MappingElement(String fieldName, String type, Boolean isToStore, String analyzer, Boolean isOmittingNorm, String indexOptions) {
            this.fieldName = fieldName;
            this.type = type;
            this.analyzer = analyzer;
            this.isToStore = isToStore;
            this.isOmittingNorm = isOmittingNorm;
            this.indexOptions = indexOptions;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getType() {
            return type;
        }

        public Boolean isToStore() {
            return isToStore;
        }

        public String getAnalyzer() {
            return analyzer;
        }

        public Boolean isOmittingNorm() {
            return isOmittingNorm;
        }

        public String getIndexOptions() {
            return indexOptions;
        }
    }
}
