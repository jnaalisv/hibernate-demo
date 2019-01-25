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

val hibernateVersion = "5.4.1.Final"
val postgreSqlVersion = "42.2.5"
val hikariCpVersion = "3.3.0"
val JUnitVersion = "5.4.0-M1"
val slf4jVersion = "1.7.25"
val logbackVersion = "1.2.3"

dependencies {

    implementation ("org.slf4j:slf4j-api:$slf4jVersion")
    implementation ("org.hibernate:hibernate-core:$hibernateVersion")

    runtimeOnly ("org.postgresql:postgresql:$postgreSqlVersion")
    runtimeOnly ("com.zaxxer:HikariCP:$hikariCpVersion")
    runtimeOnly ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    runtimeOnly ("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation ("org.junit.jupiter:junit-jupiter:$JUnitVersion")
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
