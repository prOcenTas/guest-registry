package eu.exadelpractice.registry.cards.validator;

import eu.exadelpractice.registry.cards.exception.CardFieldBadValueException;
import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.exception.LocationFieldBadValueException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.model.LocationRef;
import eu.exadelpractice.registry.cards.model.PersonRef;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.locations.client.LocationClient;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.person.client.PersonClient;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardValidator {

    private final PersonClient personClient;
    private final LocationClient locationClient;

    public void id(Card card) throws CardNotFoundException, CardFieldBadValueException, LocationFieldBadValueException {
        if (card.getId() == null) {
            throw new CardNotFoundException("Card id value is not given");
        }

        validatePersonId(card, card.getPersonRef());
        validateLocationId(card, card.getLocationRef());
    }

    private void validateLocationId(Card card, LocationRef locationRef) throws LocationFieldBadValueException {
        if(locationRef != null && card.getLocationRef().getId() != null){
            Location loadedLocation=null;
            try{
                loadedLocation = locationClient.getLocationById(card.getLocationRef().getId());
                locationRef.setLocationTitle(loadedLocation.getLocationTitle());
            }
            catch (LocationNotFoundException e){
                throw new LocationFieldBadValueException("Wrong location selected, Location ID: "
                        + card.getLocationRef().getId());
            } catch (InternalServerErrorException | BadRequestException e) {
                e.printStackTrace();
            }

        }
    }

    public void validatePersonId(Card card, PersonRef personRef) throws CardFieldBadValueException {
        if (personRef != null && card.getPersonRef().getId() != null) {
            Person loadedPerson = null;
            try {
                loadedPerson = personClient.getPersonById(card.getPersonRef().getId());
                personRef.setName(loadedPerson.getName());
                personRef.setSurname(loadedPerson.getSurname());
            } catch (PersonNotFoundException e) {
                throw new CardFieldBadValueException("Wrong person selected, Person ID: " + card.getPersonRef().getId());
            } catch (InternalServerErrorException | BadRequestException e) {
                e.printStackTrace();
            }
        }
    }
}
