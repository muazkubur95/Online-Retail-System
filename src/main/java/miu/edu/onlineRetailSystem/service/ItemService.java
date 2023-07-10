package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.CompositeItemResponse;
import miu.edu.onlineRetailSystem.contract.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ItemService {

    Page<ItemResponse> findAll(Pageable pageable);

    ItemResponse save(ItemResponse itemResponse);

    ItemResponse update(int itemId, ItemResponse itemResponse);

    Collection<ItemResponse> search(String name);

    ItemResponse remove(int itemId);

    ItemResponse findByID(int id);

    void addSubItem(int id,ItemResponse itemResponse) ;

    Collection<ItemResponse> findAllSubItemsByItemID(int itemId);

//    void removeSubItem(int itemID,int subItemID);

//    CompositeItemResponse saveCompositeItem(CompositeItemResponse compositeItemResponse);
}
