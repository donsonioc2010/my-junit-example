package com.example.junitexample.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@DisplayName("가정기반 테스트")
@SpringJUnitConfig
@TestPropertySource(properties = {"spring.profiles.active=test"})
public class AssumptionsTest {

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    // profiles가 test인 경우에만 실행함
    @Test
    @DisplayName("profiles가 test인 경우에만 Assertions이 실행된다.")
    void whenProfilesIsTest_thenTestCaseIsSuccess() {
        Assumptions.assumeTrue("test".equals(activeProfiles));

        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("profiles가 test인 경우에만 Executable이 실행된다.")
    void whenProfilesIsTest_thenDoExecutable() {
        Assumptions.assumingThat("test".equals(activeProfiles), () -> {
            log.info("Profiles가 Test일때 executable영역 실행");
            Assertions.assertTrue(true);
        });
    }

    @Test
    @DisplayName("profiles가 test가 아닌 경우에만 Assertions이 실행된다.")
    void whenProfilesIsNotTest_thenTestCaseIsSuccess() {
        Assumptions.assumeFalse("test".equals(activeProfiles), "profiles가 test면 실행하지 않음");

        log.info("Profiles가 테스트가 아니라서 실행됨");
        Assertions.assertTrue(true);
    }


    @Test
    @DisplayName("profiles가 test가 아닌 경우에만 Assertions이 실행된다.")
    void whenProfilesIsNotTestBooleanSupplier_thenTestCaseIsSuccess() {
        Assumptions.assumeFalse(
            () -> "test".equals(activeProfiles),
            "profiles가 test면 실행하지 않음"
        );

        log.info("Profiles가 테스트가 아니라서 실행됨");
        Assertions.assertTrue(true);
    }

}
