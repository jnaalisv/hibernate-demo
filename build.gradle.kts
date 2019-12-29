plugins {
    java
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_13
}

repositories {
    jcenter()
}

val hibernateVersion = "5.4.9.Final"
val postgreSqlVersion = "42.2.9"
val hikariCpVersion = "3.4.1"
val JUnitVersion = "5.5.2"
val log4j2Version = "2.13.0"

dependencies {
    implementation ("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation ("org.hibernate:hibernate-core:$hibernateVersion")

    runtimeOnly ("org.postgresql:postgresql:$postgreSqlVersion")
    runtimeOnly ("com.zaxxer:HikariCP:$hikariCpVersion")
    runtimeOnly ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    runtimeOnly ("org.apache.logging.log4j:log4j-core:$log4j2Version")
    runtimeOnly ("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")

    testImplementation ("org.junit.jupiter:junit-jupiter:$JUnitVersion")
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = false
            csv.isEnabled = true
            html.destination = file("$buildDir/jacocoHtml")
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
