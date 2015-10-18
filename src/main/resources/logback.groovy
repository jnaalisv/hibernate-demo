appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{40}: %msg%n"
    }
}

root(INFO, ["STDOUT"])

logger("jnaalisv", INFO)
logger("org.hibernate", INFO)

