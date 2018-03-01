plugins {
    java
}

repositories {
    jcenter()
}

val hibernateVersion = "5.2.14.Final"
val postgreSqlVersion = "42.2.1"
val hikariCpVersion = "2.7.8"
val jaxbVersion = "2.3.0"
var JUnitVersion = "5.1.0"

dependencies {

    compile ("org.slf4j:slf4j-api:1.7.25")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")

    runtime ("org.postgresql:postgresql:$postgreSqlVersion")
    runtime ("com.zaxxer:HikariCP:$hikariCpVersion")
    runtime ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    runtime ("ch.qos.logback:logback-classic:1.2.3")

    testCompile ("org.junit.jupiter:junit-jupiter-api:$JUnitVersion")
    testCompile ("org.assertj:assertj-core:3.9.0")

    testRuntime ("org.junit.jupiter:junit-jupiter-engine:$JUnitVersion")

    // Java EE modules used to be included with the JRE. They were deprecated in Java SE 9 with the
    // declared intent to remove them in a future release.
    testRuntime ("javax.xml.bind:jaxb-api:$jaxbVersion") // JAXB (JSR 222) Standalone Implementation
    testRuntime ("com.sun.xml.bind:jaxb-impl:$jaxbVersion")
    testRuntime ("com.sun.xml.bind:jaxb-core:$jaxbVersion")

    testRuntime ("javax.activation:activation:1.1.1") // JSR-925 Java Beans Activation Framework
}

val test: Test by tasks
test.useJUnitPlatform()

