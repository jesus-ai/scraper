plugins {
    java
    idea
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.2.1")
    implementation("org.jsoup:jsoup:1.12.1")
    implementation("org.json:json:20190722")
}

application {
    mainClassName = "me.duncte123.biblescraper.App"
}
