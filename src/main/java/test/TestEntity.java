package test;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestEntity {

    @Id
    private Integer id;

    @Embedded
    private TestComponent component;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TestComponent getComponent() {
        return component;
    }

    public void setComponent(TestComponent component) {
        this.component = component;
    }
}
