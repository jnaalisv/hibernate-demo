plugins {
    java
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val hibernateVersion = "5.6.3.Final"
val postgreSqlVersion = "42.4.0"
val hikariCpVersion = "5.0.1"
val JUnitVersion = "5.8.2"
val log4j2Version = "2.17.2"

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
            xml.required.set(false)
            csv.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }
}


tasks.withType<JavaCompile>().configureEach {
    options.apply {
        encoding = "UTF-8"
        compilerArgs.add("-Xlint:all")
    }
}
