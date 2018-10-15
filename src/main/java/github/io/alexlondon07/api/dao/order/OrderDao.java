package github.io.alexlondon07.api.dao.order;

import java.util.List;

import github.io.alexlondon07.api.models.Order;

public interface OrderDao {
	void saveOrder( Order order);
	
	void updateOrder ( Order order );
	
	void deleteOrder ( Long id);
	
	Order findById( Long id);
	
	List<Order> findAllOrders();
	
	List<Order> findByIdeClient(Long id);
}
