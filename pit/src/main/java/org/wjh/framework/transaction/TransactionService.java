package org.wjh.framework.transaction;

public interface TransactionService {

    void required(Runnable runnable);
}
