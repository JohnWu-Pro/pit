package org.wjh.framework.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wjh.framework.test.AbstractTransactionalDaoTests;
import org.wjh.pit.domain.DistributionRule;
import org.wjh.pit.domain.Portfolio;
import org.wjh.pit.domain.SecurityIssuer;

public class GenericDaoTest extends AbstractTransactionalDaoTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoTest.class);

    @Inject
    private GenericDao genericDao;

    @Test
    public void loadAllSecurityIssuer() {
        loadAll(SecurityIssuer.class, 3);
    }

    private void loadAll(Class<?> clazz, int count) {
        List<?> entities = genericDao.findAll(clazz);
        assertNotNull(entities);
        assertThat(entities.size(), is(count));

        for (Object entity : entities) {
            LOGGER.debug("{}", entity);
        }
    }

    @Test
    public void loadAllDistributionRule() {
        loadAll(DistributionRule.class, 2);
    }

    @Test
    public void loadAllPortfolio() {
        loadAll(Portfolio.class, 5);
    }
}
