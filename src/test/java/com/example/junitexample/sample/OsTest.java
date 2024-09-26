package com.example.junitexample.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@Slf4j
@DisplayName("각 OS환경에 맞는 테스트 지원")
class OsTest {
    @Test
    @EnabledOnOs(OS.MAC)
    @DisplayName("Mac인 경우에만 실행됩니다.")
    void whenRunningOsIsMac_thenTestCaseIsSuccess() {
        String osName = System.getProperty("os.name");
        log.info("현재 실행 된 테스트케이스는 MacOS버전이며, OS정보 >>> {}",  osName);
        Assertions.assertTrue(osName.contains("Mac OS"));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @DisplayName("윈도우에서만 실행되는 테스트")
    void whenRunningOsIsWindows_thenTestCaseIsSuccess() {
        log.info("현재 실행환경은 윈도우~");
        Assertions.assertTrue(System.getProperty("os.name").contains("Windows"));
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @DisplayName("윈도우에서만 실행되는 테스트")
    void whenRunningOsIsLinux_thenTestCaseIsSuccess() {
        log.info("현재 실행환경은 리눅스~");
        Assertions.assertTrue(System.getProperty("os.name").contains("Linux"));
    }
}
