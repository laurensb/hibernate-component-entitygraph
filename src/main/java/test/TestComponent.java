package test;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
