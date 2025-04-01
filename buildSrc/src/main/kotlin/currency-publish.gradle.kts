plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.eripe14"
version = Versions.PROJECT

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }
}