package guru.springframework.commonConfig;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    /* Since the StrongPasswordEncryptor is the 3rd party library and spring doesn't creates it bean by default
     * Hence we have to create its bean so that it can be injected in EncrptionServiceImpl.java class
     * this is the best practice to autowire third party libraries object via configurations.
     */
    @Bean
    public StrongPasswordEncryptor strongPasswordEncryptor() {
        return new StrongPasswordEncryptor();
    }
}
