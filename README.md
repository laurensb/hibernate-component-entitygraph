Unit test to demonstrate problem in Hibernate when using a query with fetch graph on entity containing embedded component.

Running this test using Maven with default profile

```
mvn verify
```

will result in `queryUsingFetchGraph` failing. However running this test using EclipseLink 

```
mvn -Peclipselink verify
```

will pass both tests.

The problem seems to originate in [EntityGraphQueryHint](https://github.com/hibernate/hibernate-orm/blob/e76241a3091078713dd4b57de085f5fadce5e0db/hibernate-core/src/main/java/org/hibernate/engine/query/spi/EntityGraphQueryHint.java#L125-L164) which handles entity types and collection types, but not component types. Compare this to [LoadQueryJoinAndFetchProcessor](https://github.com/hibernate/hibernate-orm/blob/e76241a3091078713dd4b57de085f5fadce5e0db/hibernate-core/src/main/java/org/hibernate/loader/plan/exec/internal/LoadQueryJoinAndFetchProcessor.java#L133-L154) which handles all 3.