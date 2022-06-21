package ru.tfs.spring.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.data.dto.person.PersonCreateRequestDto;
import ru.tfs.spring.data.dto.person.PersonFullDto;
import ru.tfs.spring.data.dto.person.PersonJournalDto;
import ru.tfs.spring.data.service.person.PersonService;

import java.util.List;

@RestController("/")
public class PersonController {

    @Autowired
    private PersonService           personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonFullDto createPerson(@Validated @RequestBody PersonCreateRequestDto request) {
        return personService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PersonFullDto updatePerson(@Validated @RequestBody PersonFullDto dto) {
        return personService.update(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonFullDto personInfo(@PathVariable Long id) {
        return personService.personInfo(id);
    }

    @GetMapping("/info/{passport}")
    @ResponseStatus(HttpStatus.OK)
    public PersonJournalDto personInfoByPassport(@PathVariable Integer passport) {
        return personService.personInfoByPassport(passport);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonJournalDto> personJournal(@RequestParam Integer size, @RequestParam Integer page,
                                                @RequestParam(required = false) String region) {
        return personService.personJournal(size, page, region);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verify(@RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam(required = false) String patronymic, @RequestParam Integer passport) {
        return personService.verify(firstName, lastName, patronymic, passport);
    }
}
