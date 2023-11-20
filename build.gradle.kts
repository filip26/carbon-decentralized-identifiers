
plugins {
    id("com.android.library") version "8.1.4"
    id("maven-publish")
}

android {
    namespace = "com.apicatalog"
    compileSdk = 34

    sourceSets.getByName("main") {
        java.setSrcDirs(listOf("src/main/java8", "src/main/java"))
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }    
    
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

 testOptions {
  execution = "ANDROIDX_TEST_ORCHESTRATOR"
 }
            
}

publishing {
    publications {
            register<MavenPublication>("release") {
                groupId = "com.apicatalog"
                artifactId = "carbon-did-android"
                version = "1.1111"

                afterEvaluate {
                    from(components["release"])
                }
            }
    }
  repositories {
    maven {
      name = "myrepo"
      url = uri("${project.buildDir}/repo")
    }
  }    
}

dependencies {
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.8.0")
    
    // custom
    implementation("com.apicatalog:titanium-json-ld-jre8:1.3.3")
    implementation("com.apicatalog:copper-multicodec:0.0.5")
    implementation("com.github.multiformats:java-multibase:1.1.1")
    
    // test
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestUtil("androidx.test:orchestrator:1.4.2")    
    
}
      
