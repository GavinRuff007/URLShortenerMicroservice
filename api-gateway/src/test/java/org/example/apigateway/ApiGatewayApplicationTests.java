package org.example.apigateway;

import org.example.apigateway.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

    @Autowired
    private UserService encode;


    @Test
    void testRegisterUser() {
        // Arrange
        String password = "123";
        encode.encodePassword(password);
    }

}
