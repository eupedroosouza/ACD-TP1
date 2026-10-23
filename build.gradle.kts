plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {

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