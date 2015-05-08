package org.wjh.framework.transaction.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wjh.framework.transaction.TransactionService;

public abstract class TransactionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionUtils.class);

    private static final int TARGET_FRAME_INDEX = 2;

    private static TransactionService transaction;

    public static void runInTx(Runnable runnable) {
        validateTransactionService();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Going to start transaction {}.", txName());
        }
        transaction.required(runnable);
    }

    private static void validateTransactionService() {
        if (transaction == null) {
            throw new RuntimeException("TRANSACTION_SERVICE_NOT_SET");
        }
    }

    private static String txName() {
        StackTraceElement[] frames = new Throwable().getStackTrace();
        if (frames == null || frames.length == 0) {
            return "UNKNOWN";
        }

        int index = frames.length - 1;
        if (index > TARGET_FRAME_INDEX) {
            index = TARGET_FRAME_INDEX;
        }

        StackTraceElement frame = frames[index];
        return new StringBuilder(frame.getClassName()).append('.').append(frame.getMethodName()).append('@').append(frame.getLineNumber()).toString();
    }

    /**
     * Injects the transactionService.
     *
     * @param transactionService
     *            the transactionService
     */
    public static void setTransactionService(TransactionService transactionService) {
        transaction = transactionService;
    }

    /**
     * Private constructor to prevent from constructing an instance of {@code TransactionUtils}.
     */
    private TransactionUtils() {
    }
}
