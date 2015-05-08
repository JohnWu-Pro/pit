package org.wjh.springframework.transaction;

import static org.wjh.lang.util.ExceptionUtils.unwrapCause;

import java.util.Random;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class RetryableTransactionInterceptor extends TransactionInterceptor {
    private static final long serialVersionUID = 2561112716664915477L;

    private static final Logger LOGGER = LoggerFactory.getLogger("com.gide.springframework.transaction.RetryableTransactionInterceptorLogger");

    private static final ThreadLocal<TransactionInfo> TRANSACTION_INFO_HOLDER = new ThreadLocal<TransactionInfo>();

    private static final Random RANDOM = new Random();

    //
    // Injected properties
    //
    private int maxRetry = 3;
    private int retryMaxDelayInMills = 500;
    private Class<Throwable>[] retryableExceptions;

    /**
     * Constructs an instance of {@code RetryableTransactionInterceptor}.
     */
    public RetryableTransactionInterceptor() {
        LOGGER.debug("Created {}.", this);
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        for (int triedAttempt = 1; true; triedAttempt++) {
            try {
                if (triedAttempt > 1) {
                    LOGGER.debug("Going to re-run the transaction after a retryable exception. Tried attempt: {}.", triedAttempt - 1);
                }
                Object result = super.invoke(invocation);
                if (triedAttempt > 1) {
                    LOGGER.info("Completed re-running the transaction successfully. Tried attempt: {}.", triedAttempt);
                }
                return result;
            } catch (Throwable throwable) {
                // Logs the throwable
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Caught throwable. Tried attempt: {}.", triedAttempt);
                    LOGGER.debug(throwable.getMessage(), throwable);
                }
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("By {}.", this);
                    LOGGER.trace("Checkpoint stack trace:", new Throwable());
                }

                // Checks the transaction status
                TransactionStatus transactionStatus = getTransactionStatus();
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("TransactionStatus: {}", stringOf(transactionStatus));
                }
                if (transactionStatus == null) {
                    LOGGER.trace("TransactionStatus is NOT available, rethrowing.");
                    throw throwable;
                }
                if (!transactionStatus.isNewTransaction()) {
                    LOGGER.trace("Method invocation did NOT start new transaction, rethrowing.");
                    throw throwable;
                }
                if (!transactionStatus.isCompleted()) {
                    LOGGER.trace("Transaction is NOT completed, rethrowing.");
                    throw throwable;
                }

                // Checks the cause
                Throwable retryableCause = unwrapCause(throwable, retryableExceptions);
                if (retryableCause == null) {
                    LOGGER.trace("Throwable is NOT retryable, rethrowing.");
                    throw throwable;
                }

                // Checks the tried attempt
                if (triedAttempt >= maxRetry) {
                    LOGGER.warn("Caught {} while calling {} and reached maximum retries ({}), rethrowing.", retryableCause.getClass().getName(),
                            getJoinpointIdentification(), maxRetry);
                    throw throwable;
                }

                // Delays random milliseconds of [0, retryMaxDelayInMills)
                try {
                    int delay = RANDOM.nextInt(retryMaxDelayInMills);
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("Caught re-tryable exception and will retry in {} millisecond(s). Tried attempt: {}.", delay, triedAttempt);
                        LOGGER.info(throwable.getMessage(), throwable);
                    }
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LOGGER.debug("Caught {}.", e.toString());
                }
            } finally {
                TRANSACTION_INFO_HOLDER.remove();
            }
        }
    }

    @Override
    protected void cleanupTransactionInfo(TransactionInfo txInfo) {
        if (txInfo != null) {
            TRANSACTION_INFO_HOLDER.set(txInfo);
        }
        super.cleanupTransactionInfo(txInfo);
    }

    private static TransactionStatus getTransactionStatus() {
        TransactionInfo transactionInfo = TRANSACTION_INFO_HOLDER.get();
        return transactionInfo == null ? null : transactionInfo.getTransactionStatus();
    }

    private static String getJoinpointIdentification() {
        TransactionInfo transactionInfo = TRANSACTION_INFO_HOLDER.get();
        return transactionInfo == null ? null : transactionInfo.getJoinpointIdentification();
    }

    private static String stringOf(Object object) {
        return object == null ? "null" : ToStringBuilder.reflectionToString(object);
    }

    /**
     * Injects the maxRetry.
     *
     * @param maxRetry
     *            the maxRetry
     */
    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    /**
     * Injects the retryMaxDelayInMills.
     *
     * @param retryMaxDelayInMills
     *            the retryMaxDelayInMills
     */
    public void setRetryMaxDelayInMills(int retryMaxDelayInMills) {
        this.retryMaxDelayInMills = retryMaxDelayInMills;
    }

    /**
     * Injects the retryableExceptions.
     *
     * @param retryableExceptions
     *            the retryableExceptions
     */
    public void setRetryableExceptions(Class<Throwable>[] retryableExceptions) {
        this.retryableExceptions = retryableExceptions;
    }
}
