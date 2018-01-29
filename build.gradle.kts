plugins {
    java
}

repositories {
    jcenter()
}

val hibernateVersion by extra {"5.2.12.Final"}
val postgreSqlVersion by extra {"42.2.1"}
val hikariCpVersion by extra {"2.7.6"}

dependencies {

    compile ("org.slf4j:slf4j-api:1.7.25")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")

    testCompile ("junit:junit:4.12")
    testCompile ("org.assertj:assertj-core:3.9.0")

    testRuntime ("org.postgresql:postgresql:$postgreSqlVersion")
    testRuntime ("com.zaxxer:HikariCP:$hikariCpVersion")
    testRuntime ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    testRuntime ("ch.qos.logback:logback-classic:1.2.3")

    // Java EE modules used to be included with the JRE. They were deprecated in Java SE 9 with the
    // declared intent to remove them in a future release.
    testRuntime ("org.glassfish.jaxb:jaxb-runtime:2.3.0") // JAXB (JSR 222) Reference Implementation
    testRuntime ("javax.activation:activation:1.1.1") // JSR-925 Java Beans Activation Framework
}