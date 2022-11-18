plugins {
    kotlin("jvm") version kotlinVersion
}

group = "$modGroup.core"
version = modVersion

repositories {
    mavenCentral()
}

dependencies {
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = jvmTarget
    }
}
