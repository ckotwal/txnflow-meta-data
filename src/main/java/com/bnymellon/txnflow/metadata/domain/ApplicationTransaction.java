package com.bnymellon.txnflow.metadata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bnymellon.txnflow.metadata.domain.enumeration.EventRepositoryType;

/**
 * A ApplicationTransaction.
 */
@Entity
@Table(name = "application_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "event_count")
    private Long eventCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_repository_type")
    private EventRepositoryType eventRepositoryType;

    @Column(name = "repository_event_name")
    private String repositoryEventName;

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FlowApplicationSequence> flows = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationTransactionField> fields = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApplicationTransaction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public ApplicationTransaction eventCount(Long eventCount) {
        this.eventCount = eventCount;
        return this;
    }

    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }

    public EventRepositoryType getEventRepositoryType() {
        return eventRepositoryType;
    }

    public ApplicationTransaction eventRepositoryType(EventRepositoryType eventRepositoryType) {
        this.eventRepositoryType = eventRepositoryType;
        return this;
    }

    public void setEventRepositoryType(EventRepositoryType eventRepositoryType) {
        this.eventRepositoryType = eventRepositoryType;
    }

    public String getRepositoryEventName() {
        return repositoryEventName;
    }

    public ApplicationTransaction repositoryEventName(String repositoryEventName) {
        this.repositoryEventName = repositoryEventName;
        return this;
    }

    public void setRepositoryEventName(String repositoryEventName) {
        this.repositoryEventName = repositoryEventName;
    }

    public Set<FlowApplicationSequence> getFlows() {
        return flows;
    }

    public ApplicationTransaction flows(Set<FlowApplicationSequence> flowApplicationSequences) {
        this.flows = flowApplicationSequences;
        return this;
    }

    public ApplicationTransaction addFlow(FlowApplicationSequence flowApplicationSequence) {
        this.flows.add(flowApplicationSequence);
        flowApplicationSequence.setApplication(this);
        return this;
    }

    public ApplicationTransaction removeFlow(FlowApplicationSequence flowApplicationSequence) {
        this.flows.remove(flowApplicationSequence);
        flowApplicationSequence.setApplication(null);
        return this;
    }

    public void setFlows(Set<FlowApplicationSequence> flowApplicationSequences) {
        this.flows = flowApplicationSequences;
    }

    public Set<ApplicationTransactionField> getFields() {
        return fields;
    }

    public ApplicationTransaction fields(Set<ApplicationTransactionField> applicationTransactionFields) {
        this.fields = applicationTransactionFields;
        return this;
    }

    public ApplicationTransaction addField(ApplicationTransactionField applicationTransactionField) {
        this.fields.add(applicationTransactionField);
        applicationTransactionField.setApplication(this);
        return this;
    }

    public ApplicationTransaction removeField(ApplicationTransactionField applicationTransactionField) {
        this.fields.remove(applicationTransactionField);
        applicationTransactionField.setApplication(null);
        return this;
    }

    public void setFields(Set<ApplicationTransactionField> applicationTransactionFields) {
        this.fields = applicationTransactionFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationTransaction applicationTransaction = (ApplicationTransaction) o;
        if (applicationTransaction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, applicationTransaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationTransaction{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", eventCount='" + eventCount + "'" +
            ", eventRepositoryType='" + eventRepositoryType + "'" +
            ", repositoryEventName='" + repositoryEventName + "'" +
            '}';
    }
}
