package com.ebispot.palash.ontologyTools.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ebispot.palash.ontologyTools.exception.ErrorDetails;
import com.ebispot.palash.ontologyTools.model.OntologyConfig;
import com.ebispot.palash.ontologyTools.model.OntologyModel;
import com.ebispot.palash.ontologyTools.service.OntologyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OntologyController.class)
public class OntologyControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private OntologyService ontologyService;

  List<OntologyModel> ontologies = new LinkedList<>();
  OntologyModel ontologyDoc = null;

  @DisplayName("Mocking Data Setup")
  @BeforeEach
  public void init() {
    String[] definitionProperties = new String[] {"http://www.ebi.ac.uk/efo/definition",
        "http://purl.obolibrary.org/obo/IAO_0000115"};
    String[] synonymProperties = new String[] {"http://www.ebi.ac.uk/efo/alternative_term",
        "http://www.geneontology.org/formats/oboInOwl#hasExactSynonym"};
    OntologyConfig ontologyConfig = new OntologyConfig("1", "", "Experimental Factor Ontology",
        "efo", "Some Text", definitionProperties, synonymProperties);
    ontologyDoc = new OntologyModel("efo", "Test", "Loaded", ontologyConfig);
    ontologies.add(ontologyDoc);
  }

  @DisplayName("FindAll GET Mvc Test")
  @Test
  public void findAllTest() throws Exception {
    Mockito.when(ontologyService.findAll()).thenReturn(ontologies);
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ols/api/ontologies"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String actualResponseBody = mvcResult.getResponse().getContentAsString();
    List<OntologyModel> ontologiesReturned =
        objectMapper.readValue(actualResponseBody, new TypeReference<List<OntologyModel>>() {});
    assertEquals("efo", ontologiesReturned.get(0).getOntologyId());
  }


  @DisplayName("Save Post Mvc Test")
  @Test
  public void saveOntologyTest() throws Exception {
    OntologyModel ontologyPost = new OntologyModel("efo", "test", "loaded", null);
    Mockito.when(ontologyService.save(ontologyPost)).thenReturn(ontologyPost);
    mockMvc
        .perform(MockMvcRequestBuilders.post("/ols/api/ontologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(ontologyPost))
            .accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @DisplayName("findOntologyByOntologyId GET Mvc Test When resource not found")
  @Test
  public void findOntologyByOntologyIdTest() throws Exception {
    Mockito.when(ontologyService.findOntologyByOntologyId(ontologyDoc.getOntologyId()))
        .thenReturn(null);
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/ols/api/ontologies/" + ontologyDoc.getOntologyId()))
        .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    String actualResponseBody = mvcResult.getResponse().getContentAsString();
    ErrorDetails errorDetailsReturned =
        objectMapper.readValue(actualResponseBody, ErrorDetails.class);
    assertEquals("Ontology was not found by provided ontologyId :" + ontologyDoc.getOntologyId(),
        errorDetailsReturned.getMessage());
  }
}
