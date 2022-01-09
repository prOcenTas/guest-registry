package eu.exadelpractice.registry.person.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

@Service
public class EntityMapper {
	 
	private DozerBeanMapper mapper;
	public EntityMapper() {
		mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("dozer_custom_convertor.xml"));
	}
	public <T, C> List<T> mapLists(List<C> sourceList, Class<T> type) {
		List<T> targetList = new ArrayList<T>();
		for(C entity : sourceList) {
			targetList.add(mapper.map(entity, type));
		}
		return targetList;
	}
	public <T, C> T map(C source, Class<T> target) {
		return mapper.map(source, target);
	}
}
