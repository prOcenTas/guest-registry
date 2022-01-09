package eu.exadelpractice.registry.cards.mapper;

import org.dozer.CustomConverter;

import java.time.LocalDate;

public class LocalDateConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object source, Class<?> aClass, Class<?> bClass) {
        if (source == null) {
            return null;
        }
        if (source instanceof LocalDate) {
            LocalDate localDate = (LocalDate) source;
            LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
            return date;
        }
        return bClass;
    }
}
