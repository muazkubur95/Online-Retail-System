package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where i.name = :name")
    Collection<Item> findByName(@Param("name") String name);

//    @Query("SELECT c FROM CompositeItem ci  join CompositeItem.subItems  where ci.id =:id  ci.subItems WHERE c.id = :subItemID")
//    Optional<ItemResponse> findSubItemByID(int subItemID);

    @Query("select cp.subItems from Item i join CompositeItem cp where cp.id =:itemId")
    Collection<Item> findAllSubItemsByItemID(int itemId);
}
