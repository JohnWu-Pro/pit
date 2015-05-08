package org.wjh.springframework.config.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextWithParentRef extends ClassPathXmlApplicationContext {
    private BeanFactoryReference parentContextRef;

    public ClassPathXmlApplicationContextWithParentRef(String[] configLocations, BeanFactoryReference parentContextRef) {
        super(configLocations, applicationContextOf(parentContextRef));
        this.parentContextRef = parentContextRef;
    }

    private static ApplicationContext applicationContextOf(BeanFactoryReference parentContextRef) {
        if (parentContextRef == null) {
            return null;
        }

        BeanFactory beanFactory = parentContextRef.getFactory();
        return beanFactory instanceof ApplicationContext ? (ApplicationContext) beanFactory : null;
    }

    @Override
    protected void onClose() {
        if (parentContextRef != null) {
            parentContextRef.release();
        }
    }
}
