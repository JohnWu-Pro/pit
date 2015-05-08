package org.wjh.framework.domain;

import java.io.Serializable;

public interface IdAwareEnum<ID extends Serializable> {

    ID getId();
}
