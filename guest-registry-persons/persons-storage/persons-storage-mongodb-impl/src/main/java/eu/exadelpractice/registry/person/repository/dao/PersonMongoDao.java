package eu.exadelpractice.registry.person.repository.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.entity.PersonEntity;
import eu.exadelpractice.registry.person.mapper.EntityMapper;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.repository.PersonRepositoryMongo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonMongoDao implements PersonRepository {
	private final PersonRepositoryMongo personRepositoryMongo;
	private final EntityMapper mapper;
	private final MongoOperations mongoOperations;

	@Override
	public List<Person> findAll(Map<String, String[]> map) throws BadRequestException {
		List<PersonEntity> entityList = complexSearch(map);
		List<Person> personList = mapper.mapLists(entityList, Person.class);
		return personList;
	}

	private List<PersonEntity> complexSearch(Map<String, String[]> map) throws BadRequestException {
		Query query = new Query();
		for (String key : map.keySet()) {
			String[] nlog = key.split("\\.");
			if (nlog.length != 2) {
				throw new BadRequestException("The format of request path is bad");
			} else {
				switch (nlog[0]) {
				case "name":
				case "surname":
					query = stringAttQuery(query, map.get(key), key, nlog[1], nlog[0]);
					break;
				case "birthDate":
					query = localdateAttQuery(query, map.get(key), key, nlog[1], "dateOfBirth");
					break;
				default:
					throw new BadRequestException("The format of request path is bad");
				}
			}
		}
		return mongoOperations.find(query, PersonEntity.class);
	}

	private Query localdateAttQuery(Query query, String[] values, String key, String operation, String name)
			throws BadRequestException {
		List<LocalDate> dates = stringsToLocalDates(values);
		if (operation.equals("in")) {
			query.addCriteria(Criteria.where(name).in(dates));
		} else if (operation.equals("equals")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).is(date));
			}
		} else if (operation.equals("notEquals")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).ne(date));
			}
		} else if (operation.equals("lt")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).lt(date));
			}
		} else if (operation.equals("lte")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).lte(date));
			}
		} else if (operation.equals("gt")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).gt(date));
			}
		} else if (operation.equals("gte")) {
			for (LocalDate date : dates) {
				query.addCriteria(Criteria.where(name).gte(date));

			}
		} else {
			throw new BadRequestException("The format of request path is bad");
		}
		return query;
	}

	private List<LocalDate> stringsToLocalDates(String[] values) throws BadRequestException {
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (String val : values) {
			String[] arr = val.split("\\-");
			if (arr.length != 3) {
				throw new BadRequestException("The format of request path is bad");
			}
			try {
				dates.add(LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]), Integer.parseInt(arr[0])));
			} catch (Exception ex) {
				throw new BadRequestException("The format of date in the path is bad");
			}
		}
		return dates;
	}

	private Query stringAttQuery(Query query, String[] values, String key, String operation, String name)
			throws BadRequestException {
		if (operation.equals("in")) {
			List<String> val = Arrays.asList(values);
			query.addCriteria(Criteria.where(name).in(val));
		} else if (operation.equals("contains")) {
			for (String val : values) {
				query.addCriteria(Criteria.where(name).regex(val));
			}
		} else if (operation.equals("equals")) {
			for (String val : values) {
				query.addCriteria(Criteria.where(name).is(val));
			}
		} else if (operation.equals("notEquals")) {
			for (String val : values) {
				query.addCriteria(Criteria.where(name).ne(val));
			}
		} else if (operation.equals("startsWith")) {
			for (String val : values) {
				query.addCriteria(Criteria.where(name).regex("^" + val));
			}
		} else {
			throw new BadRequestException("The format of request path is bad");
		}

		return query;
	}

	@Override
	public Optional<Person> findById(String id) {
		Optional<PersonEntity> optionalEntity = personRepositoryMongo.findById(id);
		Optional<Person> optionalPerson = Optional.ofNullable(null);
		if (optionalEntity.isPresent()) {
			optionalPerson = Optional.of(mapper.map(optionalEntity.get(), Person.class));
		}
		return optionalPerson;
	}

	@Override
	public void save(Person person) {
		PersonEntity entity = mapper.map(person, PersonEntity.class);
		if (entity.getId() == null) {
			entity.setId(new ObjectId().toString());
		}
		entity = personRepositoryMongo.save(entity);
		person.setId(entity.getId());
	}

	@Override
	public Person findByNameSurnameDate(String name, String surname, LocalDate dateOfBirth) {
		PersonEntity entity = personRepositoryMongo.findByNameSurnameDate(name, surname, dateOfBirth);
		if (entity == null) {
			return null;
		}
		Person person = mapper.map(entity, Person.class);
		return person;
	}

	@Override
	public void deleteById(String id) {
		personRepositoryMongo.deleteById(id);
	}

}
