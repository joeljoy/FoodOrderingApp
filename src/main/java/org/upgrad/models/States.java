package org.upgrad.models;

import javax.persistence.*;

@Entity
@Table(name = "states")
public class States {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "state_name")
    private String stateName;

    public States() {
    }



    public States(String stateName) {
        this.stateName = stateName;
    }

    public int getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
