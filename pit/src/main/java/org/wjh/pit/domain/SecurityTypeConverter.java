package org.wjh.pit.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.wjh.framework.domain.IdAwareEnumConverter;

@Converter(autoApply = true)
public class SecurityTypeConverter extends IdAwareEnumConverter<SecurityType, String> implements AttributeConverter<SecurityType, String> {

}
