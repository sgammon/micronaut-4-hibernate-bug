# Micronaut v4 Hibernate bug reproducer

This codebase reproduces a bug with Micronaut v4, KSP, and Hibernate. The app is set up with a single `@Entity`, as a
data class at [`com.example.model.ExampleModel`](./src/main/kotlin/com/example/model/ExampleModel.kt).

The [app configuration](./src/main/resources/application.yml) defines the package `com.example.model` for scanning with
Hibernate.

Micronaut is used at `4.0.0-M3` for plugin versions, `4.0.0-M2` as the main `io.micronaut.platform:micronaut-platform`
version, and `4.0.0-M4` for the latest libraries. You can see these overrides in the [Gradle build](./build.gradle.kts).
These versions are overridden because:

## Error stacktrace and diagnosis

Micronaut fails to start up with the following error:
```
Message: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
```

<details>
<summary>Click here to see stacktrace</summary>
<pre>
Message: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
Path Taken: SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder(SessionFactoryBuilder sessionFactoryBuilder) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([SessionFactoryBuilder sessionFactoryBuilder]) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([Metadata metadata],JpaConfiguration jpaConfiguration,String name)
        at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2337)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
        at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
        at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
        at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
        at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2934)
        at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2745)
        at io.micronaut.context.DefaultBeanContext.getBean(DefaultBeanContext.java:1698)
        at io.micronaut.context.AbstractBeanResolutionContext.getBean(AbstractBeanResolutionContext.java:89)
        at io.micronaut.context.AbstractInitializableBeanDefinition.resolveBean(AbstractInitializableBeanDefinition.java:2303)
        at io.micronaut.context.AbstractInitializableBeanDefinition.getBeanForConstructorArgument(AbstractInitializableBeanDefinition.java:1465)
        at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildHibernateSessionFactoryBuilder3$Definition.doInstantiate(Unknown Source)
        at io.micronaut.context.AbstractInitializableBeanDefinition.instantiate(AbstractInitializableBeanDefinition.java:894)
        at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:168)
        at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
        at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
        at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
        at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
        at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2934)
        at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2745)
        at io.micronaut.context.DefaultBeanContext.getBean(DefaultBeanContext.java:1698)
        at io.micronaut.context.AbstractBeanResolutionContext.getBean(AbstractBeanResolutionContext.java:89)
        at io.micronaut.context.AbstractInitializableBeanDefinition.resolveBean(AbstractInitializableBeanDefinition.java:2303)
        at io.micronaut.context.AbstractInitializableBeanDefinition.getBeanForConstructorArgument(AbstractInitializableBeanDefinition.java:1465)
        at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildHibernateSessionFactoryBuilder4$Definition.instantiate(Unknown Source)
        at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:171)
        at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
        at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
        at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
        at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
        at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
        at io.micronaut.context.DefaultBeanContext.initializeEagerBean(DefaultBeanContext.java:2682)
        at io.micronaut.context.DefaultBeanContext.initializeContext(DefaultBeanContext.java:1978)
        ... 9 common frames omitted
Caused by: io.micronaut.context.exceptions.ConfigurationException: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
        at io.micronaut.configuration.hibernate.jpa.conf.AbstractHibernateFactory.buildMetadata(AbstractHibernateFactory.java:90)
        at io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory.buildMetadata(SessionFactoryPerDataSourceFactory.java:88)
        at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildMetadata2$Definition.doInstantiate(Unknown Source)
        at io.micronaut.context.AbstractInitializableBeanDefinition.instantiate(AbstractInitializableBeanDefinition.java:894)
        at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:168)
        at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
        ... 45 common frames omitted
</pre>
</details>

<details>
<summary>Click here to see server logs (`debug` level)</summary>
<pre>
[test-resources-service] 15:04:13.111 [ForkJoinPool.commonPool-worker-3] INFO  i.m.t.e.TestResourcesResolverLoader - Loaded 2 test resources resolvers: io.micronaut.testresources.mysql.MySQLTestResourceProvider, io.micronaut.testresources.testcontainers.GenericTestContainerProvider
[test-resources-service] 15:04:13.188 [main] INFO  i.m.t.server.TestResourcesService - A Micronaut Test Resources server is listening on port 63407, started in 125ms
[test-resources-service] 15:04:13.842 [default-nioEventLoopGroup-1-2] INFO  i.m.t.e.TestResourcesResolverLoader - Loaded 2 test resources resolvers: io.micronaut.testresources.mysql.MySQLTestResourceProvider, io.micronaut.testresources.testcontainers.GenericTestContainerProvider
[test-resources-service] 15:04:14.023 [default-nioEventLoopGroup-1-2] INFO  o.t.utility.ImageNameSubstitutor - Image name substitution will be performed by: DefaultImageNameSubstitutor (composite of 'ConfigurationFileImageNameSubstitutor' and 'PrefixingImageNameSubstitutor')
[test-resources-service] 15:04:14.024 [default-nioEventLoopGroup-1-2] INFO  i.m.t.testcontainers.TestContainers - Starting test container mysql
[test-resources-service] 15:04:14.109 [default-nioEventLoopGroup-1-2] INFO  o.t.d.DockerClientProviderStrategy - Loaded org.testcontainers.dockerclient.UnixSocketClientProviderStrategy from ~/.testcontainers.properties, will try it first
[test-resources-service] 15:04:14.160 [default-nioEventLoopGroup-1-2] INFO  o.t.d.DockerClientProviderStrategy - Found Docker environment with local Unix socket (unix:///var/run/docker.sock)
[test-resources-service] 15:04:14.160 [default-nioEventLoopGroup-1-2] INFO  o.testcontainers.DockerClientFactory - Docker host IP address is localhost
[test-resources-service] 15:04:14.171 [default-nioEventLoopGroup-1-2] INFO  o.testcontainers.DockerClientFactory - Connected to docker: 
  Server Version: 23.0.5
  API Version: 1.42
  Operating System: Docker Desktop
  Total Memory: 24011 MB
[test-resources-service] 15:04:14.206 [default-nioEventLoopGroup-1-2] INFO  tc.testcontainers/ryuk:0.4.0 - Creating container for image: testcontainers/ryuk:0.4.0
[test-resources-service] 15:04:14.538 [default-nioEventLoopGroup-1-2] INFO  o.t.utility.RegistryAuthLocator - Credential helper/store (docker-credential-desktop) does not have credentials for https://index.docker.io/v1/
[test-resources-service] 15:04:14.578 [default-nioEventLoopGroup-1-2] INFO  tc.testcontainers/ryuk:0.4.0 - Container testcontainers/ryuk:0.4.0 is starting: ff501f79416228fea55817ec4c33a70c2ede1962fbc4874ff592157899112055
[test-resources-service] 15:04:14.789 [default-nioEventLoopGroup-1-2] INFO  tc.testcontainers/ryuk:0.4.0 - Container testcontainers/ryuk:0.4.0 started in PT0.615235S
[test-resources-service] 15:04:14.791 [default-nioEventLoopGroup-1-2] INFO  o.t.utility.RyukResourceReaper - Ryuk started - will monitor and terminate Testcontainers containers on JVM exit
[test-resources-service] 15:04:14.791 [default-nioEventLoopGroup-1-2] INFO  o.testcontainers.DockerClientFactory - Checking the system...
[test-resources-service] 15:04:14.791 [default-nioEventLoopGroup-1-2] INFO  o.testcontainers.DockerClientFactory - ✔︎ Docker server version should be at least 1.6.0
[test-resources-service] 15:04:14.794 [default-nioEventLoopGroup-1-2] INFO  tc.mysql:latest - Creating container for image: mysql:latest
[test-resources-service] 15:04:14.864 [default-nioEventLoopGroup-1-2] INFO  tc.mysql:latest - Container mysql:latest is starting: 1a21d523a803483373842f0ffabc37de548e5a633fe299c495097c2b601ee3e7
[test-resources-service] 15:04:15.042 [default-nioEventLoopGroup-1-2] INFO  tc.mysql:latest - Waiting for database connection to become available at jdbc:mysql://localhost:63420/test using query 'SELECT 1'

> Task :run
 __  __ _                                  _   
|  \/  (_) ___ _ __ ___  _ __   __ _ _   _| |_
| |\/| | |/ __| '__/ _ \| '_ \ / _` | | | | __|
| |  | | | (__| | | (_) | | | | (_| | |_| | |_
|_|  |_|_|\___|_|  \___/|_| |_|\__,_|\__,_|\__|
Micronaut (v4.0.0-M3)

15:04:13.643 [main] DEBUG i.m.context.env.DefaultEnvironment - Environment deduction is using the default of: true
15:04:13.645 [main] DEBUG i.m.context.env.DefaultEnvironment - Starting environment io.micronaut.context.DefaultApplicationContext$RuntimeConfiguredEnvironment@609e8838 for active names []
15:04:13.647 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.testresources.client.TestResourcesClientPropertySourceLoader@792b749c
15:04:13.648 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.context.env.hocon.HoconPropertySourceLoader@302c971f
15:04:13.648 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class com.typesafe.config.Config
15:04:13.648 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class com.typesafe.config.Config
15:04:13.648 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.env.hocon.HoconPropertySourceLoaderImpl
15:04:13.648 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.env.hocon.HoconPropertySourceLoaderImpl
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.jackson.core.env.CloudFoundryVcapApplicationPropertySourceLoader@304bb45b
15:04:13.649 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - No PropertySource found for file name: application.VCAP_APPLICATION
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.jackson.core.env.JsonPropertySourceLoader@723ca036
15:04:13.649 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - No PropertySource found for file name: application.json
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.jackson.core.env.EnvJsonPropertySourceLoader@25be7b63
15:04:13.649 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - No PropertySource found for file name: application.json
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.jackson.core.env.CloudFoundryVcapServicesPropertySourceLoader@28dcca0c
15:04:13.649 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - No PropertySource found for file name: application.VCAP_SERVICES
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.context.env.PropertiesPropertySourceLoader@45d84a20
15:04:13.649 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - No PropertySource found for file name: application.properties
15:04:13.649 [main] DEBUG i.m.context.env.DefaultEnvironment - Reading property sources from loader: io.micronaut.context.env.yaml.YamlPropertySourceLoader@52f27fbd
15:04:13.650 [main] DEBUG i.m.c.e.AbstractPropertySourceLoader - Found PropertySource for file name: application.yml
15:04:13.664 [main] DEBUG i.m.context.env.DefaultEnvironment - Processing property source: application
15:04:13.664 [main] DEBUG i.m.context.env.DefaultEnvironment - Processing property source: env
15:04:13.666 [main] DEBUG i.m.context.env.DefaultEnvironment - Processing property source: system
15:04:13.666 [main] DEBUG i.m.context.env.DefaultEnvironment - Processing property source: test resources
15:04:13.737 [main] DEBUG i.m.context.env.DefaultEnvironment - Environment deduction is using the default of: true
15:04:13.738 [main] DEBUG i.m.context.env.DefaultEnvironment - Starting environment io.micronaut.serde.ObjectMappers$ObjectMapperContext$1@517566b for active names []
15:04:13.742 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DefaultEnvironment
15:04:13.742 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: DefaultEnvironment
15:04:13.744 [main] DEBUG i.m.context.DefaultBeanContext - Starting BeanContext
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanCreatedEventListener
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: BeanCreatedEventListener
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanInitializedEventListener
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: BeanInitializedEventListener
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.core.convert.TypeConverter
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TypeConverter
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: TypeConverter
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.core.convert.TypeConverterRegistrar
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TypeConverterRegistrar
15:04:13.757 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: TypeConverterRegistrar
15:04:13.758 [main] DEBUG i.m.context.DefaultBeanContext - Loaded active configurations: io.micronaut.jackson,io.micronaut.configuration.jdbc.hikari,io.micronaut.configuration.hibernate.jpa
15:04:13.758 [main] DEBUG i.m.context.DefaultBeanContext - BeanContext Started.
15:04:13.758 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ObjectMapper
15:04:13.760 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.jackson.JacksonJsonMapper null Definition: io.micronaut.serde.jackson.JacksonJsonMapper
15:04:13.760 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.jackson.JacksonJsonMapper] for type: objectMapper
15:04:13.771 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SerdeRegistry registry
15:04:13.772 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.DefaultSerdeRegistry null Definition: io.micronaut.serde.support.DefaultSerdeRegistry
15:04:13.772 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.DefaultSerdeRegistry] for type: registry
15:04:13.775 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ObjectSerializer objectSerializer
15:04:13.775 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.ObjectSerializer null Definition: io.micronaut.serde.support.serializers.ObjectSerializer
15:04:13.775 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.serializers.ObjectSerializer] for type: objectSerializer
15:04:13.775 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SerdeIntrospections introspections
15:04:13.776 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.DefaultSerdeIntrospections null Definition: io.micronaut.serde.support.DefaultSerdeIntrospections
15:04:13.776 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.DefaultSerdeIntrospections] for type: introspections
15:04:13.776 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SerdeConfiguration configuration
15:04:13.777 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.config.SerdeConfiguration$Intercepted null Definition: io.micronaut.serde.config.SerdeConfiguration$Intercepted
15:04:13.777 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.config.SerdeConfiguration$Intercepted] for type: configuration
15:04:13.780 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) io.micronaut.aop.Interceptor
15:04:13.780 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Interceptor T
15:04:13.780 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice null Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice
15:04:13.781 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.781 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.781 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Environment environment
15:04:13.781 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.context.env.DefaultEnvironment @Primary io.micronaut.context.DefaultRuntimeBeanDefinition@4a11eb84
15:04:13.781 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.context.DefaultRuntimeBeanDefinition@4a11eb84] for type: environment
15:04:13.782 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.ObjectMappers$ObjectMapperContext$1@517566b] from definition [io.micronaut.context.DefaultRuntimeBeanDefinition@4a11eb84] with qualifier [@Primary]
15:04:13.782 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@7fe7c640] from definition [Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice] with qualifier [null]
15:04:13.782 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@7fe7c640 for candidate: Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice with qualifier: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice)
15:04:13.782 [main] DEBUG i.m.context.DefaultBeanContext - Found 1 bean registrations for type [@InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) T]
15:04:13.782 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice null
15:04:13.783 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: InterceptorRegistry
15:04:13.783 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.aop.InterceptorRegistry null io.micronaut.aop.internal.InterceptorRegistryBean@784b990c
15:04:13.783 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.aop.internal.InterceptorRegistryBean@784b990c] for type: interceptorRegistry
15:04:13.783 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.aop.chain.DefaultInterceptorRegistry@33aeca0b] from definition [io.micronaut.aop.internal.InterceptorRegistryBean@784b990c] with qualifier [null]
15:04:13.784 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.config.SerdeConfiguration$Intercepted@463b4ac8] from definition [Definition: io.micronaut.serde.config.SerdeConfiguration$Intercepted] with qualifier [null]
15:04:13.785 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.DefaultSerdeIntrospections@a4ca3f6] from definition [Definition: io.micronaut.serde.support.DefaultSerdeIntrospections] with qualifier [null]
15:04:13.785 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SerializationConfiguration configuration
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.config.SerializationConfiguration$Intercepted null Definition: io.micronaut.serde.config.SerializationConfiguration$Intercepted
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.config.SerializationConfiguration$Intercepted] for type: configuration
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) io.micronaut.aop.Interceptor
15:04:13.786 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.786 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.786 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@3543df7d] from definition [Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice] with qualifier [null]
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@3543df7d for candidate: Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice with qualifier: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice)
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext - Found 1 bean registrations for type [@InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) T]
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice null
15:04:13.786 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.config.SerializationConfiguration$Intercepted@7c541c15] from definition [Definition: io.micronaut.serde.config.SerializationConfiguration$Intercepted] with qualifier [null]
15:04:13.786 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serializers.ObjectSerializer@3542162a] from definition [Definition: io.micronaut.serde.support.serializers.ObjectSerializer] with qualifier [null]
15:04:13.786 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ObjectDeserializer objectDeserializer
15:04:13.787 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.deserializers.ObjectDeserializer null Definition: io.micronaut.serde.support.deserializers.ObjectDeserializer
15:04:13.787 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.deserializers.ObjectDeserializer] for type: objectDeserializer
15:04:13.787 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DeserializationConfiguration deserializationConfiguration
15:04:13.787 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.config.DeserializationConfiguration$Intercepted null Definition: io.micronaut.serde.config.DeserializationConfiguration$Intercepted
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.config.DeserializationConfiguration$Intercepted] for type: deserializationConfiguration
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) io.micronaut.aop.Interceptor
15:04:13.788 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.788 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.788 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@75b25825] from definition [Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice] with qualifier [null]
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice@75b25825 for candidate: Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice with qualifier: @InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice)
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext - Found 1 bean registrations for type [@InterceptorBinding(io.micronaut.runtime.context.env.ConfigurationAdvice) T]
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice null
15:04:13.788 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.config.DeserializationConfiguration$Intercepted@18025ced] from definition [Definition: io.micronaut.serde.config.DeserializationConfiguration$Intercepted] with qualifier [null]
15:04:13.788 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.deserializers.ObjectDeserializer@13cf7d52] from definition [Definition: io.micronaut.serde.support.deserializers.ObjectDeserializer] with qualifier [null]
15:04:13.788 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Serde objectArraySerde
15:04:13.790 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.790 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.serdes.CoreSerdes] for type: objectArraySerde
15:04:13.790 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: CoreSerdes
15:04:13.790 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.CoreSerdes null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.790 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.serdes.CoreSerdes] for type: coreSerdes
15:04:13.791 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serdes.CoreSerdes@665df3c6] from definition [Definition: io.micronaut.serde.support.serdes.CoreSerdes] with qualifier [null]
15:04:13.791 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serdes.ObjectArraySerde@36f48b4] from definition [Definition: io.micronaut.serde.support.serdes.CoreSerdes] with qualifier [null]
15:04:13.791 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Serializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.InstantSerde null Definition: io.micronaut.serde.support.serdes.InstantSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.EnumSerde null Definition: io.micronaut.serde.support.serdes.EnumSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.SqlTimestampSerde @Secondary Definition: io.micronaut.serde.support.serdes.SqlTimestampSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.OptionalSerializer null Definition: io.micronaut.serde.support.serializers.OptionalSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.ZonedDateTimeSerde null Definition: io.micronaut.serde.support.serdes.ZonedDateTimeSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.OffsetDateTimeSerde null Definition: io.micronaut.serde.support.serdes.OffsetDateTimeSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serializer null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.IterableSerializer null Definition: io.micronaut.serde.support.serializers.IterableSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.YearSerde null Definition: io.micronaut.serde.support.serdes.YearSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalDateSerde null Definition: io.micronaut.serde.support.serdes.LocalDateSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serializer null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.StreamSerializer null Definition: io.micronaut.serde.support.serializers.StreamSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.OptionalValuesSerializer null Definition: io.micronaut.serde.support.serializers.OptionalValuesSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalTimeSerde null Definition: io.micronaut.serde.support.serdes.LocalTimeSerde
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.OptionalMultiValuesSerializer null Definition: io.micronaut.serde.support.serializers.OptionalMultiValuesSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.ObjectSerializer null Definition: io.micronaut.serde.support.serializers.ObjectSerializer
15:04:13.792 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.HealthStatusSerde null Definition: io.micronaut.serde.support.serdes.HealthStatusSerde
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.DateSerde null Definition: io.micronaut.serde.support.serdes.DateSerde
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serializer null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalDateTimeSerde null Definition: io.micronaut.serde.support.serdes.LocalDateTimeSerde
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.SqlDateSerde @Secondary Definition: io.micronaut.serde.support.serdes.SqlDateSerde
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serializer null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serializer null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.793 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Deserializer
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.InstantSerde null Definition: io.micronaut.serde.support.serdes.InstantSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.EnumSerde null Definition: io.micronaut.serde.support.serdes.EnumSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.SqlTimestampSerde @Secondary Definition: io.micronaut.serde.support.serdes.SqlTimestampSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.ZonedDateTimeSerde null Definition: io.micronaut.serde.support.serdes.ZonedDateTimeSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.OffsetDateTimeSerde null Definition: io.micronaut.serde.support.serdes.OffsetDateTimeSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.YearSerde null Definition: io.micronaut.serde.support.serdes.YearSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalDateSerde null Definition: io.micronaut.serde.support.serdes.LocalDateSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.deserializers.ObjectDeserializer null Definition: io.micronaut.serde.support.deserializers.ObjectDeserializer
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalTimeSerde null Definition: io.micronaut.serde.support.serdes.LocalTimeSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.HealthStatusSerde null Definition: io.micronaut.serde.support.serdes.HealthStatusSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.DateSerde null Definition: io.micronaut.serde.support.serdes.DateSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.LocalDateTimeSerde null Definition: io.micronaut.serde.support.serdes.LocalDateTimeSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.EnumSetDeserializer null Definition: io.micronaut.serde.support.serdes.EnumSetDeserializer
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serdes.SqlDateSerde @Secondary Definition: io.micronaut.serde.support.serdes.SqlDateSerde
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.794 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Serde null Definition: io.micronaut.serde.support.serdes.CoreSerdes
15:04:13.795 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.DefaultSerdeRegistry@58c34bb3] from definition [Definition: io.micronaut.serde.support.DefaultSerdeRegistry] with qualifier [null]
15:04:13.796 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.jackson.JacksonJsonMapper@183e8023] from definition [Definition: io.micronaut.serde.jackson.JacksonJsonMapper] with qualifier [null]
15:04:13.857 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.serde.Deserializer<java.util.List<java.lang.String>>
15:04:13.857 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Deserializer
15:04:13.857 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.857 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.858 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: CoreCollectionsDeserializers
15:04:13.858 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.858 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers] for type: coreCollectionsDeserializers
15:04:13.858 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers@56f6d40b] from definition [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers] with qualifier [null]
15:04:13.858 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers$1@7e3060d8] from definition [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers] with qualifier [null]
15:04:13.858 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers$1@7e3060d8 for candidate: Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers with qualifier: null
15:04:13.858 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers$3@62fad19] from definition [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers] with qualifier [null]
15:04:13.858 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers$3@62fad19 for candidate: Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers with qualifier: null
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Deserializer
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.serde.Deserializer null Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext - Searching for @Primary for type [deserializer] from candidates: [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers, Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers]
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext - Picked bean Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers with the highest precedence for type Deserializer and qualifier null
15:04:13.859 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.deserializers.collect.CoreCollectionsDeserializers] for type: deserializer
15:04:13.862 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: CoreSerializers
15:04:13.862 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.support.serializers.CoreSerializers null Definition: io.micronaut.serde.support.serializers.CoreSerializers
15:04:13.862 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.serde.support.serializers.CoreSerializers] for type: coreSerializers
15:04:13.862 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serializers.CoreSerializers@2cac4385] from definition [Definition: io.micronaut.serde.support.serializers.CoreSerializers] with qualifier [null]
15:04:13.862 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serializers.CustomizedMapSerializer@34a1d21f] from definition [Definition: io.micronaut.serde.support.serializers.CoreSerializers] with qualifier [null]
15:04:13.863 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serializers.IterableSerializer@34625ccd] from definition [Definition: io.micronaut.serde.support.serializers.IterableSerializer] with qualifier [null]
15:04:13.863 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.serde.support.serializers.CoreSerializers$2@10fde30a] from definition [Definition: io.micronaut.serde.support.serializers.CoreSerializers] with qualifier [null]
15:04:13.872 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DefaultEnvironment
15:04:13.872 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: DefaultEnvironment
15:04:13.872 [main] DEBUG i.m.context.DefaultBeanContext - Starting BeanContext
15:04:13.874 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanCreatedEventListener
15:04:13.875 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.configuration.hibernate.jpa.metrics.$HibernateMetricsBinder$Definition] will not be loaded due to failing conditions:
15:04:13.875 [main] DEBUG i.m.context.condition.Condition - * Class [org.hibernate.stat.HibernateMetrics] is not present
15:04:13.876 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSourceTransactionManagerFactory
15:04:13.876 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.jdbc.spring.$DataSourceTransactionManagerFactory$Definition] will not be loaded due to failing conditions:
15:04:13.876 [main] DEBUG i.m.context.condition.Condition - * Class [org.springframework.jdbc.datasource.DataSourceTransactionManager] is not present
15:04:13.876 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: DataSourceTransactionManagerFactory
15:04:13.876 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.jdbc.spring.$DataSourceTransactionManagerFactory$TransactionAwareDataSourceListener1$Definition] will not be loaded due to failing conditions:
15:04:13.876 [main] DEBUG i.m.context.condition.Condition - * Class [org.springframework.jdbc.datasource.DataSourceTransactionManager] is not present
15:04:13.877 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.version.$VersionAwareRouterListener$Definition] will not be loaded due to failing conditions:
15:04:13.877 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.router.versioning.enabled] with value [true] not present
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.hibernate.naming.PhysicalNamingStrategyConfiguration null Definition: io.micronaut.data.hibernate.naming.PhysicalNamingStrategyConfiguration
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.server.netty.binders.NettyBinderRegistrar null Definition: io.micronaut.http.server.netty.binders.NettyBinderRegistrar
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.scheduling.instrument.ExecutorServiceInstrumenter null Definition: io.micronaut.scheduling.instrument.ExecutorServiceInstrumenter
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.transaction.jdbc.TransactionAwareDataSource null Definition: io.micronaut.transaction.jdbc.TransactionAwareDataSource
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanInitializedEventListener
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: BeanInitializedEventListener
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.core.convert.TypeConverter
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TypeConverter
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.server.cors.CorsOriginConverter null Definition: io.micronaut.http.server.cors.CorsOriginConverter
15:04:13.877 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.cookie.SameSiteConverter null Definition: io.micronaut.http.cookie.SameSiteConverter
15:04:13.878 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.server.cors.CorsOriginConverter@3a320ade] from definition [Definition: io.micronaut.http.server.cors.CorsOriginConverter] with qualifier [null]
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.server.cors.CorsOriginConverter@3a320ade for candidate: Definition: io.micronaut.http.server.cors.CorsOriginConverter with qualifier: null
15:04:13.878 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.cookie.SameSiteConverter@7813cb11] from definition [Definition: io.micronaut.http.cookie.SameSiteConverter] with qualifier [null]
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.cookie.SameSiteConverter@7813cb11 for candidate: Definition: io.micronaut.http.cookie.SameSiteConverter with qualifier: null
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.core.convert.TypeConverterRegistrar
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TypeConverterRegistrar
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.kotlin.converters.$FlowReactorConverterRegistrar$Definition] will not be loaded due to failing conditions:
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - * Class [kotlinx.coroutines.flow.Flow] is not present
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.kotlin.converters.$FlowPublisherConverterRegistrar$Definition] will not be loaded due to failing conditions:
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - * Class [kotlinx.coroutines.flow.Flow] is not present
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.reactor.convert.$ReactorToRxJava2ConverterRegistrar$Definition] will not be loaded due to failing conditions:
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - * Class [io.reactivex.Maybe] is not present
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.reactor.convert.$ReactorToRxJava3ConverterRegistrar$Definition] will not be loaded due to failing conditions:
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - * Class [io.reactivex.rxjava3.core.Maybe] is not present
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.channel.converters.$KQueueChannelOptionFactory$Definition] will not be loaded due to failing conditions:
15:04:13.878 [main] DEBUG i.m.context.condition.Condition - * Class [io.netty.channel.kqueue.KQueue] is not present
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.server.netty.converters.NettyConverters null Definition: io.micronaut.http.server.netty.converters.NettyConverters
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.converters.HttpConverterRegistrar null Definition: io.micronaut.http.converters.HttpConverterRegistrar
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.jackson.databind.convert.JacksonConverterRegistrar null Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar
15:04:13.878 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.json.convert.JsonConverterRegistrar null Definition: io.micronaut.json.convert.JsonConverterRegistrar
15:04:13.879 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.879 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.879 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider decoderRegistryProvider
15:04:13.879 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.879 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: decoderRegistryProvider
15:04:13.879 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MediaTypeCodecRegistry T
15:04:13.879 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.http.codec.MediaTypeCodecRegistry null Definition: io.micronaut.runtime.http.codec.MediaTypeCodecRegistryFactory
15:04:13.879 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@7e242b4d] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider channelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: channelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ChannelOptionFactory T
15:04:13.880 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.channel.converters.$EpollChannelOptionFactory$Definition] will not be loaded due to failing conditions:
15:04:13.880 [main] DEBUG i.m.context.condition.Condition - * Class [io.netty.channel.epoll.Epoll] is not present
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: EpollChannelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: EpollChannelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: KQueueChannelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: KQueueChannelOptionFactory
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.http.netty.channel.converters.DefaultChannelOptionFactory null Definition: io.micronaut.http.netty.channel.converters.DefaultChannelOptionFactory
15:04:13.880 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@423e4cbb] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.880 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.server.netty.converters.NettyConverters@6e16b8b5] from definition [Definition: io.micronaut.http.server.netty.converters.NettyConverters] with qualifier [null]
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.server.netty.converters.NettyConverters@6e16b8b5 for candidate: Definition: io.micronaut.http.server.netty.converters.NettyConverters with qualifier: null
15:04:13.880 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.880 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Provider resourceResolver
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext -   interface jakarta.inject.Provider null io.micronaut.inject.provider.JakartaProviderBeanDefinition@6a8058be
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.JakartaProviderBeanDefinition@6a8058be] for type: resourceResolver
15:04:13.880 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ResourceResolver T
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.core.io.ResourceResolver null Definition: io.micronaut.http.resource.ResourceLoaderFactory
15:04:13.881 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.JakartaProviderBeanDefinition$$Lambda$586/0x000000700123b300@4ebea12c] from definition [io.micronaut.inject.provider.JakartaProviderBeanDefinition@6a8058be] with qualifier [null]
15:04:13.881 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.converters.HttpConverterRegistrar@2a1edad4] from definition [Definition: io.micronaut.http.converters.HttpConverterRegistrar] with qualifier [null]
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.converters.HttpConverterRegistrar@2a1edad4 for candidate: Definition: io.micronaut.http.converters.HttpConverterRegistrar with qualifier: null
15:04:13.881 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.881 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider objectMapper
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: objectMapper
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ObjectMapper T
15:04:13.881 [main] DEBUG i.m.context.DefaultBeanContext -   class com.fasterxml.jackson.databind.ObjectMapper @Primary and @Named('json') Definition: io.micronaut.jackson.ObjectMapperFactory
15:04:13.882 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@fd0e5b6] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.882 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.jackson.databind.convert.JacksonConverterRegistrar@4eed46ee] from definition [Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar] with qualifier [null]
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar@4eed46ee for candidate: Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar with qualifier: null
15:04:13.882 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.882 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider objectCodec
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: objectCodec
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: JsonMapper T
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.serde.jackson.JacksonJsonMapper null Definition: io.micronaut.serde.jackson.JacksonJsonMapper
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.jackson.databind.JacksonDatabindMapper null Definition: io.micronaut.jackson.databind.JacksonDatabindMapper
15:04:13.882 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@475835b1] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider beanPropertyBinder
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: beanPropertyBinder
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanPropertyBinder T
15:04:13.882 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.json.bind.JsonBeanPropertyBinder null Definition: io.micronaut.json.bind.JsonBeanPropertyBinder
15:04:13.882 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@716a7124] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.883 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.json.convert.JsonConverterRegistrar@77192705] from definition [Definition: io.micronaut.json.convert.JsonConverterRegistrar] with qualifier [null]
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.json.convert.JsonConverterRegistrar@77192705 for candidate: Definition: io.micronaut.json.convert.JsonConverterRegistrar with qualifier: null
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext - Found 4 bean registrations for type [typeConverterRegistrar]
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar null
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.http.server.netty.converters.NettyConverters null
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.json.convert.JsonConverterRegistrar null
15:04:13.883 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.http.converters.HttpConverterRegistrar null
15:04:13.886 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.886 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.886 [main] DEBUG i.m.context.condition.Condition - Bean [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerJpaConfigurationFactory] will not be loaded due to failing conditions:
15:04:13.886 [main] DEBUG i.m.context.condition.Condition - * Existing bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] of type [class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] registered in context
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.886 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.887 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.887 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.888 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.logging.$PropertiesLoggingLevelsConfigurer$Definition] will not be loaded due to failing conditions:
15:04:13.888 [main] DEBUG i.m.context.condition.Condition - * Required property [logger.levels] with value [null] not present
15:04:13.888 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.buffer.netty.DefaultByteBufAllocatorConfiguration@1849db1a] from definition [Definition: io.micronaut.buffer.netty.DefaultByteBufAllocatorConfiguration] with qualifier [null]
15:04:13.888 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryBuilder
15:04:13.888 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.888 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.888 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.888 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.888 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Metadata
15:04:13.889 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.889 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.889 [main] DEBUG i.m.context.condition.Condition - Bean [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerJpaConfigurationFactory] will not be loaded due to failing conditions:
15:04:13.889 [main] DEBUG i.m.context.condition.Condition - * Existing bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] of type [class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] registered in context
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.889 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MetadataSources
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.889 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.889 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ServiceRegistry
15:04:13.890 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.890 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.890 [main] DEBUG i.m.context.condition.Condition - Bean [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerJpaConfigurationFactory] will not be loaded due to failing conditions:
15:04:13.890 [main] DEBUG i.m.context.condition.Condition - * Existing bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] of type [class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] registered in context
15:04:13.890 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.890 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.890 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.890 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.890 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource
15:04:13.890 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.896 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.896 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.896 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.896 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.896 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.service.ServiceRegistry @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.896 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.896 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.897 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.897 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.897 [main] DEBUG i.m.context.condition.Condition - Bean [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerJpaConfigurationFactory] will not be loaded due to failing conditions:
15:04:13.897 [main] DEBUG i.m.context.condition.Condition - * Existing bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] of type [class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] registered in context
15:04:13.897 [main] DEBUG i.m.context.DefaultBeanContext -   class org.hibernate.boot.MetadataSources @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.897 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.897 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.897 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.boot.Metadata @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.897 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.897 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.898 [main] DEBUG i.m.context.condition.Condition - Bean [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerJpaConfigurationFactory] will not be loaded due to failing conditions:
15:04:13.898 [main] DEBUG i.m.context.condition.Condition - * Existing bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] of type [class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] registered in context
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.boot.SessionFactoryBuilder @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.898 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.898 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.898 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: sessionFactoryPerDataSourceFactory
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Environment environment
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.context.env.DefaultEnvironment @Primary io.micronaut.context.DefaultRuntimeBeanDefinition@362a019c
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.context.DefaultRuntimeBeanDefinition@362a019c] for type: environment
15:04:13.899 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.context.DefaultApplicationContext$RuntimeConfiguredEnvironment@609e8838] from definition [io.micronaut.context.DefaultRuntimeBeanDefinition@362a019c] with qualifier [@Primary]
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.SessionFactoryBuilderConfigurer
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryBuilderConfigurer E
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValidatorFactory
15:04:13.899 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValidatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: ValidatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.DefaultValidatorFactory null Definition: io.micronaut.validation.validator.DefaultValidatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer null Definition: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer
15:04:13.900 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.900 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValidatorFactory validatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.DefaultValidatorFactory null Definition: io.micronaut.validation.validator.DefaultValidatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.DefaultValidatorFactory] for type: validatorFactory
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Validator validator
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.DefaultValidator null Definition: io.micronaut.validation.validator.DefaultValidator
15:04:13.900 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.DefaultValidator] for type: validator
15:04:13.902 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValidatorConfiguration configuration
15:04:13.902 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.DefaultValidatorConfiguration null Definition: io.micronaut.validation.validator.DefaultValidatorConfiguration
15:04:13.902 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.DefaultValidatorConfiguration] for type: configuration
15:04:13.902 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ConstraintValidatorRegistry constraintValidatorRegistry
15:04:13.902 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.constraints.DefaultConstraintValidators null Definition: io.micronaut.validation.validator.constraints.DefaultConstraintValidators
15:04:13.903 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.constraints.DefaultConstraintValidators] for type: constraintValidatorRegistry
15:04:13.922 [main] DEBUG i.m.c.beans.DefaultBeanIntrospector - Found BeanIntrospection for type: class io.micronaut.validation.validator.constraints.InternalConstraintValidators,
15:04:13.931 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.constraints.DefaultConstraintValidators@9cd25ff] from definition [Definition: io.micronaut.validation.validator.constraints.DefaultConstraintValidators] with qualifier [null]
15:04:13.931 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValueExtractorRegistry valueExtractorRegistry
15:04:13.931 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.extractors.DefaultValueExtractors null Definition: io.micronaut.validation.validator.extractors.DefaultValueExtractors
15:04:13.931 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.extractors.DefaultValueExtractors] for type: valueExtractorRegistry
15:04:13.932 [main] DEBUG i.m.c.beans.DefaultBeanIntrospector - Found BeanIntrospection for type: class io.micronaut.validation.validator.extractors.InternalValueExtractors,
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ValueExtractor
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: ValueExtractor
15:04:13.933 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.extractors.DefaultValueExtractors@544630b7] from definition [Definition: io.micronaut.validation.validator.extractors.DefaultValueExtractors] with qualifier [null]
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ClockProvider clockProvider
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.DefaultClockProvider null Definition: io.micronaut.validation.validator.DefaultClockProvider
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.DefaultClockProvider] for type: clockProvider
15:04:13.933 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.DefaultClockProvider@3d6300e8] from definition [Definition: io.micronaut.validation.validator.DefaultClockProvider] with qualifier [null]
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TraversableResolver traversableResolver
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.resolver.CompositeTraversableResolver null Definition: io.micronaut.validation.validator.resolver.CompositeTraversableResolver
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver null Definition: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Searching for @Primary for type [traversableResolver] from candidates: [Definition: io.micronaut.validation.validator.resolver.CompositeTraversableResolver, Definition: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver]
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.resolver.CompositeTraversableResolver] for type: traversableResolver
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: jakarta.validation.TraversableResolver
15:04:13.933 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: TraversableResolver E
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.resolver.CompositeTraversableResolver null Definition: io.micronaut.validation.validator.resolver.CompositeTraversableResolver
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver null Definition: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver
15:04:13.934 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver@532721fd] from definition [Definition: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver] with qualifier [null]
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver@532721fd for candidate: Definition: io.micronaut.configuration.hibernate.jpa.validation.JPATraversableResolver with qualifier: null
15:04:13.934 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.resolver.CompositeTraversableResolver@410954b] from definition [Definition: io.micronaut.validation.validator.resolver.CompositeTraversableResolver] with qualifier [null]
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MessageInterpolator messageInterpolator
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.messages.DefaultMessageInterpolator null Definition: io.micronaut.validation.validator.messages.DefaultMessageInterpolator
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.validation.validator.messages.DefaultMessageInterpolator] for type: messageInterpolator
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MessageSource messageSource
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.messages.DefaultMessages null Definition: io.micronaut.validation.validator.messages.DefaultMessages
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.runtime.context.CompositeMessageSource null Definition: io.micronaut.runtime.context.CompositeMessageSource
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Searching for @Primary for type [messageSource] from candidates: [Definition: io.micronaut.validation.validator.messages.DefaultMessages, Definition: io.micronaut.runtime.context.CompositeMessageSource]
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.runtime.context.CompositeMessageSource] for type: messageSource
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.context.MessageSource
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MessageSource E
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.validation.validator.messages.DefaultMessages null Definition: io.micronaut.validation.validator.messages.DefaultMessages
15:04:13.934 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.runtime.context.CompositeMessageSource null Definition: io.micronaut.runtime.context.CompositeMessageSource
15:04:13.934 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.934 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.messages.DefaultMessages@407cf41] from definition [Definition: io.micronaut.validation.validator.messages.DefaultMessages] with qualifier [null]
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.validation.validator.messages.DefaultMessages@407cf41 for candidate: Definition: io.micronaut.validation.validator.messages.DefaultMessages with qualifier: null
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Found 1 bean registrations for type [E]
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.validation.validator.messages.DefaultMessages null
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.runtime.context.CompositeMessageSource@6815c5f2] from definition [Definition: io.micronaut.runtime.context.CompositeMessageSource] with qualifier [null]
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.messages.DefaultMessageInterpolator@46cc127b] from definition [Definition: io.micronaut.validation.validator.messages.DefaultMessageInterpolator] with qualifier [null]
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.DefaultValidatorConfiguration@3a4b0e5d] from definition [Definition: io.micronaut.validation.validator.DefaultValidatorConfiguration] with qualifier [null]
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.DefaultValidator@10b892d5] from definition [Definition: io.micronaut.validation.validator.DefaultValidator] with qualifier [null]
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.validation.validator.DefaultValidatorFactory@3d3f761a] from definition [Definition: io.micronaut.validation.validator.DefaultValidatorFactory] with qualifier [null]
15:04:13.935 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer@3546d80f] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer] with qualifier [null]
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer@3546d80f for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer with qualifier: null
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Found 1 bean registrations for type [E]
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.sessionfactory.configure.internal.ValidatorFactoryConfigurer null
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: StandardServiceRegistryBuilderCreator serviceRegistryBuilderSupplier
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: StandardServiceRegistryBuilderCreator
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: StandardServiceRegistryBuilderCreator
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.supplier.internal.DefaultStandardServiceRegistryBuilderCreatorCreator null Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.supplier.internal.DefaultStandardServiceRegistryBuilderCreatorCreator
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.supplier.internal.DefaultStandardServiceRegistryBuilderCreatorCreator] for type: serviceRegistryBuilderSupplier
15:04:13.935 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.935 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.935 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Integrator integrator
15:04:13.936 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.hibernate.event.EventIntegrator null Definition: io.micronaut.data.hibernate.event.EventIntegrator
15:04:13.936 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.hibernate.event.EventIntegrator] for type: integrator
15:04:13.936 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: RuntimeEntityRegistry entityRegistry
15:04:13.937 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.support.DefaultRuntimeEntityRegistry null Definition: io.micronaut.data.runtime.support.DefaultRuntimeEntityRegistry
15:04:13.937 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.support.DefaultRuntimeEntityRegistry] for type: entityRegistry
15:04:13.938 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: EntityEventRegistry eventRegistry
15:04:13.938 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.EntityEventRegistry null Definition: io.micronaut.data.runtime.event.EntityEventRegistry
15:04:13.938 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.event.EntityEventRegistry] for type: eventRegistry
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: EntityEventListener
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.AnnotatedMethodInvokingEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.AnnotatedMethodInvokingEntityEventListener
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.UUIDGeneratingEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.UUIDGeneratingEntityEventListener
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener
15:04:13.939 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.EntityEventRegistry null Definition: io.micronaut.data.runtime.event.EntityEventRegistry
15:04:13.940 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for qualifier: @EntityEventMapping
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.data.runtime.support.convert.convert.jpa.$JxAttributeConverterProvider$Definition] will not be loaded due to failing conditions:
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - * Class [javax.persistence.AttributeConverter] is not present
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.data.runtime.multitenancy.internal.$DefaultTenantResolver$Definition] will not be loaded due to failing conditions:
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - * Class [io.micronaut.multitenancy.tenantresolver.TenantResolver] is not present
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.jackson.kotlin.$KotlinModuleFactory$KotlinModuleFactory0$Definition] will not be loaded due to failing conditions:
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - * Required property [jackson.module-scan] with value [false] not present
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.configuration.hibernate.jpa.conf.settings.internal.$JCacheManagerSettingSupplier$Definition] will not be loaded due to failing conditions:
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - * Class [org.hibernate.cache.jcache.ConfigSettings] is not present
15:04:13.940 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.springframework.jdbc.datasource.DataSourceTransactionManager
15:04:13.940 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.springframework.jdbc.datasource.DataSourceTransactionManager is not present
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.transaction.test.$SpockMethodTransactionDefinitionProvider$Definition] will not be loaded due to failing conditions:
15:04:13.940 [main] DEBUG i.m.context.condition.Condition - * Class [org.spockframework.runtime.model.FeatureMetadata] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.transaction.interceptor.$ReactorCoroutineTxHelper$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [kotlinx.coroutines.reactor.ReactorContext] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.jdbc.spring.$SpringDataSourceResolver$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [org.springframework.jdbc.datasource.DelegatingDataSource] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.jdbc.spring.$DataSourceTransactionManagerFactory$TransactionAwareDataSourceListenerUnwrapper2$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [org.springframework.jdbc.datasource.DataSourceTransactionManager] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.scheduling.io.watch.osx.$MacOsWatchServiceFactory$MacWatchService0$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.io.watch.paths] with value [null] not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.netty.jackson.$JsonViewServerFilter$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Required property [jackson.json-view.enabled] with value [null] not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.netty.websocket.$WebSocketUpgradeHandlerFactory$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [io.micronaut.websocket.context.WebSocketBeanRegistry] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.netty.jackson.$JsonViewMediaTypeCodecFactory$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Required property [jackson.json-view.enabled] with value [null] not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.websocket.$WebSocketMessageEncoder$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [io.micronaut.websocket.exceptions.WebSocketSessionException] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.channel.$KQueueEventLoopGroupFactory$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [io.netty.channel.kqueue.KQueue] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.channel.$IoUringEventLoopGroupFactory$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [io.netty.incubator.channel.uring.IOUring] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.netty.channel.$EpollEventLoopGroupFactory$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Class [io.netty.channel.epoll.Epoll] is not present
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.util.locale.$HttpFixedLocaleResolver$Definition] will not be loaded due to failing conditions:
15:04:13.941 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.locale-resolution.fixed] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.util.locale.$CookieLocaleResolver$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.locale-resolution.cookie-name] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.server.websocket.$ServerWebSocketProcessor$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Class [io.micronaut.websocket.annotation.ServerWebSocket] is not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.version.$ConfigurationDefaultVersionProvider$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.router.versioning.default-version] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.version.resolution.$HeaderVersionResolverConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.router.versioning.header.enabled] with value [true] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.version.$RoutesVersioningConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.router.versioning.enabled] with value [true] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.naming.$ConfigurableUriNamingStrategy$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.context-path] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.web.router.version.resolution.$ParameterVersionResolverConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.router.versioning.parameter.enabled] with value [true] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ServerSslConfiguration$DefaultKeyStoreConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.ssl.key-store] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ServerSslConfiguration$DefaultKeyConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.ssl.key] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ClientSslConfiguration$DefaultTrustStoreConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.http.client.ssl.trust-store] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ServerSslConfiguration$DefaultTrustStoreConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.server.ssl.trust-store] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ClientSslConfiguration$DefaultKeyConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.http.client.ssl.key] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.http.ssl.$ClientSslConfiguration$DefaultKeyStoreConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.http.client.ssl.key-store] with value [null] not present
15:04:13.942 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.discovery.cloud.digitalocean.$DigitalOceanMetadataResolver$Definition] will not be loaded due to failing conditions:
15:04:13.943 [main] DEBUG i.m.context.condition.Condition - * None of the required environments [digitalocean] are active: []
15:04:13.943 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.discovery.cloud.digitalocean.$DigitalOceanMetadataConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.943 [main] DEBUG i.m.context.condition.Condition - * None of the required environments [digitalocean] are active: []
15:04:13.943 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.methvin.watchservice.MacOSXListeningWatchService
15:04:13.943 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.methvin.watchservice.MacOSXListeningWatchService
15:04:13.943 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.scheduling.io.watch.$WatchServiceFactory$Definition] will not be loaded due to failing conditions:
15:04:13.943 [main] DEBUG i.m.context.condition.Condition - * Class [io.methvin.watchservice.MacOSXListeningWatchService] is not absent
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.runtime.server.watch.event.$FileWatchRestartListener$Definition] will not be loaded due to failing conditions:
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - * Property [micronaut.io.watch.restart] with value [false] does not equal required value: true
15:04:13.944 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.methvin.watchservice.MacOSXListeningWatchService
15:04:13.944 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.methvin.watchservice.MacOSXListeningWatchService
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.scheduling.io.watch.$WatchServiceFactory$WatchService0$Definition] will not be loaded due to failing conditions:
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - * Class [io.methvin.watchservice.MacOSXListeningWatchService] is not absent
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.scheduling.io.watch.$DefaultWatchThread$Definition] will not be loaded due to failing conditions:
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.io.watch.paths] with value [null] not present
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.scheduling.io.watch.$FileWatchConfiguration$Definition] will not be loaded due to failing conditions:
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - * Required property [micronaut.io.watch.paths] with value [null] not present
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - Bean [io.micronaut.logging.impl.$Log4jLoggingSystem$Definition] will not be loaded due to failing conditions:
15:04:13.944 [main] DEBUG i.m.context.condition.Condition - * Class [org.apache.logging.log4j.core.config.Configurator] is not present
15:04:13.944 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.event.EntityEventRegistry@324c64cd] from definition [Definition: io.micronaut.data.runtime.event.EntityEventRegistry] with qualifier [null]
15:04:13.944 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.data.model.runtime.PropertyAutoPopulator<java.lang.annotation.Annotation>
15:04:13.944 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: PropertyAutoPopulator T
15:04:13.944 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener
15:04:13.944 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener null Definition: io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener
15:04:13.944 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DateTimeProvider dateTimeProvider
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.date.CurrentDateTimeProvider null Definition: io.micronaut.data.runtime.date.CurrentDateTimeProvider
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.date.CurrentDateTimeProvider] for type: dateTimeProvider
15:04:13.945 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.date.CurrentDateTimeProvider@70d2e40b] from definition [Definition: io.micronaut.data.runtime.date.CurrentDateTimeProvider] with qualifier [null]
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataConversionService conversionService
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.convert.DataConversionServiceImpl null Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory] for type: conversionService
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataConversionServiceFactory
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.convert.DataConversionServiceFactory null Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory
15:04:13.945 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory] for type: dataConversionServiceFactory
15:04:13.946 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.convert.DataConversionServiceFactory@173b9122] from definition [Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory] with qualifier [null]
15:04:13.950 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.data.runtime.convert.DataTypeConverter
15:04:13.950 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataTypeConverter
15:04:13.950 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: DataTypeConverter
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.core.convert.TypeConverterRegistrar
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@5305c37d] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@51a06cbe] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.server.netty.converters.NettyConverters@3dddbe65] from definition [Definition: io.micronaut.http.server.netty.converters.NettyConverters] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.server.netty.converters.NettyConverters@3dddbe65 for candidate: Definition: io.micronaut.http.server.netty.converters.NettyConverters with qualifier: null
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.JakartaProviderBeanDefinition$$Lambda$586/0x000000700123b300@49a64d82] from definition [io.micronaut.inject.provider.JakartaProviderBeanDefinition@6a8058be] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.http.converters.HttpConverterRegistrar@344561e0] from definition [Definition: io.micronaut.http.converters.HttpConverterRegistrar] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.http.converters.HttpConverterRegistrar@344561e0 for candidate: Definition: io.micronaut.http.converters.HttpConverterRegistrar with qualifier: null
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@66d23e4a] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.jackson.databind.convert.JacksonConverterRegistrar@36ac8a63] from definition [Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar@36ac8a63 for candidate: Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar with qualifier: null
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@4d9d1b69] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@52c8295b] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.json.convert.JsonConverterRegistrar@251f7d26] from definition [Definition: io.micronaut.json.convert.JsonConverterRegistrar] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.json.convert.JsonConverterRegistrar@251f7d26 for candidate: Definition: io.micronaut.json.convert.JsonConverterRegistrar with qualifier: null
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found 4 bean registrations for type [typeConverterRegistrar]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.jackson.databind.convert.JacksonConverterRegistrar null
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.http.server.netty.converters.NettyConverters null
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.json.convert.JsonConverterRegistrar null
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.http.converters.HttpConverterRegistrar null
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.convert.DataConversionServiceImpl@77b21474] from definition [Definition: io.micronaut.data.runtime.convert.DataConversionServiceFactory] with qualifier [null]
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener@52d10fb8] from definition [Definition: io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener@52d10fb8 for candidate: Definition: io.micronaut.data.runtime.event.listeners.AutoTimestampEntityEventListener with qualifier: null
15:04:13.951 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener@1fe8d51b] from definition [Definition: io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener] with qualifier [null]
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener@1fe8d51b for candidate: Definition: io.micronaut.data.runtime.event.listeners.VersionGeneratingEntityEventListener with qualifier: null
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: AttributeConverterRegistry attributeConverterRegistry
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.support.convert.DefaultAttributeConverterRegistry null Definition: io.micronaut.data.runtime.support.convert.DefaultAttributeConverterRegistry
15:04:13.951 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.runtime.support.convert.DefaultAttributeConverterRegistry] for type: attributeConverterRegistry
15:04:13.952 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.data.runtime.support.convert.AttributeConverterProvider
15:04:13.952 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: AttributeConverterProvider E
15:04:13.952 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider null Definition: io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider
15:04:13.952 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider@ecf9fb3] from definition [Definition: io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider] with qualifier [null]
15:04:13.952 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider@ecf9fb3 for candidate: Definition: io.micronaut.data.runtime.support.convert.convert.jpa.JakartaAttributeConverterProvider with qualifier: null
15:04:13.952 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.support.convert.DefaultAttributeConverterRegistry@2d35442b] from definition [Definition: io.micronaut.data.runtime.support.convert.DefaultAttributeConverterRegistry] with qualifier [null]
15:04:13.952 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.runtime.support.DefaultRuntimeEntityRegistry@37d3d232] from definition [Definition: io.micronaut.data.runtime.support.DefaultRuntimeEntityRegistry] with qualifier [null]
15:04:13.952 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.hibernate.event.EventIntegrator@30c0ccff] from definition [Definition: io.micronaut.data.hibernate.event.EventIntegrator] with qualifier [null]
15:04:13.956 [main] DEBUG org.jboss.logging - Logging Provider: org.jboss.logging.Slf4jLoggerProvider
15:04:13.961 [main] DEBUG o.h.i.internal.IntegratorServiceImpl - Adding Integrator [org.hibernate.boot.beanvalidation.BeanValidationIntegrator].
15:04:13.963 [main] DEBUG o.h.i.internal.IntegratorServiceImpl - Adding Integrator [org.hibernate.cache.internal.CollectionCacheInvalidator].
15:04:13.963 [main] DEBUG o.h.i.internal.IntegratorServiceImpl - Adding Integrator [io.micronaut.data.hibernate.event.EventIntegrator].
15:04:13.972 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.supplier.internal.DefaultStandardServiceRegistryBuilderCreatorCreator@1ad777f] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.supplier.internal.DefaultStandardServiceRegistryBuilderCreatorCreator] with qualifier [null]
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.StandardServiceRegistryBuilderConfigurer
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: StandardServiceRegistryBuilderConfigurer E
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer null Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer null Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer
15:04:13.972 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.972 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider bytecodeProviderBeanProvider
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: bytecodeProviderBeanProvider
15:04:13.972 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BytecodeProvider T
15:04:13.973 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.proxy.IntrospectedHibernateBytecodeProvider null Definition: io.micronaut.configuration.hibernate.jpa.proxy.IntrospectedHibernateBytecodeProvider
15:04:13.973 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@315ba14a] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.973 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer@17f9344b] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer] with qualifier [null]
15:04:13.973 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer@17f9344b for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer with qualifier: null
15:04:13.973 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.973 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.973 [main] DEBUG i.m.context.DefaultBeanContext - Resolving beans for type: io.micronaut.configuration.hibernate.jpa.conf.settings.SettingsSupplier
15:04:13.973 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SettingsSupplier E
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier null Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier null Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier null Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier null Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier@30457e14] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier] with qualifier [null]
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier@30457e14 for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier with qualifier: null
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier@632aa1a3] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier] with qualifier [null]
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier@632aa1a3 for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier with qualifier: null
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider dataSourceBeanProvider
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: dataSourceBeanProvider
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource T
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.974 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@3b582111] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.974 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSourceResolver dataSourceResolver
15:04:13.974 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.springframework.jdbc.datasource.DataSourceTransactionManager
15:04:13.975 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.springframework.jdbc.datasource.DataSourceTransactionManager is not present
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.transaction.jdbc.DelegatingDataSourceResolver null Definition: io.micronaut.transaction.jdbc.DelegatingDataSourceResolver
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.transaction.jdbc.DelegatingDataSourceResolver] for type: dataSourceResolver
15:04:13.975 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.transaction.jdbc.DelegatingDataSourceResolver@12365c88] from definition [Definition: io.micronaut.transaction.jdbc.DelegatingDataSourceResolver] with qualifier [null]
15:04:13.975 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier@6105f8a3] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier] with qualifier [null]
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier@6105f8a3 for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier with qualifier: null
15:04:13.975 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class io.micronaut.context.annotation.Prototype
15:04:13.975 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class io.micronaut.context.annotation.Prototype
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: BeanProvider provider
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   interface io.micronaut.context.BeanProvider null io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] for type: provider
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: HibernateCurrentSessionContextClassProvider T
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: HibernateCurrentSessionContextClassProvider T
15:04:13.975 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.inject.provider.BeanProviderDefinition$1@5710768a] from definition [io.micronaut.inject.provider.BeanProviderDefinition@127b5cc2] with qualifier [null]
15:04:13.975 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier@199e4c2b] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier] with qualifier [null]
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier@199e4c2b for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier with qualifier: null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found 4 bean registrations for type [E]
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.CurrentSessionContextClassSettingSupplier null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.DataSourceSettingSupplier null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.ValidatorFactorySettingSupplier null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.settings.internal.MicronautContainerSettingsSupplier null
15:04:13.975 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer@6e0d4a8] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer] with qualifier [null]
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found a registration BeanRegistration: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer@6e0d4a8 for candidate: Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer with qualifier: null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found 2 bean registrations for type [E]
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.SettingsConfigurer null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   Definition: io.micronaut.configuration.hibernate.jpa.conf.serviceregistry.builder.configures.internal.CompileProxyConfigurer null
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: JpaConfiguration jpaConfiguration
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.JpaConfiguration @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration
15:04:13.975 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration] for type: jpaConfiguration
15:04:13.976 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: EntityScanConfiguration entityScanConfiguration
15:04:13.976 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.JpaConfiguration$EntityScanConfiguration @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration$EntityScanConfiguration
15:04:13.976 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [entityScanConfiguration] for qualifier: @Named('default')
15:04:13.977 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration$EntityScanConfiguration] for type: @Named('default') entityScanConfiguration
15:04:13.977 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.JpaConfiguration$EntityScanConfiguration@6134ac4a] from definition [Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration$EntityScanConfiguration] with qualifier [@Named('default')]
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: PhysicalNamingStrategy physicalNamingStrategy
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy null Definition: io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy] for type: physicalNamingStrategy
15:04:13.978 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy@3b809711] from definition [Definition: io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy] with qualifier [null]
15:04:13.978 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.data.hibernate.naming.PhysicalNamingStrategyConfiguration@3b0f7d9d] from definition [Definition: io.micronaut.data.hibernate.naming.PhysicalNamingStrategyConfiguration] with qualifier [null]
15:04:13.978 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.JpaConfiguration@236ab296] from definition [Definition: io.micronaut.configuration.hibernate.jpa.JpaConfiguration] with qualifier [@Named('default')]
15:04:13.978 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory@5c84624f] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] with qualifier [null]
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryBuilder sessionFactoryBuilder
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Metadata
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MetadataSources
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ServiceRegistry
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource
15:04:13.978 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.service.ServiceRegistry @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   class org.hibernate.boot.MetadataSources @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.boot.Metadata @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.979 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.boot.SessionFactoryBuilder @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [sessionFactoryBuilder] for qualifier: @Named('default')
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: @Named('default') sessionFactoryBuilder
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory null Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: sessionFactoryPerDataSourceFactory
15:04:13.979 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: Metadata metadata
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MetadataSources
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ServiceRegistry
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.service.ServiceRegistry @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   class org.hibernate.boot.MetadataSources @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.boot.Metadata @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [metadata] for qualifier: @Named('default')
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: @Named('default') metadata
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: MetadataSources metadataSources
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ServiceRegistry
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.980 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.980 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.service.ServiceRegistry @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.981 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.981 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext -   class org.hibernate.boot.MetadataSources @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [metadataSources] for qualifier: @Named('default')
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: @Named('default') metadataSources
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: ServiceRegistry serviceRegistry
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.981 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
15:04:13.981 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder is not present
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext -   interface org.hibernate.service.ServiceRegistry @Named('default') Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [serviceRegistry] for qualifier: @Named('default')
15:04:13.981 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] for type: @Named('default') serviceRegistry
15:04:13.983 [main] INFO  org.hibernate.Version - HHH000412: Hibernate ORM core version 6.2.0.Final
15:04:13.984 [main] DEBUG org.hibernate.cfg.Environment - HHH000206: hibernate.properties not found
15:04:13.985 [main] INFO  org.hibernate.cfg.Environment - HHH000406: Using bytecode reflection optimizer
15:04:13.994 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: HibernateCurrentSessionContextClassProvider T
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - No bean candidates found for type: HibernateCurrentSessionContextClassProvider T
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DataSource T
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext -   interface javax.sql.DataSource @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [T] for qualifier: @Named('default')
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory] for type: @Named('default') T
15:04:13.995 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceFactory
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceFactory null Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory] for type: datasourceFactory
15:04:13.996 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.jdbc.hikari.DatasourceFactory@4287d447] from definition [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory] with qualifier [null]
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext - Finding candidate beans for type: DatasourceConfiguration datasourceConfiguration
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext -   class io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration @Named('default') Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext - Qualifying bean [datasourceConfiguration] for qualifier: @Named('default')
15:04:13.996 [main] DEBUG i.m.context.DefaultBeanContext - Found concrete candidate [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration] for type: @Named('default') datasourceConfiguration
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.h2.Driver
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.h2.Driver is not present
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.apache.derby.jdbc.EmbeddedDriver
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.apache.derby.jdbc.EmbeddedDriver is not present
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class org.hsqldb.jdbc.JDBCDriver
15:04:13.997 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Class org.hsqldb.jdbc.JDBCDriver is not present

[test-resources-service] 15:04:22.212 [default-nioEventLoopGroup-1-2] INFO  tc.mysql:latest - Container mysql:latest started in PT7.420416S
[test-resources-service] 15:04:22.212 [default-nioEventLoopGroup-1-2] INFO  tc.mysql:latest - Container is started (JDBC URL: jdbc:mysql://localhost:63420/test)

> Task :run
15:04:22.213 [main] DEBUG i.m.t.c.TestResourcesClientPropertyExpressionResolver - Resolved expression 'datasources.default.password' to 'test'
15:04:22.218 [main] DEBUG i.m.t.c.TestResourcesClientPropertyExpressionResolver - Resolved expression 'datasources.default.username' to 'test'
15:04:22.219 [main] DEBUG com.zaxxer.hikari.HikariConfig - Driver class com.mysql.cj.jdbc.Driver found in Thread context class loader jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7
15:04:22.224 [main] DEBUG i.m.t.c.TestResourcesClientPropertyExpressionResolver - Resolved expression 'datasources.default.url' to 'jdbc:mysql://localhost:63420/test'
15:04:22.224 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration@6f815e7f] from definition [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration] with qualifier [@Named('default')]
15:04:22.226 [main] DEBUG com.zaxxer.hikari.HikariConfig - HikariPool-1 - configuration:
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - allowPoolSuspension.............false
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - autoCommit......................true
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - catalog.........................none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - connectionInitSql...............none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - connectionTestQuery............."SELECT 1"
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - connectionTimeout...............30000
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - dataSource......................none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - dataSourceClassName.............none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - dataSourceJNDI..................none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - dataSourceProperties............{password=<masked>}
15:04:22.228 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Attempting to dynamically load class com.mysql.cj.jdbc.Driver
15:04:22.228 [main] DEBUG i.micronaut.core.reflect.ClassUtils - Successfully loaded class com.mysql.cj.jdbc.Driver
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - driverClassName................."com.mysql.cj.jdbc.Driver"
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - exceptionOverrideClassName......none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - healthCheckProperties...........{}
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - healthCheckRegistry.............none
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - idleTimeout.....................600000
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - initializationFailTimeout.......1
15:04:22.228 [main] DEBUG com.zaxxer.hikari.HikariConfig - isolateInternalQueries..........false
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - jdbcUrl.........................jdbc:mysql://localhost:63420/test
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - keepaliveTime...................0
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - leakDetectionThreshold..........0
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - maxLifetime.....................1800000
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - maximumPoolSize.................10
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - metricRegistry..................none
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - metricsTrackerFactory...........none
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - minimumIdle.....................10
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - password........................<masked>
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - poolName........................"HikariPool-1"
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - readOnly........................false
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - registerMbeans..................false
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - scheduledExecutor...............none
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - schema..........................none
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - threadFactory...................internal
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - transactionIsolation............default
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - username........................"test"
15:04:22.229 [main] DEBUG com.zaxxer.hikari.HikariConfig - validationTimeout...............5000
15:04:22.230 [main] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
15:04:22.421 [main] INFO  com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@29528a22
15:04:22.422 [main] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
15:04:22.422 [main] DEBUG i.m.c.jdbc.hikari.DatasourceFactory - Could not wire metrics to HikariCP as there is no class of type MeterRegistry on the classpath, io.micronaut.configuration:micrometer-core library missing.
15:04:22.422 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.transaction.jdbc.TransactionAwareDataSource@40298285] from definition [Definition: io.micronaut.transaction.jdbc.TransactionAwareDataSource] with qualifier [null]
15:04:22.423 [main] DEBUG io.micronaut.context.lifecycle - Created bean [io.micronaut.transaction.jdbc.TransactionAwareDataSource$DataSourceProxy@1cb37ee4] from definition [Definition: io.micronaut.configuration.jdbc.hikari.DatasourceFactory] with qualifier [@Named('default')]
15:04:22.426 [main] DEBUG io.micronaut.context.lifecycle - Created bean [org.hibernate.boot.registry.internal.StandardServiceRegistryImpl@724c5cbe] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] with qualifier [@Named('default')]
15:04:22.427 [main] DEBUG io.micronaut.context.lifecycle - Created bean [org.hibernate.boot.MetadataSources@de77232] from definition [Definition: io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory] with qualifier [@Named('default')]
15:04:22.430 [main] ERROR io.micronaut.runtime.Micronaut - Error starting Micronaut server: Bean definition [org.hibernate.SessionFactory] could not be loaded: Error instantiating bean of type  [org.hibernate.boot.SessionFactoryBuilder]

Message: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
Path Taken: SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder(SessionFactoryBuilder sessionFactoryBuilder) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([SessionFactoryBuilder sessionFactoryBuilder]) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([Metadata metadata],JpaConfiguration jpaConfiguration,String name)
io.micronaut.context.exceptions.BeanInstantiationException: Bean definition [org.hibernate.SessionFactory] could not be loaded: Error instantiating bean of type  [org.hibernate.boot.SessionFactoryBuilder]

Message: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
Path Taken: SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder(SessionFactoryBuilder sessionFactoryBuilder) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([SessionFactoryBuilder sessionFactoryBuilder]) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([Metadata metadata],JpaConfiguration jpaConfiguration,String name)
at io.micronaut.context.DefaultBeanContext.initializeContext(DefaultBeanContext.java:1984)
at io.micronaut.context.DefaultApplicationContext.initializeContext(DefaultApplicationContext.java:279)
at io.micronaut.context.DefaultBeanContext.readAllBeanDefinitionClasses(DefaultBeanContext.java:3355)
at io.micronaut.context.DefaultBeanContext.finalizeConfiguration(DefaultBeanContext.java:3709)
at io.micronaut.context.DefaultBeanContext.start(DefaultBeanContext.java:347)
at io.micronaut.context.DefaultApplicationContext.start(DefaultApplicationContext.java:191)
at io.micronaut.runtime.Micronaut.start(Micronaut.java:75)
at io.micronaut.runtime.Micronaut.run(Micronaut.java:324)
at io.micronaut.runtime.Micronaut.run(Micronaut.java:299)
at com.example.Application.main(Application.kt:13)
Caused by: io.micronaut.context.exceptions.BeanInstantiationException: Error instantiating bean of type  [org.hibernate.boot.SessionFactoryBuilder]

Message: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
Path Taken: SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder(SessionFactoryBuilder sessionFactoryBuilder) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([SessionFactoryBuilder sessionFactoryBuilder]) --> SessionFactoryPerDataSourceFactory.buildHibernateSessionFactoryBuilder([Metadata metadata],JpaConfiguration jpaConfiguration,String name)
at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2337)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2934)
at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2745)
at io.micronaut.context.DefaultBeanContext.getBean(DefaultBeanContext.java:1698)
at io.micronaut.context.AbstractBeanResolutionContext.getBean(AbstractBeanResolutionContext.java:89)
at io.micronaut.context.AbstractInitializableBeanDefinition.resolveBean(AbstractInitializableBeanDefinition.java:2303)
at io.micronaut.context.AbstractInitializableBeanDefinition.getBeanForConstructorArgument(AbstractInitializableBeanDefinition.java:1465)
at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildHibernateSessionFactoryBuilder3$Definition.doInstantiate(Unknown Source)
at io.micronaut.context.AbstractInitializableBeanDefinition.instantiate(AbstractInitializableBeanDefinition.java:894)
at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:168)
at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2934)
at io.micronaut.context.DefaultBeanContext.resolveBeanRegistration(DefaultBeanContext.java:2745)
at io.micronaut.context.DefaultBeanContext.getBean(DefaultBeanContext.java:1698)
at io.micronaut.context.AbstractBeanResolutionContext.getBean(AbstractBeanResolutionContext.java:89)
at io.micronaut.context.AbstractInitializableBeanDefinition.resolveBean(AbstractInitializableBeanDefinition.java:2303)
at io.micronaut.context.AbstractInitializableBeanDefinition.getBeanForConstructorArgument(AbstractInitializableBeanDefinition.java:1465)
at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildHibernateSessionFactoryBuilder4$Definition.instantiate(Unknown Source)
at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:171)
at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2285)
at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:2297)
at io.micronaut.context.DefaultBeanContext.createRegistration(DefaultBeanContext.java:3071)
at io.micronaut.context.SingletonScope.getOrCreate(SingletonScope.java:81)
at io.micronaut.context.DefaultBeanContext.findOrCreateSingletonBeanRegistration(DefaultBeanContext.java:2973)
at io.micronaut.context.DefaultBeanContext.initializeEagerBean(DefaultBeanContext.java:2682)
at io.micronaut.context.DefaultBeanContext.initializeContext(DefaultBeanContext.java:1978)
... 9 common frames omitted
Caused by: io.micronaut.context.exceptions.ConfigurationException: Entities not found for JPA configuration: 'default' within packages [com.example.model]. Check that you have correctly specified a package containing JPA entities within the "jpa.default.entity-scan.packages" property in your application configuration and that those entities are either compiled with Micronaut or a build time index produced with @Introspected(packages="foo.bar", includedAnnotations=Entity.class) declared on your Application class
at io.micronaut.configuration.hibernate.jpa.conf.AbstractHibernateFactory.buildMetadata(AbstractHibernateFactory.java:90)
at io.micronaut.configuration.hibernate.jpa.conf.SessionFactoryPerDataSourceFactory.buildMetadata(SessionFactoryPerDataSourceFactory.java:88)
at io.micronaut.configuration.hibernate.jpa.conf.$SessionFactoryPerDataSourceFactory$BuildMetadata2$Definition.doInstantiate(Unknown Source)
at io.micronaut.context.AbstractInitializableBeanDefinition.instantiate(AbstractInitializableBeanDefinition.java:894)
at io.micronaut.context.BeanDefinitionDelegate.instantiate(BeanDefinitionDelegate.java:168)
at io.micronaut.context.DefaultBeanContext.resolveByBeanFactory(DefaultBeanContext.java:2322)
... 45 common frames omitted
</pre>
</details>

### Diagnosis notes

Debugging at runtime reveals that when the `AbstractHibernateFactory` scans for introspection references marked with
`Entity`, it finds no entries. KSP clearly finds the class, as it generates the `ReflectConfig` class for it:

<details>
<summary>Click here to see generated KSP `ReflectConfig`</summary>
<pre>
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.model;

import io.micronaut.core.annotation.AnnotationClassValue;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.Generated;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.core.annotation.TypeHint.AccessType;
import io.micronaut.core.graal.GraalReflectionConfigurer;
import io.micronaut.inject.annotation.AnnotationMetadataSupport;
import io.micronaut.inject.annotation.DefaultAnnotationMetadata;
import java.util.Map;

// $FF: synthetic class
@Generated(
service = "io/micronaut/core/graal/GraalReflectionConfigurer"
)
public final class $ExampleModel$ReflectConfig implements GraalReflectionConfigurer {
public static final AnnotationMetadata $ANNOTATION_METADATA;

    static {
        Map var0;
        $ANNOTATION_METADATA = new DefaultAnnotationMetadata(Map.of(), Map.of(), Map.of(), Map.of("io.micronaut.core.annotation.ReflectionConfig$ReflectionConfigList", Map.of("value", new AnnotationValue[]{new AnnotationValue("io.micronaut.core.annotation.ReflectionConfig", Map.of("accessType", new TypeHint.AccessType[]{AccessType.ALL_DECLARED_CONSTRUCTORS, AccessType.ALL_DECLARED_FIELDS, AccessType.ALL_PUBLIC_METHODS}, "type", new AnnotationClassValue[]{$micronaut_load_class_value_0()}), var0 = AnnotationMetadataSupport.getDefaultValues("io.micronaut.core.annotation.ReflectionConfig")), new AnnotationValue("io.micronaut.core.annotation.ReflectionConfig", Map.of("accessType", new TypeHint.AccessType[0], "type", new AnnotationClassValue[]{$micronaut_load_class_value_1()}), var0)})), Map.of(), false, false);
    }

    public $ExampleModel$ReflectConfig() {
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return $ANNOTATION_METADATA;
    }
}
</pre>
</details>

The error message recommends marking the `Application` with `@Introspected`, so I have done so, which produces the
following `InspectionRef` for `Application`:
<details>
<summary>Click here to see `IntrospectionRef` for `Application`</summary>
<pre>
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example;

import com.example..Application.IntrospectionRef0;
import com.example.model.ExampleModel;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.Generated;
import io.micronaut.core.reflect.ReflectionUtils;
import io.micronaut.core.type.Argument;
import io.micronaut.inject.annotation.DefaultAnnotationMetadata;
import io.micronaut.inject.beans.AbstractInitializableBeanIntrospection;
import java.lang.reflect.Method;
import java.util.Map;

// $FF: synthetic class
@Generated
final class $com_example_model_ExampleModel$Introspection extends AbstractInitializableBeanIntrospection {
private static final Argument[] $CONSTRUCTOR_ARGUMENTS;
private static final AbstractInitializableBeanIntrospection.BeanPropertyRef[] $PROPERTIES_REFERENCES;

    static {
        $CONSTRUCTOR_ARGUMENTS = new Argument[]{Argument.of(Long.TYPE, "id"), Argument.of(String.class, "message")};
        $PROPERTIES_REFERENCES = new AbstractInitializableBeanIntrospection.BeanPropertyRef[]{new AbstractInitializableBeanIntrospection.BeanPropertyRef(Argument.of(Long.TYPE, "id", new DefaultAnnotationMetadata(Map.of("io.micronaut.data.annotation.GeneratedValue", Map.of(), "io.micronaut.data.annotation.Id", Map.of(), "io.micronaut.data.annotation.MappedProperty", Map.of("value", "id"), "jakarta.persistence.Column", Map.of("name", "id"), "jakarta.persistence.GeneratedValue", Map.of(), "jakarta.persistence.Id", Map.of(), "javax.annotation.Nullable", Map.of()), Map.of(), Map.of(), Map.of("io.micronaut.data.annotation.GeneratedValue", Map.of(), "io.micronaut.data.annotation.Id", Map.of(), "io.micronaut.data.annotation.MappedProperty", Map.of("value", "id"), "jakarta.persistence.Column", Map.of("name", "id"), "jakarta.persistence.GeneratedValue", Map.of(), "jakarta.persistence.Id", Map.of(), "javax.annotation.Nullable", Map.of()), Map.of(), false, false), (Argument[])null), 0, -1, 1, true, true), new AbstractInitializableBeanIntrospection.BeanPropertyRef(Argument.of(String.class, "message", new DefaultAnnotationMetadata(Map.of("io.micronaut.data.annotation.MappedProperty", Map.of("value", "message"), "jakarta.persistence.Column", Map.of("name", "message")), Map.of(), Map.of(), Map.of("io.micronaut.data.annotation.MappedProperty", Map.of("value", "message"), "jakarta.persistence.Column", Map.of("name", "message")), Map.of(), false, false), (Argument[])null), 2, -1, 3, true, true)};
    }

    public $com_example_model_ExampleModel$Introspection() {
        super(ExampleModel.class, IntrospectionRef0.$ANNOTATION_METADATA, (AnnotationMetadata)null, $CONSTRUCTOR_ARGUMENTS, $PROPERTIES_REFERENCES, (AbstractInitializableBeanIntrospection.BeanMethodRef[])null);
    }

    protected final Object dispatchOne(int var1, Object var2, Object var3) {
        switch (var1) {
            case 0:
                return ((ExampleModel)var2).getId();
            case 1:
                ExampleModel var4 = (ExampleModel)var2;
                return new ExampleModel((Long)var3, var4.getMessage());
            case 2:
                return ((ExampleModel)var2).getMessage();
            case 3:
                ExampleModel var5 = (ExampleModel)var2;
                return new ExampleModel(var5.getId(), (String)var3);
            default:
                throw this.unknownDispatchAtIndexException(var1);
        }
    }

    protected final Method getTargetMethodByIndex(int var1) {
        switch (var1) {
            case 0:
                return ReflectionUtils.getRequiredMethod(ExampleModel.class, "getId", ReflectionUtils.EMPTY_CLASS_ARRAY);
            case 1:
            default:
                throw this.unknownDispatchAtIndexException(var1);
            case 2:
                return ReflectionUtils.getRequiredMethod(ExampleModel.class, "getMessage", ReflectionUtils.EMPTY_CLASS_ARRAY);
        }
    }

    public Object instantiateInternal(Object[] var1) {
        return new ExampleModel((Long)var1[0], (String)var1[1]);
    }
}
</pre>
</details>

I am not sure if this `IntrospectionRef` should be a peer class, as it is, to `Application`, or within the same package
path as the model. Even so, this `InspectionRef` does not mention `Entity`:

<details>
<summary>Generated `InspectionRef`</summary>
<pre>
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example;

import com.example..Application.IntrospectionRef0;
import com.example.model.ExampleModel;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.Generated;
import io.micronaut.core.reflect.ReflectionUtils;
import io.micronaut.core.type.Argument;
import io.micronaut.inject.annotation.DefaultAnnotationMetadata;
import io.micronaut.inject.beans.AbstractInitializableBeanIntrospection;
import java.lang.reflect.Method;
import java.util.Map;

// $FF: synthetic class
@Generated
final class $com_example_model_ExampleModel$Introspection extends AbstractInitializableBeanIntrospection {
private static final Argument[] $CONSTRUCTOR_ARGUMENTS;
private static final AbstractInitializableBeanIntrospection.BeanPropertyRef[] $PROPERTIES_REFERENCES;

    static {
        $CONSTRUCTOR_ARGUMENTS = new Argument[]{Argument.of(Long.TYPE, "id"), Argument.of(String.class, "message")};
        $PROPERTIES_REFERENCES = new AbstractInitializableBeanIntrospection.BeanPropertyRef[]{new AbstractInitializableBeanIntrospection.BeanPropertyRef(Argument.of(Long.TYPE, "id", new DefaultAnnotationMetadata(Map.of("io.micronaut.data.annotation.GeneratedValue", Map.of(), "io.micronaut.data.annotation.Id", Map.of(), "io.micronaut.data.annotation.MappedProperty", Map.of("value", "id"), "jakarta.persistence.Column", Map.of("name", "id"), "jakarta.persistence.GeneratedValue", Map.of(), "jakarta.persistence.Id", Map.of(), "javax.annotation.Nullable", Map.of()), Map.of(), Map.of(), Map.of("io.micronaut.data.annotation.GeneratedValue", Map.of(), "io.micronaut.data.annotation.Id", Map.of(), "io.micronaut.data.annotation.MappedProperty", Map.of("value", "id"), "jakarta.persistence.Column", Map.of("name", "id"), "jakarta.persistence.GeneratedValue", Map.of(), "jakarta.persistence.Id", Map.of(), "javax.annotation.Nullable", Map.of()), Map.of(), false, false), (Argument[])null), 0, -1, 1, true, true), new AbstractInitializableBeanIntrospection.BeanPropertyRef(Argument.of(String.class, "message", new DefaultAnnotationMetadata(Map.of("io.micronaut.data.annotation.MappedProperty", Map.of("value", "message"), "jakarta.persistence.Column", Map.of("name", "message")), Map.of(), Map.of(), Map.of("io.micronaut.data.annotation.MappedProperty", Map.of("value", "message"), "jakarta.persistence.Column", Map.of("name", "message")), Map.of(), false, false), (Argument[])null), 2, -1, 3, true, true)};
    }

    public $com_example_model_ExampleModel$Introspection() {
        super(ExampleModel.class, IntrospectionRef0.$ANNOTATION_METADATA, (AnnotationMetadata)null, $CONSTRUCTOR_ARGUMENTS, $PROPERTIES_REFERENCES, (AbstractInitializableBeanIntrospection.BeanMethodRef[])null);
    }

    protected final Object dispatchOne(int var1, Object var2, Object var3) {
        switch (var1) {
            case 0:
                return ((ExampleModel)var2).getId();
            case 1:
                ExampleModel var4 = (ExampleModel)var2;
                return new ExampleModel((Long)var3, var4.getMessage());
            case 2:
                return ((ExampleModel)var2).getMessage();
            case 3:
                ExampleModel var5 = (ExampleModel)var2;
                return new ExampleModel(var5.getId(), (String)var3);
            default:
                throw this.unknownDispatchAtIndexException(var1);
        }
    }

    protected final Method getTargetMethodByIndex(int var1) {
        switch (var1) {
            case 0:
                return ReflectionUtils.getRequiredMethod(ExampleModel.class, "getId", ReflectionUtils.EMPTY_CLASS_ARRAY);
            case 1:
            default:
                throw this.unknownDispatchAtIndexException(var1);
            case 2:
                return ReflectionUtils.getRequiredMethod(ExampleModel.class, "getMessage", ReflectionUtils.EMPTY_CLASS_ARRAY);
        }
    }

    public Object instantiateInternal(Object[] var1) {
        return new ExampleModel((Long)var1[0], (String)var1[1]);
    }
}
</pre>
</details>

Marking the `com.example.model.ExampleModel` class directly with `@Introspected` doesn't yield any `IntrospectionRef`
at all.

### Version selection

- **`4.0.0-M2` has a bug with KSP which results in the following exception:**
<details>
<summary>Click to see exception</summary>
<pre>
> Task :kspKotlin FAILED
e: [ksp] Originating element: ExampleModel
e: [ksp] .../data-hibernate-reproducer/src/main/kotlin/com/example/model/ExampleModel.kt:9: I/O error occurred during class generation: /Volumes/VAULTROOM/data-hibernate-reproducer/build/generated/ksp/main/classes/com/example/model/$ExampleModel$ReflectConfig.class
e: io.micronaut.inject.writer.ClassGenerationException: I/O error occurred during class generation: /Volumes/VAULTROOM/data-hibernate-reproducer/build/generated/ksp/main/classes/com/example/model/$ExampleModel$ReflectConfig.class
        at io.micronaut.graal.reflect.GraalTypeElementVisitor.visitClass(GraalTypeElementVisitor.java:220)
        at io.micronaut.kotlin.processing.visitor.TypeElementSymbolProcessor$ElementVisitor.visitClassDeclaration(TypeElementSymbolProcessor.kt:242)
        at com.google.devtools.ksp.symbol.impl.kotlin.KSClassDeclarationImpl.accept(KSClassDeclarationImpl.kt:136)
        at io.micronaut.kotlin.processing.visitor.TypeElementSymbolProcessor.process(TypeElementSymbolProcessor.kt:118)
        at com.google.devtools.ksp.AbstractKotlinSymbolProcessingExtension$doAnalysis$6$1.invoke(KotlinSymbolProcessingExtension.kt:291)
        at com.google.devtools.ksp.AbstractKotlinSymbolProcessingExtension$doAnalysis$6$1.invoke(KotlinSymbolProcessingExtension.kt:289)
        at com.google.devtools.ksp.AbstractKotlinSymbolProcessingExtension.handleException(KotlinSymbolProcessingExtension.kt:394)
        at com.google.devtools.ksp.AbstractKotlinSymbolProcessingExtension.doAnalysis(KotlinSymbolProcessingExtension.kt:289)
        at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration(TopDownAnalyzerFacadeForJVM.kt:123)
        at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration$default(TopDownAnalyzerFacadeForJVM.kt:99)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:257)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:42)
        at org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport(AnalyzerWithCompilerReport.kt:115)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyze(KotlinToJVMBytecodeCompiler.kt:248)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.repeatAnalysisIfNeeded(KotlinToJVMBytecodeCompiler.kt:182)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli(KotlinToJVMBytecodeCompiler.kt:88)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli$default(KotlinToJVMBytecodeCompiler.kt:47)
        at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:168)
        at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:53)
        at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:100)
        at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:46)
        at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:101)
        at org.jetbrains.kotlin.daemon.CompileServiceImpl.compile(CompileServiceImpl.kt:1486)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:578)
        at java.rmi/sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:360)
        at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:200)
        at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:197)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:712)
        at java.rmi/sun.rmi.transport.Transport.serviceCall(Transport.java:196)
        at java.rmi/sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:598)
        at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:844)
        at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0(TCPTransport.java:721)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
        at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:720)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
        at java.base/java.lang.Thread.run(Thread.java:1589)
Caused by: kotlin.io.FileAlreadyExistsException: .../data-hibernate-reproducer/build/generated/ksp/main/classes/com/example/model/$ExampleModel$ReflectConfig.class
        at com.google.devtools.ksp.processing.impl.CodeGeneratorImpl.createNewFile(CodeGeneratorImpl.kt:117)
        at com.google.devtools.ksp.processing.impl.CodeGeneratorImpl.createNewFile(CodeGeneratorImpl.kt:67)
        at io.micronaut.kotlin.processing.KotlinOutputVisitor.visitClass(KotlinOutputVisitor.kt:34)
        at io.micronaut.kotlin.processing.visitor.KotlinVisitorContext.visitClass(KotlinVisitorContext.kt:135)
        at io.micronaut.graal.reflect.GraalReflectionMetadataWriter.accept(GraalReflectionMetadataWriter.java:54)
        at io.micronaut.graal.reflect.GraalTypeElementVisitor.visitClass(GraalTypeElementVisitor.java:218)
</pre>
</details>

- **`4.0.0-M3` does not have a published BOM catalog, so it cannot be used in full, but plugins are available at this
  version, so I use them.**

- **`4.0.0-M4` does not have a published BOM catalog, or plugins, but does have several Micronaut libraries.**


## Reproduction environment

OS:
```
macOS Ventura 13.3.1
M2 Max (16-inch, 2023)
```

JVM:
```
java version "19.0.2" 2023-01-17
Java(TM) SE Runtime Environment GraalVM EE 22.3.1 (build 19.0.2+7-jvmci-22.3-b11)
Java HotSpot(TM) 64-Bit Server VM GraalVM EE 22.3.1 (build 19.0.2+7-jvmci-22.3-b11, mixed mode, sharing)
```
