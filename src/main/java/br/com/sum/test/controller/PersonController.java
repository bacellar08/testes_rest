package br.com.sum.test.controller;

import br.com.sum.test.model.dto.v1.PersonDTO;
import br.com.sum.test.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people.")
public class PersonController {

    @Autowired
    PersonService service;

    @Operation(
            summary = "Finds a person by ID",
            description = "This endpoint allows you to search a registered person by its Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success finding person by id",
            content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to find person with the specified id.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

    public PersonDTO findById(@PathVariable("id")Long id){
        return service.findById(id);
    }

    @Operation(
            summary = "Finds all people stored",
            description = "This endpoint allows you to search everyone registered in the database"
            )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success finding all people",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    @Operation(
            summary = "Register a new person",
            description = "Register a new person by passing a JSON or XML representation of the person"
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success adding a new person",
                    content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Interval server error", content = @Content)
    })
    @PostMapping(value = "/create",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonDTO createdPerson = service.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @Operation(
            summary = "Update a existing person on the database",
            description = "This endpoint allows you to updated already registered data of any person on the database"
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Success updating person",
                    content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Failed to find person with the specified id.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO person){
        PersonDTO updatedPerson = service.updatePerson(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @Operation(
            summary = "Deletes a person by ID",
            description = "This endpoint allows you to delete any registered person on the database"
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "204", description = "Success deleting person", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to find person with the specified id.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(value="id") Long id){
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
