package top.cyixlq.compiler.utils;

public class Consts {

    public static final String PROJECT = "CRouter"; // 项目名
    public static final String SEPARATOR = "_"; // 分隔符
    public static final String PACKAGE_NAME = "top.cyixlq.crouter";
    public static final String ROUTE_MODULE_SUFFIX = SEPARATOR + "RouterInjector"; // 路径注入的Java文件后缀名

    public static final String KEY_MODULE_NAME = "CROUTER_MODULE_NAME"; // 从Gradle中传入options参数的key

    public static final String PREFIX_OF_LOGGER = PROJECT + "::compiler"; // log前缀

}
