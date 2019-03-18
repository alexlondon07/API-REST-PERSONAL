package github.io.alexlondon07.api.dao.state;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.dao.AbstractSession;
import github.io.alexlondon07.api.models.State;

@Repository
@Transactional
public class StateDaoImpl extends AbstractSession implements StateDao {

	@Override
	public void saveState(State state) {
		
	}

	@Override
	public void updateState(State state) {
		getSession().persist(state);
	}

	@Override
	public void deleteState(Long id) {
		getSession().delete(id);
	}

	@Override
	public State findById(Long id) {
		return getSession().get(State.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> findAllStates() {
		return getSession().createQuery("from State").list();	}

	@Override
	public boolean isStateExist(State state) {
		State stateResponse = findByName(state.getName());
		boolean vBalid = false;
		
		if(stateResponse !=null) {
			if(state.getIdeState() != stateResponse.getIdeState()) {
				vBalid = true;
			}
		}
		return vBalid;
	}

	@Override
	public State findByName(String name) {
		return (State) getSession().createQuery(
				" from State where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

}
