package eu.exadelpractice.registry.person.mapper;

import java.time.LocalDate;

import org.dozer.CustomConverter;

public class LocalDateConverter implements CustomConverter {
    @Override
    public Object convert(Object dest, Object source, Class<?> arg2, Class<?> arg3) {
        if (source == null) 
            return null;
        
        if (source instanceof LocalDate) {
        	LocalDate date = (LocalDate) source;
            LocalDate ld = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
            return ld;
        }
		return arg3;
    }
}