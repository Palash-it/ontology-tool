package com.ebispot.palash.ontologyTools.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ebispot.palash.ontologyTools.embedapi.ols.OntologyLookupService;
import com.ebispot.palash.ontologyTools.exception.OLSToolsRunTimeException;
import com.ebispot.palash.ontologyTools.model.OntologyConfig;
import com.ebispot.palash.ontologyTools.model.OntologyModel;
import com.ebispot.palash.ontologyTools.repository.OntologyRepository;
import com.ebispot.palash.ontologyTools.service.impl.OntologyServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OntologyServiceImplTest {

  @MockBean
  private OntologyRepository ontologyRepository;

  @MockBean
  private OntologyLookupService olsLookupService;

  @Autowired
  private OntologyServiceImpl ontologyService;

  @Value("${ontologytools.remote.ols.api.enable}")
  private boolean isOLSEnabled;

  OntologyModel ontologyDoc = null;

  @DisplayName("Test Data Setup")
  @BeforeEach
  public void init() {
    String[] definitionProperties = new String[] {"http://www.ebi.ac.uk/efo/definition",
        "http://purl.obolibrary.org/obo/IAO_0000115"};
    String[] synonymProperties = new String[] {"http://www.ebi.ac.uk/efo/alternative_term",
        "http://www.geneontology.org/formats/oboInOwl#hasExactSynonym"};
    OntologyConfig ontologyConfig = new OntologyConfig("1", "", "Experimental Factor Ontology",
        "efo", "Some Text", definitionProperties, synonymProperties);
    ontologyDoc = new OntologyModel("efo", "Test", "Loaded", ontologyConfig);
  }

  @DisplayName("Test Data Existence in local")
  @Test
  public void findOntologyByOntologyIdTest() {
    Mockito.when(ontologyRepository.findByOntologyId(ontologyDoc.getOntologyId()))
        .thenReturn(ontologyDoc);
    OntologyModel ontology = ontologyService.findOntologyByOntologyId(ontologyDoc.getOntologyId());
    assertNotNull(ontology);
  }

  @DisplayName("Test Data is not exists in local. Find from OLS Endpoint")
  @Test
  public void findOntologyByOntologyIdFromOLSTest() {
    if (isOLSEnabled) {
      Mockito.when(ontologyRepository.findByOntologyId(ontologyDoc.getOntologyId()))
          .thenReturn(null);
      Mockito.when(olsLookupService.findOntologyFromOLS(ontologyDoc.getOntologyId()))
          .thenReturn(ontologyDoc);
      OntologyModel ontologyReturned =
          ontologyService.findOntologyByOntologyId(ontologyDoc.getOntologyId());
      assertNotNull(ontologyReturned);

      Mockito.when(olsLookupService.findOntologyFromOLS(ontologyDoc.getOntologyId()))
          .thenReturn(null);
      ontologyReturned = ontologyService.findOntologyByOntologyId(ontologyDoc.getOntologyId());
      assertNull(ontologyReturned);
    }
  }


  @DisplayName("Test Data Save. a fresh insert with a unique ontologyId and a duplicate insert")
  @Test
  public void saveTest() throws OLSToolsRunTimeException {
    Mockito.when(ontologyRepository.findByOntologyId(ontologyDoc.getOntologyId())).thenReturn(null);
    Mockito.when(ontologyRepository.save(ontologyDoc)).thenReturn(ontologyDoc);
    OntologyModel ontology = ontologyService.save(ontologyDoc);
    assertNotNull(ontology);

    Mockito.when(ontologyRepository.findByOntologyId(ontologyDoc.getOntologyId()))
        .thenReturn(ontologyDoc);
    assertThrows(OLSToolsRunTimeException.class, () -> {
      ontologyService.save(ontologyDoc);
    });
  }



}
