--- INITIALIZING THE CONTEXT ---
AnnotationConfigApplicationContext.prepareRefresh - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1ab3a8c8
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'

--- IDENTIFYING THE BEANS (LOADING AND INSTANTIATING THE BEAN-DETAILS)---
ClassPathBeanDefinitionScanner.scanCandidateComponents - Identified candidate component class: file [CustomBean.class]
ClassPathBeanDefinitionScanner.scanCandidateComponents - Identified candidate component class: file [CustomFactoryPostProcessor.class]
ClassPathBeanDefinitionScanner.scanCandidateComponents - Identified candidate component class: file [CustomPostProcessor.class]

--- INITIALIZING THE BEANS ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
        --- FOUND BEAN_FACTORY_POST_PROCESSOR ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'customFactoryPostProcessor'
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'

--- INVOKE THE postProcessBeanFactory() ---
CustomPostProcessor.postProcessBeanFactory - [POST-PROCESS BEAN FACTORY] called!

--- INITIALIZING THE BEANS ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
        --- FOUND BEAN_POST_PROCESSOR IMPLEMENTATION ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'customPostProcessor'

--- INITIALIZING THE BEANS ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'applicationConfig'
        --- INVOKE THE postProcessBeforeInitialization() ---
CustomPostProcessor.postProcessBeforeInitialization - Do some stuff [BEFORE] with bean: applicationConfig
        --- INVOKE THE postProcessAfterInitialization() ---
CustomPostProcessor.postProcessAfterInitialization - Do some stuff [AFTER] with bean: applicationConfig

--- INITIALIZING THE BEANS ---
DefaultListableBeanFactory.getSingleton - Creating shared instance of singleton bean 'customBean'
         --- INVOKE THE postProcessBeforeInitialization() ---
CustomPostProcessor.postProcessBeforeInitialization - Do some stuff [BEFORE] with bean: customBean
         --- FOUND INITIALIZING_BEAN IMPLEMENTATION ---
                 --- INVOKE THE initMethod() ---
CustomBean.afterPropertiesSet - [INIT] METHOD IS CALLED RIGHT NOW!
        --- INVOKE THE postProcessAfterInitialization() ---
CustomPostProcessor.postProcessAfterInitialization - Do some stuff [AFTER] with bean: customBean

--- THE SHUTDOWN HOOK WAS REGISTERED ---
AnnotationConfigApplicationContext.doClose - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@1ab3a8c8, started on Mon Jul 08 23:22:20 EEST 2019
                --- INVOKE THE destroyMethod() ---
CustomBean.destroy - [DESTROY] METHOD IS CALLED RIGHT NOW!