plugins {
    kotlin("jvm")
    id("fabric-loom")
    `maven-publish`
    java
}

base {
    archivesBaseName = property("archives_base_name")!!.toString()
}

group = property("maven_group")!!.toString() + ".fabric"
version = property("mod_version")!!

repositories {
    maven("https://maven.shedaniel.me/") // cloth config
    maven("https://maven.terraformersmc.com/releases/") // mod menu
}

val inJar = configurations.create("inJar")
configurations.implementation.extendsFrom(inJar)

dependencies {
    inJar(project(":core"))

    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin_version")}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${property("cloth_config_version")}") {
        exclude("net.fabricmc.fabric-api")
    }
    modApi("com.terraformersmc:modmenu:${property("mod_menu_version")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from("LICENSE")
        from(
            inJar.map {
                if (it.isDirectory) it else zipTree(it)
            }
        )
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

java {
    withSourcesJar()
}
