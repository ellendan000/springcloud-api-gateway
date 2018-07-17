package net.shadow.springcloud.apigateway.domain.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class DBString2ListConverter implements AttributeConverter<List<String>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return null;
        }
        attribute = attribute.stream()
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toList());
        return StringUtils.join(attribute, SEPARATOR);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        return newArrayList(dbData.split(SEPARATOR))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
