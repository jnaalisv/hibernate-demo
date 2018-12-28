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

val hibernateVersion = "5.4.0.Final"
val postgreSqlVersion = "42.2.5"
val hikariCpVersion = "3.2.0"
var JUnitVersion = "5.4.0-M1"

dependencies {

    compile ("org.slf4j:slf4j-api:1.7.25")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")

    runtime ("org.postgresql:postgresql:$postgreSqlVersion")
    runtime ("com.zaxxer:HikariCP:$hikariCpVersion")
    runtime ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    runtime ("ch.qos.logback:logback-classic:1.2.3")

    testCompile ("org.junit.jupiter:junit-jupiter:$JUnitVersion")
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = false
            csv.isEnabled = true
            html.destination = file("${buildDir}/jacocoHtml")
        }
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    compileJava {
        options.compilerArgs = listOf("-Xlint:all", "-Werror")
    }
}
