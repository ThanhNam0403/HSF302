package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "computer_images")
public class ComputerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "computer_id", nullable = false)
    private ComputerDevice computer;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ComputerDevice getComputer() {
        return computer;
    }

    public void setComputer(ComputerDevice computer) {
        this.computer = computer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
}
