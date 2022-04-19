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

## EXTENDED SCALAR TYPES
- you define only once and also add mapping to POM or build.gradle
- see **https://github.com/graphql-java/graphql-java-extended-scalars** for more extended types

## META FIELD & Inline Fragment
- **__typename** is used when the return type might vary (e.g interface with two implementation)
- inline fragments are fragments within query
- ```graphql
    ... on Dog {
   # Dog fields 
  }
  ```

## UNION
- merging/joining 2 objects into one
- e.g Hello Object and Book Object
- see SmartSearchResult
- see union in collection

## MUTATION 
- create/add, edit and delete operations

## DISABLING GRAPH[I]QL endpoint
```yaml
  dgs:
   graphiql:
     graphiql:
        enabled: false
```

## ERROR HANDLING
- if no error data has value and errors is null
- if error, errors has value and data is null
- we can return custom error message, custom error detail as implemented
- ```graphql
     interface GraphQLError {
        message: String!
        locations: [Location]
        path: [String]
        extensions: TypedError
    }   
  ```
- ```json
      {
    "errors": [
      {
        "message": "java.lang.IllegalArgumentException: Invalid credential",
        "locations": [],
        "path": [
          "userLogin"
        ],
        "extensions": {
          "errorType": "INTERNAL"
          "errorDetail": null
        }
      }
    ],
    "data": null
  }
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