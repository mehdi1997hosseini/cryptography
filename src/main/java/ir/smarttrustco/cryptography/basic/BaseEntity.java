package ir.smarttrustco.cryptography.basic;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity<P extends Number> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private P id;
    @CreationTimestamp
    private Date createdAt;
    private Boolean isDeleted = false;
    @Version
    private Integer version = 1;
}
