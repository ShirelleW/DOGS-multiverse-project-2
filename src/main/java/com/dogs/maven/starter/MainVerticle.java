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
public class MainVerticle extends AbstractVerticle {

  // Store our Dogs
  private Map<Integer, Dogs> dogs = new LinkedHashMap<>();
  // Create some Dog
  private void createSomeData() {
    Dogs molly = new Dogs("Molly", "Cairn Terrier", 2);
    dogs.put(molly.getId(), molly);

    Dogs coco = new Dogs("Coco", "Golden Retriever", 5);
    dogs.put(coco.getId(), coco);
  }

  private void getAll(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(dogs.values()));
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
