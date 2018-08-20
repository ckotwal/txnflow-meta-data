package com.bnymellon.txnflow.metadata.service.dto;

import java.io.Serializable;

/**
 * Created by kotwal on 20-08-2018.
 */
public class RuntimeTransactionFlowMetadataRequest implements Serializable {
    private String txnFlowName;
    private String txnId;

    public RuntimeTransactionFlowMetadataRequest() {
    }

    public RuntimeTransactionFlowMetadataRequest(String txnFlowName, String txnId) {
        this.txnFlowName = txnFlowName;
        this.txnId = txnId;
    }

    public String getTxnFlowName() {
        return txnFlowName;
    }

    public void setTxnFlowName(String txnFlowName) {
        this.txnFlowName = txnFlowName;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    @Override
    public String toString() {
        return "RuntimeTransactionFlowMetadataRequest{" +
            "txnFlowName='" + txnFlowName + '\'' +
            ", txnId='" + txnId + '\'' +
            '}';
    }
}
