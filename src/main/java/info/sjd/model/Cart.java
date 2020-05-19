package info.sjd.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer closed;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @Column(name = "creation_time")
    private Long creationTime;

}
