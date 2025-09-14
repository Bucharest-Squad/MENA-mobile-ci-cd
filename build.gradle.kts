import groovy.json.JsonOutput

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}


tasks.register("exportModuleDeps") {
    doLast {
        val allProjects = rootProject.subprojects.map { it.name }.toSet()
        val map = mutableMapOf<String, MutableSet<String>>()

        rootProject.subprojects.forEach { project ->
            val deps = mutableSetOf<String>()
            project.configurations
                .matching { it.name.contains("implementation", true) }
                .forEach { cfg ->
                    cfg.dependencies.forEach { dep ->
                        if (dep is ProjectDependency && allProjects.contains(dep.dependencyProject.name)) {
                            deps.add(dep.dependencyProject.name)
                        }
                    }
                }
            map[project.name] = deps
        }

        val reverseDeps = mutableMapOf<String, MutableSet<String>>()
        map.keys.forEach { reverseDeps[it] = mutableSetOf() }
        map.forEach { (project, deps) ->
            deps.forEach { dep -> reverseDeps[dep]?.add(project) }
        }

        fun getAllDependents(module: String, visited: MutableSet<String> = mutableSetOf()): Set<String> {
            if (!visited.add(module)) return emptySet()
            val direct = reverseDeps[module] ?: emptySet()
            return direct + direct.flatMap { getAllDependents(it, visited) }
        }

        val output = map.keys.associateWith { getAllDependents(it) }
        println(output.toString())
    }
}