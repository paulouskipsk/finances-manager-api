package br.edu.uftpr.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.edu.uftpr.model.entity.Revenue;
import br.edu.uftpr.model.repository.RevenueRepository;

@Service
public class RevenueService {
	@Autowired
	RevenueRepository revenueRepository;

	public Optional<Revenue> findById(Long id) {
		return revenueRepository.findById(id);
	}

	public List<Revenue> findAll() {
		return revenueRepository.findAll();
	}

	public Page<Revenue> findAllPagination(
			@PageableDefault(page = 0, size = 5, direction = Direction.ASC) Pageable pageable) {

		Page<Revenue> revenues = revenueRepository.findAll(pageable);
		return revenues;
	}

	public Revenue save(Revenue revenue) throws DataIntegrityViolationException {
		return revenueRepository.save(revenue);
	}

	public List<Revenue> saveAll(List<Revenue> revenues) throws DataIntegrityViolationException {
		return revenueRepository.saveAll(revenues);
	}

	public void delete(Long id) throws DataIntegrityViolationException {
		revenueRepository.deleteById(id);
	}

	public List<Revenue> findByDescriptionWith(String description) {
		return revenueRepository.findByDescriptionWith(description);
	}

}
