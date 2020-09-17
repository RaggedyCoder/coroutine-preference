package io.github.raggedycoder.preference.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.NamedDomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.task
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaTask

class MavenPublishableAndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        plugins.apply("org.jetbrains.dokka")
        plugins.apply("maven-publish")
        plugins.apply("signing")

        val sourcesJar = task<Jar>("sourcesJar") {
            from(android.sourceSets.getByName("main").java.srcDirs)
            archiveClassifier.set("sources")
        }

        val dokkaJavadocJar = task<Jar>("dokkaJavadocJar") {
            dependsOn(tasks.dokkaJavadoc)
            from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
            archiveClassifier.set("javadoc")
        }

        val dokkaHtmlJar = task<Jar>("dokkaHtmlJar") {
            dependsOn(tasks.dokkaHtml)
            from(tasks.dokkaHtml.flatMap { it.outputDirectory })
            archiveClassifier.set("html-doc")
        }

        publishing {
            publications {
                val publicationName =
                    projectProperties["POM_ARTIFACT_ID"] as? String ?: project.name

                create(publicationName, MavenPublication::class.java) {

                    from(components["release"])

                    artifacts {
                        add("archives", sourcesJar)
                        add("archives", dokkaJavadocJar)
                        add("archives", dokkaHtmlJar)
                    }

                    pom {
                        groupId = projectProperties["GROUP"] as? String
                        artifactId = projectProperties["POM_ARTIFACT_ID"] as? String
                        version = projectProperties["VERSION_NAME"] as? String

                        description.set(projectProperties["POM_DESCRIPTION"] as? String)
                        url.set(projectProperties["POM_URL"] as? String)

                        organization {
                            name.set("io.github.raggedycoder")
                            url.set("https://github.com/raggedycoder")
                        }

                        licenses {
                            license {
                                name.set(projectProperties["POM_LICENCE_NAME"] as? String)
                                url.set(projectProperties["POM_LICENCE_URL"] as? String)
                                distribution.set(projectProperties["POM_LICENCE_DIST"] as? String)
                            }
                        }
                        scm {
                            url.set(projectProperties["POM_SCM_URL"] as? String)
                            connection.set(projectProperties["POM_SCM_CONNECTION"] as? String)
                            developerConnection.set(projectProperties["POM_SCM_DEV_CONNECTION"] as? String)
                        }
                        developers {
                            developer {
                                name.set(projectProperties["POM_DEVELOPER_NAME"] as? String)
                                id.set(projectProperties["POM_DEVELOPER_ID"] as? String)
                                email.set(projectProperties["POM_DEVELOPER_EMAIL"] as? String)
                            }
                        }
                    }
                }

                repositories {
                    maven {
                        credentials {
                            username = repositoryUserName
                            password = repositoryPassword
                        }
                        url = if (isReleaseBuild) uri(releasesRepoUrl) else uri(snapshotsRepoUrl)
                    }
                }
            }
        }
        signing {
            isRequired = isReleaseBuild
            if (isReleaseBuild) sign(publishing.publications.getByName(project.name))
        }
    }
}

fun Project.publishing(configure: PublishingExtension.() -> Unit): Unit =
    extensions.configure("publishing", configure)

fun Project.signing(configure: SigningExtension.() -> Unit): Unit =
    extensions.configure("signing", configure)

inline fun <reified type : Task> Project.task(
    name: String,
    noinline configuration: type.() -> Unit
) =
    task(name, type::class, configuration)

val Project.publishing: PublishingExtension
    get() = the()

val Project.android: LibraryExtension
    get() = the()

val Project.projectProperties: Map<String, *>
    get() = properties

val Project.repositoryUserName: String
    get() = projectProperties["SONATYPE_NEXUS_USERNAME"] as? String ?: ""

val Project.repositoryPassword: String
    get() = projectProperties["SONATYPE_NEXUS_PASSWORD"] as? String ?: ""

val Project.isReleaseBuild: Boolean
    get() = projectProperties["VERSION_NAME"]?.toString()?.contains("SNAPSHOT")?.not() ?: false

const val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"

const val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots/"

operator fun <T> NamedDomainObjectCollection<T>.get(name: String) = this.getByName(name)

val TaskContainer.dokkaJavadoc: TaskProvider<DokkaTask>
    get() = named<DokkaTask>("dokkaJavadoc")

val TaskContainer.dokkaHtml: TaskProvider<DokkaTask>
    get() = named<DokkaTask>("dokkaHtml")
