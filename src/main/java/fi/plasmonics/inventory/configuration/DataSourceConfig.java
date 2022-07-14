package fi.plasmonics.inventory.configuration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("!local")
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
