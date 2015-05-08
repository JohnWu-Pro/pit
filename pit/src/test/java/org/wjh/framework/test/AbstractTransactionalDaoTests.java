package org.wjh.framework.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextHierarchy({// @formatter:off
    @ContextConfiguration(locations = {
            "classpath*:META-INF/spring/resource/trace-logging-config.xml",
            "classpath*:META-INF/spring/metadata-config.xml"
            }),
    @ContextConfiguration(locations = {
            "classpath*:META-INF/spring/resource/trace-logging-config.xml",
            "classpath*:META-INF/spring/data-config.xml",
            "classpath*:META-INF/spring/test-data-config.xml"
            })
    })// @formatter:on
public abstract class AbstractTransactionalDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

}
