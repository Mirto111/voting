package myProject.voting.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Objects;


@MappedSuperclass
public class BaseEntity implements Persistable<Integer> {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;


    public BaseEntity() {
    }

    protected BaseEntity(Integer id) {
        this.id = id;

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (getId() == null);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
