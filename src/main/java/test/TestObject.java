package test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestObject {

    @Id
    private Integer id;

    private String value1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value1;
    }

    public void setValue(String value) {
        this.value1 = value;
    }
}
