package ir.smarttrustco.cryptography.person;

import ir.smarttrustco.cryptography.basic.BaseController;
import ir.smarttrustco.cryptography.person.dto.PersonRegistryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("person")
public class PersonController extends BaseController<PersonEntity,Long,PersonService> {

    private final PersonMapper personMapper;

    public PersonController(PersonService service, PersonMapper personMapper) {
        super(service);
        this.personMapper = personMapper;
    }

    @PostMapping("/registry")
    public ResponseEntity<?> registryPerson(@RequestBody PersonRegistryDto person) {
        service.save(personMapper.toEntity(person));
        return null;
    }


}
