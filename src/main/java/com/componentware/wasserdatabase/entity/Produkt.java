package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Produkt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
  private String name;
    @Column
    private double preis;
  public Produkt(String n, double p) {
      name = n;
      preis = p;
  }
    public Produkt() {
    }

    @Override
  public String toString() {
      return "Produkt [name=" + name + ", preis=" + preis + ", id=" + id + "]";
  }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPreis() {
        return preis;
    }


}
