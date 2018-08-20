package com.bnymellon.txnflow.metadata.domain;

import com.bnymellon.txnflow.metadata.service.dto.ApplicationMetadataDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A FlowApplicationSequence.
 */
@Entity
@Table(name = "flow_application_sequence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FlowApplicationSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_sequence")
    private Integer appSequence;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "applicationOverride")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationTransactionField> fields = new HashSet<>();

    @ManyToOne
    private TransactionFlow flow;

    @ManyToOne
    private ApplicationTransaction application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppSequence() {
        return appSequence;
    }

    public FlowApplicationSequence appSequence(Integer appSequence) {
        this.appSequence = appSequence;
        return this;
    }

    public void setAppSequence(Integer appSequence) {
        this.appSequence = appSequence;
    }

    public String getName() {
        return name;
    }

    public FlowApplicationSequence name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return application.getName() + "-" + getAppSequence();
    }

    public Set<ApplicationTransactionField> getFields() {
        return fields;
    }

    public FlowApplicationSequence fields(Set<ApplicationTransactionField> applicationTransactionFields) {
        this.fields = applicationTransactionFields;
        return this;
    }

    public FlowApplicationSequence addField(ApplicationTransactionField applicationTransactionField) {
        this.fields.add(applicationTransactionField);
        applicationTransactionField.setApplicationOverride(this);
        return this;
    }

    public FlowApplicationSequence removeField(ApplicationTransactionField applicationTransactionField) {
        this.fields.remove(applicationTransactionField);
        applicationTransactionField.setApplicationOverride(null);
        return this;
    }

    public void setFields(Set<ApplicationTransactionField> applicationTransactionFields) {
        this.fields = applicationTransactionFields;
    }

    public TransactionFlow getFlow() {
        return flow;
    }

    public FlowApplicationSequence flow(TransactionFlow transactionFlow) {
        this.flow = transactionFlow;
        return this;
    }

    public void setFlow(TransactionFlow transactionFlow) {
        this.flow = transactionFlow;
    }

    public ApplicationTransaction getApplication() {
        return application;
    }

    public FlowApplicationSequence application(ApplicationTransaction applicationTransaction) {
        this.application = applicationTransaction;
        return this;
    }

    public void setApplication(ApplicationTransaction applicationTransaction) {
        this.application = applicationTransaction;
    }

    public Set<ApplicationTransactionField> getEffectiveFields() {
        if (this.getFields() != null && !this.getFields().isEmpty()) {
            return this.getFields();
        }
        return this.getApplication().getFields();
    }

    public ApplicationMetadataDTO toDTO(String input, int timeout, String correlationIdFilter) {
        ApplicationMetadataDTO dto = new ApplicationMetadataDTO(getApplication().getName(),
            getAppSequence(), getApplication().getRepositoryEventName(),
            timeout);
        buildFields(dto);
        buildFilters(input, dto, correlationIdFilter);
        buildPredecessorNodes(dto);
        buildPredecessorNodeFields(dto);
        return dto;
    }

    private void buildPredecessorNodeFields(ApplicationMetadataDTO dto) {
        Map<String, String> predecessorNodeKeyFields = getEffectiveFields().stream()
            .filter(f -> f.hasPredecessors())
            .map(f -> f.buildPredecessorNodeFields())
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        dto.setPredecessorNodesFields(predecessorNodeKeyFields);
    }

    private void buildPredecessorNodes(ApplicationMetadataDTO dto) {
        Set<String> predecessorNodeKeys = getEffectiveFields().stream()
            .filter(f -> f.hasPredecessors())
            .map(f -> f.buildPredecessorNodeKey())
            .collect(Collectors.toSet());
        dto.setPredecessorNodes(new ArrayList(predecessorNodeKeys));
    }

    private void buildFilters(String input, ApplicationMetadataDTO dto, String correlationIdFilter) {
        Map<String, String> filters = getEffectiveFields().stream()
            .filter(f -> f.isIsIdentifier())
            .map(f -> f.buildFilter(input))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        // Given that there is a common flow correlation id, add this to the filter list
        // with an expected value corresponding to the input.Do this only if the correlation id
        // is  part of the selected field list and its not already been added

        if(correlationIdFilter != null && dto.getFields().contains(correlationIdFilter)
            && !filters.containsKey(correlationIdFilter)) {
            filters.put(correlationIdFilter, input);
        }
        dto.setFilters(filters);
    }

    private void buildFields(ApplicationMetadataDTO dto) {
        List<String> fields = getEffectiveFields().stream()
            .map(f -> f.getName())
            .collect(Collectors.toList());
        dto.setFields(fields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlowApplicationSequence flowApplicationSequence = (FlowApplicationSequence) o;
        if (flowApplicationSequence.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, flowApplicationSequence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FlowApplicationSequence{" +
            "id=" + id +
            ", appSequence='" + appSequence + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
