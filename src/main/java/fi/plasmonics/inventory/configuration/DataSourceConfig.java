package fi.plasmonics.inventory.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() throws URISyntaxException {
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
