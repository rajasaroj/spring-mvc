package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class PaymentGateway {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Version
    Integer version;

    String gateWay;

    public String getGateWay() {
        return gateWay;
    }

    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
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

    @Override
    public String toString() {
        return "PaymentGateway{" +
                "id=" + id +
                ", version=" + version +
                ", gateWay='" + gateWay + '\'' +
                '}';
    }
}
