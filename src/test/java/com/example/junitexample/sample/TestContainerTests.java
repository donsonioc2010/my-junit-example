package com.example.junitexample.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

// Docker 실행 필수!

@Slf4j
@DisplayName("테스트 컨테이너 테스트")
public class TestContainerTests {

    // MySQL 컨테이너 (기존 DB라 가정), 포트는 외부포트 랜덤생성, 내부 3306고정, 근데 이미 내부 초기값 디폴트설정임 :)
    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test")
        .withExposedPorts(3306);

    // PostgreSQL 컨테이너 (B DB라 가정), 포트는 외부포트 랜덤생성, 내부포트 5432, 근데 이미 내부 초기값 디폴트설정임 :)
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test")
        .withExposedPorts(5432);

    private Connection connection;

    static {
        // 먼저 DB를 실행해야함..
        mysqlContainer.start();
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() throws SQLException{
        this.connection = DriverManager.getConnection(
            mysqlContainer.getJdbcUrl(),
            mysqlContainer.getUsername(),
            mysqlContainer.getPassword()
        );
    }

    @AfterEach
    void tearDown() throws SQLException{
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @DisplayName("DB 스위칭 테스트")
    public void databaseSwitchingTest() throws SQLException {
        Assertions.assertTrue(connection.isValid(2));
        log.info("MySQL연결 성공!");

        // A DB가 끊기는 상황 가정을 Stop으로 대체함
        mysqlContainer.stop();
        log.info("MySQL 연결 실패");

        // B DB로 연결 전환
        connection = DriverManager.getConnection(
            postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(),
            postgreSQLContainer.getPassword()
        );

        Assertions.assertTrue(connection.isValid(2));
        log.info("Postgres 연결 성공!");
    }

}
