package com.ebispot.palash.ontologyTools.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ebispot.palash.ontologyTools.model.OntologyModel;

@Repository
public interface OntologyRepository extends MongoRepository<OntologyModel, String> {

  public OntologyModel findByOntologyId(String ontologyId);
}
