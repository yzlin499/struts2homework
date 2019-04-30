package top.yzlin.homework.database;

import top.yzlin.homework.Context;
import top.yzlin.homework.ContextConfig;
import top.yzlin.homework.ioc.ComponentInit;

import javax.annotation.Resource;

public class RepositoryManagement implements ComponentInit {
    private Context context;
    private HibernateUtil hibernateUtil;

    @Resource
    public void setContext(Context context) {
        this.context = context;
    }

    @Resource
    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    @Override
    public void init() {
        for (Class aClass : ContextConfig.REPOSITORY) {
            context.addComponent(aClass,hibernateUtil.createDAO(aClass));
        }
    }
}
