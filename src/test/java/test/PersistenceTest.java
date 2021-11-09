package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit test to demonstrate problem in Hibernate when using a query with fetch graph on entity containing
 * embedded component.
 *
 * Running this test using Maven with default profile (mvn verify) will result in {@link #queryUsingFetchGraph()}
 * failing. However running this test using EclipseLink (mvn -Peclipselink verify) will pass both tests.
 *
 * The problem seems to originate in <a href="https://github.com/hibernate/hibernate-orm/blob/e76241a3091078713dd4b57de085f5fadce5e0db/hibernate-core/src/main/java/org/hibernate/engine/query/spi/EntityGraphQueryHint.java#L125-L164">EntityGraphQueryHint</a>
 * which handles entity types and collection types, but not component types. Compare this to <a href="https://github.com/hibernate/hibernate-orm/blob/e76241a3091078713dd4b57de085f5fadce5e0db/hibernate-core/src/main/java/org/hibernate/loader/plan/exec/internal/LoadQueryJoinAndFetchProcessor.java#L133-L154">LoadQueryJoinAndFetchProcessor</a>
 * which handles all 3.
 */
public class PersistenceTest {

    private static EntityManagerFactory emf;

    @Test
    public void findUsingFetchGraph() {
        var em = emf.createEntityManager();

        // Create entity graph
        var graph = em.createEntityGraph(TestEntity.class);
        graph.addAttributeNodes("component");
        graph.addSubgraph("component").addAttributeNodes("object");

        // Find entity with id 42 using fetch graph
        var a = em.find(TestEntity.class, 42, Collections.singletonMap("javax.persistence.fetchgraph", graph));

        // Closing EM to prevent lazy loading
        em.close();

        // Assert that value was eagerly loaded
        assertThat(a.getComponent().getObject().getValue()).isEqualTo("Test value");
    }

    @Test
    public void queryUsingFetchGraph() {
        var em = emf.createEntityManager();

        // Create entity graph
        var graph = em.createEntityGraph(TestEntity.class);
        graph.addAttributeNodes("component");
        graph.addSubgraph("component").addAttributeNodes("object");

        // Query entity with id 42 using fetch graph
        var a = em.createQuery("from TestEntity e where e.id = :id", TestEntity.class)
                .setParameter("id", 42)
                .setHint("javax.persistence.fetchgraph", graph)
                .getSingleResult();

        // Closing EM to prevent lazy loading
        em.close();

        // Assert that value was eagerly loaded
        assertThat(a.getComponent().getObject().getValue()).isEqualTo("Test value");
    }

    /**
     * Create {@link EntityManagerFactory} and persist {@link TestEntity} with id 42.
     */
    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("test");
        var em = emf.createEntityManager();

        var object = new TestObject();
        object.setId(101);
        object.setValue("Test value");

        var component = new TestComponent();
        component.setObject(object);

        var entity = new TestEntity();
        entity.setId(42);
        entity.setComponent(component);

        em.getTransaction().begin();

        em.persist(object);

        em.persist(entity);

        em.flush();

        em.getTransaction().commit();

        em.close();
    }
}
