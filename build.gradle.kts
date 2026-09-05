plugins {
    id("net.fabricmc.fabric-loom-remap") version("1.17.+")
    id("ploceus") version("1.17.+")
}

group = "com.example"
version = "mod_version"()

ploceus {
    setIntermediaryGeneration(2)
}

dependencies {
    minecraft("com.mojang:minecraft:${"minecraft_version"()}")
    mappings(loom.layered {
        mappings(ploceus.featherMappings("feather_version"()))
        mappings(rootProject.file("gradle/feather-overrides.tiny"))
    })

    modImplementation("net.fabricmc:fabric-loader:${"fabric_version"()}")
    ploceus.dependOsl("osl_version"())
}

tasks.processResources {
    val v = project.version
    inputs.property("version", v)

    filesMatching("fabric.mod.json") {
        expand("version" to v)
    }
}

operator fun String.invoke() = rootProject.providers.gradleProperty(this).orNull
    ?: error("Property $this not found")