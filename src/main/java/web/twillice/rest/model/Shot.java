package web.twillice.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter(value = AccessLevel.PACKAGE)
@Builder @NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull
    private Double x;
    @Column(nullable = false)
    @NotNull
    private Double y;
    @Column(nullable = false)
    @NotNull
    private Double r;
    @Column(nullable = false)
    private Boolean inArea;
    @Column(nullable = false)
    private String shotTime;
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private User user;

    @PrePersist
    protected void prePersist() {
        this.shotTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.inArea = checkHit();
    }

    private Boolean checkHit() {
        boolean area1_hit = x <= 0 && y <= 0 && x >= -r && y >= -r / 2;
        boolean area2_hit = x <= 0 && y >= 0 && y <= 2 * (x + r / 2);
        boolean area3_hit = x >= 0 && y >= 0 && x * x + y * y <= (r / 2) * (r / 2);
        return area1_hit || area2_hit || area3_hit;
    }
}
