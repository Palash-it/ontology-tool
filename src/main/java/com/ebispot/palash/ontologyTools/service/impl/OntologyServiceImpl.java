package com.ebispot.palash.ontologyTools.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ebispot.palash.ontologyTools.embedapi.ols.OntologyLookupService;
import com.ebispot.palash.ontologyTools.exception.OLSToolsRunTimeException;
import com.ebispot.palash.ontologyTools.model.OntologyModel;
import com.ebispot.palash.ontologyTools.repository.OntologyRepository;
import com.ebispot.palash.ontologyTools.service.OntologyService;

@Service
public class OntologyServiceImpl implements OntologyService {

  private Logger logger = LoggerFactory.getLogger(OntologyServiceImpl.class);

  @Autowired
  private OntologyRepository ontologyRepository;

  @Autowired
  private OntologyLookupService olsLookupService;

  @Value("${ontologytools.remote.ols.api.enable}")
  private boolean isOLSEnabled;

  @Override
  public OntologyModel findOntologyByOntologyId(String ontologyId){
    OntologyModel ontologyDocument = ontologyRepository.findByOntologyId(ontologyId);
    if (ontologyDocument != null) {
      logger.info("Ontology found into local database");
      return ontologyDocument;
    } else {
      if (isOLSEnabled) {
        ontologyDocument = olsLookupService.findOntologyFromOLS(ontologyId);
        if (ontologyDocument != null)
          return ontologyDocument;
      }
    }
    return null;
  }

  @Override
  public List<OntologyModel> findAll() {
    return ontologyRepository.findAll();
  }

  @Override
  public OntologyModel save(OntologyModel ontologyDocument) throws OLSToolsRunTimeException {
    OntologyModel ontology = ontologyRepository.findByOntologyId(ontologyDocument.getOntologyId());
    if (ontology != null)
      throw new OLSToolsRunTimeException(
          "Ontology Id [" + ontologyDocument.getOntologyId() + "] already exist ");
    return ontologyRepository.save(ontologyDocument);
  }


}
