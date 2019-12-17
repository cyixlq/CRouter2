package top.cyixlq.crouter_plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.logging.Logger

import java.util.jar.JarFile

class CRouterPluginTransform extends Transform {

    private Project project
    private Logger logger

    CRouterPluginTransform(Project project) {
        this.project = project
        logger = project.logger
    }

    @Override
    String getName() {
        return "CRouterPluginTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        def beginTime = System.currentTimeMillis()
        def aopInjector = new AOPInjector(project)
        logger.error("======================== 开始AOP操作 ========================")
        final boolean isIncremental = transformInvocation.incremental
        if (!isIncremental) {
            transformInvocation.outputProvider.deleteAll()
        }
        transformInvocation.inputs.each { TransformInput input ->
            if (!input.directoryInputs.empty) { // 如果传入的文件夹不为空，那就对文件夹内的文件进行处理
                input.directoryInputs.each { DirectoryInput directoryInput ->
                    aopInjector.injectDir(directoryInput.file.absolutePath)
                    // 获取输出目录
                    def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)
                    // 将input的目录复制到output指定目录
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
            }
            if (!input.jarInputs.empty) {
                input.jarInputs.each {
                    List<String> classNameList = Utils.getJarRouteInjectClassNameList(new JarFile(it.file))
                    if (!classNameList.isEmpty()) {
                        logger.error("======================== 找到jar包中符合要求的classes ========================")
                        aopInjector.injectJar(it.file)
                        aopInjector.addAllInjectModuleNames(classNameList)
                    }
                    String outputFileName = it.name.replace(".jar", "") + '-' + it.file.path.hashCode()
                    def output = transformInvocation.outputProvider.getContentLocation(outputFileName,
                            it.contentTypes, it.scopes, Format.JAR)
                    if (Utils.containsClass(new JarFile(it.file), Const.ROUTE_INJECTOR_CLASS_NAME)) {
                        aopInjector.injectJar(it.file)
                        aopInjector.foundTarget(it.file, output)
                        return true // 如果找到CRouter库的jar包暂时不拷贝到输出目录，等插桩后再拷贝，其余的可以直接拷贝到输出目录
                    }
                    FileUtils.copyFile(it.file, output)
                }
            }
        }
        aopInjector.setInjectFunBody() // 开始插桩，然后将插桩后的jar文件拷贝至输出目录
        aopInjector.removeClassPath()
        aopInjector = null
        ClassPool.getDefault().clearImportedPackages()
        logger.error("==== CRouter-Plugin::AOP 处理完成，耗时：" + (System.currentTimeMillis() - beginTime) / 1000 + " 秒 ====")
    }
}
