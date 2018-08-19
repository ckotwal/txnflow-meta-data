package com.bnymellon.txnflow.metadata.service.dto;

import com.bnymellon.txnflow.metadata.domain.enumeration.EventRepositoryType;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kotwal on 19-08-2018.
 */
public class ApplicationMetadataDTO implements Serializable {
    private String name;
    private int sequence;
    private String indexName;
    private List<String> fields = new ArrayList<>();
    private Map<String, String> filters = new HashMap<>();
    private int timeout;
    private int eventCount = 1;
    private List<String> predecessorNodes = new ArrayList<>();
    private EventRepositoryType eventRepostoryType = EventRepositoryType.DP;

    public ApplicationMetadataDTO(String name, int sequence, String indexName, List<String> fields, Map<String, String> filters,
                                  int timeout, List<String> predecessorNodes) {
        this.name = name;
        this.sequence = sequence;
        this.indexName = indexName;
        this.fields = fields;
        this.filters = filters;
        this.timeout = timeout;
        this.predecessorNodes = predecessorNodes;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public void setPredecessorNodes(List<String> predecessorNodes) {
        this.predecessorNodes = predecessorNodes;
    }

    public ApplicationMetadataDTO(String name, int sequence, String indexName, int timeout) {
        this.name = name;
        this.sequence = sequence;
        this.indexName = indexName;
        this.timeout = timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationMetadataDTO that = (ApplicationMetadataDTO) o;

        if (sequence != that.sequence) return false;
        if (timeout != that.timeout) return false;
        if (!name.equals(that.name)) return false;
        if (!indexName.equals(that.indexName)) return false;
        if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
        if (filters != null ? !filters.equals(that.filters) : that.filters != null) return false;
        return predecessorNodes != null ? predecessorNodes.equals(that.predecessorNodes) : that.predecessorNodes == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + sequence;
        result = 31 * result + indexName.hashCode();
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        result = 31 * result + timeout;
        result = 31 * result + (predecessorNodes != null ? predecessorNodes.hashCode() : 0);
        return result;
    }
}
