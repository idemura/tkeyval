plugins {
    java
    application
    id("me.champeau.gradle.jmh") version "0.5.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:28.0-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
    jmh("org.openjdk.jmh:jmh-core:1.23")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.23")
}

jmh {
    benchmarkMode = listOf("avgt")
    fork = 0
    jvmArgsAppend = listOf("–illegal-access=permit")
}

application {
    mainClassName = "id.tkeyval.App"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
