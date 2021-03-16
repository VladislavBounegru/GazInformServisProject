package ru.bounegru.project.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;


class ConnectToDBTest {


    @Test
    public void testMockDBConnection_Should_Return_True() {
        ConnectToDB connectToDB = new ConnectToDB();
        Connection connection = connectToDB.getConnection();
        assertThat(connection).isNotNull();
    }
}