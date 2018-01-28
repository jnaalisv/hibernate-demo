def logPattern = "%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %logger{40}: %msg%n%ex{full, sun, java.lang, java.util}"

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

root(INFO, ["STDOUT"])

logger("jnaalisv", INFO)
logger("org.hibernate", INFO)
logger("org.hibernate.SQL", DEBUG)
logger("org.hibernate.type.descriptor.sql.BasicBinder", TRACE)
logger("org.hibernate.tool.hbm2ddl.SchemaExport", DEBUG)

