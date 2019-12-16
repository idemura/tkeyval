plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:28.0-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
}

application {
    mainClassName = "id.tkeyval.App"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

