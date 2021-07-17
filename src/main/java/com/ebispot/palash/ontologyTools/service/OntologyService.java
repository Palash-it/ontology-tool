package com.ebispot.palash.ontologyTools.service;

import java.util.List;
import com.ebispot.palash.ontologyTools.exception.OLSToolsRunTimeException;
import com.ebispot.palash.ontologyTools.model.OntologyModel;

public interface OntologyService {

  /**
   * First find ontology from localdb if not found in localbd then find it from remote endpoint. If
   * still not found show error message
   * 
   * @param ontologyId
   * @return OntologyModel
   */
  public OntologyModel findOntologyByOntologyId(String ontologyId);

  /**
   * Fetch ols info from localdb and remote ols and merge both
   * 
   * @return
   */
  public List<OntologyModel> findAll();

  /**
   * Check ontologyId if exists in OLS then throw exception
   * 
   * @return
   */
  public OntologyModel save(OntologyModel ontologyDocument) throws OLSToolsRunTimeException;

}
