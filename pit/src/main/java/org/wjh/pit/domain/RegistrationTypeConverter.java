package org.wjh.pit.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.wjh.framework.domain.IdAwareEnumConverter;

@Converter(autoApply = true)
public class RegistrationTypeConverter extends IdAwareEnumConverter<RegistrationType, String> implements AttributeConverter<RegistrationType, String> {

}
