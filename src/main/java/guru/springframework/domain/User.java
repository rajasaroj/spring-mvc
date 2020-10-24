package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    Integer version;

    String userName;

    @Transient
    String password;

    String encrptedPassword;

    public String getEncrptedPassword() {
        return encrptedPassword;
    }

    public void setEncrptedPassword(String encrptedPassword) {
        this.encrptedPassword = encrptedPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", encrptedPassword='" + encrptedPassword + '\'' +
                '}';
    }
}
