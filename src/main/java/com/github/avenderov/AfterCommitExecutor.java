package com.github.avenderov;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class AfterCommitExecutor {

    public void onSuccess(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {

                public void afterCompletion(int status) {
                    if (status == TransactionSynchronization.STATUS_COMMITTED) {
                        runnable.run();
                    }
                }

            });
        }
    }

}
