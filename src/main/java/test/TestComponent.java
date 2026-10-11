package test;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class TestComponent {

    @ManyToOne(fetch = FetchType.LAZY)
    private TestObject object;

    public TestObject getObject() {
        return object;
    }

    public void setObject(TestObject object) {
        this.object = object;
    }
}
