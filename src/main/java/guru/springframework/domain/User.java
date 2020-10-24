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

    /* Now we are doing birerection asssociation where a user can also Save or update, we do this using below cascade  type
     * CascadeType PERSIST: propagates the persist operation from a parent to a child entity. When we save the User entity, the Customer entity will also get saved.
     * CascadeType.MERGE: propagates the merge operation from a parent to a child entity. this update the state of given object (That would be basically an update in parent or child object) into database persistane object.
     */
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.setUser(this);
    }

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
