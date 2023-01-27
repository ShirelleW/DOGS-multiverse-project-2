package com.dogs.maven.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(3306)
      .setHost("the-host")
      .setDatabase("Dogs")
      .setUser("root")
      .setPassword("password123");


    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

    // Create the client pool
    SqlClient client = MySQLPool.client(connectOptions, poolOptions);


    // Router
    Router router = Router.router(vertx);

  }
}
