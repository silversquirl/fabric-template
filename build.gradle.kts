plugins {
	id("fabric-loom") version "0.9-SNAPSHOT"
}

base {
	archivesBaseName = properties["base_name"] as String
}

version = properties["mod_version"]!!
group = properties["maven_group"]!!

java {
	sourceCompatibility = JavaVersion.VERSION_16
	targetCompatibility = JavaVersion.VERSION_16
}

dependencies {
	minecraft("com.mojang", "minecraft", properties["mc_version"] as String)
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc", "fabric-loader", properties["loader_version"] as String)
	modImplementation("net.fabricmc.fabric-api", "fabric-api", properties["api_version"] as String)
}

tasks {
	processResources {
		inputs.property("version", version)
		filesMatching("fabric.mod.json") {
			expand("version" to version)
		}
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesBaseName}" }
		}
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.release.set(16)
}

