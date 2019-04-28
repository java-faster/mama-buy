package com.room.datasource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.room.dao.ledger", sqlSessionTemplateRef  = "ledgerSqlSessionTemplate")
public class DataSource2Config {

    @Bean(name = "ledgerDataSource")
    @ConfigurationPropertiesBinding
    @ConfigurationProperties(prefix = "spring.second-datasource.druid")
    public DataSource ledgerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ledgerSqlSessionFactory")
    public SqlSessionFactory ledgerSqlSessionFactory(@Qualifier("ledgerDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/ledger/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "ledgerTransactionManager")
    public DataSourceTransactionManager ledgerTransactionManager(@Qualifier("ledgerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ledgerSqlSessionTemplate")
    public SqlSessionTemplate ledgerSqlSessionTemplate(@Qualifier("ledgerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
