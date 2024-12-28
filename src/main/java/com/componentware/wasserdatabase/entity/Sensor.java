package com.componentware.wasserdatabase.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("SENSOR")
public class Sensor extends Produkt {
  private String Nr;
  String form;
  public Sensor(String name, double preis, String nr,long wasserStand, String form) {
      super(name, preis);
      this.Nr = nr;

      this.form = form;
  }
    public Sensor() {
        super();
    }
  @Override
  public String toString() {
      return super.toString() + ", mit der Seriennummer :" + Nr +  ", form: " + form;
  }

    public String getNr() {
        return Nr;
    }

    public String getForm() {
        return form;
    }
}
