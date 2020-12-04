package org.engineer365.common.json;

import java.util.Date;

import org.engineer365.common.error.BadRequestError;

/**
 * Date值的JSON（Jackson）反序列化。
 * Date值用long表示。
 */
public class DateDeserialize extends com.fasterxml.jackson.databind.JsonDeserializer<Date> {


  @Override
  public Date deserialize
    (com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
    throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {

    String valueText = p.getValueAsString();

    try {
      return new Date(Long.valueOf(valueText));
    } catch (NumberFormatException ex) {
      throw new BadRequestError(BadRequestError.Code.WRONG_FORMAT, ex, "%s is NOT a long value", valueText);
    }
  }
}
