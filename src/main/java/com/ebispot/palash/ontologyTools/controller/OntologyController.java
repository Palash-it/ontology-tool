package com.ebispot.palash.ontologyTools.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ebispot.palash.ontologyTools.exception.OLSToolsRunTimeException;
import com.ebispot.palash.ontologyTools.exception.ResourceNotFoundException;
import com.ebispot.palash.ontologyTools.model.OntologyModel;
import com.ebispot.palash.ontologyTools.service.OntologyService;

@RestController
@RequestMapping("/ols/api")
public class OntologyController {

  @Autowired
  private OntologyService ontologyService;

  @PostMapping("/ontologies")
  public ResponseEntity<OntologyModel> saveOntology(@RequestBody OntologyModel ontology)
      throws OLSToolsRunTimeException {
    OntologyModel ontologyDocument = ontologyService.save(ontology);
    return new ResponseEntity<>(ontologyDocument, HttpStatus.CREATED);
  }

  @GetMapping("/ontologies")
  public ResponseEntity<List<OntologyModel>> findAll() {
    List<OntologyModel> ontologies = ontologyService.findAll();
    return new ResponseEntity<>(ontologies, HttpStatus.OK);
  }

  @GetMapping("/ontologies/{ontologyId}")
  public ResponseEntity<OntologyModel> findOntologyByOntologyId(
      @PathVariable(value = "ontologyId") Optional<String> ontologyId)
      throws ResourceNotFoundException {
    if (ontologyId.isPresent()) {
      OntologyModel ontologyDocument = ontologyService.findOntologyByOntologyId(ontologyId.get());
      if (ontologyDocument != null)
        return new ResponseEntity<OntologyModel>(ontologyDocument, HttpStatus.OK);
      throw new ResourceNotFoundException(
          "Ontology was not found by provided ontologyId :" + ontologyId.get());
    }
    throw new ResourceNotFoundException("Invalid Ontology Id was found");
  }


}