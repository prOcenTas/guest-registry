package eu.exadelpractice.registry.locations.repository.dao;

import eu.exadelpractice.registry.locations.LocationRepository;
import eu.exadelpractice.registry.locations.entity.LocationEntity;
import eu.exadelpractice.registry.locations.mapper.Mapper;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.repository.LocationRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationMongoDao implements LocationRepository {
    private final Mapper mapper;
    private final LocationRepositoryMongo repository;

    @Override
    public List<Location> findAll() {
        List<LocationEntity> locationEntities=repository.findAll();
        List<Location> locationList=mapper.mapList(locationEntities,Location.class);
        return locationList;
    }

    @Override
    public Optional<Location> findById(String id) {
        Optional<Location> optionalLocation=Optional.ofNullable(null);
        Optional<LocationEntity> optionalLocationEntity=repository.findById(id);
        if(optionalLocationEntity.isPresent()){
            optionalLocation=Optional.of(mapper.map(optionalLocationEntity.get(),Location.class));
        }
        return optionalLocation;
    }

    @Override
    public void save(Location location) {
        LocationEntity locationEntity=mapper.map(location,LocationEntity.class);
        locationEntity=repository.save(locationEntity);
        location=mapper.map(locationEntity,Location.class);
    }

    @Override
    public void deleteById(String id) {
            repository.deleteById(id);
    }

    @Override
    public List<Location> findByType(String locationType) {
        List<LocationEntity> entityList=repository.findByType(locationType);
        if(entityList.isEmpty()){
            return null;
        }
        List<Location> locationList=mapper.mapList(entityList,Location.class);
        return locationList;
    }

    @Override
    public List<Location> findByCity(String city) {
        List<LocationEntity> entityList=repository.findByCity(city);
        if(entityList.isEmpty()){
            return null;
        }
        List<Location> locationList=mapper.mapList(entityList,Location.class);
        return locationList;
    }

    @Override
    public List<Location> findByCountry(String country) {
        List<LocationEntity> entityList=repository.findByCountry(country);
        if(entityList.isEmpty()){
            return null;
        }
        List<Location> locationList=mapper.mapList(entityList,Location.class);
        return locationList;
    }
}
