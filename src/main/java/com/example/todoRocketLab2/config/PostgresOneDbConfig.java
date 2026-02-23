package com.example.todoRocketLab2.config;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.example.todoRocketLab2.repository.dbPg2"},
        entityManagerFactoryRef = "entityManagerFactoryDbPg2",
        transactionManagerRef = "transactionManagerDbPg2"
)
public class PostgresOneDbConfig {

    @Primary
    @Bean(name = "dataSourceDb1")
    @ConfigurationProperties(prefix = "spring.datasource.dbPg2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryDb1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceDb1") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.model.db1")
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerDb1")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDb1") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
