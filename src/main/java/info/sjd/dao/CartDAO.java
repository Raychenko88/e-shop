package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartDAO extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT * FROM carts WHERE user_id=:userId AND creation_time >=:timeDown AND creation_time <=:timeUp",
            nativeQuery = true)
    List<Cart> getAllByUserAndPeriod(Integer userId, Long timeDown, Long timeUp);

    @Query(value = "SELECT * FROM carts WHERE user_id =:id AND closed='0'", nativeQuery = true)
    Cart getByUserAndOpenStatus(Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE carts SET closed=:closedParam WHERE id =:idParam", nativeQuery = true)
    void updateStatus(@Param("idParam") Integer idParam, @Param("closedParam") Integer closedParam);
}
