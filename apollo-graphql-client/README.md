# Download Schema
- gradle downloadApolloSchema --endpoint="http://localhost:8080/graphql" --schema="src/main/graphql/com/zikozee/graphqlclient/schema.json" --console plain
- ensure the server is running and on port specified above

## Operations
- copy all operations 
- ```text
    **book** operations place in **book.graphql**
    **hello** operations place in **hello.graphql**
    **stock** operations place in **stock.graphql**
  ```
- to their respective file and place in the same location as schema.json 

## Run Apollo task
- run gradle generateApolloSources
- or Just click in task window