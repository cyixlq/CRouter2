package top.cyixlq.compiler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;

import top.cyixlq.compiler.utils.CheckUtil;
import top.cyixlq.compiler.utils.Consts;
import top.cyixlq.compiler.utils.Logger;

public abstract class BaseProcessor extends AbstractProcessor {

    Filer filer;
    Logger logger;
    String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        logger = new Logger(processingEnv.getMessager());
        final Map<String, String> options = processingEnv.getOptions();
        if (CheckUtil.isNotEmpty(options)) {
            moduleName = options.get(Consts.KEY_MODULE_NAME);
        }
        if (CheckUtil.isNotEmpty(moduleName)) {
            logger.info("用户设置了模块名：" + moduleName);
        } else {
            throw new RuntimeException(Consts.PREFIX_OF_LOGGER + "-->>你必须在gradle中设置模块名");
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            this.add(Consts.KEY_MODULE_NAME);
        }};
    }
}
