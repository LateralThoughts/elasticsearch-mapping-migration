package org.lateralthoughts.elasticsearch.mapping.tool;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.lateralthoughts.elasticsearch.mapping.comparator.ElasticSearchMappingComparator;
import org.lateralthoughts.elasticsearch.mapping.domain.ElasticSearchMapping;
import org.lateralthoughts.elasticsearch.mapping.serializer.ElasticSearchMappingSerializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.String.valueOf;

/**
 *
 */
public class ElasticSearchMappingHandler {
    private final Logger LOG = Logger.getLogger(ElasticSearchMappingHandler.class.getName());
    private static final ElasticSearchMappingSerializer serializer = new ElasticSearchMappingSerializer();
    private final ElasticSearchMappingComparator comparator = new ElasticSearchMappingComparator();

    public void destroyAndRecreateIfNeeded(ElasticSearchMapping mapping) {
        try {
            LOG.info("Querying ElasticSearch server...");
            String serverMapping = fetchServerMapping(mapping);
            LOG.info("Got server mapping" + serverMapping);
            String expectedMapping = serializer.dump(mapping);
            if (!comparator.areTheSame(serverMapping, expectedMapping)) {
                LOG.info("Server Mapping is not the same");
                if (deleteServerMapping(mapping)){
                    LOG.info("Successfully deleted old mapping");
                } else {
                    LOG.warning("Could not delete old mapping");
                }

                if (createNewServerMapping(mapping, expectedMapping)) {
                    LOG.info("Successfully created new mapping");
                } else {
                    LOG.warning("Could not create new mapping");
                }

                LOG.info("Finished mapping handling.");
            } else {
                LOG.info("Server Mapping is the same, no more actions needed.");
            }
        } catch (IOException e) {
            LOG.severe("Error while executing HTTP query : " + e.getMessage());
        }
    }

    private boolean createNewServerMapping(ElasticSearchMapping mapping, String newSchema) throws IOException {
        String body = Request
                .Put(mapping.getMappingUrl())
                .bodyString(newSchema, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        return body.equals("{\"ok\":true,\"acknowledged\":true}");
    }

    private boolean deleteServerMapping(ElasticSearchMapping mapping) throws IOException {
        String body = Request
                .Delete(mapping.getMappingUrl())
                .execute()
                .returnContent()
                .asString();
        return body.equals("{\"ok\":true}");
    }

    private String fetchServerMapping(ElasticSearchMapping mapping) throws IOException {
        return Request
                .Get(mapping.getMappingUrl())
                .execute()
                .returnContent()
                .asString();
    }
}
