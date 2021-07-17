package com.ebispot.palash.ontologyTools.model;

import java.util.Arrays;

public class OntologyConfig {

  private String id;
  private String versionIri;
  private String title;
  private String namespace;
  private String description;
  private String[] definitionProperties;
  private String[] synonymProperties;

  public OntologyConfig() {}


  public OntologyConfig(String id, String versionIri, String title, String namespace,
      String description, String[] definitionProperties, String[] synonymProperties) {
    this.id = id;
    this.versionIri = versionIri;
    this.title = title;
    this.namespace = namespace;
    this.description = description;
    this.definitionProperties = definitionProperties;
    this.synonymProperties = synonymProperties;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVersionIri() {
    return versionIri;
  }

  public void setVersionIri(String versionIri) {
    this.versionIri = versionIri;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String[] getDefinitionProperties() {
    return definitionProperties;
  }

  public void setDefinitionProperties(String[] definitionProperties) {
    this.definitionProperties = definitionProperties;
  }

  public String[] getSynonymProperties() {
    return synonymProperties;
  }

  public void setSynonymProperties(String[] synonymProperties) {
    this.synonymProperties = synonymProperties;
  }

  @Override
  public String toString() {
    return "OntologyConfig [id=" + id + ", versionIri=" + versionIri + ", title=" + title
        + ", namespace=" + namespace + ", description=" + description + ", definitionProperties="
        + Arrays.toString(definitionProperties) + ", synonymProperties="
        + Arrays.toString(synonymProperties) + "]";
  }


}
