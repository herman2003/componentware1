package com.componentware.wasserdatabase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTAINER")
public class Container extends Produkt {
    @Column
  private double volume;
    public Container() {
        super();
    }
  public Container(String name, double preis, double volume) {
      super(name, preis);
      this.volume = volume;
  }

  public double getVolume() {
      return volume;
  }
  public void setVolume(double volume) {
      this.volume = volume;
  }

  @Override
  public String toString() {
      return super.toString() + ", Volume=" + volume;
  }

}
