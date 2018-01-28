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

    runtime ("org.postgresql:postgresql:$postgreSqlVersion")
    runtime ("org.codehaus.groovy:groovy:2.4.9")
    runtime ("ch.qos.logback:logback-classic:1.2.3")

    compile ("org.slf4j:slf4j-api:1.7.25")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")
    compile ("org.hibernate:hibernate-hikaricp:$hibernateVersion")
    compile ("com.zaxxer:HikariCP:$hikariCpVersion")

    testCompile ("junit:junit:4.12")
    testCompile ("org.assertj:assertj-core:3.9.0")
}