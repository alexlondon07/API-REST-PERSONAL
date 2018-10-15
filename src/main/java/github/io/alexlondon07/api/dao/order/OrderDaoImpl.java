package github.io.alexlondon07.api.dao.order;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.dao.AbstractSession;
import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Order;

@Repository
@Transactional
public class OrderDaoImpl  extends AbstractSession  implements OrderDao{

	@Override
	public void saveOrder(Order order) {
		getSession().persist(order);
	}

	@Override
	public void updateOrder(Order order) {
		getSession().update(order);
	}

	@Override
	public void deleteOrder(Long id) {
		if(id!=null && id > 0){
			Order order = findById(id);
			getSession().delete(order);
		}
	}

	@Override
	public Order findById(Long id) {
		return getSession().get(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAllOrders() {
		return getSession().createQuery("from Order").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findByIdeClient(Long id) {
		return (List<Order>) getSession().createQuery("from Order where ide_client = :ide_client")
										.setParameter("ide_client", id)
										.uniqueResult();
	}

}
