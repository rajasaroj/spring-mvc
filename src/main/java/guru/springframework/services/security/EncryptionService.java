package guru.springframework.services.security;

public interface EncryptionService {
    public String encryptPassword(String password);
    public boolean checkEncrption(String password, String encryptedPassword);
}
