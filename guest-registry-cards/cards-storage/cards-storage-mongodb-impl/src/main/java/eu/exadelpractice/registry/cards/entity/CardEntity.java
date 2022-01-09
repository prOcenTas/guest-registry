package eu.exadelpractice.registry.cards.entity;

import eu.exadelpractice.registry.cards.model.LocationRef;
import eu.exadelpractice.registry.cards.model.PersonRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Card")
public class CardEntity {
    @Id
    private String id;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String cardTitle;
    private LocationRef locationRef;
    private PersonRef personRef;
}
