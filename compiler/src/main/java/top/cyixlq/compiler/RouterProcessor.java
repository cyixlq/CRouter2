package top.cyixlq.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import top.cyixlq.annotation.Route;
import top.cyixlq.compiler.utils.CheckUtil;
import top.cyixlq.compiler.utils.Consts;
import top.cyixlq.compiler.utils.Logger;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "top.cyixlq.annotation.Route"
})
public class RouterProcessor extends BaseProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        logger = new Logger(processingEnv.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (CheckUtil.isNotEmpty(annotations)) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Route.class);
            try {
                logger.info("发现@Route注解，开始...");
                parseRoute(elements);
            } catch (Exception e) {
                logger.error(e);
            }
            return true;
        }
        return false;
    }

    private void parseRoute(Set<? extends Element> elements) throws IOException {
        if (elements == null || elements.isEmpty()) return;
        logger.info("在" + moduleName + "模块下发现@Route注解，共有" + elements.size() + "条，开始肝，生成Java文件...");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);
        for (Element element : elements) {
            Route route = element.getAnnotation(Route.class);
            final Name clazzName = ((TypeElement) element).getQualifiedName();
            final String path = route.value();
            if (CheckUtil.isEmpty(path)) throw new RuntimeException(clazzName.toString() + "：路径名不能为空！！！");
            methodBuilder.addStatement("CRouter.addPath($S, $L.class)", route.value(),clazzName);
        }
        TypeSpec routerModule = TypeSpec.classBuilder(moduleName + Consts.ROUTE_MODULE_SUFFIX)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodBuilder.build())
                .build();
        JavaFile.builder(Consts.PACKAGE_NAME, routerModule).build().writeTo(filer);
    }
}
