# Similarity
Similarity Analysis

Compilation & Running:-

1) Checkout the git folder.

2) go to Similarity folder.

3) Exec maven command - "mvn clean package"

4) Exec java command to test similarity - "java -cp target/Similarity-0.0.1-SNAPSHOT.jar com.fratics.ecom.similarity.main.Main"

5) Exec java command to start similarity rest service - "java -cp target/Similarity-0.0.1-SNAPSHOT.jar com.fratics.ecom.similarity.rest.SimilarityServer"

6) run curl client to test rest service - "curl -v -u "admin:admin123" http://localhost:9000/similarityservices/v1/loadfromdb"

