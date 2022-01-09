package eu.exadelpractice.registry.events.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class Mapper {
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();
        for (Object sourceObject : sourceList) {
            destinationList.add(dozer.map(sourceObject, destinationClass));
        }
        return destinationList;
    }
    public <T, C> T map(C source, Class<T> target) {
        return dozer.map(source, target);
    }
}
