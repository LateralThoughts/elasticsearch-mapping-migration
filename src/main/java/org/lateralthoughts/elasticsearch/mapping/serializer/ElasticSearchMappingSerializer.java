package org.lateralthoughts.elasticsearch.mapping.serializer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.lateralthoughts.elasticsearch.mapping.domain.ElasticSearchMapping;


import java.util.List;

public class ElasticSearchMappingSerializer {

    public String dump(ElasticSearchMapping mapping) {
        ObjectMapper factory = new ObjectMapper();
        ObjectNode rootNode = factory.createObjectNode();

        ObjectNode propertiesNode = factory.createObjectNode();
        propertiesNode.put("properties", createPropertiesMapping(mapping.getProperties(), factory));

        rootNode.put(mapping.getType(), propertiesNode);
        try {
            return factory.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonNode createPropertiesMapping(List<ElasticSearchMapping.MappingElement> properties,
                                             ObjectMapper factory) {
        ObjectNode rootPropertiesNode = factory.createObjectNode();
        for (ElasticSearchMapping.MappingElement element : properties) {
            createMappingByProperty(element, rootPropertiesNode, factory);
        }
        return rootPropertiesNode;
    }

    private void createMappingByProperty(ElasticSearchMapping.MappingElement element,
                                         ObjectNode rootPropertiesNode,
                                         ObjectMapper factory) {
        ObjectNode node = factory.createObjectNode();
        node.put("type", element.getType());
        if (element.isToStore() != null) {
            node.put("store", element.isToStore());
        }
        if (element.getAnalyzer() != null) {
            node.put("index", element.getAnalyzer());
        }
        if (element.isOmittingNorm() != null) {
            node.put("omit_norms", element.isOmittingNorm());
        }
        if (element.getIndexOptions() != null) {
            node.put("index_options", element.getIndexOptions());
        }
        rootPropertiesNode.put(element.getFieldName(), node);
    }
}
