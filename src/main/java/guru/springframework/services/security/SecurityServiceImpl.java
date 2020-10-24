package guru.springframework.services.security;


import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements EncryptionService {

    private StrongPasswordEncryptor strongPasswordEncryptor;

    @Autowired
    public void setStrongPasswordEncryptor(StrongPasswordEncryptor strongPasswordEncryptor) {
        this.strongPasswordEncryptor = strongPasswordEncryptor;
    }

    @Override
    public String encryptPassword(String password) {
        return strongPasswordEncryptor.encryptPassword(password);
    }

    @Override
    public boolean checkEncrption(String password, String encryptedPassword) {

        if ( !(password.isEmpty() && encryptedPassword.isEmpty())) {
         return strongPasswordEncryptor.checkPassword(password, encryptedPassword);
        }

        return false;
    }
}
