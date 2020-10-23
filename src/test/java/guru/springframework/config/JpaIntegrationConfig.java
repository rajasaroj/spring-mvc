package guru.springframework.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/* @Configuartion tells the spring boot that this class is the configuaration class
 * @EnableAutoConfiguration ask the the spring boot to auto configure it self
 * @ComponentScan tell springboot to load the beans from heirarchy of all the packages inside "guru.springframework"
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("guru.springframework")
public class JpaIntegrationConfig {
}
