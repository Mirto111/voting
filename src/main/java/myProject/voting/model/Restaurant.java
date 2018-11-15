package myProject.voting.model;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;



@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {


    @Column(name = "name")
    @NotBlank
    private String name;


    public Restaurant() {
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                '}';
    }
}
