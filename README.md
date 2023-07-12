# traverse-the-diagram
A coding challenge that traverse a diagram using camunda library

Java program does the following:
* It fetches the XML representation of the ‘invoice approval’ BPMN diagram from a remote server.
* It parses the XML into a traversable data structure.
* It finds one possible path on the graph between a given start node and a given end node.
* It prints out the IDs of all nodes on the found path.

The code is not finding the shortest path, but a possible path that does not contain loops

## Get started

Starting from the root folder of the project

1. compile the project
```sh
mvnw clean package
```
2. execute the application with:
```sh
java -jar .\target\Diagram_Challenge-0.0.1-SNAPSHOT.jar <start_node_id> <end_node_id>
```

### Example of usage
Below an example of usage

```
java -jar .\target\Diagram_Challenge-0.0.1-SNAPSHOT.jar approveInvoice invoiceProcessed
```

