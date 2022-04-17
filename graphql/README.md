### GRAPHQL
- class must be annotated with **@DgsComponent**
- Resolvers e.g FakeHelloDataResolver methods must match method name in schema
- methods must be annotated with **@DgsQuery**  ELSE use **@DgsData** as below

## CUSTOM MAPPING
- to use custom mapping
- use @DgsData(parentType="<SCHEMA_NAME>", field="<FIELD_WITHIN_SCHEMA>") OR @DgsQuery(field = "books")
- **InputArgument** used to customize argument name
- Optional<CustomType> we must use **@InputArgument(collectionType = CustomType.class)**

## DataFetchingEnvironment  
- this helps store all argument used in all graphql query as a map
- control click in(FakeBookDataResolver)  to see 

 
## DISABLING GRAPH[I]QL endpoint
```yaml
  dgs:
   graphiql:
     graphiql:
        enabled: false
```

### TEST
- GraphQL client: localhost:[port]/graphiql
- GraphQL service: localhost:[port]/graphql    #### if using **Altair Tool**


### ALTAIR TEST
- 1
```graphql

    query {
        oneHello {
            ...allFields
        }

       allHellos {
        ...allFields
       }
    }

    fragment allFields on Hello {
        text
        randomNumber
    }
```
-2 
```graphql
 query books {
    books(author: ""){   # you can add author name or skip
        title
        creator: author {
            name
            originCountry
        }
        released {
            year
            printedEdition
            releasedCountry
        }
    }
}
```