package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "create_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}

