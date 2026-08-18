package ibee.webapp.todo_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractHateoasCrudController;
import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.service.person.PersonDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;

@RequestMapping("/api/v1/persons")
@RestController
public class PersonController extends AbstractHateoasCrudController<PersonData, Long> {

    private final PersonDtoService personDtoService;

    public PersonController(PersonDtoService personDtoService, TranslationService translationService) {
        super(personDtoService, translationService, "/persons", "entity.person");
        this.personDtoService = personDtoService;
    }

    // Hier kannst du nun ganz bequem deine spezifischen Endpunkte 
    // (wie das Accordion-Overview oder die Feldsuchen) ergänzen!
}
