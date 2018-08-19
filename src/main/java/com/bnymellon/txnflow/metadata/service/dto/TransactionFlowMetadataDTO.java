package com.bnymellon.txnflow.metadata.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotwal on 19-08-2018.
 */
public class TransactionFlowMetadataDTO implements Serializable {
    private String name;
    private List<ApplicationMetadataDTO> applications = new ArrayList<>();

    public TransactionFlowMetadataDTO(String name, List<ApplicationMetadataDTO> applications) {
        this.name = name;
        this.applications = applications;
    }

    public TransactionFlowMetadataDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ApplicationMetadataDTO> getApplications() {
        return applications;
    }

    public void addApplication(ApplicationMetadataDTO applicationMetadataDTO) {
        applications.add(applicationMetadataDTO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionFlowMetadataDTO that = (TransactionFlowMetadataDTO) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return applications != null ? applications.equals(that.applications) : that.applications == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (applications != null ? applications.hashCode() : 0);
        return result;
    }
}
