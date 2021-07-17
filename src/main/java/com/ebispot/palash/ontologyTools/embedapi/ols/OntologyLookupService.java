package com.ebispot.palash.ontologyTools.embedapi.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ebispot.palash.ontologyTools.model.OntologyModel;

@Component
public class OntologyLookupService {

  private Logger logger = LoggerFactory.getLogger(OntologyLookupService.class);

  @Value("${ontologytools.remote.ols.api.endpoint}")
  private String olsRestEndPoint;

  @Autowired
  private RestTemplate restTemplate;

  public OntologyModel findOntologyFromOLS(String ontologyId) {
    OntologyModel ontology = null;
    try {
      StringBuilder olsEndpointBuild =
          new StringBuilder(olsRestEndPoint).append("/").append(ontologyId);
      logger.debug("---OLS Endpoint Request Send----" + olsEndpointBuild.toString());
      ResponseEntity<OntologyModel> olsResponse =
          restTemplate.getForEntity(olsEndpointBuild.toString(), OntologyModel.class);
      if (olsResponse.getStatusCode() == HttpStatus.OK) {
        return olsResponse.getBody();
      }
    } catch (Exception e) {
      logger.error("OLS Endpoint Exception ", e);
    }
    return ontology;
  }

}
