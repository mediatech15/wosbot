plugins {
    base
    `java-library`
    distribution
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    `project-report`
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    maven {
        url = uri("https://maven.google.com/")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

group = "dev.fidgetcode"
version = "1.7.0"
description = "wos-automator"
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

javafx {
    version = "25.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

distributions {
    main {
        distributionBaseName = "wos-automator"
        contents {
            into("lib") {
                from("lib")
            }
        }
    }
}

application {
    mainClass = "dev.fidgetcode.bot.main.Main"
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED", "--enable-native-access=javafx.graphics")
    executableDir = file("bin/exec").toString()
}

dependencies {
    api("org.openpnp:opencv:4.9.0-0")
    api("net.sourceforge.tess4j:tess4j:5.14.0")
    api("org.slf4j:slf4j-api:2.0.17")
    api("net.dv8tion:JDA:5.3.0")
    api("com.android.tools.ddms:ddmlib:31.11.1")
    api("org.xerial:sqlite-jdbc:3.42.0.0")
    api("org.hibernate.orm:hibernate-core:7.0.8.Final")
    api("com.zaxxer:HikariCP:5.0.1")
    api("jakarta.persistence:jakarta.persistence-api:3.2.0")
    api("org.hibernate.orm:hibernate-community-dialects:7.0.8.Final")
    api("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    api("org.glassfish.jaxb:jaxb-runtime:4.0.3")
    api("org.controlsfx:controlsfx:11.2.2")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")
}
