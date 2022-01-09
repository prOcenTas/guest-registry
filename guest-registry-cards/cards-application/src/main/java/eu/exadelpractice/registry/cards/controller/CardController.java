package eu.exadelpractice.registry.cards.controller;

import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.service.CardService;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {
	private final CardService service;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public String saveCard(@RequestBody Card card) throws ObjectNotFoundException {
		service.saveCard(card);
		return card.getId();
	}

	@GetMapping
	public List<Card> getCards(HttpServletRequest httpServletRequest) {
		Map<String,String[]> parameters=httpServletRequest.getParameterMap();

		return service.getAll(parameters);
	}

	@GetMapping("{id}")
	public Card getCard(@PathVariable String id) throws CardNotFoundException {
		return service.getCard(id);
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateCard(@RequestBody Card card) throws CardNotFoundException {
		service.updateCard(card);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteCard(@PathVariable String id) throws CardNotFoundException {
		service.deleteCard(id);
	}
}
