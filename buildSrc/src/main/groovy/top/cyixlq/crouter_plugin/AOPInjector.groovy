package top.cyixlq.crouter_plugin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.JarClassPath
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.logging.Logger

import java.util.jar.JarFile

class AOPInjector {

    private File targetJarFile
    private File outDir
    private Logger logger
    private Project project
    private List<String> injectModuleNames = new ArrayList<>()
    private List<JarClassPath> classPathList = new ArrayList<>()

    AOPInjector(Project project) {
        this.logger = project.logger
        this.project = project
    }

    // 搜集文件夹内符合要求的class文件
    void injectDir(final String path) {
        ClassPool pool = ClassPool.getDefault()
        pool.appendClassPath(path)
        File dir = new File(path)
        if (!dir.isDirectory()) {
            return
        }
        dir.eachFileRecurse { File file ->
            final String filePath = file.absolutePath
            // 如果当前文件是我们APT生成的class文件，就把它放进list中
            if (filePath.endsWith(Const.ROUTE_MODULE_SUFFIX)) {
                final String className = Utils.getClassName(filePath.indexOf("top"), filePath)
                logger.error("======================== 找到文件夹中符合要求的class ========================")
                injectModuleNames.add(className)
            }
        }
    }

    // 解压jar文件，并且给RouteInjector中的inject方法重新设置方法体
    void setInjectFunBody() {
        if (null == targetJarFile) {
            throw new RuntimeException("未找到RouteInjector.class，请检查是否引入CRouter依赖")
        }
        JarFile jarFile = new JarFile(targetJarFile)
        logger.error("======================== 开始向RouteInjector中的inject方法中注入代码 ========================")
        def baseDir = new StringBuilder().append(project.projectDir.absolutePath)
                .append(File.separator).append("build")
                .append(File.separator).append("crouter_temp").toString()
        File rootFile = new File(baseDir)
        Utils.clearFile(rootFile)
        if (!rootFile.mkdirs()) {
            logger.error("mkdirs ${rootFile.absolutePath} failure")
        }
        File unzipDir = new File(rootFile, "unzipJar")
        File jarDir   = new File(rootFile, "jar")

        // 1、判断是否进行过解压缩操作
        if (!Utils.hasFiles(unzipDir)) {
            Utils.unzipJarFile(jarFile, unzipDir)
            // 2、开始注入文件，需要注意的是，appendClassPath后边跟的根目录，没有后缀，className后完整类路径，也没有后缀
            ClassPool.getDefault().appendClassPath(unzipDir.absolutePath)
        }

        // 3、开始注入代码
        CtClass ctClass = ClassPool.getDefault().getCtClass("top.cyixlq.crouter.RouteInjector")
        CtMethod ctMethod = ctClass.getDeclaredMethod("inject")
        StringBuilder methodBody = new StringBuilder("{")
        injectModuleNames.each { String className ->
            methodBody.append(className).append(".inject();\n")
        }
        methodBody.append("}")
        logger.error("在inject方法中注入的代码 --->>> \n\n" + methodBody.toString() + "\n<<<-----------")
        ctMethod.setBody(methodBody.toString())
        // 4、写入文件
        if (ctClass.isFrozen())
            ctClass.defrost()
        ctClass.writeFile(unzipDir.absolutePath)
        ctClass.detach()//用完一定记得要卸载，否则pool里的永远是旧的代码
        // 5、判断classes文件夹下是否有文件
        File destFile = null
        if (Utils.hasFiles(unzipDir)) {
            destFile = new File(jarDir, targetJarFile.name)
            Utils.clearFile(destFile)
            Utils.zipJarFile(unzipDir, destFile)
            //Utils.clearFile(unzipDir)
        } else {
            Utils.clearFile(rootFile)
        }
        jarFile.close()
        // 6，最终注入过代码的jar文件复制到输出目录，并且将该jar文件从croute_temp目录下删除
        if (destFile != null) {
            FileUtils.copyFile(destFile, outDir)
            destFile.delete()
            return
        }
        throw new RuntimeException("生成注入过代码的Jar文件失败")
    }

    void removeClassPath() {
        if (classPathList != null) {
            def pool = ClassPool.getDefault()
            classPathList.each {
                try {
                    pool.removeClassPath(it)
                } catch (Exception e) {
                    logger.error(e.getMessage())
                }
            }
            classPathList.clear()
        }
    }

    void injectJar(File jar) {
        ClassPool pool = ClassPool.getDefault()
        def classPath = new JarClassPath(jar.getAbsolutePath())
        classPathList.add(classPath)
        pool.appendClassPath(classPath)
    }

    void foundTarget(File jar, File outDir) {
        logger.error("======================== 找到了RouteInjector ========================")
        this.targetJarFile = jar
        this.outDir = outDir
    }

    void addAllInjectModuleNames(List<String> injectModuleNames) {
        this.injectModuleNames.addAll(injectModuleNames)
    }

}
