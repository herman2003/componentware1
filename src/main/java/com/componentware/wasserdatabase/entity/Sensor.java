package com.componentware.wasserdatabase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("SENSOR")
public class Sensor extends Produkt {


    private String Nr;

  public Sensor(String name, double preis) {
      super(name, preis);


  }
    public Sensor() {
        super();
    }
  @Override
  public String toString() {
      return super.toString() ;
  }

}
