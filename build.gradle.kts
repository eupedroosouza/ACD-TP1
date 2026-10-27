plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    compileOnly("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

application {
    mainClass = "Main"
}

tasks {
    jar {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass))
        }
    }
}