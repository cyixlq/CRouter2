package top.cyixlq.crouter_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CRouterPlugin implements Plugin<Project> {

    void apply(Project project) {
        def log = project.logger
        def android = project.extensions.findByName("android")
        android.registerTransform(new CRouterPluginTransform(project))
        log.error "======================== CRouter的Plugin配置生效 ========================"
    }
}
