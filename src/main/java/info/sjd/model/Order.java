package info.sjd.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = Item.class)
    private Item item;
    @ManyToOne(targetEntity = Cart.class)
    private Cart cart;
    private Integer amount;

}
