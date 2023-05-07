rootProject.name = "PackServe"
pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}

	plugins {
		val kotlin_version: String by settings

		kotlin("jvm") version kotlin_version
		kotlin("kapt") version kotlin_version
	}
}