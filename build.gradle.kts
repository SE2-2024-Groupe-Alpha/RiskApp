// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.sonarqube") version "4.4.1.3373"

}

sonarqube {
    properties {
        property("sonar.projectKey", "SE2-2024-Group-Alpha_RiskApp")
        property("sonar.organization", "se2-2024-group-alpha")
        property("sonar.host.url", "https://sonarcloud.io/")
    }
}
