package com.github.marioariasc.zigsupport.services

import com.intellij.openapi.project.Project
import com.github.marioariasc.zigsupport.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
