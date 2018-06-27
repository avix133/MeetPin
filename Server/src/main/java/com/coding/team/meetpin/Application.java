package com.coding.team.meetpin;

import com.coding.team.meetpin.client_server.netty.Server;
import com.coding.team.meetpin.client_server.netty.ServerHandler;
import com.coding.team.meetpin.client_server.request.impl.DefaultRequestResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DefaultRequestResolver defaultRequestResolver;

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Elo wariaty");

        ServerHandler serverHandler = new ServerHandler(defaultRequestResolver);
        Server server = new Server(8081, serverHandler);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
