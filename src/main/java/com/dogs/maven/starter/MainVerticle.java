package com.dogs.maven.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.*;
import java.util.LinkedHashMap;
import java.util.Map;
import io.vertx.core.json.Json;
import io.vertx.core.Launcher;
import io.vertx.ext.web.handler.BodyHandler;
public class MainVerticle extends AbstractVerticle {

  // Store our Dogs
  private Map<Integer, Dogs> dogMapping = new LinkedHashMap<>();
  // Create some Dog
  private void createSomeData() {
    Dogs molly = new Dogs("Molly", "Cairn Terrier", 2);
    dogMapping.put(molly.getId(), molly);

    Dogs coco = new Dogs("Coco", "Golden Retriever", 5);
    dogMapping.put(coco.getId(), coco);
  }

  private void getAll(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(dogMapping.values()));
  }

  private void addOne(RoutingContext routingContext) {
    final Dogs dogs = Json.decodeValue(routingContext.getBodyAsString(),
      Dogs.class);
    dogMapping.put(dogs.getId(), dogs);
    routingContext.response()
      .setStatusCode(201)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(dogs));
  }

  private void deleteOne(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    if (id == null) {
      routingContext.response().setStatusCode(400).end();
    } else {
      Integer idAsInteger = Integer.valueOf(id);
      dogMapping.remove(idAsInteger);
    }
    routingContext.response().setStatusCode(204).end();
  }

  @Override
  public void start() {
//    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
//      .setPort(3306)
//      .setHost("the-host")
//      .setDatabase("Dogs")
//      .setUser("root")
//      .setPassword("password123");
//
//
//    PoolOptions poolOptions = new PoolOptions()
//      .setMaxSize(5);
//
//    // Create the client pool
//    SqlClient client = MySQLPool.client(connectOptions, poolOptions);

    createSomeData();
    // Router
    Router router = Router.router(vertx);

    //Routes
    router.get("/api/dogs").handler(this::getAll);
    router.route("/api/dogs").handler(BodyHandler.create());
    router.post("/api/dogs").handler(this::addOne);
    router.delete("/api/dogs/:id").handler((this::deleteOne));

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8888)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
}
