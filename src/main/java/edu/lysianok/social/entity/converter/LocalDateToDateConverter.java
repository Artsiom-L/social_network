package edu.lysianok.social.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateToDateConverter implements AttributeConverter<LocalDate, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate localDate) {
        if (localDate != null) {
            return Timestamp.valueOf(localDate.atStartOfDay());
        } else {
            return null;
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime().toLocalDate();
        } else {
            return null;
        }
    }
}
