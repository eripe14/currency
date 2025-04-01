plugins {
    `currency-java-21`
    `currency-repositories`

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "com.eripe14"
version = Versions.PROJECT

dependencies {
    implementation(project(":currency-api"))

    // -- paper --
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // -- adventure --
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")
    implementation("net.kyori:adventure-api:4.18.0")

    // -- notifications --
    implementation("com.eternalcode:multification-bukkit:1.1.4")
    implementation("com.eternalcode:multification-okaeri:1.1.4")

    // -- configs --
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.6")
    implementation("eu.okaeri:okaeri-configs-json-simple:5.0.6")

    // -- commands --
    implementation("dev.rollczi:litecommands-bukkit:3.9.7")

    // -- database --
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation("eu.okaeri:okaeri-persistence-jdbc:2.0.4")

    // -- lombok --
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

bukkit {
    main = "com.eripe14.currency.CurrencyPlugin"
    apiVersion = "1.13"
    prefix = "CurrencyPlugin"
    name = "CurrencyPlugin"
    author = "eripe14"
    version = "${project.version}"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.runServer {
    version("1.21.4")
}