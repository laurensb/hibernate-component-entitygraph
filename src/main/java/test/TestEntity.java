package test;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

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
