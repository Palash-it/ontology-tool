package com.ebispot.palash.ontologyTools.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "ontologies")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OntologyModel {

  @MongoId(targetType = FieldType.OBJECT_ID)
  private String id;
  @Indexed(name = "ontologyId_index_unique", unique = true)
  @NonNull
  private String ontologyId;
  private String message;
  private String status;

  private OntologyConfig config;

  public OntologyModel() {}

  public OntologyModel(String ontologyId, String message, String status,
      OntologyConfig config) {
    this.ontologyId = ontologyId;
    this.message = message;
    this.status = status;
    this.config = config;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOntologyId() {
    return ontologyId;
  }

  public void setOntologyId(String ontologyId) {
    this.ontologyId = ontologyId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public OntologyConfig getConfig() {
    return config;
  }

  public void setConfig(OntologyConfig config) {
    this.config = config;
  }

  @Override
  public String toString() {
    return "OntologyModel [ontologyId=" + ontologyId + ", message=" + message + ", status=" + status
        + ", config=" + config + "]";
  }

}
