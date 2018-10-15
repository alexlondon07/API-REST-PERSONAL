package github.io.alexlondon07.api.services.order;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.order.OrderDao;
import github.io.alexlondon07.api.models.Order;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService  {
	
	@Autowired
	private OrderDao orderDao;
	

	@Override
	public void saveOrder(Order order) {
		orderDao.saveOrder(order);
	}

	@Override
	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	@Override
	public void deleteOrder(Long id) {
		orderDao.deleteOrder(id);
	}

	@Override
	public Order findById(Long id) {
		return orderDao.findById(id);
	}

	@Override
	public List<Order> findAllOrders() {
		return orderDao.findAllOrders();
	}

	@Override
	public List<Order> findByIdeClient(Long id) {
		return orderDao.findByIdeClient(id);
	}
	
}
