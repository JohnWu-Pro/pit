package org.wjh.framework.transaction;

public class SimpleTransactionService implements TransactionService {

    @Override
    public void required(Runnable runnable) {
        runnable.run();
    }

}
