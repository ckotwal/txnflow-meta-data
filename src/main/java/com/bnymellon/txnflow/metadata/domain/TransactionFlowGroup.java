package com.bnymellon.txnflow.metadata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TransactionFlowGroup.
 */
@Entity
@Table(name = "transaction_flow_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionFlowGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransactionFlow> flows = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TransactionFlowGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TransactionFlow> getFlows() {
        return flows;
    }

    public TransactionFlowGroup flows(Set<TransactionFlow> transactionFlows) {
        this.flows = transactionFlows;
        return this;
    }

    public TransactionFlowGroup addFlow(TransactionFlow transactionFlow) {
        this.flows.add(transactionFlow);
        transactionFlow.setGroup(this);
        return this;
    }

    public TransactionFlowGroup removeFlow(TransactionFlow transactionFlow) {
        this.flows.remove(transactionFlow);
        transactionFlow.setGroup(null);
        return this;
    }

    public void setFlows(Set<TransactionFlow> transactionFlows) {
        this.flows = transactionFlows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionFlowGroup transactionFlowGroup = (TransactionFlowGroup) o;
        if (transactionFlowGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transactionFlowGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransactionFlowGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
