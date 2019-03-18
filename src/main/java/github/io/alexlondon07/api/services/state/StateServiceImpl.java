package github.io.alexlondon07.api.services.state;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.state.StateDao;
import github.io.alexlondon07.api.models.State;

@Service("stateService")
@Transactional
public class StateServiceImpl implements StateService{

	@Autowired
	private StateDao stateDao;
	
	@Override
	public void saveState(State state) {
		stateDao.saveState(state);
	}

	@Override
	public void updateState(State state) {
		stateDao.updateState(state);
	}

	@Override
	public void deleteState(Long id) {
		stateDao.deleteState(id);
	}

	@Override
	public State findById(Long id) {
		return stateDao.findById(id);
	}

	@Override
	public State findByName(String name) {
		return stateDao.findByName(name);
	}

	@Override
	public List<State> findAllStates() {
		return stateDao.findAllStates();
	}

	@Override
	public boolean isStateExist(State state) {
		return stateDao.isStateExist(state);
	}
}
