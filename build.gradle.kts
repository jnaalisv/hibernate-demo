plugins {
    java
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    jcenter()
}

val hibernateVersion = "5.3.5.Final"
val postgreSqlVersion = "42.2.5"
val hikariCpVersion = "3.2.0"
val jaxbVersion = "2.3.0"
var JUnitVersion = "5.2.0"

dependencies {

    compile ("org.slf4j:slf4j-api:1.7.25")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")

    runtime ("org.postgresql:postgresql:$postgreSqlVersion")
    runtime ("com.zaxxer:HikariCP:$hikariCpVersion")
    runtime ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    runtime ("ch.qos.logback:logback-classic:1.2.3")

    // JAXB (JSR 222) Standalone Implementation
    runtime ("javax.xml.bind:jaxb-api:$jaxbVersion")
    runtime ("com.sun.xml.bind:jaxb-impl:$jaxbVersion")
    runtime ("com.sun.xml.bind:jaxb-core:$jaxbVersion")

    // JSR-925 Java Beans Activation Framework
    runtime ("javax.activation:activation:1.1.1")

    testCompile ("org.junit.jupiter:junit-jupiter-api:$JUnitVersion")
    testCompile ("org.junit.jupiter:junit-jupiter-params:$JUnitVersion")
    testRuntime ("org.junit.jupiter:junit-jupiter-engine:$JUnitVersion")
}

val jacocoTestReport: JacocoReport by tasks
jacocoTestReport.reports {
    xml.isEnabled = false
    html.isEnabled = true
    html.destination = file("${buildDir}/jacocoHtml")
}

tasks {
    "test" (Test::class) {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    "compileJava" (JavaCompile::class) {
        options.compilerArgs = listOf("-Xlint:all", "-Werror")
    }
}

jacoco {
    toolVersion = "0.8.2" // latest jacoco with support for java11
}