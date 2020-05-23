package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT * FROM carts WHERE user_id=:userId AND creation_time >=:timeDown AND creation_time <=:timUp",
            nativeQuery = true)
    List<Cart> getAllByUserAndPeriod(Integer userId, Long timeDown, Long timUp);

    @Query(value = "SELECT * FROM carts WHERE user_id =:id AND closed='0'", nativeQuery = true)
    Cart getByUserAndOpenStatus(Integer id, Integer status);

    @Query(value = "UPDATE carts SET closed=:closedParam WHERE id =:idParam",nativeQuery = true)
    Cart updateStatus(Integer idParam, Integer closedParam);
}
