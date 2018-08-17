package com.bnymellon.txnflow.metadata.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ApplicationTransactionField.
 */
@Entity
@Table(name = "application_transaction_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationTransactionField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_identifier")
    private Boolean isIdentifier;

    @Column(name = "filter_value")
    private String filterValue;

    @Column(name = "is_output")
    private Boolean isOutput;

    @ManyToOne
    private ApplicationTransaction application;

    @ManyToOne
    private FlowApplicationSequence applicationOverride;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApplicationTransactionField name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsIdentifier() {
        return isIdentifier;
    }

    public ApplicationTransactionField isIdentifier(Boolean isIdentifier) {
        this.isIdentifier = isIdentifier;
        return this;
    }

    public void setIsIdentifier(Boolean isIdentifier) {
        this.isIdentifier = isIdentifier;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public ApplicationTransactionField filterValue(String filterValue) {
        this.filterValue = filterValue;
        return this;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public Boolean isIsOutput() {
        return isOutput;
    }

    public ApplicationTransactionField isOutput(Boolean isOutput) {
        this.isOutput = isOutput;
        return this;
    }

    public void setIsOutput(Boolean isOutput) {
        this.isOutput = isOutput;
    }

    public ApplicationTransaction getApplication() {
        return application;
    }

    public ApplicationTransactionField application(ApplicationTransaction applicationTransaction) {
        this.application = applicationTransaction;
        return this;
    }

    public void setApplication(ApplicationTransaction applicationTransaction) {
        this.application = applicationTransaction;
    }

    public FlowApplicationSequence getApplicationOverride() {
        return applicationOverride;
    }

    public ApplicationTransactionField applicationOverride(FlowApplicationSequence flowApplicationSequence) {
        this.applicationOverride = flowApplicationSequence;
        return this;
    }

    public void setApplicationOverride(FlowApplicationSequence flowApplicationSequence) {
        this.applicationOverride = flowApplicationSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationTransactionField applicationTransactionField = (ApplicationTransactionField) o;
        if (applicationTransactionField.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, applicationTransactionField.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationTransactionField{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", isIdentifier='" + isIdentifier + "'" +
            ", filterValue='" + filterValue + "'" +
            ", isOutput='" + isOutput + "'" +
            '}';
    }
}
