package org.wjh.springframework.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

public class ClassPathXmlApplicationContextWithSingletonParentRef extends ClassPathXmlApplicationContextWithParentRef {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathXmlApplicationContextWithSingletonParentRef.class);

    public ClassPathXmlApplicationContextWithSingletonParentRef(String parentContextKey, String parentContextSelector, String[] configLocations) {
        super(configLocations, parentContextRefOf(parentContextKey, parentContextSelector));
    }

    private static final BeanFactoryReference parentContextRefOf(String parentContextKey, String parentContextSelector) {
        LOGGER.debug("Getting parent context reference at '{}' by key '{}' ...", parentContextSelector, parentContextKey);

        return ContextSingletonBeanFactoryLocator.getInstance(parentContextSelector).useBeanFactory(parentContextKey);
    }
}
