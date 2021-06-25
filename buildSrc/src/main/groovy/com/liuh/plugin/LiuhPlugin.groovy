package com.liuh.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class LiuhPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        def extension = target.extensions.create('liuhuan', LiuhPluginExtension)
        target.afterEvaluate {
            println("hello ${extension.name}!")
        }
    }
}